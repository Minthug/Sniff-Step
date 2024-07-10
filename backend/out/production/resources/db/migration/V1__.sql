ALTER TABLE board
    ADD activity_dates VARCHAR(255);

ALTER TABLE board
    ADD activity_times VARCHAR(255);

ALTER TABLE image
    ADD member_id BIGINT;

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE board
    DROP COLUMN activity_date;

ALTER TABLE board
    DROP COLUMN activity_time;