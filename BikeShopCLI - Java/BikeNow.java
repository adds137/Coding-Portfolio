public class BikeNow{
    public static void main(String[] args)throws invalidDurationException{
        BikeShop bs = new BikeShop();
        bs.populateLists();
        bs.run();
    }
}