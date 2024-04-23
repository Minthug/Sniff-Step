package SniffStep.dto;

import SniffStep.entity.Image;
import lombok.Getter;

@Getter
public class ImageResponseDTO {

    private Long imageId;
    private String s3Url;

    public ImageResponseDTO(Image image) {
        this.imageId = image.getId();
        this.s3Url = image.getS3Url();
    }
}
