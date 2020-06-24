#ifndef GAME_ENGINE_H
#define GAME_ENGINE_H

#include <vector>
#include <iostream>
#include <ctime>
#include "Player.h"
#include "CentreBoard.h"

#define COLOR_RED "\u001b[31m\u2665\u001b[0m"
#define COLOR_YELLOW "\u001b[33m\u2600\u001b[0m"
#define COLOR_DARK_BLUE "\u001b[34m\u26c6\u001b[0m"
#define COLOR_LIGHT_BLUE "\u001b[36;1m\u266b\u001b[0m"
#define COLOR_BLACK "\u001b[30;1m\u262f\u001b[0m"
#define COLOR_FIRST_PLAYER "\u001b[30;1m\u26d4\u001b[0m"
#define COLOR_NO_TILE "\u001b[37m\u2610\u001b[0m"
#define MAX_PLAYER 4
#define HEAD_CENTRE_FACTORY 0
#define ONE 1
#define TWO 2


class GameEngine{
public:
    GameEngine();
    ~GameEngine();

    void newPlayers();
    void playGame();
    char getColourFromInput(std::string colour);
    void displayPlayerMosaic(Player* current);;
    void checkEndRound();
    void checkFinished();
    void calculateRound();
    void printRoundResults();
    void setEndRoundToFalse();
    void setTurn(int turn);
    int getTurn();
    void setPlayer(int i,std::string name);
    void getUserInput();
    void managePlayerTurn(Player* current);
    Player* getPlayer(int i);
    Player* getPlayer(std::string name);
    CentreBoard* getCentreBoard();
    bool isFinished();
    void endOfGameScoring();
    void displayWinner();
    void moveDiscardedTilesToBoxLid();
    void printBrokenTiles(TilePtr* broken);
    void displayFactories();
    void exitProgram();
    void saveFile(std::string string, int numturn);
    void printCurrentPlayer(std::ostream& ostream, int num);
    void newLine(std::ostream& ostream);
    void dateComment(std::ostream& ostream);
    void playerInfo(std::ostream& ostream);
    std::string appendSaveFile(std::string string);
    void saveGame(int turnNum);
    void managenextturn();
    void showplayer();
    void clearinput();
    void setgamesize(int size);
    int getgamesize();
    void createPlayerslist();
    void printgameinfo(std::ostream& ostream);
    void displaygamehelp();
    std::string getAnsiColor(char c);
    void displayrule();
private:
    Player* player1;
    Player* player2;
    Player* player3;
    Player* player4;
    Player** players;
    CentreBoard* centreBoard;
    bool finished;
    bool endRound;
    int turn;
    std::string choice;
    std::string command;
    int factory;
    std::string colour;
    int row;
    int gamesize;
};

#endif //GAME_ENGINE_H