import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        SkiPassSystem system = new SkiPassSystem();
        Turnstile turnstile = new Turnstile(system);

        System.out.println("~ ВИПУСК КАРТОК ~");
        SkiPass p1 = system.issueLiftsPass(SkiPassType.WEEKDAY, LiftCapacity.LIFTS_10, 5);
        SkiPass p2 = system.issueTimePass(SkiPassType.WEEKDAY, TimeSlot.HALF_DAY_MORNING, 1);
        SkiPass p3 = system.issueSeasonalPass(90);

        System.out.println("\n~ ТЕСТУВАННЯ ТУРНІКЕТУ ~");

        LocalDateTime mondayMorning = LocalDateTime.of(2026, 10, 5, 10, 0);
        LocalDateTime sundayNoon = LocalDateTime.of(2026, 10, 4, 12, 0);

        turnstile.validateAndPass(p1.getId(), mondayMorning);
        turnstile.validateAndPass(p1.getId(), mondayMorning);
        turnstile.validateAndPass(p1.getId(), mondayMorning);

        turnstile.validateAndPass(p2.getId(), sundayNoon);
        turnstile.validateAndPass(p3.getId(), mondayMorning);

        System.out.println("\n--- Блокуємо сезонну картку через порушення ---");
        system.blockPass(p3.getId());
        turnstile.validateAndPass(p3.getId(), mondayMorning);

        turnstile.validateAndPass("UNKNOWN_ID", mondayMorning);

        turnstile.printTotalStats();
        turnstile.printStatsByType();
    }
}