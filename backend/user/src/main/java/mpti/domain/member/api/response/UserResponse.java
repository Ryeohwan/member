package mpti.domain.member.api.response;


import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import  org.springframework.http.HttpStatus;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class UserResponse {
    // 회원가입했을 때 담아서 갈 response
    Long id;
    String name;

    int age;
    String email;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    Date birth;
    String gender;
    String phone;
    String address;
    @Column(updatable = false)
    LocalDateTime createAt;
    LocalDateTime updateAt;

    String s3Url;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate stopUntil;
    HttpStatus status;

    public UserResponse() {
    }

    @Builder
    public UserResponse(Long id, String name, int age,String email, Date birth, String gender, String phone, String address, LocalDateTime createAt, LocalDateTime updateAt, String s3Url, LocalDate stopUntil) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.s3Url = s3Url;
        this.stopUntil = stopUntil;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public  void setS3Url(String s3Url){this.s3Url = s3Url;}

    public void setStopUntil(LocalDate stopUntil) {
        this.stopUntil = stopUntil;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
