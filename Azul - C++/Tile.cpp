#include "Tile.h"

//Tile Constructor, set colour to parameter
Tile::Tile(char colour) {
    this->colour = colour;
}

//Tile deconstructor
Tile::~Tile(){
    delete &colour;
}

//getter for colour
char Tile::getColour() {
    return this->colour;
}

//setter for colour
void Tile::setColour(char colour){
    this->colour = colour;
}
