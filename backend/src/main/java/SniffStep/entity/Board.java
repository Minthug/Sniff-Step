package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.dto.BoardRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "board")
public class Board extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String description;

    private String activityLocation;

    private LocalDate activityDate;

    private LocalTime activityTime;

    private File imageFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type")
    private BoardType boardType;

    public Board(Long id, String title, String description, String activityLocation,
                 LocalDate activityDate, LocalTime activityTime, File imageFile, Member author, BoardType boardType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.imageFile = imageFile;
        this.author = author;
        this.boardType = boardType;
    }

    @Builder


    public Board updateBoard(BoardRequestDTO boardRequestDTO) {
        this.title = boardRequestDTO.getTitle();
        this.description = boardRequestDTO.getDescription();
        this.activityLocation = boardRequestDTO.getActivityLocation();
        this.activityDate = LocalDate.parse(boardRequestDTO.getActivityDate());
        this.activityTime = LocalTime.parse(boardRequestDTO.getActivityTime());
        return this;
    }

    public void updateMember(Member member) {
        this.author = member;
    }
}
