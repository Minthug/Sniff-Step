package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.common.exception.UnsupportedFileTypeException;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uniqueName;

    @Column(name = "image_name")
    private String originName;

    private String s3Url;

    private String s3FilePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public Image(String originName, String s3Url, String s3FilePath) {
        this.originName = originName;
        this.s3Url = s3Url;
        this.s3FilePath = s3FilePath;
    }

    private final static String[] supportedExtensions = new String[]{"jpg", "jpeg", "png", "gif"};

    public Image(String originName) {
        this.originName = originName;
        this.uniqueName = generateUniqueName(extractExtension(originName));
    }
    private String extractExtension(String originName) {
        int dotIndex = originName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == originName.length() - 1) {
            throw new UnsupportedFileTypeException("Invalid or missing file extension.");
        }
        String extension = originName.substring(dotIndex + 1).toLowerCase();
        if (!isSupportedFormat(extension)) {
            throw new UnsupportedFileTypeException("Unsupported file type: " + extension);
        }
        return extension;
    }

    private boolean isSupportedFormat(String extension) {
        return Arrays.asList(supportedExtensions).contains(extension);
    }

    public void initBoard(Board board) {
        if (this.board == null) {
            this.board = board;
        }
    }

    private String generateUniqueName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    public void updatePost(Board board) {
        this.board = board;
    }
}
