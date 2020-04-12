public class Ebike extends Bike{
    private String Motor;
    public Ebike(){};
    public Ebike(String type, int PricePerDay, String Motor){
        super(type, PricePerDay);
        this.Motor = Motor;
    }
    public String getMotor(){
        return Motor;
    }
    public void setMotor(String Motor){
        this.Motor = Motor;
    }
    @Override
    public String toString(){
        return super.toString() + ", motor type: " + Motor;
    }
}