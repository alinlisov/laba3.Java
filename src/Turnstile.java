import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class Turnstile {
    private final SkiPassSystem system;

    private int totalPasses = 0;
    private int totalFails = 0;

    private final Map<SkiPassType, Integer> passesByType = new EnumMap<>(SkiPassType.class);
    private final Map<SkiPassType, Integer> failsByType = new EnumMap<>(SkiPassType.class);

    public Turnstile(SkiPassSystem system) {
        this.system = system;
        for (SkiPassType type : SkiPassType.values()) {
            passesByType.put(type, 0);
            failsByType.put(type, 0);
        }
    }

    public boolean validateAndPass(String cardId, LocalDateTime currentTime) {
        SkiPass pass = system.getPass(cardId);

        if (pass == null) {
            System.out.println(" [ВІДМОВА] Картку з ID " + cardId + " не знайдено в системі!");
            totalFails++;
            return false;
        }

        SkiPassType type = pass.getPassType();

        if (pass.isBlocked()) {
            System.out.println(" [ВІДМОВА] Картка " + cardId + " заблокована!");
            registerFail(type);
            return false;
        }

        if (currentTime.isBefore(pass.getValidFrom()) || currentTime.isAfter(pass.getValidTo())) {
            System.out.println(" [ВІДМОВА] Термін дії картки " + cardId + " вичерпано!");
            registerFail(type);
            return false;
        }

        boolean isWeekend = currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY;
        if (type == SkiPassType.WEEKDAY && isWeekend) {
            System.out.println(" [ВІДМОВА] Картка для робочих днів не діє у вихідні!");
            registerFail(type);
            return false;
        }
        if (type == SkiPassType.WEEKEND && !isWeekend) {
            System.out.println(" [ВІДМОВА] Картка для вихідних днів не діє у робочі дні!");
            registerFail(type);
            return false;
        }

        if (pass.getLimitType() == PassLimitType.BY_TIME) {
            int currentHour = currentTime.getHour();
            TimeSlot slot = pass.getTimeSlot();

            if (currentHour < slot.getStartHour() || currentHour >= slot.getEndHour()) {
                System.out.println(" [ВІДМОВА] Прохід у цей час (" + currentHour + ":00) заборонено за вашим тарифом!");
                registerFail(type);
                return false;
            }
        } else if (pass.getLimitType() == PassLimitType.BY_LIFTS) {
            if (pass.getRemainingLifts() <= 0) {
                System.out.println(" [ВІДМОВА] На картці закінчилися поїздки!");
                registerFail(type);
                return false;
            }
            pass.decrementLift();
        }

        System.out.println(" [ПРОХІД ДОЗВОЛЕНО] Вітаємо! Гарного катання. (Картка ID: " + cardId + ")");
        registerPass(type);
        return true;
    }

    private void registerPass(SkiPassType type) {
        totalPasses++;
        passesByType.put(type, passesByType.get(type) + 1);
    }

    private void registerFail(SkiPassType type) {
        totalFails++;
        failsByType.put(type, failsByType.get(type) + 1);
    }

    public void printTotalStats() {
        System.out.println("\n--- СУМАРНА СТАТИСТИКА ТУРНІКЕТУ ---");
        System.out.println("Успішних проходів: " + totalPasses);
        System.out.println("Відмов у проході:   " + totalFails);
        System.out.println("Всього спроб:       " + (totalPasses + totalFails));
    }

    public void printStatsByType() {
        System.out.println("\n --- СТАТИСТИКА ПО ТИПАХ SKI-PASS ---");
        for (SkiPassType type : SkiPassType.values()) {
            System.out.println("Тип: " + type + " -> Дозволено: " + passesByType.get(type) + " | Відмовлено: " + failsByType.get(type));
        }
    }
}