#include "Player.h"

//Player Constructor, sets names,point and create a Mosaic object
Player::Player(std::string playerName) {
    this->playerName = playerName;
    points = 0;
    mosaic = new Mosaic();
}

//Player Deconstructor
Player::~Player() {
    delete mosaic;
    delete &playerName;
    delete &points;
    delete &pointsForRound;
}

//getter for playerName
std::string Player::getPlayerName() {
    return playerName;
}

//getter for mosaic
Mosaic* Player::getMosaic() {
    return mosaic;
}

//getter for points
int Player::getPoints() {
    return points;
}

//setter for points
void Player::setPoints(int ppoints) {
    this->points = ppoints;
}

//updates the point at the end of the round
void Player::updatePoints(int ppoints) {
    this->points += ppoints;
}

//setter for pointsForRound
void Player::setPointsforRound(int points) {
    this->pointsForRound = points;
}

//getter for pointsForRound
int Player:: getPointsForRound() {
    return this->pointsForRound;
}

//passing playerName and points to a file for savefile()
void Player::printName(std::ostream &ostream,int playernum) {
    ostream << "PLAYER_" << playernum << "_NAME=";
    ostream << playerName << std::endl;
    ostream << "PLAYER_" << playernum << "_SCORE=";
    ostream << points << std::endl;
}
