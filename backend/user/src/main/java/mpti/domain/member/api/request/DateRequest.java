package mpti.domain.member.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopUntil;
    private Long id;

    public void setStopUntil(LocalDate stopUntil) {
        this.stopUntil = stopUntil;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
