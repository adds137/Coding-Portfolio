public class Customer{
    static int NextcustID = 1;
    private int copyID;
    private String name;
    public Customer(){};
    public Customer(String name){
        copyID = NextcustID++;
        this.name = name;
    }
    public int getcopyID(){
        return copyID;
    }
    public String getname(){
        return name;
    }
    public void setname(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return "Customer ID: " + copyID + ", name: " + name;
    }
}