package mpti.domain.member.dto;

import lombok.Getter;

@Getter
public class BusinessRequest {
    private Long id;
    private int hour;

    public void setId(Long id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
