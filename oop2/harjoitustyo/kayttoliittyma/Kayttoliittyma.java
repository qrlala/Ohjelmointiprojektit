package harjoitustyo.kayttoliittyma;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.util.Scanner;
import harjoitustyo.dokumentit.*;
import harjoitustyo.kokoelma.Kokoelma;
import java.time.format.DateTimeFormatter;
/**
 * Konkreettinen Kayttoliittyma-luokka, jossa luetaan k√ytt√j√n komennot.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet 2, kevat 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnan tiedekunta,
 * Tampereen yliopisto.
 */


public class Kayttoliittyma{
    
    /**luokkavakiot komennoille*/
    public static final String LOPETA = "quit";
    public static final String TULOSTA = "print";
    public static final String HAE = "find";
    public static final String POISTA = "remove";
    public static final String LISAA = "add";
    public static final String LATAA = "reset";
    public static final String SIIVOA = "polish";
    
    /**
    * Rakentaja, joka vastaa p√√silmukasta
    *@param komentoriviparametrit
    */
    public Kayttoliittyma(String[] args){
        Scanner lukija = new Scanner(System.in);
        //kutsutaan tervehdys kaynnistaessa
        System.out.println("Welcome to L.O.T.");
        
        //t√ytyy olla 2 komentoriviparametria
        if (args.length >= 2 && args != null) {
            //luodaan kokoelmaolio
            Kokoelma kokoelma = new Kokoelma();
            //tarkistetaan ett√ tiedosto on oikea
            boolean tarkastus = kokoelma.lataaTiedosto(args);
            String sulkusanat = args[1];     
            //lista sulkusanoille
            LinkedList<String> sulkusanalista = kokoelma.lataaSulkusanat(sulkusanat);
            
            if (tarkastus == false){
                System.out.println("Missing file!");
                System.out.println("Program terminated."); 
            }       
            else if (sulkusanalista == null){
                System.out.println("Missing file!");
                System.out.println("Program terminated."); 
            }
            else{
                boolean echo = false;
                boolean jatketaan = true;
                
                while (jatketaan){
                    System.out.println("Please, enter a command:");
                    //luetaan kayttajan syote
                    String syote = lukija.nextLine();
                    //apumuuttuja, syotteen ensimm√§inen sana eli komento
                    String komento = syote.split(" ")[0];
                    
                    if(echo == true){
                        System.out.println(syote);
                    }
                    if (syote.split(" ").length == 1 && komento.equals("echo")) {
                        if (echo == false){
                            echo = true;
                        }
                        else{
                            echo = false;
                        }  
                        //kaiuttaa kun echo on p√√ll√
                        if(echo){
                            System.out.println(syote);
                        }
                    }   
                    //suljetaan ohjelma, jos annetaan lopetuskasky
                    else if (syote.split(" ").length == 1 && komento.equals(LOPETA)) {
                        jatketaan = false;
                    }
                    //jos komento = find
                    else if (syote.split(" ").length >= 2 && komento.equals(HAE)){
                        //poistetaan komennosta itse "find" kasky              
                        String[] hakuSanat = syote.replace(komento,"").split(" ");
                        LinkedList<Integer> loydetaan = kokoelma.etsi(hakuSanat); 
                        if (loydetaan.size() > 0){
                            for(int i = 0; i < loydetaan.size(); i++){
                                System.out.println(loydetaan.get(i));
                            }
                        }
                    }
                    //Jos komento = add
                    else if (komento.equals(LISAA)){
                        boolean onnistui = kokoelma.vitsiVaiUutinen(syote, args);
                        if (onnistui == false){
                            System.out.println("Error!");
                        }
                    }
                    //jos komento = polish
                    else if (komento.equals(SIIVOA)){
                        if (syote.split(" ").length == 2 ) { 
                        String valimerkit = syote.split(" ")[1];
                        kokoelma.siivoa(sulkusanalista, valimerkit);
                        }
                        else {
                            System.out.println("Error!");
                        }
                    }
                    //jos komento = print;
                    //voi olla joko pelkka print, tai print ja tunniste
                    else if (komento.equals(TULOSTA)){
                        if (syote.split(" ").length == 1){
                            for (int i = 0; i < kokoelma.dokumentit().size(); i++){
                                System.out.println(kokoelma.dokumentit().get(i));
                            }
                        }
                        else if (syote.split(" ").length == 2) {
                            kokoelma.printtausApu(syote);
                        }
                        else{
                            System.out.println("Error!");
                        }
                    }
                    //jos komento = reset
                    else if (syote.split(" ").length == 1 && komento.equals(LATAA)){
                        kokoelma = new Kokoelma();
                        kokoelma.lataaTiedosto(args);
                    }
                    //jos komento remove
                    else if (syote.split(" ").length == 2 && komento.equals(POISTA)){
                        boolean onnistui = kokoelma.poistoApu(syote);
                        if (onnistui == false){
                            System.out.println("Error!");
                        }
                    }
                    //jos k√sky ei mik√√n yll√ olevista
                    else{
                        System.out.println("Error!");
                    }
                }
            System.out.println("Program terminated.");
            }
        }
        //virheellinen komento
        else{
            System.out.println("Wrong number of command-line arguments!");
            System.out.println("Program terminated."); 
        }   
    }
}