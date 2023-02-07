package mpti.domain.member.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class DateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stopUntil;
    private Long id;

    public void setStopUntil(LocalDateTime stopUntil) {
        this.stopUntil = stopUntil;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
