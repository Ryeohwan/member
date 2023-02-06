package mpti.domain.member.api.response;

import lombok.Getter;

@Getter
public class UpdateResponse {
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }
}
