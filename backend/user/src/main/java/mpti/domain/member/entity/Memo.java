package mpti.domain.member.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
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
    private Long trainer_id;

    private LocalDateTime date;

    public void setUser(User user) {
        this.user = user;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setTrainer_id(Long trainer_id) {
        this.trainer_id = trainer_id;
    }

    public void setDate(LocalDateTime date) {
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
}
