package SniffStep.dto.board;

import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUpdateResultDTO {
    private List<MultipartFile> addedImageFiles;
    private List<Image> deletedImage;
    private List<Image> addedImages;
}
