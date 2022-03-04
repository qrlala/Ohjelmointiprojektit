package harjoitustyo.dokumentit;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import harjoitustyo.dokumentit.Dokumentti;

/**
 * Konkreettinen Uutinen-luokka.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet 2, kevat 2020.
 * <p>
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnan tiedekunta,
 * Tampereen yliopisto.
 */

public class Uutinen extends Dokumentti {

    /**attribuutti uutisen paivamaaralle*/
    private LocalDate paivamaara;

    //parametrillinen rakentaja, jossa kutsutaan yliluokan rakentajaa
    public Uutinen(int uusiTunniste, LocalDate uusiPaivamaara, String uusiTeksti)throws IllegalArgumentException{
        super(uusiTunniste, uusiTeksti);
        paivamaara(uusiPaivamaara);
    }

    //aksessorit
    public LocalDate paivamaara() {
        return paivamaara;
    }

    public void paivamaara(LocalDate uusiPaivamaara) throws IllegalArgumentException{
        if(uusiPaivamaara == null) {
            throw new IllegalArgumentException("Error!");
        }  
        else{
            paivamaara = uusiPaivamaara;
        }
    }

    //ToStringin korvaus
    @Override 
    public String toString() {
        DateTimeFormatter formatteri = DateTimeFormatter.ofPattern("d.M.YYYY");
        //dokumentti-luokasta tuodaan "tunniste///teksti"
        String vanhat = super.toString(); 
        String[] erotetut = vanhat.split("///");
        
        //lisataan valiin uutisen paivamaara oikeassa muodossa
        return erotetut[0] + EROTIN + paivamaara.format(formatteri) + EROTIN + erotetut[1];
    }
}

