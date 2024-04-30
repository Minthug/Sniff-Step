package SniffStep.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class AwsS3 {

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;
}
