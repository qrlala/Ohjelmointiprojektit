/*
 * laura.launonen@tuni.fi
 * harkkatyö
 * lausekielinen ohjelmointi 2
 *
 * Ohjelma lataa kuvan tiedostosta taulukkoon, jota käyttäjä voi käskyjen mukaan muokata.
 *
 */
import java.io.*;
import java.util.Scanner;
public class BimEdit{

    //luokkavakiot ohjelmalle
    public static final String LOPETA = "quit";
    public static final String TULOSTA = "print";
    public static final String INFO = "info";
    public static final String INVERT = "invert";
    public static final String DILAATIO = "dilate";
    public static final String ERODE = "erode";
    public static final String LATAUS = "load";

    public static final Scanner lukija = new Scanner(System.in);
    public static void main(String[] args) {
        //pääohjelma tarkistaa komentoriviparametrit ja komennon ja kutsuu komennonsuoritusmetodia

        //alkunäytön tulostus
        tervehdys();

        //tarkistetaan onko annettu komentoriviparametri oikea
        if(args.length == 1 || (args.length == 2 && args[1].equals("echo"))) {

            //parametrin ensimmäinen osa on tiedoston nimi
            String tiedostonNimi = args[0];

            //apumuuttuja, johon lisätään tausta- ja edustavärit
            char[] merkit = new char[2];

            //ladataan tiedostosta kuva taulukkoon
            char[][] kuva = lataaKuvaTaulukkoon(tiedostonNimi, merkit);
            
            //jos kuva ei ole tyhjä, niin tarkistetaan onko komento kaksiosainen vai yksiosainen.
            if (kuva != null) {
                
                boolean jatketaan = true;
                while (jatketaan) {
                    System.out.println("print/info/invert/dilate/erode/load/quit?");
                    //luetaan syöte käyttäjältä
                    String komento = lukija.nextLine();

                    //Jos komentoriviparametreissa on annettu käsky "echo", tulostetaan se syöttämisen jälkeen näytölle
                    if (args.length == 2 && args[1].equals("echo")) {
                    System.out.println(komento);
                    }
                    
                    if (komento.equals(LOPETA)) {
                        jatketaan = false;
                    }
                    //Suoritetaan valittu komento
                    // tarkastetaan dilate ja erode käskyistä, että ne sisältävät numeron
                    else if (komento.split(" ")[0].equals(DILAATIO) && komento.split(" ").length == 2) {

                        String[] komennonOsat = komento.split(" ");
                        int syote = Integer.parseInt(komennonOsat[1]);
                        char etuMerkki = merkit[1];
                        kuva = dilateJaErode(kuva, syote, etuMerkki);
                    }

                    else if (komento.split(" ")[0].equals(ERODE) && komento.split(" ").length == 2) {
                        String[] komennonOsat = komento.split(" ");
                        int syote2 = Integer.parseInt(komennonOsat[1]);
                        char takaMerkki = merkit[0];
                        kuva = dilateJaErode(kuva, syote2, takaMerkki);
                    }
                    else if (komento.split(" ").length == 1 && komento.equals(TULOSTA)) {
                        kuvanTulostus(kuva);
                    }
                    else if (komento.split(" ").length == 1 && komento.equals(INFO)) {
                        infous(kuva, merkit);
                    }
                    else if (komento.split(" ").length == 1 && komento.equals(INVERT)) {
                        kuva = inverttaus(kuva, merkit);
                    }
                    else if (komento.split(" ").length == 1 && komento.equals(LATAUS)){
                        kuva = lataaKuvaTaulukkoon(tiedostonNimi, merkit);
                    }
                    else {
                        System.out.println("Invalid command!");
                    }
                }
                System.out.println("Bye, see you soon.");
            }
            //jos kuva on tyhjä
            else {
                System.out.println("Invalid image file!");
                System.out.println("Bye, see you soon.");
            }
        }
        //jos komentoriviparametrit ovat virheelliset
        else {
            System.out.println("Invalid command-line argument!");
            System.out.println("Bye, see you soon.");
        }
    }
    // PRINT-syötteen METODI
    public static void kuvanTulostus(char[][] kuva) {
        if (kuva != null) {
            // Käydään läpi rivi kerrallaan
            for (int rivi = 0; rivi < kuva.length; rivi++) {
                // Käydään sarakkeet läpi.
                for (int sarake = 0; sarake < kuva[0].length; sarake++) {
                    //tulostetaan taulukko
                    System.out.print(kuva[rivi][sarake]);
                }
                // Rivin lopussa vaihdetaan riviä.
                System.out.println();
            }
        }
    }

    //METODI info syötteelle
    public static void infous(char[][] kuva, char[] merkit) {
        //tehdään tarvittavat muuttujat
        int riviLkm = kuva.length;
        int sarakeLkm = kuva[0].length;
        char taustaMerkki = merkit[0];
        char etuMerkki = merkit[1];
        int eMerkkiLkm = 0;
        int tMerkkiLkm = 0;

        //käydään rivit läpi
        for (int rivi = 0; rivi < kuva.length; rivi++) {
            // Käydään sarakkeet läpi.
            for (int sarake = 0; sarake < kuva[rivi].length; sarake++) {

                //lasketaan edusta- ja taustamerkkien lukumäärä
                if (kuva[rivi][sarake] == taustaMerkki){
                    tMerkkiLkm++;
                }
                else if (kuva[rivi][sarake] == etuMerkki) {
                    eMerkkiLkm++;
                }
            }
        }
        //tulostetaan kuvan info
        System.out.println(riviLkm + " x " + sarakeLkm);
        System.out.println(taustaMerkki + " " + tMerkkiLkm);
        System.out.println(etuMerkki + " " + eMerkkiLkm);

    }
    //METODI INVERT-SYÖTTEELLE
    public static char[][]inverttaus(char[][] kuva, char[] merkit) {
        char a = merkit[0];
        char b = merkit[1];
        //tämä vaihtaa merkit a:sta b:hen
        if (kuva != null && kuva.length > 0 && kuva[0].length > 0 && a > 0 && b > 0) {
            // Käydään läpi kaikki taulukon alkiot kaksinkertaisen silmukan avulla.
            int rivilkm = kuva.length;
            for (int rivi = 0; rivi < rivilkm; rivi++) {
                for (int sarake = 0; sarake < kuva[rivi].length; sarake++) {
                    // Vaihdetaan merkki a merkiksi b.
                    if (kuva[rivi][sarake] == a) {
                        kuva[rivi][sarake] = b;
                    }
                    // Vaihdetaan merkki b merkiksi a.
                    else if (kuva[rivi][sarake] == b) {
                        kuva[rivi][sarake] = a;
                    }
                }
            }
            merkit[0] = b;
            merkit[1] = a;
            //palautetaan muutettu taulukko
            return kuva;
        }
        else {
            return null;
        }
    }


    //METODI DILATE- ja ERODE käskyille, suurentaa ja pienentää kuvaa
    public static char[][] dilateJaErode(char[][] kuva, int syote, char merkki) {
        if (kuva != null && kuva.length > 0 && kuva[0].length > 0) {
            int riviLkm = kuva.length;
            int sarakeLkm = kuva[0].length;

            char[][] uusiKuva = new char[riviLkm][sarakeLkm];

            //kopioidaan kuva uudeksi kuvaksi
            for (int rivi = 0; rivi < riviLkm; rivi++) {
                for (int sarake = 0; sarake < sarakeLkm; sarake++) {
                    uusiKuva[rivi][sarake] = kuva[rivi][sarake];
                }
            }


            //syöte = käyttäjän komennon perässä oleva luku,
            //joka kertoo kuvan koon, määritellään pääohjelmassa
            if (syote < 3 || (syote % 2) == 0 || syote > sarakeLkm) {
                System.out.println("Invalid command!");
            }
            else {
                //r = taulukon reunat, jotka eivät ole "ikkunan" keskikohdassa koskaan
                int r = (syote - 1) / 2;
                //käydään läpi taulukkoa, jätetään r kokoiset reunat käymättä
                for (int rivi = r; rivi < kuva.length - r; rivi++) {
                    for (int sarake = r; sarake < kuva[rivi].length - r; sarake++){
                        for (int x = rivi - r; x < rivi + r + 1; x++) {
                            for (int y = sarake - r; y < sarake + r + 1; y++) {

                                if (kuva[x][y] == merkki) {
                                    uusiKuva[rivi][sarake] = merkki;
                                }
                            }
                        }
                    }
                }
            }
            return uusiKuva;
        }
        return null;
    }

    //ALKUNÄYTÖN TULOSTUS METODI tervehdys
    public static void tervehdys() {
        System.out.println("-----------------------");
        System.out.println("| Binary image editor |");
        System.out.println("-----------------------");
    }

    //KUVAN LATAUSMETODI
    public static char[][] lataaKuvaTaulukkoon(String tiedostonNimi, char[] merkit) {
        Scanner tiedostonLukija = null;

        //varaudutaan poikkeukseen ja yritetään lukea tiedostoa
        try {
            //luodaan tiedosto-olio
            if (merkit.length == 2 && tiedostonNimi != null) {
                File tiedosto = new File(tiedostonNimi);
                //liitetään lukija tiedostoon
                tiedostonLukija = new Scanner(tiedosto);

                //luodaan muuttujat tiedon keräykselle tiedoston alusta 4 riviä.
                int riviLkm = Integer.parseInt(tiedostonLukija.nextLine());
                int sarakeLkm = Integer.parseInt(tiedostonLukija.nextLine());
                char merkki1 = tiedostonLukija.nextLine().charAt(0);
                char merkki2 = tiedostonLukija.nextLine().charAt(0);

                //kuvan tausta ja edustamerkit
                merkit[0] = merkki1;
                merkit[1] = merkki2;   
                
                //tarkistetaan että koot ovat sopivat
                if (riviLkm < 3 || sarakeLkm < 3 ) {
                    return null;
                }
                else {
                    char[][] kuva = new char[riviLkm][sarakeLkm];
                    
                    int i = 0;
                    while (tiedostonLukija.hasNextLine()) {
                        //käydään läpi rivejä
                        String onkoLisaa = tiedostonLukija.nextLine();
                        for (int rivi = 0; rivi < onkoLisaa.length(); rivi++ ) {
                            char merkki = onkoLisaa.charAt(rivi);
                            if ((merkki == merkki1 || merkki == merkki2) && onkoLisaa.length() == sarakeLkm){
                               kuva[i][rivi] = merkki;
                            }
                            else{
                                return null;
                            }
      
                        }
                        i++;
                    }
                    if (i != riviLkm){
                        return null;
                    }
                //suljetaan lukija
                tiedostonLukija.close();
                return kuva;
                }
            }   
        }
        //siepataan poikkeus, tai jos kuva on tyhjä
        catch (Exception e){
            if (tiedostonLukija != null) {
                tiedostonLukija.close();
            }
            return null;
        }
    //palautus
    return null;
    }
}
