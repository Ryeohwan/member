package mpti.domain.member.dto;

import lombok.Getter;

@Getter
public class ptOrderDto {
    private String chest;
    private String shoulder;
    private String biceps;
    private String triceps;
    private String back;
    private String core;
    private String legs;
    private String aerobic;

    public void setChest(String chest) {
        this.chest = chest;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public void setBiceps(String biceps) {
        this.biceps = biceps;
    }

    public void setTriceps(String triceps) {
        this.triceps = triceps;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public void setAerobic(String aerobic) {
        this.aerobic = aerobic;
    }
}
