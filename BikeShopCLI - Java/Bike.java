import java.io.*;
public class Bike implements Serializable{
    static int NextBikeID = 1;
    protected int copy;
    protected String type;
    protected int PricePerDay;
    protected Boolean Availability = true;
    public Bike(){};
    public Bike(String type, int PricePerDay){
        copy = NextBikeID++;
        this.type = type;
        this.PricePerDay = PricePerDay;
    }
    public String getType(){
        return type;
    }
    public int getPricePerDay(){
        return PricePerDay;
    }
    public Boolean getAvailability(){
        return Availability;
    }
    public int GetCopy(){
        return copy;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setPricePerDay(int PricePerDay){
        this.PricePerDay = PricePerDay;
    }
    public void setAvailability(Boolean Availability){
        this.Availability = Availability;
    }
    @Override
    public String toString(){
        return "Bike ID: " + copy + ", type: " + type + ", price per day: " + PricePerDay + ", availability: " + Availability;
    }
}