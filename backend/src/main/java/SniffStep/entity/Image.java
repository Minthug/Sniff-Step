package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_url")
    private String s3Url;

    @Column(name = "image_file_path")
    private String s3FilePath;

    @Builder
    public Image(String name, String s3Url, String s3FilePath) {
        this.name = name;
        this.s3Url = s3Url;
        this.s3FilePath = s3FilePath;
    }

    public void updatePost(Board board) {
        this.board = board;
    }
}
