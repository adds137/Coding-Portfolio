#ifndef TILEBAG_H
#define TILEBAG_H

#include "Tile.h"
#include <istream>
#include <ostream>

class Node {
public:
    Node(TilePtr tile, Node* next);
    TilePtr tile;
    Node* next;
};

class TileBag {
public:
    TileBag();
    ~TileBag();

    unsigned int size();
    void addTile(TilePtr tile);
    void removeTileFront();
    TilePtr getHead();
    void clear();
    void printBag(std::ostream& ostream);
private:
    Node* head;
    int length;
};

#endif //TILEBAG_H