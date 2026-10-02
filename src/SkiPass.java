import java.time.LocalDateTime;

public class SkiPass {
    private final String id;
    private final SkiPassType passType;
    private final PassLimitType limitType;
    private final TimeSlot timeSlot;
    private final LocalDateTime validFrom;
    private final LocalDateTime validTo;

    private int remainingLifts;
    private boolean isBlocked;

    public SkiPass(String id, SkiPassType passType, PassLimitType limitType,
                   TimeSlot timeSlot, LocalDateTime validFrom, LocalDateTime validTo, int remainingLifts) {
        this.id = id;
        this.passType = passType;
        this.limitType = limitType;
        this.timeSlot = timeSlot;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.remainingLifts = remainingLifts;
        this.isBlocked = false;
    }

    public String getId() { return id; }
    public SkiPassType getPassType() { return passType; }
    public PassLimitType getLimitType() { return limitType; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public LocalDateTime getValidTo() { return validTo; }
    public int getRemainingLifts() { return remainingLifts; }
    public boolean isBlocked() { return isBlocked; }

    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    public boolean decrementLift() {
        if (remainingLifts > 0) {
            remainingLifts--;
            return true;
        }
        return false;
    }
}