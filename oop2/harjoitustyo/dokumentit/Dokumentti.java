package harjoitustyo.dokumentit;
import java.util.*;
import harjoitustyo.apulaiset.*;

/**
 * Abstrakti Dokumentti-luokka
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet I, kevat 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnan tiedekunta,
 * Tampereen yliopisto.
 */

public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti>{
    //vitsille ja uutiselle myos nakyva erotin, joka on apuna koodin selkeytyksessa.
    public static final String EROTIN = "///";
    
    /**attribuutit dokumenttien tunnisteelle ja tekstille*/
    private int tunniste;
    private String teksti;
    
    //rakentaja 
    public Dokumentti(int uusiTunniste, String uusiTeksti)throws IllegalArgumentException  {
        tunniste(uusiTunniste);
        teksti(uusiTeksti);
    }

    //aksessorit
    public int tunniste() {
        return tunniste;
    }

    public void tunniste(int uusiTunniste)throws IllegalArgumentException{
        if(uusiTunniste < 1) {
            throw new IllegalArgumentException("Error!");
        }
        else{
            tunniste = uusiTunniste;
        }
    }
    public String teksti(){
        return teksti;
    }
    
    public void teksti(String uusiTeksti) throws IllegalArgumentException{
        if (uusiTeksti != null && !uusiTeksti.equals("")){
            teksti = uusiTeksti;
        }
        else{
            throw new IllegalArgumentException("Error!");
        }
    }
    
    /**toString korvaus
    *@return muotoiltu teksti: erotus
    */
    @Override 
    public String toString() {
        //palautetaan muotoiltu merkkijono
        String erotus = tunniste + EROTIN + teksti;
        return erotus;
    }
    
    /**Equals metodin korvaus
    *@return boolean
    */
    @Override 
    public boolean equals(Object obj) {
        try {
            Dokumentti toinen = (Dokumentti)obj;
            return (tunniste == toinen.tunniste);
        }
        catch (Exception e) {
            return false;
        }
    }
    
    /**
    *@return -1, 1, 0
    *rajapinnan compareTo metodin toteutus comparable rajapinnasta
    */
    @Override
    public int compareTo(Dokumentti t) {
        if (tunniste() < t.tunniste()) {
            return -1;
        }
        else if (tunniste() == t.tunniste()) {
            return 0;
        }
        else {
            return 1;
        }
    }
    
    /**sanatTasmaavat metodin toteutus Tietoinen rajapinnasta
    *@param hakusanat lista dokumentin tekstista haettavia sanoja
    *@return true, jos kaikki listan sanat loytyvat dokumentista.
    */ 
    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException{
      
        if (hakusanat != null && hakusanat.size() >= 1){
            
            List<String> valiTaulukko = Arrays.asList(teksti.split(" "));
            
            //käydään läpi hakusanat muuttujan avulla
            for(String haku : hakusanat){
                //jos dokumentin sanoista ei löydy kaikkia hakusanoja, palautetaan false
                if (!valiTaulukko.contains(haku)){
                    return false;
                }
            }
        return true;
        }
        else{
            throw new IllegalArgumentException("Error!");            
        }
    }

    
    /**Tietoinen rajapinnan siivoa-metodin toteutus
    * Metodi poistaa dokumentin tekstistä annetut välimerkit ja sanat
    *
    * @param sulkusanat, tekstistä poistettavia sanoja
    * @param välimerkit, tekstistä poistettavia välimerkkejä
    *@throws IllegalArgumentException, jos tyhjät parametrit tai null-arvo
    */
    @Override
    public void siivoa(LinkedList<String> sulkusanat, String valimerkit)throws IllegalArgumentException{
        if((sulkusanat == null || valimerkit == null) || (sulkusanat.size() < 1 || valimerkit.equals(""))) {
            throw new IllegalArgumentException("Error!");
        }
        else{
            //annettujen valimerkkien poisto valiaikaisen taulukon avulla
            //split("") erottaa jokaisen annetun merkin
            String[] valiTaulukko = valimerkit.split("");
            for (int i = 0; i < valiTaulukko.length; i++){
                teksti = teksti.replace(valiTaulukko[i], "");
            }
            
            //kirjainmerkkien pienennys
            String pienennettyTeksti = teksti.toLowerCase();

            String[] apuTaulukko = pienennettyTeksti.split(" ");
            
            //kaydaan sulkusanoja lapi ja lisataan ne apumuuttujaan sulkuSana
            for (int i = 0; i < sulkusanat.size(); i++){

                String sulkuSana = sulkusanat.get(i);

                for (int j = 0; j < apuTaulukko.length; j++){
                    String tekstiVaihdos = apuTaulukko[j];
                    if(tekstiVaihdos.equals(sulkuSana)) {
                        apuTaulukko[j] = "";
                    }
                }
            }
            
            //viimeinen versio tekstista, josta on poistettu valimerkit ja sulkusanat    
            String siivottu = "";
            for (int i = 0; i < apuTaulukko.length; i++) {
                siivottu = siivottu + " " + apuTaulukko[i];
            }

            //alkuperainen teksti vaihdetaan siivotuksi versioksi.
            teksti = siivottu;
            teksti = teksti.replaceAll("[ ]{2,}", " ");

            //poistetaan turhat valit
            teksti = teksti.trim();
        }
    }
}