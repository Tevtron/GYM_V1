import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeiertageApiClient {

    public static Set<LocalDate> getFeiertageNRW(int jahr) {
        Set<LocalDate> feiertage = new HashSet<>();

        try {
            String url = "https://date.nager.at/api/v3/PublicHolidays/" + jahr + "/DE-NW";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON-Daten parsen
            Pattern datePattern = Pattern.compile("\"date\":\"(\\d{4}-\\d{2}-\\d{2})\"");
            Matcher matcher = datePattern.matcher(response.body());

            while (matcher.find()) {
                feiertage.add(LocalDate.parse(matcher.group(1)));
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("⚠️ Fehler beim Laden der Feiertage: " + e.getMessage());
        }

        return feiertage;
    }
}
