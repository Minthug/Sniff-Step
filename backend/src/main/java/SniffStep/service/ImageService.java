//package SniffStep.service;
//
//import SniffStep.common.exception.BusinessLogicException;
//import SniffStep.common.exception.ExceptionCode;
//import SniffStep.common.exception.FileUploadFailureException;
//import com.amazonaws.services.s3.AmazonS3;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.fileupload.FileUploadException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService implements FileService {
//
//  private String location = "/Users/minthug/Desktop/Sniff-Step/backned/temp/";
//
//  @PostConstruct
//  void postContruct() {
//    File file = new File(location);
//    if (!file.exists()) {
//      file.mkdirs();
//    }
//  }
//
//    @Override
//    public void upload(MultipartFile file, String filename) {
//        try {
//            file.transferTo(new File(location + filename));
//        } catch (IOException e) {
//            throw new FileUploadFailureException("파일 업로드에 실패했습니다.");
//        }
//    }
//
//    @Override
//    public void delete(String filename) {
//        new File(location + filename).delete();
//    }
//}
