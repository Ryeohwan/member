package mpti.domain.member.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mpti.domain.member.api.request.DateRequest;
import mpti.domain.member.api.request.UserRequest;
import mpti.domain.member.api.response.*;
import mpti.domain.member.application.FileService;
import mpti.domain.member.application.S3Service;
import mpti.domain.member.dto.FileDto;
import mpti.domain.member.dto.UserDto;
import mpti.domain.member.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mpti.domain.member.application.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(result);
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
        DeleteResponse result = new DeleteResponse();
        return ResponseEntity.ok(result);
    }

    @PostMapping("admin/delete")
    @ResponseBody
    public ResponseEntity adminDelete(User form){
        String email = form.getEmail();
        String temp = userService.delete(email);
        DeleteResponse result = new DeleteResponse();
        result.setResult(temp);
        return ResponseEntity.ok(result);
    }


    @PostMapping ("update")
    @ResponseBody
    public ResponseEntity update(UserRequest form){
        String temp = userService.update(form);
        UpdateResponse result = new UpdateResponse();
        result.setStatus(temp);
        return ResponseEntity.ok(result);
    }

    @GetMapping("list/{page}")
    @ResponseBody
    public ResponseEntity findAll(@PathVariable int page){
        Page<UserResponse> reult = userService.findAll(page);
        return ResponseEntity.ok(reult);
        // why
    }

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
        UpdateResponse result = new UpdateResponse();
        result.setStatus(SUCCESS);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/info/name/{id}")
    @ResponseBody
    public ResponseEntity checkName(@PathVariable Long id){
        String name = userService.checkName(id);
        IdResponse result = new IdResponse();
        result.setName(name);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/userList/{page}")
    public ResponseEntity findUserList(@PathVariable int page, @RequestBody Map<String , String> body){
        String id = body.get("id");
        Long temp = Long.parseLong(id);
        Page<UserResponse> result = userService.findList(page,temp);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/count")
    @ResponseBody
    public ResponseEntity counting(@RequestBody List<String> form){
        String temp = userService.ptUpdate(form);
        UpdateResponse result = new UpdateResponse();
        result.setStatus(temp);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{email}")
    @ResponseBody
    public ResponseEntity userStatus(@PathVariable String email){
        UserStatus result = userService.findStatus(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/admin/stop")
    @ResponseBody
    public ResponseEntity adminStop(@RequestBody DateRequest form){
        Long id = form.getId();
        System.out.println(form.getStopUntil());
        LocalDateTime date = form.getStopUntil();
        UserRequest temp = new UserRequest();
        temp.setId(id);
        temp.setStopUntil(date);
        String result = userService.updateStop(temp);
        return ResponseEntity.ok(result);
    }



}
