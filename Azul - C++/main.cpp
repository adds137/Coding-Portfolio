#include <iostream>
#include <string>
#include <sstream>
#include <limits>
#include "GameEngine.h"

#define LOAD_GAMEINFO_NUM 3
#define READ_PLAYER_NUM 13
#define READ_NO_TILE '-'
#define LINE_ONE 1
#define LINE_TWO 2
#define LINE_SIX 6
#define LINE_SEVEN 7
#define EQUAL '='
#define HASHTAG '#'

using std::string;
using std::cout;
using std::cin;
using std::endl;

class Args {
public:

   std::string deckFile;

   bool haveSeed;
   int seed;
};

void processArgs(int argc, char** argv, Args* args);
void displayMenu();
void showCredits();
void newGame(Args* args);
void loadGame();
void loadcentrefactory(std::ifstream& readfile, GameEngine* ge);
char* getchar(string line);
void readcentrefactory(char * array, int index, GameEngine* ge);
void readplayer(std::ifstream& readfile,GameEngine* ge,int index);
string getstring(char* array);
void addstoragerow(char* array, int index,Player* p);
void addbroken(char* array,Player* p);
void addMosic(char* array, Player* p,int index);
void Rungame(GameEngine* ge,bool resume);
void loadgameinfo(std::ifstream& readfile, GameEngine* ge);
void loadfactories(std::ifstream& readfile, GameEngine* ge);
void displayhowtoplay();

int main(int argc, char** argv) {
    //grabbing seed values (if any)
    Args args;
    processArgs(argc, argv, &args);

    cout << "Welcome to Azul!\n-------------------" << endl;
    int choice = 0;

    // display the main menu amd loop through choices until a valid choices is picked
    do {
        displayMenu();
        cin >> choice;

        if((choice < 1 || choice > 5 || !cin.good()) && !cin.eof()) {
            cout << "\nInvalid Input. Try again." << endl;
            cin.clear();
            cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        } else {
            if(choice == 1) {
                newGame(&args);
            } else if(choice == 2) {
                loadGame();
            } else if(choice == 3) {
                showCredits();
            }else if(choice == 4){
                displayhowtoplay();
            }else if(choice == 5 || cin.eof()) {
                cout << "Goodbye! \n";           
            }
        }
    } while (choice != 5 && !cin.eof() && choice != 1 && choice != 2);

}

// displays the main menu
void displayMenu() {
    cout << "\nMenu\n----" << endl;
    cout << "1. New Game" << endl;
    cout << "2. Load Game" << endl;
    cout << "3. Credits (Show student information)" << endl;
    cout << "4. How to play" << endl;
    cout << "5. Quit" << endl;
    cout << "> ";
}

// shows the credits
void showCredits() {
    string names[] = {"Adrian Vong", "Mark Baptista", "Amna Alfarah Alonto"};
    string studentId[] = {"s3721092", "s3722699", "s3778713"};
    string email = "@student.rmit.edu.au";
    cout << "----------------------------------" << endl;

    for(int i=0; i < 3; ++i) {
        cout << "Name: " << names[i] << endl;
        cout << "Student ID: " << studentId[i] << endl;
        cout << "Email: " << studentId[i] << email << endl;
        if(i != 2) {
            cout << endl;
        }
    }
    cout << "----------------------------------" << endl;
}

//create a new game
void newGame(Args* args){
    GameEngine* ge = new GameEngine();
    ge->getCentreBoard()->inputcentralfactory();
    ge->getCentreBoard()->createcentrefactory();
    ge->newPlayers();
    ge->getCentreBoard()->createfactories();
    ge->getCentreBoard()->shuffleTileBag(args->seed);
    Rungame(ge,true);
}

// load existing game from file
void loadGame(){
    string filename;
    bool filecheck = true;
    std::ifstream readfile;
    GameEngine *ge = new GameEngine();

    while(filecheck) {
        cout << "Enter the filename from which loads a game (starts with ../)" << endl;
        cout << ">";
        cin >> filename;
        readfile.open(filename);

        if (readfile.good()) {
            filecheck = false;
            loadgameinfo(readfile, ge);
            loadcentrefactory(readfile, ge);
            loadfactories(readfile,ge);
            ge->createPlayerslist();
            readplayer(readfile, ge, 1);
            readplayer(readfile, ge, 2);
            if(ge->getgamesize() == 3)
                readplayer(readfile,ge,3);
            else if(ge->getgamesize() == 4){
                readplayer(readfile,ge,3);
                readplayer(readfile,ge,4);
            }
            string line;
            std::getline(readfile, line);
            char *chararray = getchar(line);
            string charstring = getstring(chararray);
            int stringint = std::stoi(charstring);
            ge->setTurn(stringint);
        }else
            cout << "ERROR: file doesn't exist" << endl;
    }
    readfile.close();
    Rungame(ge,false);
}

// processing the Arguments/seed
void processArgs(int argc, char** argv, Args* args) {
   if (argc >= 3) {
       std::string argv1 = argv[1];

       if(argv1 == "-s") {
            std::string strSeed(argv[2]);
            std::stringstream sstream(strSeed);
            sstream >> args->seed;
            args->haveSeed = true;
       }
   } else {
      args->haveSeed = false;
      args->seed = 0;
   }
}

// helper for loadgame(), used for reading the factories, lidbox and tilebag from file
void loadcentrefactory(std::ifstream& readfile, GameEngine* ge) {
    int loopnum = 3;
    int centrefactory = 1;
    ge->getCentreBoard()->createcentrefactory();
    ge->getCentreBoard()->removeTilesFromCentralFactory(FIRST_PLAYER,1);

    if(ge->getCentreBoard()->getcenterfactorynum() == TWO)
        loopnum++;

    for(int i = 0; i < loopnum; i++) {
        string line;
        std::getline(readfile, line);

        if (line.at(0) == HASHTAG)
            --i;
        else {
            char *chararray = getchar(line);

            if (i == 0 || i == 1) {
                for (unsigned int x = 0; x < sizeof(chararray); x++) {
                    if (i == 0)
                        ge->getCentreBoard()->getTileBag()->addTile(new Tile(chararray[x]));
                    else if (i == ONE)
                        ge->getCentreBoard()->addTileToBoxlid(new Tile(chararray[x]));
                }
            }else if (i >= TWO) {
                readcentrefactory(chararray, centrefactory, ge);
                centrefactory++;
            }
        }
    }
}

// helper for loadgame(),converting the input from std::getline() into char to be used by others
char* getchar(string line){
    bool allowed = false;
    char* array = new char[100];
    int arraysize = 0;

    for(unsigned int i = 0; i < line.length();i++){
        if(line.at(0) == HASHTAG)
            --i;
        else if(line.at(i) == EQUAL)
            allowed = true;
        else if(allowed && line.at(i) != EQUAL){
            array[i] = line.at(i);
            array[arraysize] = line.at(i);
            arraysize++;
        }
    }
    return array;
}

// helper for loadgame(), used for adding the std::vector centrefactory or 1D array standard factories
void readcentrefactory(char * array, int index, GameEngine* ge){
    if(index == ONE) {
        for (unsigned int i = 0; i < sizeof(array); i++)
            ge->getCentreBoard()->loadcenterFactory(array[i],index);
    }else if(index == TWO) {
        for (unsigned int i = 0; i < sizeof(array); i++)
            ge->getCentreBoard()->loadcenterFactory(array[i],index);
    }
}

// helper for loadgame(), used for reading all the player info from file
void readplayer(std::ifstream& readfile, GameEngine* ge, int index){
    int storageline = 1;
    int mosaicline = 0;

    for(int i = 0; i < READ_PLAYER_NUM; i++) {
        string line;
        std::getline(readfile, line);

        if(line.at(ONE) == '#')
            --i;
        else{
            char * chararray = getchar(line);
            if(i == 0) {
                string charstring = getstring(chararray);
                ge->setPlayer(index,charstring);
            }else if(i == LINE_ONE) {
                string charstring = getstring(chararray);
                int stringint = std::stoi(charstring);
                ge->getPlayer(index)->setPoints(stringint);
            }else if(i >= LINE_TWO && i <= LINE_SIX) {
                    addstoragerow(chararray,storageline, ge->getPlayer(index));
                    storageline++;
            }else if (i == LINE_SEVEN)
                addbroken(chararray, ge->getPlayer(index));
            else {
                    addMosic(chararray, ge->getPlayer(index), mosaicline);
                    mosaicline++;
            }
        }
    }
}

// helper for loadgame(),converting the input from std::getline() into string to be used by others
string getstring(char* array){
    string toreturn;

    for (unsigned int i = 0; i <= sizeof(array); i++)
        toreturn = toreturn + array[i];
    return toreturn;
}

// helper for loadgame(), used for adding the 1D storagerows from file
void addstoragerow(char* array, int index,Player* p){
    for (unsigned int i = 0; i < sizeof(array); i++)
        p->getMosaic()->getLine(index)[i] = new Tile(array[i]);
}

// helper for loadgame(), used for adding the 1D broken array from file
void addbroken(char* array,Player* p){
    for(unsigned int i = 0; i < sizeof(array); i++)
        p->getMosaic()->getBroken()[i] = new Tile(array[i]);
}

// helper for loadgame(), used for adding the 2D mosicboard from file
void addMosic(char* array, Player* p,int index){
    for (unsigned int i = 0; i < /*sizeof(array)*/ 5; i++){
        if(array[i] == READ_NO_TILE)
            p->getMosaic()->getBoard()[index][i] = new Tile(NO_TILE);
        else
            p->getMosaic()->getBoard()[index][i] = new Tile(array[i]);
    }
}

// Runs Game for both Load and New Game
void Rungame(GameEngine* ge,bool resume){
    bool round = resume;

    do {
        if(resume == true)
            ge->getCentreBoard()->populateFactories();

        if(round == true)
            cout << "== START ROUND ==" << endl;
        else {
            cout << "== RESUME GAME ==" << endl;
            round = true;
        }
        ge->playGame();
        cout << "\n == END ROUND == \n" << endl;
        cout << endl;
        ge->calculateRound();
        ge->printRoundResults();
        ge->checkFinished();

        if(!ge->isFinished()) {
            cout << "Preparing for next round... \n" << endl;
            ge->setEndRoundToFalse();
            ge->moveDiscardedTilesToBoxLid();
        }
    } while(!ge->isFinished());

    if(ge->isFinished()) {
        cout << "=== GAME OVER ===" << endl;
        ge->endOfGameScoring();
        ge->displayWinner();
    }
    delete ge;
}

//loading the important gameinfo from a file
void loadgameinfo(std::ifstream& readfile, GameEngine* ge){
    for(int i = 0; i < LOAD_GAMEINFO_NUM; i++) {
        string line;
        std::getline(readfile, line);

        if (line.at(0) == HASHTAG)
            --i;
        else {
            char *chararray = getchar(line);
            string charstring = getstring(chararray);
            int lineint = std::stoi(charstring);

            if(i == 0)
                ge->setgamesize(lineint);
            else if(i == ONE)
                ge->getCentreBoard()->setcenterfactorynum(lineint);
            else if(i == TWO)
                ge->getCentreBoard()->setmaxfactories(lineint);
        }
    }
}

//reading the factories from a file
void loadfactories(std::ifstream& readfile, GameEngine* ge){
    int factorynum = 0;
    int maxfactory = ge->getCentreBoard()->getmaxfactories();
    ge->getCentreBoard()->createfactories();

    for(int i = 0; i < maxfactory;i++){
        string line;
        std::getline(readfile, line);

        if(line.at(0) == HASHTAG)
            --i;
        else{
            char *chararray = getchar(line);
            for(unsigned int x = 0; x < sizeof(chararray);x++)
                ge->getCentreBoard()->getFactories()[factorynum][x] = new Tile(chararray[x]);
        }
        factorynum++;
    }
}

//display How to play the game
void displayhowtoplay(){
    string filename = "rules.txt";
    std::ifstream readrule;
    readrule.open(filename);

    while(readrule.eof() == false){
        string line;
        std::getline(readrule,line);
        cout << line << endl;
    }
    readrule.close();
}