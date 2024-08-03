package SniffStep.service;

import SniffStep.common.config.S3Config;
import SniffStep.dto.board.AwsS3;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.cloud.aws.bucket}")
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


    // 수정된 사항
    private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }

    public boolean deleteFolder(Long memberId, Long boardId) {
        String folderKey = String.format("members/%d/%d/", memberId, boardId);

        try {
            log.debug("Deleting post folder: {}", folderKey);
            ObjectListing objectListing = amazonS3Client.listObjects(bucketName, folderKey);

            if (objectListing.getObjectSummaries().isEmpty()) {
                log.warn("Folder is empty. KeyName: {}", folderKey);
                return true;
            }

            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                amazonS3Client.deleteObject(bucketName, objectSummary.getKey());
            }

            while (objectListing.isTruncated()) {
                log.debug("Listing next batch of objects");
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
                for (S3ObjectSummary file : objectListing.getObjectSummaries()) {
                    log.debug("Attempting to delete object: {}", file.getKey());
                    amazonS3Client.deleteObject(bucketName, file.getKey());
                    log.debug("Successfully deleted object: {}", file.getKey());
                }
            }

            log.debug("Attempting to delete folder object itself: {}", folderKey);
            amazonS3Client.deleteObject(bucketName, folderKey);
            log.info("Successfully deleted S3 folder: {}", folderKey);
            return true;
        } catch (AmazonServiceException e) {
            log.error("Failed to delete folder", e);
            throw new RuntimeException("Failed to delete folder", e);
        } catch (SdkClientException e) {
            log.error("Failed to delete folder", e);
            throw new RuntimeException("Failed to delete folder", e);
        }
    }


    public boolean deleteFileV3(String fileUrl) {
        try {
            String splitStr = ".com/";
            String keyName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

            if (amazonS3Client.doesObjectExist(bucketName, keyName)) {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
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


    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + getFileExtension(originalFileName);
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    private AwsS3 createFailedAwsS3(String originalFileName, String errorMessage) {
        return AwsS3.builder()
                .originalFileName(originalFileName)
                .uploadSuccessful(false)
                .errorMessage(errorMessage)
                .build();
    }

    public List<AwsS3> uploadBoardV4(Long memberId, Long boardId, List<MultipartFile> imageFile) throws Exception {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new FileUploadException("파일이 비어있습니다.");
        }

        String uploadFilePath = String.format("member/%d/boards/%d", memberId, boardId);

        List<AwsS3> uploadResult = uploadFilesV3(uploadFilePath, imageFile);

        if (uploadResult.isEmpty()) {
            throw new FileUploadException("파일 업로드에 실패하였습니다.");
        }

        AwsS3 result = uploadResult.get(0);
        if (result.getUploadFileUrl() == null || result.getUploadFileUrl().isEmpty()) {
            throw new FileUploadException("업로드된 파일 URL이 비어있습니다.");
        }

        log.info("Board image uploaded successfully. URL: {}", boardId, result.getUploadFileUrl());
        return uploadResult;
    }

    public AwsS3 uploadProfileV3(Long memberId, MultipartFile imageFile) throws Exception {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new FileUploadException("파일이 비어있습니다.");
        }

        String uploadFilePath = String.format("member/%d/profile", memberId);
        List<MultipartFile> fileList = Collections.singletonList(imageFile);

        List<AwsS3> uploadResult = uploadFilesV3(uploadFilePath, fileList);

        if (uploadResult.isEmpty()) {
            throw new FileUploadException("파일 업로드에 실패하였습니다.");
        }

        AwsS3 result = uploadResult.get(0);
        if (result.getUploadFileUrl() == null || result.getUploadFileUrl().isEmpty()) {
            throw new FileUploadException("업로드된 파일 URL이 비어있습니다.");
        }

        log.info("Profile image uploaded successfully. URL: {}", memberId ,result.getUploadFileUrl());
        return result;
    }
}
