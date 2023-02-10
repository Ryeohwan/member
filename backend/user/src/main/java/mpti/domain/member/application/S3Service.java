package mpti.domain.member.application;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @Transactional
    public String uploadFile(MultipartFile multipartFile, String email) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = "";

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        switch (ext) {
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "csv":
                contentType = "text/csv";
                break;
        }
        String user = email.split("@")[0];

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            System.out.println(metadata);
            metadata.setContentType(contentType);
            amazonS3.putObject(new PutObjectRequest(bucket, user, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        //object 정보 가져오기
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucket);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        for (S3ObjectSummary object: objectSummaries) {
            System.out.println("object = " + object.toString());
        }

        String temp = amazonS3.getUrl(bucket, user).toString();
        String https = temp.split("//")[0];
        temp = temp.split("//")[1];
        String file = temp.split("/")[1];
        temp = temp.split("/")[0];
        String front = "";

        for (int i = 25; i < temp.length(); i++) {
            front += temp.charAt(i);
        }
        front += "/";
        for (int i = 0; i < 24; i++) {
            front += temp.charAt(i);
        }
        String result = https + "//"+ front + "/" + file;


        return result;
    }
}