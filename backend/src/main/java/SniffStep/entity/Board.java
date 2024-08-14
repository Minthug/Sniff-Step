package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String description;

    private String activityLocation;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type")
    private BoardType boardType;

    @Column(name = "activity_dates")
    private String activityDate;

    @Column(name = "activity_times")
    private String activityTime;


    public Board(String title, String description, String activityLocation, Member member, List<Image> images, List<ActivityDate> activityDate, List<ActivityTime> activityTime) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.images = new ArrayList<>();
        this.member = member;
        setActivityDateInternal(activityDate);
        setActivityTimeInternal(activityTime);
        initializeImages(images);
    }

    private void initializeImages(List<Image> images) {
        if (images != null) {
            images.forEach(this::addImages);
        }
    }

    public void setActivityDateInternal(List<ActivityDate> activityDate) {
        this.activityDate = activityDate.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    public void setActivityTimeInternal(List<ActivityTime> activityTime) {
        this.activityTime = activityTime.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    public List<ActivityDate> getActivityDates() {
        if (activityDate == null || activityDate.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(activityDate.split(","))
                .map(ActivityDate::valueOf)
                .collect(Collectors.toList());
    }

    public List<ActivityTime> getActivityTimes() {
        if (activityTime == null || activityTime.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(activityTime.split(","))
                .map(ActivityTime::valueOf)
                .collect(Collectors.toList());
    }

    public void update(String title, String description, String activityLocation,
                       List<ActivityDate> activityDates, List<ActivityTime> activityTimes) {
        if (title != null && !title.isEmpty()) this.title = title;
        if (description != null) this.description = description;
        if (activityLocation != null && !activityLocation.isEmpty()) this.activityLocation = activityLocation;
        if (activityDates != null && !activityDates.isEmpty()) setActivityDateInternal(activityDates);
        if (activityTimes != null && !activityTimes.isEmpty()) setActivityTimeInternal(activityTimes);
    }

    public boolean isOwnBoard(Member member) {
        return this.member.equals(member);
    }


    public void addImages(Image image) {
        this.images.add(image);
        image.assignToBoard(this);
    }

    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

}
