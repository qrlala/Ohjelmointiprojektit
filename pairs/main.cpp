/* Muistipeli
 *
 * Kuvaus:
 * Ohjelma toteuttaa muistipelin. Pelin alussa pelaajalta kysytään siemenluku, koska kortit
 * arvotaan satunnaisesti pelilaudalle.
 *  Joka kierroksella vuorossa oleva pelaaja antaa kahden kortin
 * koordinaatit (neljä lukua), jonka jälkeen kyseiset kortit
 * käännetään näkyviin ja kerrotaan, ovatko ne parit vai ei.
 * Jos pelaaja sai parit, kortit poistetaan pelilaudalta ja pelaajan
 * pistesaldoa kasvatetaan, jonka jälkeen pelaaja saa uuden vuoron. Jos pelaaja ei saanut
 * pareja, kortit käännetään takaisin piiloon, ja vuoro siirtyy seuraavalle
 * pelaajalle.
 *  Ohjelma tarkistaa myös pelaajan antamat koordinaatit oikeellisiksi.
 *  Muutosten jälkeen pelilauta tulostetaan aina uudelleen. Kortit ovat
 * kirjaimia alkaen A:sta niin pitkälle, kuin kortteja riittää.
 * Piiloon käännettyä korttia kuvaa risuaita (#), ja laudalta poistetun
 * kortin kohdalle tulostetaan piste.
 *  Pelin voittaa, kun kaikki parit on löydetty, jonka jälkeen
 *  kerrotaan, kuka tai ketkä voittivat eli saivat eniten pareja.
 *
 * Ohjelman kirjoittaja
 * Nimi: Laura Launonen
 * Opiskelijanumero: 438837
 * Käyttäjätunnus: qrlala
 * E-Mail: laura.launonen@tuni.fi
 *
 * */

#include <player.hh>
#include <card.hh>
#include <iostream>
#include <vector>
#include <random>

using namespace std;

const string INPUT_AMOUNT_OF_CARDS = "Enter the amount of cards (an even number): ";
const string INPUT_SEED = "Enter a seed value: ";
const string INPUT_AMOUNT_OF_PLAYERS = "Enter the amount of players (one or more): ";
const string INPUT_CARDS = "Enter two cards (x1, y1, x2, y2), or q to quit: ";
const string INVALID_CARD = "Invalid card.";
const string FOUND = "Pairs found.";
const string NOT_FOUND = "Pairs not found.";
const string GIVING_UP = "Why on earth you are giving up the game?";
const string GAME_OVER = "Game over!";


using Game_row_type = vector<Card>;
//vektorin sisällä on monta riviä
using Game_board_type = vector<Game_row_type>;

// Muuntaa annetun numeerisen merkkijonon vastaavaksi kokonaisluvuksi
// (kutsumalla stoi-funktiota).
// Jos annettu merkkijono ei ole numeerinen, palauttaa nollan
// (mikä johtaa laittomaan korttiin myöhemmin).
unsigned int stoi_with_check(const string& str){
    bool is_numeric = true;
    for(unsigned int i = 0; i < str.length(); ++i){
        if(not isdigit(str.at(i))){
            is_numeric = false;
            break;
        }
    }
    if(is_numeric){
        return stoi(str);
    }
    else{
        return 0;
    }
}

// Täyttää pelilaudan (kooltaan rows * columns) tyhjillä korteilla.
void init_with_empties(Game_board_type& g_board, unsigned int rows, unsigned int columns){
    g_board.clear();
    Game_row_type row;

    //lisätään laudalle sarakkeet
    for(unsigned int i = 0; i < columns; ++i){
        Card card;
        row.push_back(card);
    }
    //lisätään laudalle rivit
    for(unsigned int i = 0; i < rows; ++i){
        g_board.push_back(row);
    }
}

// Etsii seuraavan tyhjän kohdan pelilaudalta (g_board) aloittamalla
// annetusta kohdasta start ja jatkamalla tarvittaessa alusta.
// (Kutsutaan vain funktiosta init_with_cards.)
unsigned int next_free(Game_board_type& g_board, unsigned int start){
    // Selvitetään annetun pelilaudan rivien ja sarakkeiden määrät
    unsigned int rows = g_board.size();
    unsigned int columns = g_board.at(0).size();

    // Aloitetaan annetusta arvosta
    for(unsigned int i = start; i < rows * columns; ++i){
        if(g_board.at(i / columns).at(i % columns).get_visibility() == EMPTY) {
            return i;
        }
    }
    // Jatketaan alusta
    for(unsigned int i = 0; i < start; ++i)
    {
        if(g_board.at(i / columns).at(i % columns).get_visibility() == EMPTY)
        {
            return i;
        }
    }
    // Tänne ei pitäisi koskaan päätyä
    cout << "No more empty spaces" << endl;
    return rows * columns - 1;
}

// Alustaa annetun pelilaudan (g_board) satunnaisesti arvotuilla korteilla
// annetun siemenarvon (seed) perusteella.
void init_with_cards(Game_board_type& g_board, int seed){
    // Selvitetään annetun pelilaudan rivien ja sarakkeiden määrät
    unsigned int rows = g_board.size();
    unsigned int columns = g_board.at(0).size();

    // Arvotaan täytettävä sijainti
    default_random_engine randomEng(seed);
    uniform_int_distribution<int> distr(0, rows * columns - 1);
    // Hylätään ensimmäinen satunnaisluku (joka on aina jakauman alaraja)
    distr(randomEng);

    // Jos arvotussa sijainnissa on jo kortti, valitaan siitä seuraava tyhjä paikka.
    // (Seuraava tyhjä paikka haetaan kierteisesti funktion next_free avulla.)
    for(unsigned int i = 0, c = 'A'; i < rows * columns - 1; i += 2, ++c){
        // Lisätään kaksi samaa korttia (parit) pelilaudalle
        for(unsigned int j = 0; j < 2; ++j) {
            unsigned int cell = distr(randomEng);
            cell = next_free(g_board, cell);
            g_board.at(cell / columns).at(cell % columns).set_letter(c);
            g_board.at(cell / columns).at(cell % columns).set_visibility(HIDDEN);
        }
    }
}

// Tulostaa annetusta merkistä c koostuvan rivin,
// jonka pituus annetaan parametrissa line_length.
// (Kutsutaan vain funktiosta print.)
void print_line_with_char(char c, unsigned int line_length){
    for(unsigned int i = 0; i < line_length * 2 + 7; ++i){
        cout << c;
    }
    cout << endl;
}

// Tulostaa vaihtelevankokoisen pelilaudan reunuksineen.
void print(const Game_board_type& g_board){
    // Selvitetään annetun pelilaudan rivien ja sarakkeiden määrät
    unsigned int rows = g_board.size();
    unsigned int columns = g_board.at(0).size();

    print_line_with_char('=', columns);
    cout << "|   | ";
    for(unsigned int i = 0; i < columns; ++i){
        cout << i + 1 << " ";
    }
    cout << "|" << endl;
    print_line_with_char('-', columns);
    for(unsigned int i = 0; i < rows; ++i){
        cout << "| " << i + 1 << " | ";
        for(unsigned int j = 0; j < columns; ++j){
            g_board.at(i).at(j).print();
            cout << " ";
        }
        cout << "|" << endl;
    }
    print_line_with_char('=', columns);
}

// Kysyy käyttäjältä tulon ja sellaiset tulon tekijät, jotka ovat
// mahdollisimman lähellä toisiaan.
void ask_product_and_calculate_factors(unsigned int& smaller_factor, unsigned int& bigger_factor){

    unsigned int product = 0;
    while(not (product > 0 and product % 2 == 0)) {
        cout << INPUT_AMOUNT_OF_CARDS;
        string product_str = "";
        getline(std::cin, product_str);
        product = stoi_with_check(product_str);
    }

    for(unsigned int i = 1; i * i <= product; ++i){
        if(product % i == 0){
            smaller_factor = i;
        }
    }
    bigger_factor = product / smaller_factor;
}

vector<int> make_vector_int(vector<string> string_coords){
    int coordinate= 0;
    vector<int> coords;

    for (int i = 0; i < 4; i++){
        coordinate = stoi_with_check(string_coords[i]);
        if(coordinate > 0){
            coords.push_back(coordinate);
        }
        else{
            return {};
        }
    }

    return coords;

}
//LISÄTTY
//tarkastetaan käyttäjän antamat koordinaatit
bool checkCoord(vector<int> coords, Game_board_type game_board){
    if(coords.size() == 4){
        int x1 = coords[0];
        int x2 = coords[2];
        int y1 = coords[1];
        int y2 = coords[3];

        int rowSize = game_board[0].size();
        int columnSize = game_board.size();

        //ei saa olla laudan ulkopuolelta
        if (x1 > rowSize || x2 > rowSize || y1 > columnSize || y2 > columnSize){
            return false;
        }
        //ei samoja koordinaatteja
        else if (x1 == x2 and y1 == y2){
            return false;
        }
        //ei pienempää kuin 0
        else if(x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0){
            return false;
        }
        else {
            //ei saa olla tyhjä
            if(game_board[y1 - 1][x1 - 1].get_visibility() == EMPTY){
                if(game_board[y2 - 1][x2 - 1].get_visibility() == EMPTY){
                    return false;
                }
            }
            else{
                return true;
            }
        }
    }
    else {
        return false;
    }
    return false;
}

//LISÄTTY
// selvitetään onko voittaja yksi pelaaja vai tasapeli
void checkWinner(vector<Player *> players, int how_many_players){
    string winner = "";
    int playerCount = 1;
    int n = 0;
    unsigned int mostPairs = 0;
    while(n < how_many_players){
        // jos selatessa pelaajien parien määrää
        // seuraavalla pelaajalla on sama määrä pareja
        // kasvatetaan pelaajalaskuria ja määritetään suurin parimäärä
        //jos kahdella vierekkäisellä ei ole sama, asetetaan sen hetkinen isoin parimäärä
        if (players[n]->number_of_pairs() > mostPairs){
            mostPairs = 0;
            mostPairs += players[n]->number_of_pairs();
            if (players[n + 1]->number_of_pairs() == mostPairs){
                playerCount++;
            }
            else{
                playerCount = 1;
                winner = players[n]->get_name();
            }
        }
    n++;
    }
    //single winner
    if(playerCount < 2){
        cout << winner << " has won with " << mostPairs << " pairs." << endl;
    }
    //tie
    else{
         cout << "Tie of " << playerCount << " players with " << mostPairs << " pairs." << endl;
    }
}
//tarkistaa pelilaudan tyhjyyyden, parametrinä taulukko
int emptyBoard(Game_board_type& game_board){
    int rowSize = game_board.size();
    int columnSize = game_board[0].size();
    for (int i = 0; i < rowSize; i++){
        for (int j = 0; j < columnSize; j++){
            if(game_board[i][j].get_visibility() != EMPTY){
                return 1;
            }
        }
    }
    return 0;
}
int main(){
    Game_board_type game_board;

    unsigned int factor1 = 1;
    unsigned int factor2 = 1;
    ask_product_and_calculate_factors(factor1, factor2);
    init_with_empties(game_board, factor1, factor2);

    //kysytään siemenluku
    string seed_str = "";
    cout << INPUT_SEED;
    getline(std::cin, seed_str);
    unsigned int seed = stoi_with_check(seed_str);
    init_with_cards(game_board, seed);

    // Pelaajien määrän kysyntä
    int how_many_players;
    bool continues = true;
    while(continues){
        string how_many = "";
        cout << INPUT_AMOUNT_OF_PLAYERS;
        cin >> how_many;
        how_many_players = stoi_with_check(how_many);
        if (how_many_players > 0){
            continues = false;
        }
    }

    //pelaajien nimien kysyntä
    string give_name = "";
    cout << "List " << how_many_players << " players: ";
    vector<Player *> players;
    for (int i = 0; i < how_many_players; i++){
        cin >> give_name;
        players.push_back(new Player(give_name));
    }
    print(game_board);

    //tarvittavia muuttujia
    vector<string> string_coords;
    vector<int> coords;
    int currentPlayer = 0;
    bool turn = true;
    bool THE_END = false;
    Player* in_turn;
    string input = "";

    //ALOITETAAN PELI
    while(THE_END == false){
        in_turn = players[currentPlayer];
        turn = true;
        //vuoro käynnissä, kunnes käännetään väärät kortit
        while(turn == true){
            // Kysytään valittavia kortteja
            cout << in_turn->get_name() << ": " << INPUT_CARDS;
            string_coords.clear();
            int x = 0;
            //otetaan täsmälleen 4 koordinaattia
            while (x <= 3){
                cin >> input;
                if (input == "q"){
                    cout << GIVING_UP << endl;
                    return EXIT_SUCCESS;
                }
                else{
                    string_coords.push_back(input);
                }
            x++;
            }
            coords = make_vector_int(string_coords);
            //täysitarkistus koordinaateille
            bool correctInput = checkCoord(coords, game_board);
            if (coords.empty() || correctInput == false){
                cout << INVALID_CARD << endl;
            }
            //jos koordinaatit olivat hyviä
            else {
                //laitetaan coordinaatit korteiksi
                int x1 = coords[0];
                int x2 = coords[2];
                int y1 = coords[1];
                int y2 = coords[3];

                game_board[y1 - 1][x1 - 1].turn();
                game_board[y2 - 1][x2 - 1].turn();

                print(game_board);

                //tarkistetaan onko kortit samat
                char card1 = game_board[y1 - 1][x1 - 1].get_letter();
                char card2 = game_board[y2 - 1][x2 - 1].get_letter();
                if (card1 == card2){
                    cout << FOUND << endl;

                    //lisätään pisteet pelaajalle
                    in_turn->add_card();

                    //poistetaan kortit laudalta
                    game_board[y1 - 1][x1 - 1].remove_from_game_board();
                    game_board[y2 - 1][x2 - 1].remove_from_game_board();

                    //tulostetaan tulos
                    unsigned long int n = 0;
                    while(n < players.size()){
                        players[n]->print();
                        n++;
                    }
                    print(game_board);
                    int notEmpty = emptyBoard(game_board);
                    if (notEmpty == 0){
                        //peli loppuu
                        cout << GAME_OVER << endl;
                        checkWinner(players, how_many_players);
                        THE_END = true;
                        return EXIT_SUCCESS;
                    }
                    //ei tyhjä
                    else{
                        turn = true;
                    }
                }
                else{
                    cout << NOT_FOUND << endl;
                    game_board[y1 - 1][x1 - 1].turn();
                    game_board[y2 - 1][x2 - 1].turn();

                    //tulostetaan tulos
                    unsigned long int n = 0;
                    while(n < players.size()){
                        players[n]->print();
                        n++;
                    }
                    print(game_board);

                    //vaihdetaan vuoroa
                    currentPlayer++;
                    //aloitetaan kierros alusta jos oli viimeinen pelaaja
                    if (currentPlayer == how_many_players){
                        currentPlayer = 0;
                    }
                    turn = false;
                }
            }
        }
        THE_END = false;
    }
    //ohjelma loppuu
    return EXIT_SUCCESS;
}

