package mpti.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String email) {
        super("이미 사용하고 있는 아이디 이메일입니다 : " + email);
    }
}