package SniffStep.dto;

import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageResponseDTO {

    private Long id;
    private String originName;
    private String uniqueName;

    public static ImageResponseDTO toDto(Image image) {
        return new ImageResponseDTO(image.getId(), image.getOriginName(), image.getUniqueName());
    }

}
