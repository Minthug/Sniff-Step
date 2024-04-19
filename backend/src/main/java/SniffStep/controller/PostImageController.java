package SniffStep.controller;

import SniffStep.dto.ImageResponseDTO;
import SniffStep.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static SniffStep.common.HttpResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class PostImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity saveFile(@RequestPart(value = "files") List<MultipartFile> multipartFiles) throws IOException {

        List<ImageResponseDTO> imageResponse = imageService.addFile(multipartFiles);
        return new ResponseEntity(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity findFile(@PathVariable(value = "id") Long id) {
        String url = imageService.findUrlById(id);
        return new ResponseEntity(url, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFile(@PathVariable(value = "id") Long id) {
        imageService.deleteFile(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
