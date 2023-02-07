package mpti.domain.member.api.response;


import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import  org.springframework.http.HttpStatus;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class UserResponse {
    // 회원가입했을 때 담아서 갈 response
    Long id;
    String name;
    String email;
    int age;
    String gender;
    String phone;
    String address;
    @Column(updatable = false)
    LocalDateTime createAt;
    LocalDateTime updateAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime stopUntil;
    HttpStatus status;

    public UserResponse() {
    }

    @Builder
    public UserResponse(String name, String email, int age, String gender, String phone, String address, LocalDateTime createAt, LocalDateTime updateAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.createAt = createAt;
        this.updateAt = updateAt;
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
}
