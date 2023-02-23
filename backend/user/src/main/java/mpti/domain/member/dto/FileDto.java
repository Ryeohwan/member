package mpti.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
// I have to remove this annotation
@Setter
public class FileDto {
    private String email;
    private String title;
    private String url;
    private MultipartFile file;
}

