package mpti.domain.member.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BusinessDto {
    private Long id;
    private Long trainerId;
    private String trainerName;
    private Long userId;

    private String userName;
    private int year,month,day,hour;
    private String sessionId;
    private LocalDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
