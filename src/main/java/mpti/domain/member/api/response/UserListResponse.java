package mpti.domain.member.api.response;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class UserListResponse {
    private String name;
    private String memo;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birth;
    private int core,chest, shoulder, biceps, triceps,back,legs,aerobic;

    public void setName(String name) {
        this.name = name;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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
