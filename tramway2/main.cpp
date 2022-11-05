/*
 * Tramway
 *
 * Description:
 *   This program reads tramstops and lines from file
 * and gives the user information about them with different commands.
 * User can search stops from specific line,
 * calculate distance from two stops of the same line,
 * print out all lines,
 * add a new line and a stop,
 * remove lines
 * and search which lines certain stop goes through.
 *
 *
 * Program author
 * Name: Laura Launonen
 * Student number: 438837
 * UserID: qrlala
 * E-Mail: laura.launonen@tuni.fi
 *
 * */

#include <iostream>
#include <string>
#include<bits/stdc++.h>
#include <fstream>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;

struct tramline{
    string tramStop;
    string distance;
};

typedef pair<string, tramline> tramPair;
typedef vector<tramPair> tramways;

// The most magnificent function in this whole program.
// NOT MADE BY ME
// Prints a RASSE
void print_rasse(){
    cout <<
                 "=====//==================//===\n"
                 "  __<<__________________<<__   \n"
                 " | ____ ____ ____ ____ ____ |  \n"
                 " | |  | |  | |  | |  | |  | |  \n"
                 " |_|__|_|__|_|__|_|__|_|__|_|  \n"
                 ".|                  RASSE   |. \n"
                 ":|__________________________|: \n"
                 "___(o)(o)___(o)(o)___(o)(o)____\n"
                 "-------------------------------" << endl;
}

//splits the given string
vector<string> split (const string& text, char separator, bool ignore_empty = false) {
    vector<string> parts;

    string::size_type left = 0;
    string::size_type right = text.find(separator, left);

    while(right!= string::npos) {
        string part = text.substr(left, right - left);
        if(part != "" || !ignore_empty) {
            parts.push_back(part);
        }
        left = right + 1;
        right = text.find(separator, left);
    }

    string final_part = text.substr(left, string::npos);
    parts.push_back(final_part);

    return parts;
}

//checks if row of file have too many or too few information.
bool checkParts(vector<string> parts){
    if (parts.size() > 3 || parts.size() < 2 ||
        parts.at(0) == "" || parts.at(1) == ""){
        cout << "Error: Invalid format in file." << endl;
        return false;
    }
    else {
        return true;
    }
}

// reads from given file.
tramways read_file(){
    //asking and reading the file
    string input_file = "";
    cout << "Give a name for input file: ";
    getline(cin, input_file);
    fstream reading_file;
    reading_file.open(input_file);

    //declaring returnable
    tramways lineMap;

    //if can't be opened
    if (not reading_file){
        cout << "Error: File could not be read." << endl;
        return {};
    }
    //success reading
    else {
        string row = "";
        //splitting one row at a time

        while(getline(reading_file, row)) {
            vector<string> parts = split(row, ';');
            bool right = checkParts(parts);
            if (right == false){
                return {};
            }
            else{
                tramline tramL;
                //setting distance to be 0, if it is not mentioned
                if (parts.size() == 3){
                    if (parts.at(2) == ""){
                        parts.pop_back();
                        parts.push_back("0");
                    }
                    tramL.distance = parts.at(2);
                }

                //if there is not a distance, set it to 0
                else if (parts.size() == 2){
                    tramL.distance = "0";
                }
                //setting parts to variables
                string line = parts.at(0);
                tramL.tramStop = parts.at(1);

                //Data structure where is vector where line and struct is paired.
                lineMap.push_back(make_pair(line, tramL));
            }
        }
        reading_file.close();
    }
    return lineMap;
}

// makes vector of all lines, helps to search through them
vector<string> make_line_vector(tramways lineMap){
    //making lines into vector non-permanently
    vector<string> lines;
    for (tramways::iterator i = lineMap.begin(); i != lineMap.end(); ++i){
        string line = i->first;
        if (not(find(lines.begin(), lines.end(), line) != lines.end())){
            lines.push_back(line);
        }
    }
    return lines;
}
vector<string> make_stops(tramways lineMap){
    vector<string> stops;
    for (tramways::iterator i = lineMap.begin(); i != lineMap.end(); ++i){
        string stop = i->second.tramStop;
        if (not(find(stops.begin(), stops.end(), stop) != stops.end()) && stop != "")
            stops.push_back(stop);
    }
    return stops;
}

string make_uppercase(string command){
    unsigned long int i = 0;
    while(i < command.length()){
        char c = command.at(i);
        if (islower(c)){
            command.at(i) = toupper(c);
        }
        i++;
    }
    return command;
}

bool is_smaller(const pair<string, string>& a, const pair<string, string>& b){
    return (a.second < b.second);
}

// acts as an interface for user.
bool ask_command(tramways lineMap){
    tramline tramL;
    string line;
    bool asking = true;
    //asking command
    while(asking){
        string command;
        cout << "tramway> ";
        getline(cin, command);
        //splitting command to parts
        vector<string> command_parts = split(command, ' ');

        string temp = command_parts.at(0);
        string first = make_uppercase(temp);
        int amount = command_parts.size();

        if (first == "QUIT" && amount == 1){
            return false;
        }
        else if(amount == 2 && first == "LINE"){
            vector<string> temp = make_line_vector(lineMap);
            // if line is not in lines vector, program gives an error.
            if (not(find(temp.begin(), temp.end(), command_parts.at(1)) != temp.end())){
                cout << "Error: Line could not be found." << endl;
            }
            else{
                cout << "Line " << command_parts.at(1) << " goes through these stops in the order they are listed:" << endl;
                vector<pair<string, string>> temporary;
                for (tramways::iterator i = lineMap.begin(); i != lineMap.end(); i++){
                    string line = i->first;
                    string stop = i->second.tramStop;
                    string dist = i->second.distance;
                    if (stop != "" && dist != "" && line == command_parts.at(1)){
                        temporary.push_back(make_pair(stop, dist));
                    }
                }
                sort(temporary.begin(), temporary.end(), is_smaller);
                for(vector<pair<string, string>>::iterator itr = temporary.begin(); itr != temporary.end(); itr++){
                    string stop_ = itr->first;
                    string distance = itr->second;
                    cout << " - " << stop_ << " : " << distance << endl;
                }
            }
        }
        else if(first == "LINES" && amount == 1){
            vector<string> lines = make_line_vector(lineMap);
            sort(lines.begin(), lines.end());

            cout << "All tramlines in alphabetical order: " << endl;
            for (string row : lines){
                cout << row << endl;
            }
        }
        //removes stop from all lines
        else if (first == "REMOVE" && amount == 2){
            bool found = false;
            string empty = "";
            for (tramways::iterator itr = lineMap.begin(); itr != lineMap.end(); ++itr){
                string stop = itr->second.tramStop;
                //string dist = itr->second.distance;
                if (stop == command_parts.at(1)){
                    itr->second.tramStop = "";
                    itr->second.distance = "";
                    found = true;
                }
            }
            if (found == false){
                cout << "Error: Stop could not be found." << endl;
            }
            else{
                cout << "Stop was removed from all lines." << endl;
            }
        }
        else if (first == "ADDLINE" && amount == 2){
            string line_wanted = command_parts.at(1);
            //giving empty stop and distance if line is made.
            tramL.tramStop = "";
            tramL.distance = "";
            vector<string> lines = make_line_vector(lineMap);
            for (tramways::iterator itr = lineMap.begin(); itr != lineMap.end(); ++itr){
                string line = itr->first;
                //if line is not in the tramway already, new line is created
                if (not(find(lines.begin(), lines.end(), command_parts.at(1)) != lines.end())){
                    lineMap.push_back(make_pair(line_wanted, tramL));
                    cout << "Line was added." << endl;
                    break;
                }
                else{
                    cout << "Error: Stop/line already exists." << endl;
                    break;
                }
            }
        }
        else if(first == "ADDSTOP" && amount > 3){
            vector<string> lines = make_line_vector(lineMap);
            vector<string> stops = make_stops(lineMap);
            for (tramways::iterator itr = lineMap.begin(); itr != lineMap.end(); ++itr){
                string line = itr->first;
                string stop = itr->second.tramStop;
                string distance = itr->second.distance;
                if (find(lines.begin(), lines.end(), line) != lines.end()){
                    if (command_parts.at(1) == line){
                        if (find(stops.begin(), stops.end(), command_parts.at(2)) != stops.end()){
                            cout << "Error: Stop/line already exists." << endl;
                            break;
                        }
                        else{
                            stop = command_parts.at(2);
                            distance = command_parts.at(3);
                            tramL.distance = distance;
                            tramL.tramStop = stop;
                            lineMap.push_back(make_pair(line, tramL));
                            cout << "Stop was added." << endl;
                            break;
                        }
                    }
                }
                else{
                    cout << "Error: invalid input." << endl;
                }
            }
        }
        else if (first == "STOP" && amount == 2){
            vector<string> rows;
            vector<string> stops = make_stops(lineMap);
            for (tramways::iterator itr = lineMap.begin(); itr != lineMap.end(); ++itr){
                string line = itr->first;
                string stop = itr->second.tramStop;
                if (command_parts.at(1) == stop){
                    rows.push_back(line);
                }
            }
            if (not(find(stops.begin(), stops.end(), command_parts.at(1)) != stops.end())){
                cout << "Error: Stop could not be found." << endl;
            }
            else{
                cout << "Stop " << command_parts.at(1) << " can be found on the following lines:" << endl;
                sort(rows.begin(), rows.end());
                for (string name : rows){
                    cout << " - " << name << endl;
                }
            }
        }

        else if(first == "DISTANCE" && amount == 4){
            double first_distance = 0.0;
            double scnd_distance = 0.0;
            string first_location = command_parts.at(2);
            string second_location = command_parts.at(3);
            vector<string> temp;
            for (tramways::iterator itr = lineMap.begin(); itr != lineMap.end(); ++itr){
                string line = itr->first;
                string stop = itr->second.tramStop;
                string dist = itr->second.distance;
                //if line is found, program continues to find right stops
                if (command_parts.at(1) == line){
                    if(stop == first_location){
                        first_distance = stof(dist);
                    }
                    else if(stop == second_location){
                        scnd_distance = stod(dist);
                    }
                }
            }
            double result = first_distance - scnd_distance;
            double distance = abs(result);

            cout << "Distance between " << first_location << " and " << second_location << " is " << distance << endl;
        }
        else if(first == "STOPS" && amount == 1){
            vector<string> stops = make_stops(lineMap);
            cout << "All stops in alphabetical order:" << endl;
            sort(stops.begin(), stops.end());
            for (size_t i = 0; i <= stops.size() - 1; ++i){
                cout << stops.at(i) << endl;
            }
        }
        else{
            cout << "Error: Invalid input."  << endl;
        }
    }
    return true;
}

// Short and sweet main.
int main(){
    print_rasse();

    //reading file
    tramways lineMap = read_file();
    if (lineMap.empty()){
        return EXIT_FAILURE;
    }
    ask_command(lineMap);

    return EXIT_SUCCESS;
}
