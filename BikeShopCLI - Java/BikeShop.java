import java.io.*;
import java.util.*;
import java.time.*;
public class BikeShop{
    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private Bike bike1;
    private Bike bike2;
    private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Rent> rents = new ArrayList<Rent>();
    public void populateLists(){
        Bike[] BikeLists = {
            new Bike("Mountain", 150),
            bike1 = new Bike("Mountain", 7),
            new Bike("Mountain", 35),
            new Bike("City", 24),
            new Bike("City", 4),
            new Bike("City", 19),
            new Bike("Road", 145),
            new Bike("Road", 13),
            new Bike("Road", 60),
            new Ebike("Electric", 150, "Full Power"),
            bike2 = new Ebike("Electric", 131, "Powered Assist"),
            new Ebike("Electric", 95, "Full Power"),
            new Ebike("Electric", 56, "Powered Assist")
        };
        Customer[] CustLists = {
            new Customer("Riyad"),
            new Customer("Corrina"),
            new Customer("Hope")
        };
        Rent[] RentLists = {
            new Rent("1", 2, bike1, 12, "2018-01-03"),
            new Rent("2", 3, bike2, 5, "2019-06-07")
        };
        for(int i = 0; i < BikeLists.length; i++)
            bikes.add(BikeLists[i]);
        for(int i = 0; i < CustLists.length; i++)
            customers.add(CustLists[i]);
        for(int i = 0; i <RentLists.length; i++)
            rents.add(RentLists[i]);
        bike1.setAvailability(false);
        bike2.setAvailability(false);
    }
    public void run(){
        boolean flag = true;
        while(flag){
            System.out.println("Welcome to Bike Now Rental");
            System.out.println("Choose from the following options:\n" +
                             "1. Display all Bikes\n" +
                             "2. Add customers\n" +
                             "3. List customers\n" +
                             "4. Search by customer ID\n" +
                             "5. Add Rental\n" +
                             "6. Display all rentals\n" +
                             "7. Return bike\n"+
                             "8. Search Rentals by date\n" +
                             "9. Save rentals to file\n" +
                             "10.Read rental from file\n" +
                             "11.Exit");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                displaybikes();
                break;
                case 2:
                addCustomer();
                break;
                case 3:
                displaycustomers();
                break;
                case 4:
                Customer c = searchbycustID();
                int id = c.getcopyID();
                String name = c.getname();
                System.out.println("Customer ID: " + id + ", name: " + name);
                break;
                case 5:
                addRent();
                break;
                case 6:
                displayRent();
                break;
                case 7:
                ReturnBikes();
                break;
                case 8:
                searchbydate();
                break;
                case 9:
                writerent();
                break;
                case 10:
                ReadRent();
                break;
                case 11:
                System.out.println("Thank you for using Bike Now Rental for all your bike needs");
                System.exit(0);
                break;
                default:
                System.out.println("Not a Vaild option!!");
            }
        }
    }
    public void displaybikes(){
        for(Bike b: bikes){
            System.out.println(b);
        }
    }
    public void addCustomer(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String name = input.nextLine();
        customers.add(new Customer(name));
        System.out.println("You are now a customer of Bike Now Rental");
    }
    public void displaycustomers(){
        for(Customer c: customers){
            System.out.println(c);
        }
    }
    public Customer searchbycustID(){
        Scanner input = new Scanner(System.in);
        System.out.println("Customer ID? ");
        int custid = input.nextInt();
        for(Customer c: customers){
            if(c.getcopyID() == custid)
                return c;
        }
        return null;
    }
    public void addRent(){
        Scanner input = new Scanner(System.in);
        Rent rent = null;
        displaycustomers();
        System.out.println("Customer ID? ");
        int custid = input.nextInt();
        Bike bikeforrent = searchavailablebikes();
        if(bikeforrent != null){
            rent = new Rent(custid, bikeforrent);
            rent.Setdate();
            rent.RentDuration();
            rents.add(rent);
            bikeforrent.setAvailability(false);
            System.out.println("your rent has been saved");
        }
        else{
            System.out.println("No bike of this type are available");
        }
    }
    public Bike searchavailablebikes(){
        Bike bchosen = null;
        Scanner typechoice = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        System.out.println("What type time do you want to Rent?");
        System.out.println("1. Normal bike");
        System.out.println("2. Ebike");
        int choice = typechoice.nextInt();
        switch(choice){
            case 1:
            System.out.println("Bike type? ");
            String biketype = input.nextLine();
            for(Bike b:bikes){
                if(b.getType().equalsIgnoreCase(biketype) && b.getAvailability()){
                    bchosen = b;
                    break;
                }
            }
            break;
            case 2:
            System.out.println("Motor type? ");
            String motortype = input.nextLine();
            for(Bike b: bikes){
                if(b instanceof Ebike){
                    Ebike eb = (Ebike)b;
                    if(eb.getMotor().equalsIgnoreCase(motortype) && b.getAvailability()){
                        bchosen = b;
                        break;
                    }
                }
            }
        }
        return bchosen;
    }
    public void displayRent(){
        for(Rent r: rents){
            System.out.println(r);
        }
    }
    public void ReturnBikes(){
        displayoutbikes();
        Scanner input = new Scanner(System.in);
        System.out.println("Bike ID to return? ");
        int bikeID = input.nextInt();
        for(int i=0; i < rents.size(); i++){
            Rent r = rents.get(i);
            Bike b = r.Getbike();
            if(b.GetCopy() == bikeID){
                b.setAvailability(true);
                rents.remove(r);
                System.out.println("Your Bike has been successfully returned");
            }
        }
    }
    public void displayoutbikes(){
        for(Rent r: rents){
            Bike b = r.Getbike();
            if(!b.getAvailability())
                System.out.println(b);
        }
    }
    public void searchbydate(){
        LocalDate currdate = LocalDate.now();
        System.out.println("Today is " + currdate);
        System.out.println("Overdue rents: ");
        for(Rent r: rents){
            LocalDate newdate = r.Getdate();
            if(currdate.isAfter(newdate) && !r.Getbike().getAvailability())
                System.out.println(r);
        }
    }
    public void writerent(){
        fos = null;
        oos = null;
        try{
            fos = new FileOutputStream("Rents.dat");
            oos = new ObjectOutputStream(fos);
            for(Rent r: rents){
                oos.writeObject(r);
            }
            System.out.println("Rent data have been saved into 'Rents.dat'");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            try{
                fos.close();
                oos.close();
            }
            catch(Exception ee){}
        }
    }
    public void ReadRent(){
        rents.clear();
        fis = null;
        ois = null;
        try{
            fis = new FileInputStream("rents.dat");
            ois = new ObjectInputStream(fis);
            while(true){
                Object obj = ois.readObject();
                Rent r = (Rent)obj;
                rents.add(r);
            }

        }
        catch(EOFException eof){}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("data from 'rents.dat' added to rent list");
    }
}