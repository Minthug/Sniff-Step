//package SniffStep.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectResult;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class S3Uploader {
//
//    private final AmazonS3 amazonS3;
//
//    @Value(("${cloud.aws.s3.bucket}"))
//    private String bucket;
//
//    public String uploadImageToS3(MultipartFile image, String type) {
//        String originName = image.getOriginalFilename();
//        String ext = originName.substring(originName.lastIndexOf("."));
//        String changedName = changedImageName(originName);
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(ext);
//
//        try {
//            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucket, type + "/" + changedName, image.getInputStream(), metadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        String imageUrl = amazonS3.getUrl(bucket + "/" + type, changedName).toString();
//
//        return imageUrl;
//    }
//
//    public void deleteImageFromS3(String imageUrl, String type) {
//        if (imageUrl.contains("https://s3.ap-northeast-2.amazonaws.com/" + bucket))
//            amazonS3.deleteObject(bucket + "/" + type, imageUrl.split("/")[6]);
//    }
//
//    private static String changedImageName(String originName) {
//        String random = UUID.randomUUID().toString();
//        return random + originName;
//    }
//}
