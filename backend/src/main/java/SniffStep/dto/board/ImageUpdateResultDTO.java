package SniffStep.dto.board;

import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageUpdateResultDTO {
    private List<AwsS3> addedImageFiles;
    private List<Image> deletedImage;
    private List<Image> addedImages;
    private List<Image> failedToDeleteImages;

}
