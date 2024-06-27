//package SniffStep.controller;
//
//import SniffStep.service.AwsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/upload")
//public class AwsController {
//
//    private final AwsService AwsService;
//
//    @PostMapping("/create")
//    public ResponseEntity<Object> uploadFile(@RequestParam(value = "fileType") String fileType,
//                                             @RequestPart(value = "files") List<MultipartFile> files) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(AwsService.uploadFiles(fileType, files));
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<Object> deleteFile(@RequestParam(value = "uploadFilePath") String uploadFilePath,
//                                             @RequestPart(value = "uuidFileName") String uuidFileName) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(AwsService.deleteFile(uploadFilePath, uuidFileName));
//    }
//
//}
