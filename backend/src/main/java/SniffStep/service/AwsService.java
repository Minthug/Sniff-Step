package SniffStep.service;

import SniffStep.common.config.S3Config;
import SniffStep.dto.board.AwsS3;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsService {

    private final AmazonS3Client amazonS3Client;
    private final S3Config s3Config;
    private String bucketName;

    @PostConstruct
    public void init() {
        bucketName = s3Config.getBucketName();
    }


    public List<AwsS3> uploadFiles(Long memberId, String folderPath, List<MultipartFile> multipartFiles) {
        List<AwsS3> s3files = new ArrayList<>();
        String uploadFilePath = String.format("member/%d", memberId);

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String keyName = uploadFilePath + "/" + uploadFileName;

            try (InputStream inputStream = multipartFile.getInputStream()) {
                ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);


                uploadFileToS3(keyName, inputStream, objectMetadata);
                String uploadFileUrl = getUploadedFileUrl(keyName);

                s3files.add(createAwsS3(originalFileName, uploadFileName, uploadFilePath, uploadFileUrl));
            } catch (IOException e) {
                e.printStackTrace();
                log.error("File upload failed", e);
            }
        }
        return s3files;
    }

    // 수정된 사항
    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }

    public boolean deleteFileV2(String uploadFilePath, String uuidFileName) {
        String keyName = uploadFilePath + "/" + uuidFileName;

        try {
            if (amazonS3Client.doesObjectExist(bucketName, keyName)) {
                amazonS3Client.deleteObject(bucketName, keyName);
                log.info("File delete is completed. KeyName: {}", keyName);
                return true;
            } else {
                log.warn("File does not exist. KeyName: {}", keyName);
                return false;
            }
        } catch (AmazonServiceException e) {
            return false;
        } catch (SdkClientException e) {
            log.error("SDK client error while deleting file: {}", keyName, e);
            return false;
        }
    }

    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }

    private String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }


    // 필요치 않으면 주석
    private void uploadFileToS3(String keyName, InputStream inputStream, ObjectMetadata objectMetadata) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));
        log.info("File upload is completed. KeyName: {}", bucketName, keyName);
    }

    private String getUploadedFileUrl(String keyName) {
        return amazonS3Client.getUrl(bucketName, keyName).toString();
    }

    private AwsS3 createAwsS3(String originalFileName, String uploadFileName, String uploadFilePath, String uploadFileUrl) {
        return AwsS3.builder()
                .originalFileName(originalFileName)
                .uploadFileName(uploadFileName)
                .uploadFilePath(uploadFilePath)
                .uploadFileUrl(uploadFileUrl)
                .build();
    }
}
