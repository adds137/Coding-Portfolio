#include "GameEngine.h"
#include <exception>
#include <iomanip>
#include <string>
#include <limits>
#include <sstream>
#include <fstream>

//GameEngine Constructor, create CentreBoard, set player to null, create array of players,
//set finshed and endRound to false, row and factory = 0 and choice, comand and colour to ""
GameEngine::GameEngine(){
    this->centreBoard = new CentreBoard();
    this->player1 = nullptr;
    this->player2 = nullptr;
    this->player3 = nullptr;
    this->player4 = nullptr;
    this->finished = false;
    this->endRound = false;
    this->turn = 1;
    choice = "";
    command = "";
    factory = 0;
    colour = "";
    row = 0;
}

//GameEngine Deconstructor
GameEngine::~GameEngine(){
    if(centreBoard != nullptr) {
        delete centreBoard;
    }

    for(int i=0; i != MAX_PLAYER; ++i) {
        if(players[i] != nullptr){
            delete players[i];
        }
    }
    delete &finished;
    delete &endRound;
    delete &turn;
    delete &choice;
    delete &command;
    delete &factory;
    delete &colour;
    delete &row;
    delete &gamesize;
}

//ask how many players they want and create the new players
void GameEngine::newPlayers() {
    int playernum = 0;

    do{
        std::cout << "\nHow many player?" << std::endl;
        std::cin >> playernum;
        if((playernum < 2 || playernum > 4) && !std::cin.eof()){
            std::cout << "\nInvalid Input. Try again." << std::endl;
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
    }while(playernum != 2 && playernum != 3 && playernum != 4 && !std::cin.eof());
    this->players = new Player*[playernum];
    this->gamesize = playernum;

    if(playernum == 2)
        getCentreBoard()->setmaxfactories(MAX_2_PLAYER_FACTORY);
    else if(playernum == 3)
        getCentreBoard()->setmaxfactories(MAX_3_PLAYER_FACTORY);
    else if(playernum == 4)
        getCentreBoard()->setmaxfactories(MAX_4_PLAYER_FACTORY);

    for(int i=0; i != playernum; ++i) {
        players[i] = nullptr;
    }

    for(int i = 0; i < playernum; i++) {
        std::string name;
        std::cout << "\nEnter name for player" << " " << i + 1 << ":" << std::endl;
        std::cout << "> ";
        std::cin >> name;
        if (std::cin.eof()) {
            exitProgram();
        }
        setPlayer(i + ONE,name);
    }
}

//runs the games
void GameEngine::playGame() {
    for (int i = 0; i < gamesize; i++) {
        if (i == 0)
            players[0] = this->player1;
        else if (i == 1)
            players[1] = this->player2;
        else if (i == 2)
            players[2] = this->player3;
        else if (i == 3)
            players[3] = this->player4;
    }

    while (!endRound) {
        Player *current = getPlayer(getTurn());
        std::cout << "\nTURN FOR PLAYER: " << current->getPlayerName() << std::endl;
        displayFactories();
        displayPlayerMosaic(current);

        do {
            getUserInput();
        } while (command != "turn" && command != "save" && command != "help" && command != "rules" &&
                 command != "show" && !std::cin.eof());

        if (std::cin.eof()) {
            exitProgram();
        }
        if (command == "save")
            saveGame(getTurn());
        else if (command == "turn") {
            managePlayerTurn(current);
            std::cout << "Turn successful" << std::endl;
            managenextturn();
            checkEndRound();
            clearinput();
        } else if (command == "show")
            showplayer();
        else if (command == "help")
            displaygamehelp();
        else if (command == "rules")
            displayrule();
    }
}

//get the Tile char value
char GameEngine::getColourFromInput(std::string tcolour){
    char toReturn;

    if(tcolour == "R") {
        toReturn = RED;
    } else if (tcolour == "Y"){
        toReturn = YELLOW;
    } else if (tcolour == "B"){
        toReturn = DARK_BLUE;
    } else if (tcolour == "L"){
        toReturn = LIGHT_BLUE;
    } else if (tcolour == "U"){
        toReturn = BLACK;
    } else {
        toReturn = NO_TILE;
    }
    return toReturn;
}

//grabs the user inputs
void GameEngine::getUserInput() {
    std::cout << "> ";
    std::getline(std::cin, choice);
    std::stringstream playerTurn(choice);
    playerTurn >> command >> factory >> colour >> row;
}

//manager the player turn if they selected turn or save etc
void GameEngine::managePlayerTurn(Player* current) {
    Mosaic* mosaic = current->getMosaic();
    char colourToChar = getColourFromInput(colour);
    int numTilesInFactory = 0;

    if(!centreBoard->isFactoryEmpty(factory)) {
        numTilesInFactory = centreBoard->getNumTilesInFactory(colourToChar, factory);
    }
    int pos = mosaic->indexToMove(colourToChar, row);

    while ((centreBoard->isFactoryEmpty(factory) || !mosaic->isStorageLineAvailable(row, colourToChar) ||
    mosaic->getBoard()[row - ONE][pos]->getColour() != NO_TILE || numTilesInFactory == 0 || colourToChar == NO_TILE) && !std::cin.eof()) {      
        if (centreBoard->isFactoryEmpty(factory)) {
            std::cout << "\nChosen factory is empty. Choose another one." << std::endl;
        } else if(!mosaic->isStorageLineAvailable(row, colourToChar)) {
            std::cout << "\nStorage line chosen has a different colour. Choose another one." << std::endl;
        } else if(mosaic->getBoard()[row - ONE][pos]->getColour() != NO_TILE) {
            std::cout << "\nColour has already been placed in the mosaic. Choose another one." << std::endl;
        } else if(numTilesInFactory == 0 || colourToChar == NO_TILE) {
            std::cout << "\nColour invalid. Choose another one." << std::endl;
        } 
        getUserInput();
        colourToChar = getColourFromInput(colour);
        pos = mosaic->indexToMove(colourToChar, row);
        numTilesInFactory = centreBoard->getNumTilesInFactory(colourToChar, factory);
    } 

    if(std::cin.eof()) {
        exitProgram();
    }
    int remaining = mosaic->placeTiles(row, colourToChar, numTilesInFactory);

    while (remaining != 0) {
        centreBoard->addTileToBoxlid(new Tile(colourToChar));
        remaining--;
    }

    if(getCentreBoard()->getcenterfactorynum() == ONE) {
        if (factory != 0) {
            centreBoard->moveTilesToCentralFactory(colourToChar, factory);
        } else {
            if (centreBoard->getCentralFactory(HEAD_CENTRE_FACTORY + ONE).front()->getColour() == FIRST_PLAYER) {
                centreBoard->removeTilesFromCentralFactory(FIRST_PLAYER,HEAD_CENTRE_FACTORY + ONE);
                mosaic->placeFirstPlayerTile();
            }
            centreBoard->removeTilesFromCentralFactory(colourToChar,HEAD_CENTRE_FACTORY + ONE);
        }
    }else if(getCentreBoard()->getcenterfactorynum() == TWO){
        if(factory != 0 && factory != 1)
            centreBoard->moveTilesToCentralFactory(colourToChar,factory);
        else{
            if (centreBoard->getCentralFactory(HEAD_CENTRE_FACTORY + ONE).front()->getColour() == FIRST_PLAYER) {
                centreBoard->removeTilesFromCentralFactory(FIRST_PLAYER,HEAD_CENTRE_FACTORY + ONE);
                mosaic->placeFirstPlayerTile();
            }
            centreBoard->removeTilesFromCentralFactory(colourToChar,factory + ONE);
        }
    }
}

//display the current player turn, centrefacory, factoires, player board, storage rows and broken tile
void GameEngine::displayPlayerMosaic(Player* current) {
    TilePtr* storageLine = nullptr;
    TilePtr* broken = current->getMosaic()->getBroken();
    TilePtr** board = current->getMosaic()->getBoard();
    std::cout << current->getPlayerName() << "'s Mosaic:" << std::endl;
    int space = 8;

    for(int i=0; i != MAX_MOSAIC_ROWS; ++i) {
        std::cout << i+1 << ":";
        storageLine = current->getMosaic()->getLine(i+1);

        for (int j = 0; j != space; ++j){ 
                std::cout << " "; 
            }
        space = space - TWO; 
  
        for(int j = i; j >= 0; --j) {
            std::cout << " " <<  getAnsiColor(storageLine[j]->getColour());
            getAnsiColor(storageLine[j]->getColour());
        }
        std::cout << " ||";

        for(int k = 0; k != MAX_MOSAIC_COLS; ++k) {
            std::cout << " " << getAnsiColor(board[i][k]->getColour());
        }
        std::cout << std::endl;
    }
    printBrokenTiles(broken);
}

//check if the round has ended
void GameEngine::checkEndRound() {
    if(centreBoard->checkEmpty()) {
        endRound = true;
    }
}

//calulate the points gained and loss for each round
void GameEngine::calculateRound() {
    for(int i=0; i != gamesize; ++i) {
        int pointsEarned = 0;
        Mosaic* playerMosaic = players[i]->getMosaic();

        for(int j = 0; j != MAX_MOSAIC_ROWS; ++j) {
            int index = playerMosaic->moveTile(j);

            if(index != -ONE) {
                int horizontal = playerMosaic->countHorizontal(j, index);
                int vertical = playerMosaic->countVertical(j, index);
                pointsEarned = pointsEarned + horizontal + vertical;
                if (horizontal == ONE || vertical == ONE) {
                    pointsEarned = pointsEarned - ONE;
                }
            }
        }
        int pointstoSubtract = playerMosaic->getBrokenPoints();

        if(pointstoSubtract != 0) {
            playerMosaic->clearBrokenTiles();
        }
        int pointsForRound = pointsEarned - pointstoSubtract;
        players[i]->setPointsforRound(pointsForRound);
        players[i]->updatePoints(pointsForRound);
    }
}

// check if the game has finshed or not
void GameEngine::checkFinished() {
    for(int i=0; i != gamesize; ++i) {
        for (int j = 0; j != MAX_MOSAIC_ROWS; ++j) {
            if(players[i]->getMosaic()->checkForCompleteLine(j)) {
                finished = true;
            }
        }
    }
}

//getter for finished
bool GameEngine::isFinished() {
    return finished;
}

//getter for centreBoard
CentreBoard* GameEngine::getCentreBoard() {
    return centreBoard;
}

//print the result at the end of each round
void GameEngine::printRoundResults() {
    std::cout << "=== ROUND RESULT ===" << std::endl;
    std::cout << std::endl;

    for(int i=0; i != gamesize; ++i) {
        std::cout << players[i]->getPlayerName() << " earned " << players[i]->getPointsForRound() << 
        " points for this round." << std::endl;
        std::cout << players[i]->getPlayerName() << "'s total points so far: " 
        << players[i]->getPoints() << "points" << std::endl;
        displayPlayerMosaic(players[i]);
        std::cout << std::endl;
    }
}

//set the endround to false
void GameEngine::setEndRoundToFalse() {
    endRound = false;
}

//check the the player has scored any point in that round
void GameEngine::endOfGameScoring() {
    for(int i=0; i != gamesize; ++i) {
        Mosaic* playerMosaic = players[i]->getMosaic();
        int pointsEarned = 0;

        for (int j = 0; j != MAX_MOSAIC_ROWS; ++j) {
            if(playerMosaic->checkForCompleteLine(j)) {
                pointsEarned += 2;
            }
        }

        for (int j = 0; j != MAX_MOSAIC_COLS; ++j) {
            if(playerMosaic->checkForCompleteColumn(j)) {
                pointsEarned += 7;
            }
        }
        int completeColours = playerMosaic->countCompleteColours();
        pointsEarned += completeColours * 10;
        players[i]->updatePoints(pointsEarned);
    }
}

//display the winner of the game
void GameEngine::displayWinner() {
    Player* winner = players[0];

    for(int i = 1; i != gamesize; ++i) {
        if(players[i]->getPoints() >= winner->getPoints()) {
            winner = players[i];
        }
    } 
    std::cout << "Player " << winner->getPlayerName() << " wins!" << std::endl;
}

//moves the discared tiles to the boxLid
void GameEngine::moveDiscardedTilesToBoxLid() {
    for(int i=0; i != gamesize; ++i) {
        std::vector<char> discarded = players[i]->getMosaic()->getDiscardedTiles();
        
        while(!discarded.empty()) {
            TilePtr toAdd = new Tile(discarded.back());
            centreBoard->addTileToBoxlid(toAdd);
            discarded.pop_back();
        }
    }
}

//print the tiles from the broken array
void GameEngine::printBrokenTiles(TilePtr* broken) {
    int countF = 0;
    int countR = 0;
    int countY = 0;
    int countB = 0;
    int countL = 0;
    int countU = 0;
    std::cout << "broken:";

    for (int i = 0; i != 7; ++i) {
        if(broken[i] != nullptr) {
            if (broken[i]->getColour() == FIRST_PLAYER) {
                countF++;
            } else if (broken[i]->getColour() == RED) {
                countR++;
            } else if(broken[i]->getColour() == YELLOW) {
                countY++;
            }  else if(broken[i]->getColour() == DARK_BLUE) {
                countB++;
            } else if(broken[i]->getColour() == LIGHT_BLUE) {
                countL++;
            } else if(broken[i]->getColour() == BLACK) {
                countU++;
            }
        }
    }

    for(int i = 0; i != countF; ++i) {
        std::cout << " " << COLOR_FIRST_PLAYER; 
    }
    getCentreBoard()->orderprintinghelper(countR,countY,countB,countL,countU);
    std::cout << std::endl;
}

//getter for turn;
int GameEngine::getTurn(){
    return turn;
}

//setter for turn
void GameEngine::setTurn(int pturn){
    this->turn = pturn;
}

//get the player based on which player they are player1, player2 etc
Player* GameEngine::getPlayer(int i){
    Player* toreturn = nullptr;

    if(i == 1)
        toreturn = this->player1;
    else if(i == 2)
        toreturn = this->player2;
    else if(i == 3)
        toreturn = this->player3;
    else if(i == 4)
        toreturn = this->player4;
    return toreturn;
}

//setting the player to the right variable (player1 or player2) based the int you pass
void GameEngine::setPlayer(int i,std::string name){
    if(i == 1)
        this->player1 = new Player(name);
    else if(i == 2)
        this->player2 = new Player(name);
    else if(i == 3)
        this->player3 = new Player(name);
    else if(i == 4)
        this->player4 = new Player(name);
}

//displays the 5 standard factories
void GameEngine::displayFactories() {
    std::cout << "Factories:" << std::endl;
    centreBoard->printCentralFactory();
    centreBoard->printFactories();
    std::cout << std::endl;
}

//save the current game to a file 
void GameEngine::saveGame(int turnNum) {
    std::string fileNameInput;
    std::cout << "What would you like the save file to be named?" << std::endl;
    std::cout << ">";
    std::cin >> fileNameInput;

    if (!std::cin.good()){
        std::cout << "Invalid Input please try again" << std::endl;
        std::cout << ">";
        std::cin >> fileNameInput;
    }
    saveFile(fileNameInput,turnNum);
    std::cout << "The game has saved" << std::endl;
    exit(0);
}

//saving the game to the file
void GameEngine::saveFile(std::string string, int numturn){
    // Setup the file for saving
    std::ofstream myfile(appendSaveFile(string));

    // Create new GameEngine Object

    /* Open the file, we then call upon our print methods
     and pass through our file so that the data can be saved*/

    if (myfile.is_open()) {
        dateComment(myfile);
        printgameinfo(myfile);
        getCentreBoard()->getTileBag()->printBag(myfile);
        getCentreBoard()->printBoxLid(myfile);
        newLine(myfile);
        getCentreBoard()->saveCenterFactories(myfile);
        getCentreBoard()->saveFactories(myfile);
        playerInfo(myfile);
        printCurrentPlayer(myfile, numturn);
    }
    myfile.close();
}

//adding the ".txt" to the end of the filename
std::string GameEngine::appendSaveFile(std::string string){
    std::string filename = string;
    std::string txt = ".txt";
    filename.append(txt);
    return filename;
}

//add the current player name to the std::ostream
void GameEngine::printCurrentPlayer(std::ostream &ostream, int num){
    ostream << "CURRENT_PLAYER=" << num;
    newLine(ostream);
}

//add a std::endl for saving game
void GameEngine::newLine(std::ostream& ostream){
    ostream << std::endl;
}

// Obtain current date and time so that we add it to a comment
// for our save file.
void GameEngine::dateComment(std::ostream& ostream){
    // Obtain current date and time
    std::time_t now = time(0);
    char *datetime = std::ctime(&now);
    ostream << "#Save game file " << datetime;
}

// Save the information for each player including their name,
// score and their mosaic elements such as the lines and board.
void GameEngine::playerInfo(std::ostream& ostream) {
    for(int i = ONE; i <= gamesize; i++){
        getPlayer(i)->printName(ostream,i);
        getPlayer(i)->getMosaic()->printLines(ostream,i);
        getPlayer(i)->getMosaic()->printBoard(ostream,i);
    }
}

// exit the program
void GameEngine::exitProgram() {
    std::cout << "Goodbye!" << std::endl;
    exit(0);
}

//manage which turn is it
void GameEngine::managenextturn(){
    if(getTurn() == gamesize)
        setTurn(ONE);
    else
        setTurn(getTurn() + ONE);
}

//show the playermosic board of chosen player
void GameEngine::showplayer(){
    std::string input;

    do{
        std::cout << "Enter name of player you wanna see" << std::endl;
        std::cout << "> ";
        std::cin >> input;

        if(input != player1->getPlayerName() && input != player2->getPlayerName() && input != player3->getPlayerName() && input != player4->getPlayerName() && !std::cin.eof()){
            std::cout << "\nInvalid Input. Try again." << std::endl;
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
    }while (input != player1->getPlayerName() && input != player2->getPlayerName() && input != player3->getPlayerName() && input != player4->getPlayerName() && !std::cin.eof());
    displayPlayerMosaic(getPlayer(input));
    command = "";
}

//get the player based on which player name
Player* GameEngine::getPlayer(std::string name){
    Player* toreturn = nullptr;

    if(name == player1->getPlayerName())
        toreturn = player1;
    else if(name == player2->getPlayerName())
        toreturn = player2;
    else if(name == player3->getPlayerName())
        toreturn = player3;
    else if(name == player4->getPlayerName())
        toreturn = player4;
    return toreturn;
}

//clear input for next player
void GameEngine::clearinput(){
    command = "";
    colour = "";
    factory = 0;
    row = 0;
}

//setter for gamesize
void GameEngine::setgamesize(int size){
    this->gamesize = size;
}

//getter for gamesize
int GameEngine::getgamesize(){
    return gamesize;
}

//create the player array based on how big it needed
void GameEngine::createPlayerslist(){
    this->players = new Player*[gamesize];
}

//saving the inportant gameinfo to the ostream
void GameEngine::printgameinfo(std::ostream& ostream){
    ostream << "GAMESIZE=" << gamesize;
    newLine(ostream);
    ostream << "CENTREFACTORY=" << getCentreBoard()->getcenterfactorynum();
    newLine(ostream);
    ostream << "MAXFACTORY=" << getCentreBoard()->getmaxfactories();
    newLine(ostream);
}

//display the help information
void GameEngine::displaygamehelp(){
        std::string filename = "help.txt";
    std::ifstream readhelp;
    readhelp.open(filename);

    while(readhelp.eof() == false){
        std::string line;
        std::getline(readhelp,line);
        std::cout << line << std::endl;
    }
    readhelp.close();;
    std::cout << std::endl;
    std::cout << "Symbol to colourchar--------------------" << std::endl;
    std::cout << COLOR_DARK_BLUE << "  =" << "B\t" << "Blue" <<std::endl;
    std::cout << COLOR_LIGHT_BLUE << "  =" << "L\t" << "Light Blue" << std::endl;
    std::cout << COLOR_RED << "  =" << "R\t" <<  "Red" << std::endl;
    std::cout << COLOR_YELLOW << "  =" << "Y\t" << "Yellow"<< std::endl;
    std::cout << COLOR_BLACK << "  =" << "U\t" << "Black"<< std::endl;
    std::cout << "Other symbols---------------------------" << std::endl;
    std::cout << COLOR_FIRST_PLAYER << "  =" << "F\t" << "First Player" << std::endl;
    std::cout << COLOR_NO_TILE << "  =" << ".\t" << "No tile" << std::endl;
    std::cout << std::endl;
    std::cout << "Preview full board----------------------" << std::endl;
    std::cout << "-----------" << std::endl;
    std::cout << "|" << COLOR_DARK_BLUE << " " << COLOR_YELLOW << " " << COLOR_RED << " " << COLOR_BLACK << " " << COLOR_LIGHT_BLUE << "|" <<std::endl;
    std::cout << "|" << COLOR_LIGHT_BLUE << " " << COLOR_DARK_BLUE << " " << COLOR_YELLOW << " " << COLOR_RED << " " << COLOR_BLACK << "|" <<std::endl;
    std::cout << "|" << COLOR_BLACK << " " << COLOR_LIGHT_BLUE << " " << COLOR_DARK_BLUE << " " << COLOR_YELLOW << " " << COLOR_RED << "|" <<std::endl;
    std::cout << "|" << COLOR_RED << " " << COLOR_BLACK << " " << COLOR_LIGHT_BLUE << " " << COLOR_DARK_BLUE << " " << COLOR_YELLOW << "|" <<std::endl;
    std::cout << "|" << COLOR_YELLOW << " " << COLOR_RED<< " " << COLOR_BLACK << " " << COLOR_LIGHT_BLUE << " " << COLOR_DARK_BLUE << "|" <<std::endl;
    std::cout << "-----------" << std::endl;
    std::cout << "---------------------------------------" << std::endl;
}

//gets ansi color code for cout
std::string GameEngine::getAnsiColor(char c){
    std::string toreturn;

    if(c == RED)
        toreturn = COLOR_RED;
    else if(c == YELLOW)
        toreturn = COLOR_YELLOW;
    else if(c == DARK_BLUE)
        toreturn = COLOR_DARK_BLUE;
    else if(c == LIGHT_BLUE)
        toreturn = COLOR_LIGHT_BLUE;
    else if(c == BLACK)
        toreturn = COLOR_BLACK;
    else if(c == NO_TILE)
        toreturn = COLOR_NO_TILE;
    else if(c == FIRST_PLAYER)
        toreturn == COLOR_FIRST_PLAYER;   
    return toreturn;
}

//display the rule of the game
void GameEngine::displayrule(){
    std::string filename = "rules.txt";
    std::ifstream readrule;
    readrule.open(filename);

    while(readrule.eof() == false){
        std::string line;
        std::getline(readrule,line);
        std::cout << line << std::endl;
    }
    readrule.close();
}