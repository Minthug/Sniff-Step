package SniffStep.service.request;

import java.util.Objects;

public class ConnectNotificationCommand {

    private final Long memberId;
    private final String lastEventId;

    public ConnectNotificationCommand(Long memberId, String lastEventId) {
        this.memberId = memberId;
        this.lastEventId = lastEventId;
    }

    public static ConnectNotificationCommand of(final Long memberId, final String lastEventId) {
        return new ConnectNotificationCommand(memberId, lastEventId);
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getLastEventId() {
        return lastEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectNotificationCommand that = (ConnectNotificationCommand) o;
        return Objects.equals(memberId, this.memberId) && Objects.equals(lastEventId, this.lastEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, lastEventId);
    }

    @Override
    public String toString() {
        return "ConnectNotificationCommand{" +
                "memberId=" + memberId +
                ", lastEventId='" + lastEventId + '\'' +
                '}';
    }
}
