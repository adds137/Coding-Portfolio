#ifndef CENTREBOARD_H
#define CENTREBOARD_H

#include "TileBag.h"
#include <vector>
#include <iostream>
#include "Tile.h"

#define MAX_2_PLAYER_FACTORY 5
#define MAX_3_PLAYER_FACTORY 7
#define MAX_4_PLAYER_FACTORY 9
#define MAX_TILES_PER_FACTORY 4

class CentreBoard{
public:
    CentreBoard();
    ~CentreBoard();

    int getNumTilesInFactory(char colour, int row);
    TilePtr** getFactories();
    std::vector<TilePtr> getCentralFactory(int num);
    TileBag* getTileBag();   
    void printCentralFactory();
    void printFactories();
    bool checkEmpty();
    void shuffleTileBag(int seed);
    bool fillTileBag(int value);
    void populateFactories();
    void moveTilesToCentralFactory(char colour, int row);
    void removeTilesFromCentralFactory(char colour, int factorynum);
    void printBoxLid(std::ostream& ostream);
    bool isFactoryEmpty(int factory);
    void printFactoriesByOrder(int row);
    void addTileToBoxlid(TilePtr tile);
    void addcenterFactory(int index,TilePtr t);
    void loadcenterFactory(char c,int index);
    void inputcentralfactory();
    void createcentrefactory();
    int getcenterfactorynum();
    void createfactories();
    void setmaxfactories(int num);
    void setcenterfactorynum(int num);
    int getmaxfactories();
    void saveFactories(std::ostream& ostream);
    void saveCenterFactories(std::ostream& ostream);
    void orderprintinghelper(int countR,int countY,int countB,int countL,int countU);
private:
    TilePtr** factories;
    std::vector<TilePtr> centralFactory1;
    std::vector<TilePtr> centralFactory2;
    TileBag* tileBag;
    int dbToFill;
    int yToFill;
    int rToFill;
    int bToFill;
    int lbToFill;
    std::vector<TilePtr> boxLid;
    int centerfactorynum;
    int maxfactories;
};

#endif //CENTREBOARD_H
