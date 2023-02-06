package mpti.domain.member.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mpti.domain.member.api.request.UserRequest;
import mpti.domain.member.api.response.DeleteResponse;
import mpti.domain.member.api.response.IdResponse;
import mpti.domain.member.api.response.UserResponse;
import mpti.domain.member.application.FileService;
import mpti.domain.member.application.S3Service;
import mpti.domain.member.dto.FileDto;
import mpti.domain.member.dto.UserDto;
import mpti.domain.member.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mpti.domain.member.application.UserService;

import java.io.IOException;

// final
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    private final S3Service s3Service;
    private final FileService fileService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";


    // email 중복체크
    @GetMapping("/duplicate/{email}")
    public ResponseEntity CheckEmailDuplicated(@PathVariable String email) {
        boolean result = userService.isEmailDuplicate(email);
        String responseMessage = result ? "DUPLICATED" : "NON-DUPLICATE";
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping ("/test")
    public ResponseEntity test() {
        UserDto userDto = UserDto.builder().email("sdfsdafsdaf").build();
        if(userDto == null){
            System.out.println("null!");
        }else{
            System.out.println("here is it");
        }
        return ResponseEntity.ok("here is it");
    }


    @PostMapping(value = "/join")
    @ResponseBody
    public String create(UserRequest form){
        return userService.join(form);
    }

    @PostMapping("info") // 개인정보 조회
    @ResponseBody
    public ResponseEntity find(String email) {
        UserResponse result = userService.findByEmail(email);
        if(result.getEmail() == null){
            result.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.ok(result);
        }
    }

    // are you there?
    @PostMapping("check")
    @ResponseBody
    public ResponseEntity check(User form) {
        String email = form.getEmail();
        String name = form.getName();
        Boolean result = userService.relog(email, name);
        return ResponseEntity.ok(result);
    }

    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(User form) {
        String email = form.getEmail();
        String name = form.getName();
        userService.delete(email,name);
        return ResponseEntity.ok(name);
    }

    @PostMapping ("update")
    @ResponseBody
    public ResponseEntity update(UserRequest form){
        String result = userService.update(form);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("list")
//    @ResponseBody
//    public ResponseEntity<BasicResponse<Page<UserResponse>>> list(List<String> formList){
//        List<UserResponse> reult = userService.findList(formList);
//
//    }

//    @PostMapping("ptlog")
//    @ResponseBody
//    public ResponseEntity<BasicResponse<UserListResponse>> ptlog(UserRequest form){
//


    @GetMapping("/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity uploadFile(FileDto fileDto) throws IOException {
        String url = s3Service.uploadFile(fileDto.getFile());

        fileDto.setUrl(url);
        fileService.save(fileDto);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/info/name/{id}")
    @ResponseBody
    public ResponseEntity checkName(@PathVariable Long id){
        String name = userService.checkName(id);
        IdResponse result = new IdResponse();
        result.setName(name);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/userList")
//    @ResponseBody
//    public ResponseEntity<BasicResponse<UserResponse>> checkName(List<String> ids){
//        userService.
//    }

//    @PostMapping("/ptlog/count")
//    @ResponseBody
//    public ResponseEntity<BasicResponse<String>> counting(List<Integer> counts,){
//        String result = userService.count(counts);
//        return result;
//    }

}
