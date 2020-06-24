#ifndef MOSAIC_H
#define MOSAIC_H

#include <vector>
#include <iostream>
#include <fstream>
#include "Tile.h"

#define MAX_MOSAIC_ROWS 5
#define MAX_MOSAIC_COLS 5
#define LINE_1 1
#define LINE_2 2
#define LINE_3 3
#define LINE_4 4
#define LINE_5 5
#define BROKEN 7
#define IF_1 1
#define IF_2 2
#define IF_3 3
#define IF_4 4
#define IF_5 5

class Mosaic{
public:
    Mosaic();
    ~Mosaic();

    TilePtr* getLine(int pos);
    TilePtr* getBroken();
    TilePtr** getBoard();
    int placeTiles(int storageRow, char colour, int numTiles);
    void placeFirstPlayerTile();
    int moveTile(int row);
    void clearStorageRow(int row);
    bool isStorageComplete(int line);
    int indexToMove(char colour, int row);
    char getTileToMove(int line);
    bool checkForCompleteLine(int row);
    int countHorizontal(int row, int col);
    int countVertical(int row, int col);
    int getBrokenPoints();
    void clearBrokenTiles();
    bool checkForCompleteColumn(int col);
    int countCompleteColours();
    void clearLines();
    std::vector<char> getDiscardedTiles();
    bool isStorageLineAvailable(int row, char colour);
    void printBoard(std::ostream& ostream,int playernum);
    void printLines(std::ostream& ostream,int playernum);
private:
    TilePtr** board;
    TilePtr* line1;
    TilePtr* line2;
    TilePtr* line3;
    TilePtr* line4;
    TilePtr* line5;
    TilePtr* broken;
    std::vector<char> discardedTiles;
};

#endif //MOSAIC_H