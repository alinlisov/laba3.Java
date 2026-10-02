public enum LiftCapacity {LIFTS_10(10), LIFTS_20(20), LIFTS_50(50), LIFTS_100(100);
    private final int value;
    LiftCapacity(int value) {this.value = value;}
    public int getValue() {return value;}
}