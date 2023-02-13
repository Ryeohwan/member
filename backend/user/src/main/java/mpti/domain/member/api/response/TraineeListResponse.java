package mpti.domain.member.api.response;

import lombok.Getter;

@Getter
public class TraineeListResponse {
    private Long id;
    private String email;
    private String Gender;
    private int age;
    private String name;
    private String s3Url;

    private String hour;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
