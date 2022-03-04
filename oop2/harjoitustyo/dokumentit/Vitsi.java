package harjoitustyo.dokumentit;

import java.util.*;
import harjoitustyo.dokumentit.Dokumentti;
/**
 * Konkreettinen Vitsi-luokka.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet 2, kev�t 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinn�n tiedekunta,
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
        
        //lis�t��n v�liin vitsin laji
        return erotetut[0] + EROTIN + laji + EROTIN + erotetut[1];
    }
}