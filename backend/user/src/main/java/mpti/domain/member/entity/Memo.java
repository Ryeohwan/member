package mpti.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Getter
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column()
    private String record; // 일단 날짜랑 트레이너

    @Column(name ="trainer_id")
    private Long trainerId;

    private LocalDate date;

    private int core,chest, shoulder, biceps, triceps,back,legs,aerobic;

    public void setUser(User user) {
        this.user = user;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Memo() {
    }

    @Builder
    public Memo(Long id, User user, String memo) {
        this.id = id;
        this.user = user;
        this.record = record;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public void setShoulder(int shoulder) {
        this.shoulder = shoulder;
    }

    public void setBiceps(int biceps) {
        this.biceps = biceps;
    }

    public void setTriceps(int triceps) {
        this.triceps = triceps;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setAerobic(int aerobic) {
        this.aerobic = aerobic;
    }
}
