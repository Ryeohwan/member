package mpti.domain.member.application;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import mpti.domain.member.dto.BusinessDto;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BusinessCommunication {

    private OkHttpClient client = new OkHttpClient();

    @Value("${app.business.url}")
    private String SERVER_URL;

    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

    }).registerTypeAdapter(LocalDateTime.class,  new JsonDeserializer<LocalDateTime>(){
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
    }).create();


    public List<BusinessDto> getIds(Long id) {

        String trainer = id.toString();

        BusinessDto bdto = new BusinessDto();
        bdto.setTrainerId(id);

        String json = gson.toJson(bdto);

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(SERVER_URL+"/"+trainer)
                .build();

        Type type = new TypeToken<List<BusinessDto>>() {}.getType();

        List<BusinessDto> list = new ArrayList<>();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()){
                String st = response.body().string();
                list = gson.fromJson(st,type);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            return list;
    }
}
