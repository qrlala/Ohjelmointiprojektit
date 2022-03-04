package harjoitustyo.dokumentit;

import java.util.*;
import harjoitustyo.dokumentit.Dokumentti;
/**
 * Konkreettinen Vitsi-luokka.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet 2, kevät 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnän tiedekunta,
 * Tampereen yliopisto.
 */

public class Vitsi extends Dokumentti {
    
    /**attribuutti vitsin lajille*/
    private String laji;

    //parametrillinen rakentaja, jossa kutsutaan yliluokan rakentajaa
    public Vitsi(int uusiTunniste, String uusiLaji, String uusiTeksti)throws IllegalArgumentException {
        super(uusiTunniste, uusiTeksti);
        laji(uusiLaji);
    }

    //aksessorit
    public String laji() {
        return laji;
    }
    
    public void laji(String uusiLaji)throws IllegalArgumentException {
        if (uusiLaji != null && !uusiLaji.equals("")) {
            laji = uusiLaji;
        }
        else{
            throw new IllegalArgumentException("Error!");
        }
    }
    
    /**ToString korvaus
    * @return muotoiltu tekstirivi
    */
    @Override 
    public String toString() {
        //dokumentti-luokasta tuodaan "tunniste///teksti"
        String vanhat = super.toString(); 
        String[] erotetut = vanhat.split("///");
        
        //lisätään väliin vitsin laji
        return erotetut[0] + EROTIN + laji + EROTIN + erotetut[1];
    }
}