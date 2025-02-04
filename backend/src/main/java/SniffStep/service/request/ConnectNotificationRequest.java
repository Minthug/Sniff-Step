package SniffStep.service.request;

public record ConnectNotificationRequest(Long memberId, String lastEventId) {

    public static ConnectNotificationRequest of(Long memberId, String lastEventId) {
        return new ConnectNotificationRequest(memberId, lastEventId);
    }
}
