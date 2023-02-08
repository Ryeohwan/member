package mpti.domain.member.api.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class DeleteResponse {
    private String result;
    public void setResult(String result) {
        this.result = result;
    }

}
