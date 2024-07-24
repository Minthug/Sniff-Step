package SniffStep.service;

import SniffStep.common.config.S3Config;
import SniffStep.dto.board.AwsS3;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    public List<AwsS3> uploadBoardFilesV3(Long memberId, Long boardId, List<MultipartFile> imageFiles) {
        String uploadFilePath = String.format("member/%d/boards/%d", memberId, boardId);
        return uploadFilesV3(uploadFilePath, imageFiles);
    }

    public List<AwsS3> uploadFilesV3(String uploadFilePath, List<MultipartFile> multipartFiles) {
        List<AwsS3> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            try {
                AwsS3 uploadResult = uploadSingleFile(multipartFile, uploadFilePath);
                s3files.add(uploadResult);
            } catch (IOException e) {
                log.error("File upload failed for file: {}", multipartFile.getOriginalFilename(), e);
                s3files.add(createFailedAwsS3(multipartFile.getOriginalFilename(), e.getMessage()));
            }
        }
        return s3files;
    }

    private AwsS3 uploadSingleFile(MultipartFile multipartFile, String uploadFilePath) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String keyName = uploadFilePath + "/" + uploadFileName;

        ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            uploadFileToS3(keyName, inputStream, objectMetadata);
            String uploadFileUrl = getUploadedFileUrl(keyName);

            return createAwsS3(originalFileName, uploadFileName, uploadFilePath, uploadFileUrl);
        }
    }

    public AwsS3 uploadProfileFilesV2(Long memberId, MultipartFile imageFile) {

        log.info("Uploading profile image for Member Id: {}", memberId);

        try {
            validateImageFile(imageFile);

            String originalFileName = imageFile.getOriginalFilename();
            String fileName = generateFileName(imageFile.getOriginalFilename());
            String uploadFilePath = String.format("member/%d/profile", memberId, fileName);

            log.info("Generated file path: {}", uploadFilePath);

            List<AwsS3> uploadFiles = uploadFilesV3(uploadFilePath, Collections.singletonList(imageFile));

            if (uploadFiles.isEmpty()) {
                throw new RuntimeException("Failed to upload file");
            }

            AwsS3 uploadedFile = uploadFiles.get(0);
            log.info("File uploaded successfully. URL: {}", uploadedFile.getUploadFileUrl());

            if (uploadedFile.getUploadFileUrl() != null || uploadedFile.getUploadFileUrl().isEmpty()) {
                throw new FileUploadException("Uploaded file URL is empty or null");
            }

            return uploadedFile;

        } catch (Exception e) {
            log.error("Failed to upload profile image", e);
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    // 수정된 사항
    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }

    public boolean deleteFileV2(String uploadFilePath, String uuidFileName) {

        try {
            String keyName = uploadFilePath + "/" + uuidFileName;
            if (amazonS3Client.doesObjectExist(bucketName, keyName)) {
                amazonS3Client.deleteObject(bucketName, keyName);
                log.info("File delete is completed. KeyName: {}", keyName);
                return true;
            } else {
                log.warn("File does not exist. KeyName: {}", keyName);
                return false;
            }
        } catch (AmazonServiceException e) {
            log.error("Failed to delete file", e);
            throw new RuntimeException("Failed to delete file", e);
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

    private AwsS3 createFailedAwsS3(String originalFileName, String errorMessage) {
        return AwsS3.builder()
                .originalFileName(originalFileName)
                .uploadSuccessful(false)
                .errorMessage(errorMessage)
                .build();
    }

}
