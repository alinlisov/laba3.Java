public enum TimeSlot {
    HALF_DAY_MORNING(9, 13),
    HALF_DAY_AFTERNOON(13, 17),
    FULL_DAY(9, 17),
    TWO_DAYS(9, 17),
    FIVE_DAYS(9, 17);

    private final int startHour;
    private final int endHour;

    TimeSlot(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() { return startHour; }
    public int getEndHour() { return endHour; }
}