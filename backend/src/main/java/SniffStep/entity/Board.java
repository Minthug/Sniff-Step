package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.dto.BoardPatchDTO;
import SniffStep.dto.BoardRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private String activityDate;

    private String activityTime;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type")
    private BoardType boardType;

    @Builder
    public Board(String title, String description, String activityLocation, String activityDate, String activityTime) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }

    public void updateBoard(Long id, BoardPatchDTO patch) {
        this.title = patch.getTitle();
        this.description = patch.getDescription();
        this.activityLocation = patch.getActivityLocation();
        this.activityDate = patch.getActivityDate();
        this.activityTime = patch.getActivityTime();
    }
    public void saveMember(Member member) {
        this.author = member;
    }
}
