#include "card.hh"
#include <iostream>

//tyhjä kortti
Card::Card():
    letter_(EMPTY_CHAR), visibility_(EMPTY){
}

//piilotettu kortti
Card::Card(const char c):
    letter_(c), visibility_(HIDDEN){
}

void Card::set_letter(const char c){
    letter_ = c;
}

void Card::set_visibility(const Visibility_type visibility){
    visibility_ = visibility;
}

char Card::get_letter() const{
    return letter_;
}

Visibility_type Card::get_visibility() const{
    return visibility_;
}

//kääntää kortin
void Card::turn(){
    if(visibility_ == HIDDEN){
        visibility_ = OPEN;
    }
    else if(visibility_ == OPEN){
        visibility_ = HIDDEN;
    }
    // if(visibility_ == EMPTY)
    else {
        std::cout << "Cannot turn an empty place." << std::endl;
    }
}

// Tulostaa kortin tämänhetkisen tilanteen.
void Card::print() const{
    if (visibility_ == HIDDEN){
        std::cout << HIDDEN_CHAR;
    }
    else if (visibility_ == OPEN){
        std::cout << letter_;
    }
    else{
        std::cout << EMPTY_CHAR;
    }
}

void Card::remove_from_game_board(){
    visibility_ = EMPTY;
}
