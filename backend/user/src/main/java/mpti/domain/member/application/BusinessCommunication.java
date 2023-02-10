package mpti.domain.member.application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import mpti.domain.member.dto.BusinessDto;
import mpti.domain.member.entity.User;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessCommunication {

    private final Gson gson;
    private OkHttpClient client = new OkHttpClient();

    @Value("${app.business.url}")
    private String SERVER_URL;

    public BusinessDto isValidDB(Long trainerId) {

//        Map<String, String> TokenValidrequest = new HashMap<>();
//        TokenValidrequest.put("refresh_token", refreshToken);
        BusinessDto bdto = new BusinessDto();
        bdto.setTrainerId(trainerId);

        String json = gson.toJson(bdto);
        List<Long> result = new ArrayList<>();
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(requestBody)
                .build();

        Gson gson = new Gson();
        Type type = new TypeToken<List<BusinessDto>>() {}.getType();

//        List<BusinessDto> bdtos = gson.fromJson(jsonArray.toString(), type);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()){
                String st = response.body().string();
                bdto = gson.fromJson(st,BusinessDto.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return bdto;
//        return tokenDto.getState();
    }
}
