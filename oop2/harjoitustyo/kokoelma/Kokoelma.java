package harjoitustyo.kokoelma;

import java.io.*;
import harjoitustyo.apulaiset.Kokoava;
import harjoitustyo.omalista.OmaLista;
import harjoitustyo.dokumentit.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Konkreettinen Kokoelma-luokka.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet 2, kevat 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnan tiedekunta,
 * Tampereen yliopisto.
 */
 
public class Kokoelma extends Object implements Kokoava<Dokumentti>{
    
    /**attribuutti, joka sisaltaa viitteet dokumenttiolioihin*/
    private OmaLista<Dokumentti> dokumentit;
    
    //parametriton oletusrakentaja
    public Kokoelma(){
        dokumentit = new OmaLista<Dokumentti>();
    }
    
    //aksessori
    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;
    }
    
    /**POISTA-metodi (remove)
    * poistaa annetun tunnisteen mukaisen dokumentin
    * @param tunniste
    */
    public void poistaminen(int tunniste){
        //kutsutaan hae-metodia, joka loytaa oikean dokumentin
        int poistettavaDokumentti = dokumentit.indexOf(hae(tunniste));
        dokumentit.remove(poistettavaDokumentti);
    }
    
    /**
    * LISÄTTY korjaukseen
    * remove-komennon toiminnallisuus siirretty tähän
    * @param syote
    */
    public boolean poistoApu(String syote){
        boolean onnistui = true;
        try{
            int tunniste = Integer.parseInt(syote.split(" ")[1]);
            if (hae(tunniste) != null) {
                poistaminen(tunniste);
            }
            else{
                onnistui = false;
            }
        }
        catch(Exception e){
            onnistui = false;
        }
    return onnistui;
    }
    
    /**
    * Etsi-metodi, joka etsii dokumentteja, joista löytyy annetut hakusanat
    * @param hakusanojen taulukko
    * @return vastaavien dokumenttien tunnisteet
    */
    public LinkedList<Integer> etsi(String[] hakuSanat){
        //palautettava tyhjä uusi lista tunnisteille
        LinkedList<Integer> dokumenttienTunnisteet = new LinkedList<Integer>();
        //tehdään lista hakusanoille, sanatTäsmäävät tarvitsee linkedlist muotoisen listan.
        LinkedList<String> hakuSanojenLista = new LinkedList<String>();
        
        //tallennetaan listaan hakusanat
        for(int i = 1; i < hakuSanat.length; i++){
            hakuSanojenLista.add(hakuSanat[i]);
        }
        //käydään dokumentit läpi
        for(int i = 0; i < dokumentit.size(); i++){
            Dokumentti dokumentti = (Dokumentti)dokumentit.get(i);
            //kutsutaan sanojen tarkistajaa
            boolean loytyy = dokumentti.sanatTäsmäävät(hakuSanojenLista);
            if (loytyy){
                //lisätään tunniste linkedlistiin, jos sanat täsmäävät
                dokumenttienTunnisteet.add(dokumentti.tunniste());
            }
        }
        //palautetaan dokumenttien tunnisteet
        return dokumenttienTunnisteet;
    }

    /**Lisää-metodi (add) kokoava-rajapinnasta metoditoteutus*/
    @Override
    public void lisää(Dokumentti uusi) throws IllegalArgumentException{
        if (dokumentit.contains(uusi) == false && uusi != null){
            dokumentit.lisää(uusi);
        }
        else{
            throw new IllegalArgumentException("Error!");
        }
    }
    
    /**Tiedoston latausmetodi
    *@param komentoriviparametrit
    *@return boolean arvo
    */ 
    public boolean lataaTiedosto(String[] args) {
        //muuttuja tiedoston lataamisen onnistumiselle
        boolean onnistui = true;
        String tiedostonNimi = args[0];
        
        //varaudutaan poikkeukseen ja yritetaan lukea tiedostoa
        try{
            //ladataan tiedosto
            File tiedosto = new File(tiedostonNimi);
            Scanner tiedostonLukija = new Scanner(tiedosto);
            // apumuuttuja dokumentin tyypille
            String tyyppi = tiedostonNimi.split("_")[0];
            
            //eritellään tiedot riveiltä
            while (tiedostonLukija.hasNextLine()){
                //tehdään dokumenttien rivien osista taulukko 
                String[] rivi = tiedostonLukija.nextLine().split("///");
                int tunniste = Integer.parseInt(rivi[0]);
                String laji = rivi[1];
                String teksti = rivi[2];
                
                //tarkistetaan onko dokumentti vitsi
                if (tyyppi.startsWith("news")){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
                    LocalDate paivamaara = LocalDate.parse(rivi[1], formatter);
                    Uutinen uutinen = new Uutinen(tunniste, paivamaara, teksti);
                    this.lisää(uutinen);
                }
                //jos uutinen
                else if (tyyppi.startsWith("jokes")){
                    Vitsi vitsi = new Vitsi(tunniste, laji, teksti);
                    this.lisää(vitsi);
                }
            }
        //suljetaan lukija
        tiedostonLukija.close();
        }
        //siepataan mahdolliset poikkeukset
        catch (Exception e){
            onnistui = false;
        }
    //palautetaan tila
    return onnistui;
    }
    
    /**Sulkusanatiedoston lataus ja tarkistus
    *@param sulkusanat 
    *@return lista sulkusanoja, tai null jos tarkistus epäonnistuu
    */
    public LinkedList<String> lataaSulkusanat(String sulkusanat) {
        
        //tehdaan lista sulkusanoille
        LinkedList<String> sulkusanalista = new LinkedList<String>();

        Scanner tiedostonLukija = null; 
        
        try {
            //sulkusanatiedoston taytyy alkaa sanalla "stop"
            if (sulkusanat.length() > 0 && sulkusanat.split("_")[0].equals("stop")) {
                
                //ladataan tiedosto
                File tiedosto = new File(sulkusanat);
                tiedostonLukija = new Scanner(tiedosto);

                //käydään läpi sulkusanatiedostoa, ja lisätään sulkusanalistalle sanat
                while (tiedostonLukija.hasNextLine()) {
                    sulkusanalista.add(tiedostonLukija.nextLine());
                }
                tiedostonLukija.close();
                
            //palautetaan sulkusanalista
            return sulkusanalista;
            }
            else{
                return null;
            }
        }
        //siepataan mahdollinen poikkeus
        catch (Exception e) {
            if (tiedostonLukija != null) {
                tiedostonLukija.close();
                return null;
            }
        }
    return null;
    }
    
    /**
    * Hakee kokoelmasta dokumenttia, jonka tunniste on sama kuin parametrin
    * arvo.
    * @param tunniste haettavan dokumentin tunniste.
    * @return viite loydettyyn dokumenttiin. Paluuarvo on null, jos haettavaa
    * dokumenttia ei loydetty.
    */
    @Override
    public Dokumentti hae(int tunniste){
        //kaydaan lapi dokumentteja
        for (int i = 0; i < dokumentit.size(); i++){
            Dokumentti avain = (Dokumentti)dokumentit.get(i);
            
            if (avain.tunniste() == tunniste){
                //palautetaan viite dokumenttiin
                return (Dokumentti)dokumentit.get(i);
            }
        }
        return null;
    }
    
    /**
    * Kokoelman siivoa-metodi toimii välikätenä dokumentin siivoa-metodiin
    * @param sulkusanojen lista, välimerkit
    */
    public void siivoa(LinkedList<String> sulkusanalista, String valiMerkit) {
        for (int i = 0; i < dokumentit.size(); i++) {
            Dokumentti dokumentti = (Dokumentti)dokumentit.get(i);
            dokumentti.siivoa(sulkusanalista, valiMerkit);
        }
    }
    
    /**
    * LISÄTTY korjaukseen
    * add-komennon toiminnallisuus
    * @param syote, komentoriviparametrit
    */
    public boolean vitsiVaiUutinen(String syote, String[] args){
        boolean onnistui = true;
        String komento = syote.split(" ")[0];
        //otetaan pois add sana komennosta
        String poisto = syote.replace(komento + " ", "");
        try {
            //erotellaan tiedot, jotta voidaan tarkistaa tunnisteen yksilollisyys 
            String[] tiedotErikseen = poisto.split("///");
            //apumuuttujia
            int tunniste = Integer.parseInt(tiedotErikseen[0]);
            String teksti = tiedotErikseen[2];
            
            if (args[0].split("_")[0].equals("news")) {
                DateTimeFormatter pvm = DateTimeFormatter.ofPattern("d.M.yyyy");
                LocalDate paivamaara = LocalDate.parse(tiedotErikseen[1],pvm);
                Uutinen uutinen = new Uutinen(tunniste, paivamaara, teksti);
                this.lisää(uutinen);
            }
            else if(args[0].split("_")[0].equals("jokes")) {           
                //tarkistetaan onko välissä pvm vai laji. 
                try {
                    DateTimeFormatter pvm = DateTimeFormatter.ofPattern("d.M.yyyy");
                    LocalDate paivamaara = LocalDate.parse(tiedotErikseen[1],pvm);
                    onnistui = false;
                }
                catch(Exception e) {
                    String laji = tiedotErikseen[1];
                    Vitsi vitsi = new Vitsi(tunniste, laji, teksti);
                    this.lisää(vitsi);
                }
            } 
        }
        catch (Exception e) {
            onnistui = false;
        }
    return onnistui;
    }
    
    /**
    * LISÄTTY korjaukseen
    * print-komennon toiminnallisuus
    * @param syote
    */
    public void printtausApu(String syote){
        try{               
            int tunniste = Integer.parseInt(syote.split(" ")[1]); 
            if (hae(tunniste) != null){
                System.out.println(hae(tunniste));
            }
            else {
                System.out.println("Error!");
            }
        }
        catch (Exception e) {
            System.out.println("Error!");
        }
    }
}