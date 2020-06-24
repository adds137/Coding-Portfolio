#include "CentreBoard.h"
#include "GameEngine.h"
#include <random>
#include <algorithm>

//CentreBoard Constructor, create new tilebag and set the fill limit
CentreBoard::CentreBoard() {
    this->tileBag = new TileBag();
    dbToFill = 20;
    yToFill = 20;
    rToFill = 20;
    bToFill = 20;
    lbToFill = 20;
}

//CentreBoard Deconstructor
CentreBoard::~CentreBoard() {
    if(!centralFactory1.empty() || !centralFactory2.empty()) {
        centralFactory1.clear();
        centralFactory2.clear();
    }

    for(int i=0; i != maxfactories; ++i) {
        for(int j=0; j != MAX_TILES_PER_FACTORY; ++j) {
            if(factories[i][j]!= nullptr) {
                delete factories[i][j];
            }
        }
    }
    delete factories;

    if(tileBag != nullptr) {
        delete tileBag;
    }
    boxLid.clear();
    delete &dbToFill;
    delete &yToFill;
    delete &rToFill;
    delete &bToFill;
    delete &lbToFill;
    delete &centerfactorynum;
    delete &maxfactories;
}

//gets the number of a certain colour in a factory row
int CentreBoard::getNumTilesInFactory(char colour, int row) {
    int counter = 0;

    if(centerfactorynum == ONE) {
        if (row != 0) {
            for (int j = 0; j != MAX_TILES_PER_FACTORY; ++j) {
                if (factories[row - ONE][j]->getColour() == colour) {
                    counter++;
                }
            }
        }else {
            for (unsigned int i = 0; i != centralFactory1.size(); ++i) {
                if (centralFactory1[i]->getColour() == colour) {
                    counter++;
                }
            }
        }
    }else if(centerfactorynum == TWO){
        if (row != 1 && row != 0) {
            for (int j = 0; j != MAX_TILES_PER_FACTORY; ++j) {
                if (factories[row - TWO][j]->getColour() == colour) {
                    counter++;
                }
            }
        }else {
            for (unsigned int x = 0; x != getCentralFactory(row + 1).size(); ++x) {
                if (getCentralFactory(row + 1)[x]->getColour() == colour) {
                    counter++;
                }
            }
        }
    }
    return counter;
}

//moves tile to the CentreFactory and if needed ask with centrefactory to put the discarded tiles in
void CentreBoard::moveTilesToCentralFactory(char colour, int row) {
    if (centerfactorynum == ONE) {
        for (int j = 0; j != MAX_TILES_PER_FACTORY; ++j) {
            if (factories[row - ONE][j]->getColour() != colour) {
                centralFactory1.push_back(factories[row - ONE][j]);
            }
            factories[row - ONE][j] = nullptr;
        }
    } else {
        int centralfactorychoice;

        do {
            std::cout << "Which centre factory would you like to place the discard tile in?" << std::endl;
            std::cout << ">";
            std::cin >> centralfactorychoice;

            if ((centralfactorychoice < 0 || centralfactorychoice > 1 || !std::cin.good()) && !std::cin.eof()) {
                std::cout << "\nInvalid Input. Try again." << std::endl;
                std::cin.clear();
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        } while (centralfactorychoice != 0 && centralfactorychoice != 1 && !std::cin.eof());

        for (int j = 0; j != MAX_TILES_PER_FACTORY; ++j) {
            if (factories[row - TWO][j]->getColour() != colour) {
                addcenterFactory(centralfactorychoice + ONE, factories[row - TWO][j]);
            }
            factories[row - TWO][j] = nullptr;
        }
    }
}

//getter for factories
TilePtr** CentreBoard::getFactories() {
    return factories;
}

//getter for centreFactory
std::vector<TilePtr> CentreBoard::getCentralFactory(int num) {
    std::vector<TilePtr> toreturn;

    if(num == ONE)
        toreturn = centralFactory1;
    else if(num == TWO)
        toreturn = centralFactory2;
    return toreturn;
}

//getter for tileBag
TileBag* CentreBoard::getTileBag() {
    return tileBag;
}

//prints the centralfactorys
void CentreBoard::printCentralFactory() {
    for (int i = 0; i < centerfactorynum; i++) {
        std::cout << i << ":";
        int countF = 0;
        int countR = 0;
        int countY = 0;
        int countB = 0;
        int countL = 0;
        int countU = 0;

        for (unsigned int x = 0; x < getCentralFactory(i + ONE).size(); x++) {
            if (getCentralFactory(i + 1).at(x)->getColour() == FIRST_PLAYER) {
                countF++;
            } else if (getCentralFactory(i + ONE).at(x)->getColour() == RED) {
                countR++;
            } else if (getCentralFactory(i + ONE).at(x)->getColour() == YELLOW) {
                countY++;
            } else if (getCentralFactory(i + ONE).at(x)->getColour() == DARK_BLUE) {
                countB++;
            } else if (getCentralFactory(i + ONE).at(x)->getColour() == LIGHT_BLUE) {
                countL++;
            } else if (getCentralFactory(i + ONE).at(x)->getColour() == BLACK) {
                countU++;
            }
        }

        for (int i = 0; i != countF; ++i) {
            std::cout << " " << COLOR_FIRST_PLAYER;
        }
        orderprintinghelper(countR,countY,countB,countL,countU);
        std::cout << std::endl;
    }
}

//prints out factories by calling printFactoriesByOrder(int row);
void CentreBoard::printFactories(){
    int startfactory = 1;

    if(getcenterfactorynum() == ONE)
        startfactory = 1;
    else if(getcenterfactorynum() == TWO)
        startfactory = 2;

    for(int i = 0; i != maxfactories; ++i) {
        std::cout << startfactory << ":";
        printFactoriesByOrder(i);
        startfactory++;
    }
}

//check if centralfactory is empty
bool CentreBoard::checkEmpty() {
    int count = 0;
    bool empty = false;

    if(centralFactory1.empty() && centralFactory2.empty()) {
        for(int i = 0; i != maxfactories; ++i) {
            for(int j = 0; j != MAX_TILES_PER_FACTORY; ++j) {
                if (factories[i][j] == nullptr) {
                    count++;
                }
            }
        }
    }

    if(count == 20) {
        empty = true;
    } else {
        empty = false;
    }

    return empty;
}

//shuffle the tilebag at the start of a new game
void CentreBoard::shuffleTileBag(int seed) {
    bool filled = false;
    int min = 1;
    int max = 5;

    while(!filled) {
        if(seed == 0) {
            std::random_device engine;
            std::uniform_int_distribution<int> uniform_dist(min, max);

            int value = -1;
            for (int i = 0; i != 100; ++i) {
                value = uniform_dist(engine);
                filled = fillTileBag(value);
            }
        } else {
            std::default_random_engine engine(seed);
            std::uniform_int_distribution<int> uniform_dist(min, max);

            int value = -1;
            for (int i = 0; i != 100; ++i) {
                value = uniform_dist(engine);
                filled = fillTileBag(value);
            }
        }
    }
}

//fills the tilebag at the start of a new game
bool CentreBoard::fillTileBag(int value) {
    bool completed = false;

    if(value == 1 && dbToFill != 0) {
        tileBag->addTile(new Tile(DARK_BLUE));
        dbToFill--;
    } else if(value == 2 && yToFill != 0) {
        tileBag->addTile(new Tile(YELLOW));
        yToFill--;        
    } else if(value == 3 && rToFill != 0) {
        tileBag->addTile(new Tile(RED));
        rToFill--;        
    } else if(value == 4 && bToFill != 0) {
        tileBag->addTile(new Tile(BLACK));
        bToFill--;        
    } else if(value == 5 && lbToFill != 0) {
        tileBag->addTile(new Tile(LIGHT_BLUE));
        lbToFill--;        
    }
    if(dbToFill == 0 && yToFill == 0 && rToFill == 0 && bToFill == 0 && lbToFill == 0) {
        completed = true;
    }
    return completed;
}

//populate the centralfactory and factories with tiles at the start of a new round
void CentreBoard::populateFactories() {
    for(int i=0; i != maxfactories; ++i) {
        for(int j=0; j != MAX_TILES_PER_FACTORY; ++j) {
            if(factories[i][j] == nullptr) {
                if( tileBag->size() == 0) {
                    factories[i][j] = boxLid.back();
                    boxLid.pop_back();
                } else {
                    factories[i][j] = tileBag->getHead();
                }  
                          
            }
        }
    }

    if(centralFactory1.empty()) {
        centralFactory1.push_back(new Tile(FIRST_PLAYER));
    }    
}

//removing Tiles from centralfactory
void CentreBoard::removeTilesFromCentralFactory(char colour, int factorynum) {
    if (factorynum == ONE) {
        for (auto &tilePtr : centralFactory1) {
            if (tilePtr->getColour() == colour) {
                tilePtr = nullptr;
            }
        }
        centralFactory1.erase(std::remove(centralFactory1.begin(), centralFactory1.end(), nullptr),centralFactory1.end());
    } else if (factorynum == TWO) {
        for (auto &tilePtr : centralFactory2) {
            if (tilePtr->getColour() == colour) {
                tilePtr = nullptr;
            }
        }
        centralFactory2.erase(std::remove(centralFactory2.begin(), centralFactory2.end(), nullptr),centralFactory2.end());
    }
}

// check if all the factories are empty
bool CentreBoard::isFactoryEmpty(int factory) {
    bool empty = false;

    if(centerfactorynum == ONE) {
        if (factory == 0) {
            if (centralFactory1.empty() || centralFactory1.back()->getColour() == FIRST_PLAYER) {
                empty = true;
            } else {
                empty = false;
            }
        } else {
            if (factories[factory - ONE][0] == nullptr) {
                empty = true;
            } else {
                empty = false;
            }
        }
    }else{
        if(factory == 0){
            if (centralFactory1.empty() || centralFactory1.back()->getColour() == FIRST_PLAYER)
                empty = true;
            else
                empty = false;
        }else if(factory == ONE){
            if (centralFactory2.empty())
                empty = true;
            else
                empty = false;
        }else{
            if (factories[factory - TWO][0] == nullptr)
                empty = true;
            else
                empty = false;
        }
    }
    return empty;
}

//prints the character of the boxLid to a file
void CentreBoard::printBoxLid(std::ostream &ostream) {
    ostream << "LID=";

    for (Tile *i : boxLid) {
        char colour = i->getColour();
        ostream << colour;
    }
}

//prints out the 5 factoriest in color order
void CentreBoard::printFactoriesByOrder(int row) {
    int countR = 0;
    int countY = 0;
    int countB = 0;
    int countL = 0;
    int countU = 0;

    for (int i = 0; i != MAX_TILES_PER_FACTORY; ++i) {
        if(factories[row][i] != nullptr) {
            if (factories[row][i]->getColour() == RED) {
                countR++;
            } else if(factories[row][i]->getColour() == YELLOW) {
                countY++;
            }  else if(factories[row][i]->getColour() == DARK_BLUE) {
                countB++;
            } else if(factories[row][i]->getColour() == LIGHT_BLUE) {
                countL++;
            } else if(factories[row][i]->getColour() == BLACK) {
                countU++;
            }
        }
    }
    orderprintinghelper(countR,countY,countB,countL,countU);
    std::cout << std::endl;
}

//add tiles to the boxLid
void CentreBoard::addTileToBoxlid(TilePtr tile) {
    boxLid.push_back(tile);
}

//asking input for amount of centre factory
void CentreBoard::inputcentralfactory(){
    int input;
    std::cout << "\nStarting a New Game..." << std::endl;

    do{
        std::cout << "Enter the amount of Centre Factory you want" << std::endl;
        std::cout << "> ";
        std::cin >> input;
        if((input < 0 || input > TWO || !std::cin.good()) && !std::cin.eof()){
            std::cout << "\nInvalid Input. Try again." <<  std::endl;
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
    }while (input != ONE && input != TWO && !std::cin.eof());
    this->centerfactorynum = input;
}

//create the centre factory needed for the new game
void CentreBoard::createcentrefactory() {
    if(!centralFactory1.empty()) {
        centralFactory1.clear();
    }
    centralFactory1.push_back(new Tile(FIRST_PLAYER));

    if(centerfactorynum == TWO){
        if(!centralFactory2.empty()) {
            centralFactory2.clear();
        }
    }
}

//getter for centrefactorynum
int CentreBoard::getcenterfactorynum(){
    return centerfactorynum;
}

//adding the TilePtr to the centrefactory used
void CentreBoard::addcenterFactory(int index,TilePtr t){
    if(index == ONE)
        centralFactory1.push_back(t);
    else if(index == TWO)
        centralFactory2.push_back(t);
}

//create the amount of factories needed for the new game
void CentreBoard::createfactories(){
    factories = new TilePtr*[maxfactories];

    for(int i = 0; i != maxfactories; ++i) {
        factories[i]= new TilePtr[MAX_TILES_PER_FACTORY];
        for(int j=0; j != MAX_TILES_PER_FACTORY; ++j) {
            factories[i][j] = nullptr;
        }
    }
}

//setter for maxfactories
void CentreBoard::setmaxfactories(int num){
    this->maxfactories = num;
}

//setter for centrefactorynum
void CentreBoard::setcenterfactorynum(int num){
    this->centerfactorynum = num;
}

//used in loading, adding the character to the centrefactory
void CentreBoard::loadcenterFactory(char c, int index){
    if(index == ONE)
        centralFactory1.push_back(new Tile(c));
    else if(index == TWO)
        centralFactory2.push_back(new Tile(c));
}

//getter for max factories
int CentreBoard::getmaxfactories(){
    return maxfactories;
}

//saves all the factories infomation to the ostream
void CentreBoard::saveFactories(std::ostream& ostream){
    for(int i = 0; i < maxfactories;i++){
        ostream << "FACTORY_" << i << "=";
        for(int x = 0; x < MAX_TILES_PER_FACTORY; x++){
            if(factories[i][x] != nullptr)
                ostream << factories[i][x]->getColour();
        }
        ostream << std::endl;
    }
}

//saves all the centrefactory infomation to the ostream
void CentreBoard::saveCenterFactories(std::ostream& ostream){
    for(int i = 0; i < centerfactorynum; i++){
        ostream << "FACTORY_CENTER" << i + ONE << "=";
        for(unsigned int x = 0; x < getCentralFactory(i + ONE).size(); x++)
            ostream << getCentralFactory(i + ONE).at(x)->getColour();
        ostream << std::endl;
    }
}

//helper for order printing, reduce code duplication
void CentreBoard::orderprintinghelper(int countR,int countY,int countB,int countL,int countU){
    for(int i = 0; i != countR; ++i) {
        std::cout << " " << COLOR_RED; 
    }

    for(int i = 0; i != countY; ++i) {
        std::cout << " " << COLOR_YELLOW; 
    }

    for(int i = 0; i != countB; ++i) {
        std::cout << " " << COLOR_DARK_BLUE; 
    }

    for(int i = 0; i != countL; ++i) {
        std::cout << " " << COLOR_LIGHT_BLUE; 
    }

    for(int i = 0; i != countU; ++i) {
        std::cout << " " << COLOR_BLACK; 
    }
}