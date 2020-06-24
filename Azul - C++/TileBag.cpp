#include <exception>
#include <iostream>
#include "TileBag.h"

//Node Constructor, setting tile and next has their respected parameters
Node::Node(TilePtr tile, Node* next) :
    tile(tile),
    next(next)
{}

//Node Constructor, setting head to nullpitr and length to 0
TileBag::TileBag() {
    head = nullptr;
    length = 0;
}


//Tilebag Deconstructor, calls clear();
TileBag::~TileBag() {
    clear();
}

//gets the size of the TileBag
unsigned int TileBag::size() {
    return length;   
}

//adds a tile to the back of the Linkedlist
void TileBag::addTile(TilePtr tile) {
    Node* toAdd = new Node(tile, nullptr);

    if(head == nullptr) {
        head = toAdd;
    }else{
        Node* current = head;

        while (current->next != nullptr) {
            current = current->next;
        }
        current->next = toAdd;    
    }
    length++;
}

//removes the first in the list
void  TileBag::removeTileFront() {
    if (head != nullptr){
        Node* todelete = head;
        head = head->next;
        delete todelete;
        length--;
    }else {
        throw std::logic_error("Deleting on empty list");
    }
}

//get the first tile in the bag
TilePtr TileBag::getHead() {
    TilePtr toGet = nullptr;

    if (head != nullptr){
        toGet = head->tile; 
        removeTileFront(); 
    }
    return toGet;
}

//Clear all tiles from the bag
void TileBag::clear(){
   while (head != nullptr) {
      removeTileFront();
   }
}

//saving the tilebag to the file
void TileBag::printBag(std::ostream &ostream) {
    Node *tmp = head;
    ostream << "BAG=";
    
    while (tmp != NULL){
        ostream << tmp->tile->getColour();
        tmp = tmp->next;
    }
    ostream << std::endl;
}