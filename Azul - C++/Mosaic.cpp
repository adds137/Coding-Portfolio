#include "Mosaic.h"

//Mosaic Constructor,  implement the game board + the storage row and set the to No_TILE
Mosaic::Mosaic() {
    board = new TilePtr*[MAX_MOSAIC_ROWS];

    for(int i = 0; i != MAX_MOSAIC_ROWS; ++i) {
        board[i]= new TilePtr[MAX_MOSAIC_COLS];

        for(int j=0; j != MAX_MOSAIC_COLS; ++j) {
            board[i][j] = new Tile(NO_TILE);
        }
    }
    line1 = new TilePtr[LINE_1];
    line2 = new TilePtr[LINE_2];
    line3 = new TilePtr[LINE_3];
    line4 = new TilePtr[LINE_4];
    line5 = new TilePtr[LINE_5];

    for(int i=0; i != MAX_MOSAIC_ROWS; ++i) {
        for(int j=0; j <= i; ++j) {
            if(i == 0) {
                line1[j] = new Tile(NO_TILE);
            } else if(i == IF_1) {
                line2[j] = new Tile(NO_TILE);
            } else if(i == IF_2) {
                line3[j] = new Tile(NO_TILE);
            } else if(i == IF_3) {
                line4[j] = new Tile(NO_TILE);
            } else if(i == IF_4) {
                line5[j] = new Tile(NO_TILE);
            }
        }
    }
    broken = new TilePtr[BROKEN];

    for(int i=0; i < BROKEN; ++i) {
        broken[i] = nullptr;
    } 
    discardedTiles.clear();
}

//Mosaic Deconstructor
Mosaic::~Mosaic() {
    clearLines();
    delete[] board;
    delete line1;
    delete line2;
    delete line3;
    delete line4;
    delete line5;
    delete broken;
    discardedTiles.clear();
    // delete &playernum;
}

//getter for the right storage line
TilePtr* Mosaic::getLine(int pos) {
    TilePtr* toReturn = new TilePtr(nullptr);

    if (pos == IF_1){
        toReturn = line1;
    } else if (pos == IF_2){
        toReturn = line2;
    } else if (pos == IF_3){
        toReturn = line3;
    } else if (pos == IF_4){
        toReturn = line4;
    }else if (pos == IF_5){
        toReturn = line5;
    }
    return toReturn;
}

//getter for broken
TilePtr* Mosaic::getBroken() {
    return broken;
}

//getter for board
TilePtr** Mosaic::getBoard() {
    return board;
}

//places the tiles from the factories or centralfactory to the right storage rows AKA plays turn
int Mosaic::placeTiles(int storageRow, char colour, int numTiles) {
    int remaining = numTiles;
    TilePtr* line = getLine(storageRow);
    
    for(int i = 0; i != storageRow; ++i) {
        if (line[i]->getColour() == NO_TILE && remaining != 0) {
            line[i]->setColour(colour);
            --remaining;
        }
    }

    for(int i=0; i != BROKEN; ++i) {
        if(broken[i] == nullptr && remaining != 0) {
            broken[i] = new Tile(colour);
            --remaining;
        }
    }
    return remaining;
}

//moves complete storage rows to the board at the end of round
int Mosaic::moveTile(int row) {
    int index = -1;

    if(isStorageComplete(row + IF_1)) {
        char colour = getTileToMove(row + 1);
        index = indexToMove(colour, row + 1);
        board[row][index]->setColour(colour);
        clearStorageRow(row + 1);   
    }
    return index;
}

//count the tiles in horizontal and output the points gained
int Mosaic::countHorizontal(int row, int col) {
    int counter = 0;
    int i=col;

    while (i < MAX_MOSAIC_COLS && board[row][i]->getColour() != NO_TILE) {
        counter++;
        i++;
    } 

    if(col != 0) {
        int j = col - 1;
        while(j >= 0 && board[row][j]->getColour() != NO_TILE) {
            counter++;
            j--;
        } 
    }
    return counter;
}

//count the tiles in vertical and output the points gained
int Mosaic::countVertical(int row, int col) {
    int counter = 0;
    int i=row;

    while (i < MAX_MOSAIC_ROWS && board[i][col]->getColour() != NO_TILE) {
        counter++;
        i++;
    } 

    if(row != 0) {
        int j = row - 1;
        while(j >= 0 && board[j][col]->getColour() != NO_TILE) {
            counter++;
            j--;
        } 
    }
    return counter;
}

//clear the storagerow after it has been moved and filled
void Mosaic::clearStorageRow(int row) {
    TilePtr* storageLine = getLine(row);

    for(int i = 0; i != row; ++i) {
        if(storageLine[i]->getColour() != NO_TILE) {
            discardedTiles.push_back(storageLine[i]->getColour());
            storageLine[i]->setColour(NO_TILE);
        }
    }
    discardedTiles.pop_back();
}

//check if the storage row is full
bool Mosaic::isStorageComplete(int line) {
    bool complete = false;
    TilePtr* storageLine = getLine(line);

    for(int i=0; i != line; ++i) {
        if(i == line - IF_1) {
            if(storageLine[i]->getColour() != NO_TILE) {
                complete = true;
            }
        }
    }
    return complete;
}

//get the color index to of the tile
int Mosaic::indexToMove(char colour, int row) {
    int index = 0;

    for(int i=0; i != row; ++i) {
        if(i == row - IF_1) {
            if(colour == DARK_BLUE) {
                index = i;
            } else if (colour == YELLOW) {
                index = i+1;
            } else if (colour == RED) {
                index = i + 2;
            } else if (colour == BLACK) {
                index = i + 3;
            } else if (colour == LIGHT_BLUE) {
                index = i + 4;
            }            
        }
    }

    if(index > IF_4) {
        index = index - 5;
    }   
    return index;    
}

//gets the tile needed to move to board
char Mosaic::getTileToMove(int line) {
    return getLine(line)[0]->getColour();
}

//check for completed Line on the board
bool Mosaic::checkForCompleteLine(int row) {
    bool complete = false;
    int count = 0;

    for(int j = 0; j != MAX_MOSAIC_COLS; ++j) {
        if(board[row][j]->getColour() != NO_TILE) 
            count++;    
    }

    if(count == IF_5)
        complete = true;
    return complete;
}

//places first player Tile
void Mosaic::placeFirstPlayerTile() {
    int i = 0;
    bool placed = false;

    while(i != BROKEN && placed == false) {
        if(broken[i] == nullptr) {
            broken[i] = new Tile(FIRST_PLAYER);
            placed = true;
        }
        i++;
    }    
}

//count up the broken tiles and outputs the points loss
int Mosaic::getBrokenPoints(){
    int count = 0;
    int points = 0;

    for(int i=0; i != BROKEN; ++i) {
        if(broken[i] != nullptr) 
            count++;
    }

    if(count == IF_1 || count == IF_2) 
        points = count;
    else if(count == IF_3 || count == IF_4 || count == IF_5) 
        points =  2 * (count-1);
    else if( count == 6) 
        points = 11;
    else if(count == 7) 
        points = 14;
    return points;
}

//clear the broken tiles into the boxLid at the end of a round
void Mosaic::clearBrokenTiles() {
    for(int i=0; i != BROKEN; ++i) {
        if(broken[i] != nullptr) {
            if(broken[i]->getColour() != FIRST_PLAYER) 
                discardedTiles.push_back(broken[i]->getColour());
            delete broken[i];
            broken[i] = nullptr;
        }
    }
}

//check for completed column on the board
bool Mosaic::checkForCompleteColumn(int col) {
    bool complete = false;
    int count = 0;

    for(int i = 0; i != MAX_MOSAIC_ROWS; ++i) {
        if(board[i][col]->getColour() != NO_TILE) 
            count++;
    }

    if(count == IF_5) 
        complete = true;
    return complete;    
}

//check for completedcolours on the board
int Mosaic::countCompleteColours() {
    int complete = 0;
    char colours[] = {RED, YELLOW, DARK_BLUE, LIGHT_BLUE, BLACK};
    
    for(int i=0; i != NUMBER_OF_COLOURS; ++i) {
        int count = 0;
        for(int j = 0; j != MAX_MOSAIC_ROWS; ++j) {
            for(int k = 0; k != MAX_MOSAIC_COLS; ++k) {
                if(board[j][k]->getColour() == colours[i]) 
                    count++;
            }

            if(count == IF_5) 
                complete++;
        }
    }
    return complete;
}

// clear the lines from the storage rows
void Mosaic::clearLines() {
    for(int i = 0; i != MAX_MOSAIC_ROWS; ++i) {
        for(int j = 0; j != MAX_MOSAIC_COLS; ++j) {
            if(board[i][j] != nullptr) {
                delete board[i][j];
                board[i][j] = nullptr;
            }
        }
    }

    for(int i=0; i != MAX_MOSAIC_ROWS; ++i) {
        for(int j=0; j <= i; ++j) {
            if(i == 0) {
                if(line1[j] != nullptr) {
                    delete line1[j];
                    line1[j] = nullptr;
                }
            } else if(i == IF_1) {
                if(line2[j] != nullptr) {
                    delete line2[j];
                    line2[j] = nullptr;
                }
            } else if(i == IF_2) {
                if(line3[j] != nullptr) {
                    delete line3[j];
                    line3[j] = nullptr;
                }
            } else if(i == IF_3) {
                if(line4[j] != nullptr) {
                    delete line4[j];
                    line4[j] = nullptr;
                }
            } else if(i == IF_4) {
                if(line5[j] != nullptr) {
                    delete line5[j];
                    line5[j] = nullptr;
                }
            }
        }
    }

    for(int i=0; i < BROKEN; ++i) {
        if(broken[i] != nullptr) {
            delete broken[i];
            broken[i] = nullptr;
        }
    } 
}

//getter for discardedTiles
std::vector<char> Mosaic::getDiscardedTiles() {
    return discardedTiles;
}

//check if storage line is avaiable
bool Mosaic::isStorageLineAvailable(int row, char colour) {
    bool valid = false;

    if(getLine(row)[0]->getColour() == colour || getLine(row)[0]->getColour() == NO_TILE)
        valid = true;
    else
        valid = false;
    return valid;
}

//print the board to a file
void Mosaic::printBoard(std::ostream &ostream,int playernum) {
    for(int i= 0; i != MAX_MOSAIC_ROWS; ++i) {
        ostream << "PLAYER_" << playernum << "_MOSAIC_" << i << "=";
        for (int j = 0; j != MAX_MOSAIC_COLS; ++j) {
            char colour;
            if (board[i][j]->getColour() == NO_TILE){
                colour = '-';
                ostream << colour;
            } else{
                colour = board[i][j]->getColour();
                ostream << colour;
            }
        }
        ostream << std::endl;
    }
}

//print the storage line to a file
void Mosaic::printLines(std::ostream& ostream,int playernum){
    ostream << "PLAYER_" << playernum << "_PATTERN_LINE_1=";
    for (int i = 0; i < LINE_1 ; ++i) {
        char colour;
        colour = line1[i]->getColour();
        ostream << colour;
    }
    ostream << std::endl;

    ostream << "PLAYER_" << playernum << "_PATTERN_LINE_2=";
    for (int j = 0; j < LINE_2; ++j){
        char colour;
        colour = line2[j]->getColour();
        ostream << colour;
    }
    ostream << std::endl;

    ostream << "PLAYER_" << playernum << "_PATTERN_LINE_3=";
    for (int k = 0; k < LINE_3; ++k){
        char colour;
        colour = line3[k]->getColour();
        ostream << colour;
    }
    ostream << std::endl;

    ostream << "PLAYER_" << playernum << "_PATTERN_LINE_4=";
    for (int l = 0; l < LINE_4; ++l) {
        char colour;
        colour = line4[l]->getColour();
        ostream << colour;
    }
    ostream << std::endl;

    ostream << "PLAYER_" << playernum << "_PATTERN_LINE_5=";
    for (int m = 0; m < LINE_5; ++m) {
        char colour;
        colour = line5[m]->getColour();
        ostream << colour;
    }
    ostream << std::endl;
    
    ostream << "PLAYER_" << playernum << "_FLOOR_LINE=";
    for (int n = 0; n < BROKEN; ++n) {
        if (broken[n] == nullptr){
            broken[n] = new Tile(NO_TILE);
            char colour;
            colour = broken[n]->getColour();
            ostream << colour;
        }else {
            char colour;
            colour = broken[n]->getColour();
            ostream << colour;
        }
    }
    ostream << std::endl;
}

