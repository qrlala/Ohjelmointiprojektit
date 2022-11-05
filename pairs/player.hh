/* Luokka: Player
 * --------------
 * Kuvaa yhtä pelaajaa muistipelissä.
 *
 * COMP.CS.110 K2021
 * */
//
/* Class: Player
 * -------------
 * Represents a single player in pairs (memory) game.
 *
 * COMP.CS.110 K2021
 * */

#ifndef PLAYER_HH
#define PLAYER_HH

#include "card.hh"
#include <string>

class Player{
public:
    // Rakentaja: luo annetun nimisen pelaajan.
    Player(const std::string& name);

    // Palauttaa pelaajan nimen.
    std::string get_name() const;

    // Palauttaa pelaajan tähän asti keräämien parien määrän.
    unsigned int number_of_pairs() const;

    // Siirtää annetun kortin pelilaudalta pelaajalle,
    // eli lisää kortin pelaajan keräämiin kortteihin
    // ja poistaa sen pelilaudalta.
    void add_card();
    // Tulostaa pelaajan tilanteen: nimen ja tähän asti kerättyjen parien määrän.
    void print();

private:
    // Lisää tarvittavat attribuutit tähän
    std::string name;
    double pairs;
    Player* in_turn;
};

#endif // PLAYER_HH
