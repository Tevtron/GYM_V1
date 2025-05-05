import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class OeffnungszeitenCheck {

    public static String pruefeOeffnungszeiten() {
        LocalDate heute = LocalDate.now();
        LocalTime aktuelleZeit = LocalTime.now();
        DayOfWeek tag = heute.getDayOfWeek();

        Set<LocalDate> feiertage = FeiertageApiClient.getFeiertageNRW(heute.getYear());

        LocalTime oeffnungszeit;
        LocalTime schliesszeit;

        if (feiertage.contains(heute)) {
            oeffnungszeit = LocalTime.of(9, 0);   // Feiertag: 9–16 Uhr
            schliesszeit = LocalTime.of(16, 0);
        } else if (tag == DayOfWeek.SATURDAY || tag == DayOfWeek.SUNDAY) {
            oeffnungszeit = LocalTime.of(9, 0);   // Wochenende: 9–20 Uhr
            schliesszeit = LocalTime.of(20, 0);
        } else {
            oeffnungszeit = LocalTime.of(7, 0);   // Mo–Fr: 7–23 Uhr
            schliesszeit = LocalTime.of(23, 0);
        }

        // Wenn geöffnet
        if (aktuelleZeit.isAfter(oeffnungszeit) && aktuelleZeit.isBefore(schliesszeit)) {
            // Ausgabe der Schließzeit
            return String.format("✅ GEÖFFNET" + "Es schließt heute um: %s Uhr.", schliesszeit);
        } else {
            return "❌ Aktuell GESCHLOSSEN";
        }
    }
}
