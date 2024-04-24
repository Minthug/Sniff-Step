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
    public Long id;

    @Column(nullable = false)
    private String uniqueName;

    @Column(name = "image_name")
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;


    private final static String[] supportedExtensions = new String[]{"jpg", "jpeg", "png", "gif"};

    public Image(String originName) {
        this.originName = originName;
        this.uniqueName = generateUniqueName(extractExtension(originName));
    }

    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(",") + 1);
            if (isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException ignored) {
            throw new UnsupportedFileTypeException();
        }
        throw new UnsupportedFileTypeException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtensions).anyMatch(supportedExt -> supportedExt.equals(ext));
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
