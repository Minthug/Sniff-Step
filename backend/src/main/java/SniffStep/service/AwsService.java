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

    public List<AwsS3> uploadFilesV2(String uploadFilePath, List<MultipartFile> multipartFiles) {
        List<AwsS3> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String keyName = uploadFilePath + "/" + uploadFileName;

            try (InputStream inputStream = multipartFile.getInputStream()) {
                ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);

                uploadFileToS3(keyName, inputStream, objectMetadata);
                String uploadFileUrl = getUploadedFileUrl(keyName);

                s3files.add(createAwsS3(originalFileName, uploadFileName, uploadFilePath, uploadFileUrl));
            } catch (IOException e) {
                log.error("File upload failed", e);
                throw new RuntimeException("File upload failed", e);
            }
        }
        return s3files;
    }

    public List<AwsS3> uploadBoardFiles(Long memberId, Long boardId, List<MultipartFile> imageFile) {
        String uploadFilePath = String.format("member/%d/boards/%d", memberId, boardId);
        return uploadFilesV2(uploadFilePath, imageFile);
    }

    public List<AwsS3> uploadProfileFiles(Long memberId, List<MultipartFile> imageFile) {
        String uploadFilePath = String.format("member/%d/profile", memberId);
        return uploadFilesV2(uploadFilePath, imageFile);
    }


    public AwsS3 uploadProfileFilesV2(Long memberId, MultipartFile imageFile) {

        validateImageFile(imageFile);

        String fileName = generateFileName(imageFile.getOriginalFilename());
        String uploadFilePath = String.format("member/%d/profile/%s", memberId, fileName);

        List<AwsS3> uploadFiles = uploadFilesV2(uploadFilePath, Collections.singletonList(imageFile));

        if (uploadFiles.isEmpty()) {
            throw new RuntimeException("Failed to upload file");
        }

        return uploadFiles.get(0);
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

    private void validateImageFile(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Empty file submitted");
        }

        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type");
        }
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }
}
