package mpti.domain.member.api.response;

import lombok.Getter;

@Getter
public class IdResponse {
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
