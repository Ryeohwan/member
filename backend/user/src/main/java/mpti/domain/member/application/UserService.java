package mpti.domain.member.application;


import lombok.RequiredArgsConstructor;
import mpti.domain.member.api.request.UserRequest;
import mpti.domain.member.api.response.TraineeListResponse;
import mpti.domain.member.api.response.UserResponse;
import mpti.domain.member.api.response.UserStatus;
import mpti.domain.member.dao.MemoRepository;
import mpti.domain.member.dao.UserRepository;
import mpti.domain.member.dto.BusinessRequest;
import mpti.domain.member.dto.StatusDto;
import mpti.domain.member.dto.ptOrderDto;
import mpti.domain.member.entity.Memo;
import mpti.domain.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    private final PasswordEncoder passwordEncoder;
    private EntityManager em;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Transactional(readOnly = true)
    public boolean isEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            return true;
        }
        return false;
    }

    @Transactional
    public String join(UserRequest user) {
        User result = user.toEntity().builder()
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birth(user.getBirth())
                .gender(user.getGender())
                .name(user.getName())
                .age(user.getAge())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .password(passwordEncoder.encode(user.getPassword()))
                .stopUntil(LocalDate.now().minusDays(1))
                .build();

//        System.out.println("result = " + result);
        userRepository.save(result);
        return result.getName();
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email){
        User user1 = userRepository.findUserByEmail(email);
        UserResponse result = new UserResponse();
        result.setEmail(user1.getEmail());
        result.setBirth(user1.getBirth());
        result.setName(user1.getName());
        result.setPhone(user1.getPhone());
        result.setAddress(user1.getAddress());
        result.setCreateAt(user1.getCreateAt());
        result.setUpdateAt(user1.getUpdateAt());
        result.setGender(user1.getGender());
        result.setS3Url(user1.getS3Url());
        result.setId(user1.getId());
        return result;
    }


    public String delete(String email, String name){
        int check = userRepository.deleteUserByEmailAndPassword(email,name);
        if(userRepository.deleteUserByEmailAndPassword(email,name) == 1){
            return SUCCESS;
        }else{
            return FAIL;
        }
    }

    public String delete(String email){
        int check = userRepository.deleteUserByEmail(email);
        if( check == 1){
            return SUCCESS;
        }else{
            return FAIL;
        }
    }

    public String update(UserRequest check){
        String email = check.getEmail();
        User temp = userRepository.findUserByEmail(email);
        temp.setPhone(check.getPhone());
        temp.setAddress(check.getAddress());
        temp.setBirth(check.getBirth());
        temp.setName(check.getName());
        temp.setPassword(passwordEncoder.encode(check.getPassword()));
        temp.setGender(check.getGender());
        temp.setUpdateAt(LocalDateTime.now());
        String result = temp.getName();
        return result;
    }

    public String updateStop(UserRequest check){
        Long id = check.getId();
        LocalDate date = check.getStopUntil();
        User temp = userRepository.findUserById(id);
        temp.setStopUntil(date);
        temp.setUpdateAt(LocalDateTime.now());
        String result = temp.getName();
        return result;
    }

    public String checkName(Long id){
        User temp = userRepository.findUserById(id);
        return temp.getName();
    }

    public String ptUpdate(List<String> form) {
        User temp = userRepository.findUserById(Long.parseLong(form.get(0)));
        Long trainerId = Long.parseLong(form.get(1));
        String memo = "";
        Memo mem = new Memo();
        for (int i = 1 ; i< form.size();i++) {
            switch (form.get(i)){
                case "biceps":
                    temp.setBiceps(1);
                    mem.setBiceps(1);
                    break;
                case "triceps":
                    temp.setTriceps(1);
                    mem.setTriceps(1);
                    break;
                case "back":
                    temp.setBack(1);
                    mem.setBack(1);
                    break;
                case "legs":
                    temp.setLegs(1);
                    break;
                case "chest":
                    temp.setChest(1);
                    mem.setChest(1);
                    break;
                case "aerobic":
                    temp.setAerobic(1);
                    mem.setAerobic(1);
                    break;
                case "core":
                    temp.setCore(1);
                    mem.setCore(1);
                    break;
                case "shoulder":
                    temp.setShoulder(1);
                    mem.setShoulder(1);
                    break;
                default:
                    memo = form.get(i);
                    break;
            }
        }

        mem.setUserId(temp.getId());
        mem.setTrainerId(trainerId);
        mem.setRecord(memo);
        mem.setDate(LocalDate.now());
        memoRepository.save(mem);

        temp.setUpdateAt(LocalDateTime.now());
        return SUCCESS;
    }

    @Transactional(readOnly = true)
    public UserStatus findStatus(String email) {
        User temp = userRepository.findUserByEmail(email);
        UserStatus result = new UserStatus();
        result.setAerobic(temp.getAerobic());
        result.setBack(temp.getBack());
        result.setShoulder(temp.getShoulder());
        result.setCore(temp.getCore());
        result.setBiceps(temp.getBiceps());
        result.setTriceps(temp.getTriceps());
        result.setLegs(temp.getLegs());
        result.setChest(temp.getChest());
        return result;

    }


    public Page<UserResponse> findList(int page,long id) {
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "email"));
        Page<User> result = userRepository.findPageById(id, pageRequest);
        Page<UserResponse> toMap = result.map(m -> UserResponse.builder()
                .name(m.getName())
                .email(m.getEmail())
                .gender(m.getGender())
                .age(m.getAge())
                .birth(m.getBirth())
                .phone(m.getPhone())
                .s3Url(m.getS3Url())
                .stopUntil(m.getStopUntil())
                .createAt(m.getCreateAt())
                .build()
        );
        return toMap;
    }

    public Page<UserResponse> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page,8,Sort.by(Sort.Direction.DESC,"id"));
        Page<User> result = userRepository.findAllByEmailIsNotNull(pageRequest);
        Page<UserResponse> toMap = result.map(m -> UserResponse.builder().name(m.getName())
                .email(m.getEmail())
                .gender(m.getGender())
                .phone(m.getPhone())
                .birth(m.getBirth())
                .s3Url(m.getS3Url())
                .stopUntil(m.getStopUntil())
                .createAt(m.getCreateAt())
                .build()
        );
        return toMap;
    }

    public String  joinList(List<UserRequest> list) {
        for (UserRequest a: list) {
            User temp = new User();
            temp.setBirth(a.getBirth());
            temp.setName(a.getName());
            temp.setGender(a.getGender());
            temp.setBirth(a.getBirth());
            temp.setS3Url(a.getS3Url());
            temp.setAddress(a.getAddress());
            temp.setEmail(a.getEmail());
            temp.setPhone(a.getPhone());
            temp.setAge(a.getAge());
            temp.setPassword(passwordEncoder.encode(a.getPassword()));
            temp.setShoulder(a.getShoulder());
            temp.setCore(a.getCore());
            temp.setAerobic(a.getAerobic());
            temp.setChest(a.getChest());
            temp.setBiceps(a.getBiceps());
            temp.setBack(a.getBack());
            temp.setLegs(a.getLegs());
            temp.setTriceps(a.getTriceps());
            temp.setRole(a.getRole());
            temp.setStopUntil(LocalDate.now().minusDays(1));

            userRepository.save(temp);
        }

        return "a";
    }


    public Page<TraineeListResponse> findTrainee(List<BusinessRequest> temp, Long page) {
        int nPage = page.intValue();

        List<User> userList = new ArrayList<>();

        for (BusinessRequest a: temp) {
            userList.add(userRepository.findUserById(a.getId()));
        }


        List<TraineeListResponse> result = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            TraineeListResponse a = new TraineeListResponse();

            int one = temp.get(i).getHour();
            int two = one + 1;
            String nStart = Integer.toString(one) + ":00 ~ ";
            String nEnd = Integer.toString(two) + ":00";
            String fHour = nStart + nEnd;
            a.setHour(fHour);

            a.setId(userList.get(i).getId());
            a.setName(userList.get(i).getName());
            a.setGender(userList.get(i).getGender());
            a.setEmail(userList.get(i).getEmail());
            a.setAge(userList.get(i).getAge());
            a.setS3Url(userList.get(i).getS3Url());
            result.add(a);
        }
        PageRequest pageRequest = PageRequest.of(nPage, 8);
        int start = (int) pageRequest.getOffset();
        int end  = (start + pageRequest.getPageSize()) > result.size() ? result.size() : (start + pageRequest.getPageSize());
        Page<TraineeListResponse> userPage = new PageImpl<>(result.subList(start,end), pageRequest, result.size());

        return userPage;
    }

    public Page<StatusDto> findPtStatus(Long id,int page) {
        PageRequest pageRequest = PageRequest.of(page,4);
        List<Memo> temp = memoRepository.findMemoByUserId(id);
        List<StatusDto> result = new ArrayList<>();
        for (Memo a:temp) {
            StatusDto b = new StatusDto();
            ptOrderDto c = new ptOrderDto();
            if(a.getChest() != 0){
                c.setChest("chest");
            }
            if(a.getShoulder() != 0){
                c.setShoulder("shoulder");
            }
            if(a.getBiceps() != 0){
                c.setBiceps("biceps");
            }
            if(a.getTriceps() != 0){
                c.setTriceps("triceps");
            }
            if(a.getBack() != 0){
                c.setBack("back");
            }
            if(a.getCore() != 0){
                c.setCore("core");
            }
            if(a.getLegs() != 0){
                c.setLegs("legs");
            }
            if(a.getAerobic() != 0){
                c.setAerobic("aerobic");
            }

            b.setPart(c);
            b.setDate(a.getDate());
            b.setRecord(a.getRecord());
            b.setUserId(a.getUserId());
            result.add(b);
        }
        int start = (int) pageRequest.getOffset();
        int end  = (start + pageRequest.getPageSize()) > result.size() ? result.size() : (start + pageRequest.getPageSize());
        Page<StatusDto> statusPage = new PageImpl<>(result.subList(start,end), pageRequest, result.size());

        return statusPage;
    }
}
