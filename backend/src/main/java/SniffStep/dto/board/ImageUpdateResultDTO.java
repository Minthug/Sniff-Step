package SniffStep.dto.board;

import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUpdateResultDTO {
    private List<AwsS3> addedImageFiles;
    private List<Image> deletedImage;
    private List<Image> addedImages;
}
