#include "player.hh"
#include <string>
#include <iostream>

// Lisää luokan toteutus tähän.
// Kuhunkin julkiseen metodiin pitäisi riittää 1-2 koodiriviä.
Player::Player(const std::string& name):
    name(name), pairs(0){
}

unsigned int Player::number_of_pairs() const{
    return pairs;
}

std::string Player::get_name() const{
    return name;
}

void Player::add_card(){
    pairs = pairs + 1;
}

// Tulostaa pelaajan tilanteen: nimen ja tähän asti kerättyjen parien määrän.
void Player::print(){
    std::cout << "*** " << name << " has " << pairs << " pair(s)."<< std::endl;
}
