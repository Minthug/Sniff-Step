package SniffStep.dto.board;

import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {

    private Long id;
    private String originName;
    private String imageUrl;

    public static ImageResponseDTO toDto(Image image) {
        return new ImageResponseDTO(
                image.getId(),
                image.getOriginName(),
                getFullImageUrl(image.getS3Url())
                );
    }

    private static String getFullImageUrl(String s3Url) {
        if (s3Url == null || s3Url.isEmpty()) {
            return null;
        }

        if (s3Url.startsWith("http")) {
            return s3Url;
        }
        return "https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/" + s3Url;
    }
}
