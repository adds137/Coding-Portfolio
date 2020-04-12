import java.time.*;
import java.io.*;
import java.util.*;
public class Rent implements Serializable{
    private int custID;
    static int NextRent = 1;
    private int rentID;
    private Bike bike;
    private LocalDate date;
    private int duration;
    private String intdate;
    private String intrentid;
    public Rent(String intrentid ,int custID, Bike bike, int duration, String intdate){
        this(custID, bike);
        this.duration = duration;
        this.intrentid = intrentid;
        this.date = LocalDate.parse(intdate);
    }
    public Rent(int custID, Bike bike){
        this.custID = custID;
        this.bike = bike;
        rentID = NextRent++;
    }
    public int GetCustID(){
        return custID;
    }
    public Bike Getbike(){
        return bike;
    }
    public int GetrentID(){
        return rentID;
    }
    public LocalDate Getdate(){
        return date;
    }
    public int Getduration(){
        return duration;
    }
    public double getCost(){
        return bike.getPricePerDay()* duration;
    }
    public void Setdate(){
        boolean done = false;
        while(!done){
            Scanner input = new Scanner(System.in);
            try{
                System.out.println("Enter a start date: (yyyy-mm-dd)");
                String setdate = input.nextLine();
                this.date = LocalDate.parse(setdate);
                done = true;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void Setduration(int duration)throws invalidDurationException{
        if(duration > 30)
           throw new invalidDurationException("Rent Duration cannot be more that 30 days");
        else
            this.duration = duration;
    }
    public void RentDuration(){
        boolean done = false;
        Scanner Rentinput = new Scanner(System.in);
        while(!done){
            try{
                System.out.println("Enter Duration of rent? (in days, must be less than 30)");
                int days = Rentinput.nextInt();
                Setduration(days);
                done = true;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public String toString(){
        return "Rent ID: " + rentID + ", Customer ID: " + custID + ", Date: " + date + ", Duration: " + duration + "\n" + bike + ", Amount Due: " + getCost();
    }
}