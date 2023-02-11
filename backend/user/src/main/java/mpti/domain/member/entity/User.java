package mpti.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "user_id", columnDefinition = "BINARY(16)")
//    private UUID id;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Memo> memoList = new ArrayList<>();

    public void addMemo(Memo memo){
        memoList.add(memo);
        memo.setUser(this);
    }


    @Column(unique = true)
    @Email
    private String email;

    private String role;

    // 하면 이거 json 으로 파싱할 때 비밀번호 정보는 주지 않는다고 한다.
    @Column(nullable = false)
    private String password;

    private String gender;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$" ,message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    private String phone;
    private String address;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birth;
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopUntil;


    private String provider;

    @Column(columnDefinition = "integer default 0")
    private int core,chest, shoulder, biceps, triceps,back,legs,aerobic;

    @Column
    private String title;

    @Column
    private String s3Url;

    public User(String title, String s3Url) {
        this.title = title;
        this.s3Url = s3Url;
    }
    public User() {
    }


    public void setEmail(String email) {

        if(email != null) this.email = email;
    }

    public void setName(String name) {
        if(name != null) this.name = name;
    }
    public void setPassword(String password) {
        if(password != null) this.password = password;
    }

    public void setGender(String gender) {
        if(gender != null)this.gender = gender;
    }

    public void setPhone(String phone) {
        if(phone != null) this.phone = phone;
    }

    public void setAddress(String address) {
        if(address != null) this.address = address;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        if(updateAt != null) this.updateAt = updateAt;
    }

    public void setProvider(String provider) {if(provider != null)this.provider = provider;}

    public void setMemoList(List<Memo> memoList) {
        this.memoList = memoList;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setStopUntil(LocalDate stopUntil) {
        this.stopUntil = stopUntil;
    }

    public void setCore(int core) {
        this.core += core;
    }

    public void setChest(int chest) {
        this.chest += chest;
    }

    public void setShoulder(int shoulder) {
        this.shoulder += shoulder;
    }

    public void setBiceps(int biceps) {
        this.biceps += biceps;
    }

    public void setTriceps(int triceps) {
        this.triceps += triceps;
    }

    public void setBack(int back) {
        this.back += back;
    }

    public void setLegs(int legs) {
        this.legs += legs;
    }

    public void setAerobic(int aerobic) {
        this.aerobic += aerobic;
    }

    public void setTitle(String title) {
        if(title != null) this.title = title;
    }

    public void setS3Url(String s3Url) {
        if(s3Url != null) this.s3Url = s3Url;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", provider='" + provider + '\'' +
                '}';
    }

    @Builder
    public User(long id, String name, Memo memo, String email, String role, String password,  String gender, Date birth,
                String phone, String address, LocalDateTime createAt, LocalDateTime updateAt, String s3Url, LocalDate stopUntil) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.s3Url = s3Url;
        this.stopUntil = stopUntil;
    }


}
