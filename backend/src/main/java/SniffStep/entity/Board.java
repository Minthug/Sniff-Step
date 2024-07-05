package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
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

    private String description;

    private String activityLocation;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

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

    private void setActivityDateInternal(List<ActivityDate> activityDate) {
        this.activityDate = activityDate.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    private void setActivityTimeInternal(List<ActivityTime> activityTime) {
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

    public void update(String title, String description, String activityLocation) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
    }

    public boolean isOwnBoard(Member member) {
        return this.member.equals(member);
    }


    public void addImages(Image image) {
        this.images.add(image);
        image.assignToBoard(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.assignToBoard(null);
    }

    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

    public void updateBoardWithImages(String title, String description, String activityLocation, List<Image> newImage) {
        this.update(title, description, activityLocation);
        this.images.clear();
        initializeImages(newImage);
    }
}
