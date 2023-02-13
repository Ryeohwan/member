package mpti.domain.member.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mpti.domain.member.api.request.DateRequest;
import mpti.domain.member.api.request.UserRequest;
import mpti.domain.member.api.response.*;
import mpti.domain.member.application.BusinessCommunication;
import mpti.domain.member.application.FileService;
import mpti.domain.member.application.S3Service;
import mpti.domain.member.dao.MemoRepository;
import mpti.domain.member.dto.BusinessDto;
import mpti.domain.member.dto.BusinessRequest;
import mpti.domain.member.dto.FileDto;
import mpti.domain.member.entity.Memo;
import mpti.domain.member.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mpti.domain.member.application.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    private final BusinessCommunication businessCommunication;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";


    // email 중복체크
    @GetMapping("/duplicate/{email}")
    public ResponseEntity CheckEmailDuplicated(@PathVariable String email) {
        boolean result = userService.isEmailDuplicate(email);
        String responseMessage = result ? "DUPLICATED" : "NON-DUPLICATE";
        return ResponseEntity.ok().body(responseMessage);
    }


    @PostMapping(value = "/join")
    @ResponseBody
    public ResponseEntity create(@RequestBody UserRequest form){

        String result = userService.join(form);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "join/list")
    @ResponseBody
    public ResponseEntity joinList(@RequestBody List<UserRequest> list){
        String result = userService.joinList(list);
        return ResponseEntity.ok(result);
    }

    @PostMapping("info") // 개인정보 조회
    @ResponseBody
    public ResponseEntity find(@RequestBody UserRequest form) {
        UserResponse result = userService.findByEmail(form.getEmail());
        return ResponseEntity.ok(result);
    }

    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody User form) {
        String email = form.getEmail();
        String temp = userService.delete(email);
        DeleteResponse result = new DeleteResponse();
        result.setResult(temp);
        return ResponseEntity.ok(result);
    }

    @PostMapping("admin/delete")
    @ResponseBody
    public ResponseEntity adminDelete(@RequestBody User form){
        String email = form.getEmail();
        String temp = userService.delete(email);
        DeleteResponse result = new DeleteResponse();
        result.setResult(temp);
        return ResponseEntity.ok(result);
    }


    @PostMapping ("update")
    @ResponseBody
    public ResponseEntity update(@RequestBody UserRequest form){
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
    }


    @GetMapping("/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity uploadFile(FileDto fileDto) throws IOException {
        String url = s3Service.uploadFile(fileDto.getFile(),fileDto.getEmail());
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
    public ResponseEntity findUserList(@PathVariable Long page ,@RequestBody UserRequest form){
        // trainer_ id
        List<BusinessDto> list = businessCommunication.getIds(form.getId());
        List<BusinessRequest> temp = new ArrayList<>();
        for (BusinessDto a: list) {
            BusinessRequest b = new BusinessRequest();
            b.setId(a.getUserId());
            b.setHour(a.getHour());
            temp.add(b);
        }
        Page<TraineeListResponse> result = userService.findTrainee(temp,page);
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
        LocalDate date = form.getStopUntil();
        UserRequest temp = new UserRequest();
        temp.setId(id);
        temp.setStopUntil(date);
        String result = userService.updateStop(temp);
        return ResponseEntity.ok(result);
    }


    @PostMapping("pt/status/{page}")
    public ResponseEntity userPtStatus(@PathVariable int page, @RequestBody Long id){
        Page<Memo> result = userService.findPtStatus(id,page);
        return ResponseEntity.ok(result);
    }


}
