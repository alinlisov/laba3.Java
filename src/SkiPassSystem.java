import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkiPassSystem {
    private final Map<String, SkiPass> registry = new HashMap<>();

    // Випуск картки по часу (з перевіркою обмеження 5 днів для вихідних)
    public SkiPass issueTimePass(SkiPassType passType, TimeSlot slot, int daysValid) {
        if (passType == SkiPassType.WEEKEND && daysValid > 2) {
            throw new IllegalArgumentException("Абонемент на вихідні не може перевищувати 2 дні!");
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTo = now.plusDays(daysValid);

        SkiPass pass = new SkiPass(id, passType, PassLimitType.BY_TIME, slot, now, validTo, 0);
        registry.put(id, pass);
        return pass;
    }

    // Випуск картки за чіткою кількістю підйомів (10, 20, 50, 100)
    public SkiPass issueLiftsPass(SkiPassType passType, LiftCapacity capacity, int daysValid) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTo = now.plusDays(daysValid);

        SkiPass pass = new SkiPass(id, passType, PassLimitType.BY_LIFTS, null, now, validTo, capacity.getValue());
        registry.put(id, pass);
        return pass;
    }

    public SkiPass issueSeasonalPass(int seasonDays) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTo = now.plusDays(seasonDays);

        SkiPass pass = new SkiPass(id, SkiPassType.SEASONAL, PassLimitType.BY_TIME, TimeSlot.FULL_DAY, now, validTo, 0);
        registry.put(id, pass);
        return pass;
    }

    public boolean blockPass(String id) {
        SkiPass pass = registry.get(id);
        if (pass != null) {
            pass.setBlocked(true);
            return true;
        }
        return false;
    }

    public SkiPass getPass(String id) {
        return registry.get(id);
    }
}