import java.util.*;
import java.time.LocalDate;


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
            System.out.println("8. Impressum/Infortmationen");
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
                case 8 -> {
                    System.out.println("\nGYM Cleverfit Siegen");
                    System.out.println("Inhaber: Sergej Wagner, Michael Wagner");
                    System.out.println("\nStraße: Freudenberger Str. 488");
                    System.out.println("Stadt: 57072 Siegen");
                    System.out.println("\nÖffnungszeiten:");
                    String ergebnis = OeffnungszeitenCheck.pruefeOeffnungszeiten();
                    System.out.println(" " + ergebnis);
                    System.out.println("Montag - Freitag: 07:00Uhr - 23:00Uhr");
                    System.out.println("Samstag - Sonntag: 09:00Uhr - 20:00Uhr");
                    System.out.println("Feiertags: 09:00Uhr - 16:00Uhr");
                    System.out.println("\nEmail: studio@siegen.clever-fit.com");
                    System.out.println("TEL: +49 (0) 27180918578");
                }
                case 0 -> {
                    running = false;
                    System.out.println("Programm beendet.");
                }
                default -> System.out.println("Ungültig! Bitte erneut auswählen.");
            }
        }
    }


    private static void mitgliedErstellen() {
        System.out.print("Name des Mitglieds: ");
        String name = scanner.nextLine();
        System.out.print("Geburtsdatum (Tag-Monat-Jahr): ");
        String geburtsdatum = scanner.nextLine();
        studio.mitgliedAnmelden(name, geburtsdatum);
    }

    private static void trainerErstellen() {
        System.out.print("Name des Trainers: ");
        String name = scanner.nextLine();
        System.out.print("Spezialisierungen (Oberkörper, Unterkörper ; Mann, Frau, Divers): ");
        List<String> spezialisierungen = Arrays.asList(scanner.nextLine().split(","));
        studio.trainerEintragen(name, spezialisierungen);
    }

    private static void kursErstellen() {
        System.out.print("Name des Kurses: ");
        String name = scanner.nextLine();
        System.out.print("Leiter des Kurses: ");
        String leiter = scanner.nextLine();
        System.out.print("Zeit (XX:XX): ");
        String zeit = scanner.nextLine();
        System.out.print("Maximale Teilnehmeranzahl: ");
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
}

class Mitglied extends VerwalteteEntitaet {
    private String geburtsdatum;
    private List<Kurs> kurse;

    public Mitglied(String name, int id, String geburtsdatum) {
        super(name, id);
        this.geburtsdatum = geburtsdatum;
        this.kurse = new ArrayList<>();
    }

    public void fuerKursAnmelden(Kurs kurs) {
        if (kurs.hatPlatz()) {
            kurse.add(kurs);
            kurs.teilnehmerHinzufuegen(this);
        } else {
            System.out.println("Kurs ist voll!");
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

    public List<String> getSpezialisierungen() {
        return spezialisierungen;
    }

    @Override
    public String toString() {
        return "Trainer ID: " + getId() + " | Name: " + getName() + " , Spezialisierungen: " + String.join(", ", spezialisierungen);
    }
}

abstract class VerwalteteEntitaet {
    protected String name;
    protected int id;

    public VerwalteteEntitaet(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

class Kurs extends VerwalteteEntitaet {
    private String leiter;
    private String zeit;
    private int maxTeilnehmer;
    private List<Mitglied> teilnehmer;

    public Kurs(String name, int id, String leiter, String zeit, int maxTeilnehmer) {
        super(name, id);
        this.leiter = leiter;
        this.zeit = zeit;
        this.maxTeilnehmer = maxTeilnehmer;
        this.teilnehmer = new ArrayList<>();
    }

    public boolean hatPlatz() {
        return teilnehmer.size() < maxTeilnehmer;
    }

    public void teilnehmerHinzufuegen(Mitglied mitglied) {
        if (hatPlatz()) {
            teilnehmer.add(mitglied);
            System.out.println(mitglied.getName() + " wurde in den Kurs " + name + " hinzugefügt.");
        } else {
            System.out.println("Kurs ist voll! " + mitglied.getName() + " kann nicht teilnehmen!");
        }
    }

    public void teilnehmerAnzeigen() {
        if (teilnehmer.isEmpty()) {
            System.out.println("Dieser Kurs hat noch keine Teilnehmer.");
        } else {
            System.out.println("Teilnehmer des Kurses '" + name + "':");
            for (Mitglied m : teilnehmer) {
                System.out.println(m);
            }
        }
    }

    @Override
    public String toString() {
        return "Kurs ID: " + getId() + " | Name: " + getName() + " | Leiter: " + leiter + " | Zeit: " + zeit + " | Max Teilnehmer: " + maxTeilnehmer;
    }
}


class FitnessStudio {
    private String name;
    private String adresse;
    private List<Mitglied> mitglieder;
    private List<Trainer> trainer;
    private List<Kurs> kurse;
    private int mitgliedCounter = 1;
    private int trainerCounter = 1;
    private int kursCounter = 1;

    public FitnessStudio(String name, String adresse) {
        this.name = name;
        this.adresse = adresse;
        this.mitglieder = new ArrayList<>();
        this.trainer = new ArrayList<>();
        this.kurse = new ArrayList<>();
    }

    public void mitgliedAnmelden(String name, String geburtsdatum) {
        Mitglied m = new Mitglied(name, mitgliedCounter++, geburtsdatum);
        mitglieder.add(m);
        System.out.println("Mitglied " + name + " wurde erfolgreich erstellt.");
    }

    public void trainerEintragen(String name, List<String> spezialisierungen) {
        Trainer t = new Trainer(name, trainerCounter++, spezialisierungen);
        trainer.add(t);
        System.out.println("Trainer " + name + " wurde erfolgreich erstellt.");
    }

    public void kursErstellen(String name, String leiter, String zeit, int maxTeilnehmer) {
        Kurs k = new Kurs(name, kursCounter++, leiter, zeit, maxTeilnehmer);
        kurse.add(k);
        System.out.println("Kurs " + name + " wurde erfolgreich erstellt.");
    }

    public void mitgliedInKursAnmelden(int mitgliedId, int kursId) {
        Mitglied mitglied = mitglieder.stream().filter(m -> m.getId() == mitgliedId).findFirst().orElse(null);
        Kurs kurs = kurse.stream().filter(k -> k.getId() == kursId).findFirst().orElse(null);

        if (mitglied == null || kurs == null) {
            System.out.println("Mitglied oder Kurs nicht gefunden!");
            return;
        }

        mitglied.fuerKursAnmelden(kurs);
    }

    public void mitgliederAnzeigen() {
        System.out.println("\n👤 Mitglieder-Liste:");
        if (mitglieder.isEmpty()) {
            System.out.println("Keine Mitglieder vorhanden.");
            return;
        }
        for (Mitglied m : mitglieder) {
            System.out.println(m);
        }
    }

    public void trainerAnzeigen() {
        System.out.println("\n🏋 Trainer-Liste:");
        if (trainer.isEmpty()) {
            System.out.println("Keine Trainer vorhanden.");
            return;
        }
        for (Trainer t : trainer) {
            System.out.println(t);
        }
    }

    public void kurseAnzeigen() {
        System.out.println("\n📅 Kurs-Liste:");
        if (kurse.isEmpty()) {
            System.out.println("Keine Kurse vorhanden.");
            return;
        }
        for (Kurs k : kurse) {
            System.out.println(k);
            k.teilnehmerAnzeigen();
        }
    }
}
