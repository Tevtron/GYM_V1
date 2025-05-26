import java.util.*;
import java.time.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FitnessStudio studio = new FitnessStudio("GYM Cleverfit Siegen", "Freudenberger Str. 488, 57072 Siegen");

    public static void main(String[] args) {
        boolean running = true;


        while (running) {
            System.out.println("\n ");
            System.out.println("GYM Cleverfit Siegen");

            System.out.println("\n-----FitnessStudio Verwaltung-----");
            System.out.println("1. Mitglied erstellen");
            System.out.println("2. Mitglieder anzeigen");
            System.out.println("3. Mitglied in Kurs hinzufügen");
            System.out.println("4. Trainer erstellen");
            System.out.println("5. Trainer anzeigen");
            System.out.println("6. Kurs erstellen");
            System.out.println("7. Kurse anzeigen");
            System.out.println("8. Impressum/Informationen");
            System.out.println("9. Mitglied suchen / sortieren");
            System.out.println("0. Beenden");
            System.out.print("Wähle eine Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> mitgliedErstellen();
                case 2 -> studio.mitgliederAnzeigen();
                case 3 -> mitgliedInKursHinzufügen();
                case 4 -> trainerErstellen();
                case 5 -> studio.trainerAnzeigen();
                case 6 -> kursErstellen();
                case 7 -> studio.kurseAnzeigen();
                case 8 -> impressumAnzeigen();
                case 9 -> mitgliedSuchmenue();
                case 0 -> {
                    running = false;
                    System.out.println("Programm beendet.");
                }
                default -> System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            }
        }
    }

    private static void mitgliedErstellen() {
        System.out.print("Name des Mitglieds: ");
        String name = scanner.nextLine();
        System.out.print("Geburtsdatum (TT-MM-JJJJ): ");
        String geburtsdatum = scanner.nextLine();
        studio.mitgliedAnmelden(name, geburtsdatum);
    }

    private static void trainerErstellen() {
        System.out.print("Name des Trainers: ");
        String name = scanner.nextLine();
        System.out.print("Spezialisierungen (z.B. Kraft, Cardio, Yoga): ");
        List<String> spezialisierungen = Arrays.asList(scanner.nextLine().split(","));
        studio.trainerEintragen(name, spezialisierungen);
    }

    private static void kursErstellen() {
        System.out.print("Name des Kurses: ");
        String name = scanner.nextLine();
        System.out.print("Leiter des Kurses: ");
        String leiter = scanner.nextLine();
        System.out.print("Zeit (z.B. 18:00): ");
        String zeit = scanner.nextLine();
        System.out.print("Maximale Teilnehmerzahl: ");
        int maxTeilnehmer = scanner.nextInt();
        scanner.nextLine();

        studio.kursErstellen(name, leiter, zeit, maxTeilnehmer);
    }

    private static void mitgliedInKursHinzufügen() {
        studio.mitgliederAnzeigen();
        System.out.print("ID des Mitglieds: ");
        int mitgliedId = scanner.nextInt();
        scanner.nextLine();

        studio.kurseAnzeigen();
        System.out.print("ID des Kurses: ");
        int kursId = scanner.nextInt();
        scanner.nextLine();

        studio.mitgliedInKursAnmelden(mitgliedId, kursId);
    }

    private static void impressumAnzeigen() {
        System.out.println("\nGYM Cleverfit Siegen");
        System.out.println("Inhaber: Sergej Wagner, Michael Wagner");
        System.out.println("Adresse: Freudenberger Str. 488, 57072 Siegen");
        System.out.println("\nÖffnungszeiten:");
        System.out.println(" " + OeffnungszeitenCheck.pruefeOeffnungszeiten()); // bereits eingebunden
        System.out.println("Mo–Fr: 07:00–23:00 | Sa–So: 09:00–20:00 | Feiertags: 09:00–16:00");
        System.out.println("Email: studio@siegen.clever-fit.com | Tel: +49 (0) 27180918578");
    }

    private static void mitgliedSuchmenue() {
        boolean zurueck = false;

        while (!zurueck) {
            System.out.println("\n🔍 Mitglied suchen / sortieren:");
            System.out.println("1. Suche nach Mitglieds-ID (binär)");
            System.out.println("2. Suche nach Name (linear)");
            System.out.println("3. Mitglieder sortieren + Laufzeit anzeigen");
            System.out.println("0. Zurück zum Hauptmenü");
            System.out.print("Auswahl: ");
            int auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1 -> mitgliedSuchenNachId();
                case 2 -> mitgliedSuchenNachName();
                case 3 -> sortierVergleich();
                case 0 -> zurueck = true;
                default -> System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private static void mitgliedSuchenNachId() {
        System.out.print("Gib die Mitglieds-ID ein: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        studio.sortiereMitgliederNachIdInsertionSort(); // vorsortieren
        Mitglied gefunden = studio.binäreSucheNachId(id);

        if (gefunden != null) {
            System.out.println("✅ Mitglied gefunden: " + gefunden);
        } else {
            System.out.println("❌ Kein Mitglied mit ID " + id + " gefunden.");
        }
    }

    private static void mitgliedSuchenNachName() {
        System.out.print("Gib den Namen des Mitglieds ein: ");
        String name = scanner.nextLine();
        Mitglied m = studio.sucheMitgliedNachName(name);

        if (m != null) {
            System.out.println("✅ Mitglied gefunden: " + m);
        } else {
            System.out.println("❌ Kein Mitglied mit Namen \"" + name + "\" gefunden.");
        }
    }

    private static void sortierVergleich() {
        System.out.print("Wähle Sortierverfahren (binaer/linear): ");
        String methode = scanner.nextLine();

        long dauerNano = studio.sortiereUndMesseZeit(methode);
        long dauerMillis = dauerNano / 1_000_000;

        System.out.println("⏱️ Sortierung mit \"" + methode + "\" dauerte: " + dauerMillis + " ms");
        studio.mitgliederAnzeigen();
    }
}

class FitnessStudio {
    private String name;
    private String adresse;
    private List<Mitglied> mitglieder = new ArrayList<>();
    private List<Trainer> trainer = new ArrayList<>();
    private List<Kurs> kurse = new ArrayList<>();
    private int mitgliedCounter = 1, trainerCounter = 1, kursCounter = 1;

    public FitnessStudio(String name, String adresse) {
        this.name = name;
        this.adresse = adresse;
    }

    public void mitgliedAnmelden(String name, String geburtsdatum) {
        Mitglied m = new Mitglied(name, mitgliedCounter++, geburtsdatum);
        mitglieder.add(m);
        System.out.println("✅ Mitglied " + name + " wurde erfolgreich erstellt.");
    }

    public void trainerEintragen(String name, List<String> spezialisierungen) {
        Trainer t = new Trainer(name, trainerCounter++, spezialisierungen);
        trainer.add(t);
        System.out.println("✅ Trainer " + name + " wurde erfolgreich erstellt.");
    }

    public void kursErstellen(String name, String leiter, String zeit, int maxTeilnehmer) {
        Kurs k = new Kurs(name, kursCounter++, leiter, zeit, maxTeilnehmer);
        kurse.add(k);
        System.out.println("✅ Kurs " + name + " wurde erfolgreich erstellt.");
    }

    public void mitgliedInKursAnmelden(int mitgliedId, int kursId) {
        Mitglied m = mitglieder.stream().filter(x -> x.getId() == mitgliedId).findFirst().orElse(null);
        Kurs k = kurse.stream().filter(x -> x.getId() == kursId).findFirst().orElse(null);
        if (m == null || k == null) {
            System.out.println("❌ Mitglied oder Kurs nicht gefunden!");
            return;
        }
        m.fuerKursAnmelden(k);
    }

    public void mitgliederAnzeigen() {
        System.out.println("\n👤 Mitgliederliste:");
        if (mitglieder.isEmpty()) System.out.println("Keine Mitglieder vorhanden.");
        for (Mitglied m : mitglieder) System.out.println(m);
    }

    public void trainerAnzeigen() {
        System.out.println("\n🏋 Trainerliste:");
        if (trainer.isEmpty()) System.out.println("Keine Trainer vorhanden.");
        for (Trainer t : trainer) System.out.println(t);
    }

    public void kurseAnzeigen() {
        System.out.println("\n📅 Kursliste:");
        if (kurse.isEmpty()) System.out.println("Keine Kurse vorhanden.");
        for (Kurs k : kurse) {
            System.out.println(k);
            k.teilnehmerAnzeigen();
        }
    }

    public void sortiereMitgliederNachIdInsertionSort() {
        for (int i = 1; i < mitglieder.size(); i++) {
            Mitglied key = mitglieder.get(i);
            int j = i - 1;
            while (j >= 0 && mitglieder.get(j).getId() > key.getId()) {
                mitglieder.set(j + 1, mitglieder.get(j));
                j--;
            }
            mitglieder.set(j + 1, key);
        }
    }

    public void sortiereMitgliederNachIdBubbleSort() {
        int n = mitglieder.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (mitglieder.get(j).getId() > mitglieder.get(j + 1).getId()) {
                    Mitglied temp = mitglieder.get(j);
                    mitglieder.set(j, mitglieder.get(j + 1));
                    mitglieder.set(j + 1, temp);
                }
            }
        }
    }

    public long sortiereUndMesseZeit(String methode) {
        long start = System.nanoTime();
        switch (methode.toLowerCase()) {
            case "binear" -> sortiereMitgliederNachIdInsertionSort();
            case "linear" -> sortiereMitgliederNachIdBubbleSort();
            default -> System.out.println("❌ Unbekanntes Verfahren.");
        }
        return System.nanoTime() - start;
    }

    public Mitglied binäreSucheNachId(int id) {
        int links = 0, rechts = mitglieder.size() - 1;
        while (links <= rechts) {
            int mitte = links + (rechts - links) / 2;
            int midId = mitglieder.get(mitte).getId();
            if (midId == id) return mitglieder.get(mitte);
            else if (midId < id) links = mitte + 1;
            else rechts = mitte - 1;
        }
        return null;
    }

    public Mitglied sucheMitgliedNachName(String name) {
        for (Mitglied m : mitglieder) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }
}

abstract class VerwalteteEntitaet {
    protected String name;
    protected int id;

    public VerwalteteEntitaet(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }
}

class Mitglied extends VerwalteteEntitaet {
    private String geburtsdatum;
    private List<Kurs> kurse = new ArrayList<>();

    public Mitglied(String name, int id, String geburtsdatum) {
        super(name, id);
        this.geburtsdatum = geburtsdatum;
    }

    public void fuerKursAnmelden(Kurs kurs) {
        if (kurs.hatPlatz()) {
            kurse.add(kurs);
            kurs.teilnehmerHinzufuegen(this);
        } else {
            System.out.println("❌ Kurs ist voll!");
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Geburtsdatum: " + geburtsdatum;
    }
}

class Trainer extends VerwalteteEntitaet {
    private List<String> spezialisierungen;

    public Trainer(String name, int id, List<String> spezialisierungen) {
        super(name, id);
        this.spezialisierungen = spezialisierungen;
    }

    @Override
    public String toString() {
        return "Trainer ID: " + id + " | Name: " + name + " | Spezialisierungen: " + String.join(", ", spezialisierungen);
    }
}

class Kurs extends VerwalteteEntitaet {
    private String leiter, zeit;
    private int maxTeilnehmer;
    private List<Mitglied> teilnehmer = new ArrayList<>();

    public Kurs(String name, int id, String leiter, String zeit, int maxTeilnehmer) {
        super(name, id);
        this.leiter = leiter;
        this.zeit = zeit;
        this.maxTeilnehmer = maxTeilnehmer;
    }

    public boolean hatPlatz() {
        return teilnehmer.size() < maxTeilnehmer;
    }

    public void teilnehmerHinzufuegen(Mitglied m) {
        teilnehmer.add(m);
        System.out.println("✅ " + m.getName() + " wurde in den Kurs '" + name + "' eingetragen.");
    }

    public void teilnehmerAnzeigen() {
        if (teilnehmer.isEmpty()) {
            System.out.println("Keine Teilnehmer.");
        } else {
            System.out.println("Teilnehmer in '" + name + "':");
            for (Mitglied m : teilnehmer) {
                System.out.println(" - " + m.getName());
            }
        }
    }

    @Override
    public String toString() {
        return "Kurs ID: " + id + " | Name: " + name + " | Leiter: " + leiter + " | Zeit: " + zeit + " | Max: " + maxTeilnehmer;
    }
}
