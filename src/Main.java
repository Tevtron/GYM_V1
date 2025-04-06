//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public.string ("name")
    public.string("adresse")
    public static void main(String[] args) {

        class GYM{
            String gymname;
            String gymadresse;
        }

        class VerwalteteEntität{

        }

        class Mitglied {
            String name;
            int mitgliedsnummer;
            String geburtsdatum;

            Mitglied(String name, int mitgliedsnummer, String geburtsdatum) {
                this.name = name;
                this.mitgliedsnummer = mitgliedsnummer;
                this.geburtsdatum = geburtsdatum;
            }

            void anzeigen() {
                System.out.println("Mitglied: " + name + ", ID: " + mitgliedsnummer + ", Geb.: " + geburtsdatum);
            }
        }

        class Kurs{

        }

        class Trainer{

        }
    }
}