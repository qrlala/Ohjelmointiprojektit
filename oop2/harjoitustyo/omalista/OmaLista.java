package harjoitustyo.omalista;

/**
 * Konkreettinen Omalista-luokka ,jota käytetään kokoelman dokumenttien säilömiseen.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet 2, kevat 2020.
 * <p>
 * Toteutetaan Ooperoiva-rajapinta
 * @author Laura Launonen (laura.launonen@tuni.fi),
 * Informaatioteknologian ja viestinnan tiedekunta,
 * Tampereen yliopisto.
 */

import harjoitustyo.apulaiset.Ooperoiva;
import java.util.LinkedList;

public class OmaLista<E> extends LinkedList<E>  implements Ooperoiva<E>{
    
  
    /**toteutetaan lisää metodi, joka lisaa alkion(dokumentin) listalle
    *@param uusi(alkio)
    *@throws IllegalArgumentException, jos alkio on null-arvoinen, tai alkiota ei voida lisätä
    */
    @SuppressWarnings(("unchecked"))
    public void lisää(E uusi) throws IllegalArgumentException{
        if (uusi != null) {
            try {
                Comparable objekti = (Comparable)uusi;
                
                if (size() == 0) {
                    addFirst(uusi);
                }
                else{
                    boolean jatketaan = true;
                    int i = 0;
                    
                    //vertaillaan lisattavaa alkiota ja listan alkioita 
                    while (jatketaan && i < size()) {
                        int toinenObjekti = objekti.compareTo(get(i));
                        if (toinenObjekti < 0) {
                            add(i, uusi);
                            jatketaan = false;
                        }
                    i++;
                    }
                    if (i == size()) {
                        addLast(uusi);
                    }
                }
            }
            catch(Exception E){
                throw new IllegalArgumentException();
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}