import java.io.*;
import java.util.*;
import java.time.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class BikeShopGUI extends JFrame implements ActionListener{    
    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private Bike bike1;
    private Bike bike2;
    private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Rent> rents = new ArrayList<Rent>();
    JTabbedPane tabs = new JTabbedPane();
    JPanel pnlrents, pnlcustomer, pnlbikes;
    JLabel lblcustid, lblrentdur, lblrentdate, lblbikeid, lblbiketype, lblbikestyle, lblcustname, lblcustid2;
    JTextField txtcustid, txtrentdur, txtrentdate, txtbikeid, txtbikestyle, txtcustname, txtcustid2;
    JButton btnaddrent, btndisplayrent, btnoverduerent, btnreturnbike, btnsave, btnread, btnaddcust, btndisplaycust, btndisplaybike, btnsearchbycustid;
    JTextArea txtrentdisplay, txtcustdisplay, txtbikedisplay;
    JComboBox<String> biketype;
    String [] combolabel ={"Normal Bike", "Ebike"};
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
    public BikeShopGUI(){
        populateLists();
        JPanel pnlrents = new JPanel();
        JPanel pnlcustomer = new JPanel();
        JPanel pnlbikes = new JPanel();
        tabs.add("Rent", pnlrents);
        JLabel lblcustid = new JLabel("Customer ID: ");
        txtcustid = new JTextField(8);
        JLabel lblbiketype = new JLabel("Style of Bike to rent: ");
        biketype = new JComboBox<String>(combolabel);
        JLabel lblbikestyle = new JLabel("Bike/Motor Type: ");
        txtbikestyle = new JTextField(18);
        JLabel lblrentdate = new JLabel("Start date (yyyy-mm-dd): ");
        txtrentdate = new JTextField(8);
        JLabel lblrentdur = new JLabel("Duration of Rent(in days, must be less than 30): ");
        txtrentdur = new JTextField(8);
        btnaddrent = new JButton("Add Rent");
        txtrentdisplay = new JTextArea(7,25);
        JLabel lblbikeid = new JLabel("Bike ID to Return: ");
        txtbikeid = new JTextField(8);
        btnreturnbike = new JButton("Return Bike");
        btndisplayrent = new JButton("Display Rent");
        btnoverduerent = new JButton("Display Overdue Rent");
        btnsave = new JButton("Save Rents");
        btnread = new JButton("Read Rents");
        pnlrents.add(lblcustid); 
        pnlrents.add(txtcustid);
        pnlrents.add(lblbiketype);
        pnlrents.add(biketype);
        pnlrents.add(lblbikestyle);
        pnlrents.add(txtbikestyle);
        pnlrents.add(lblrentdate); 
        pnlrents.add(txtrentdate);
        pnlrents.add(lblrentdur); 
        pnlrents.add(txtrentdur);
        pnlrents.add(btnaddrent);
        pnlrents.add(txtrentdisplay);
        pnlrents.add(lblbikeid); 
        pnlrents.add(txtbikeid);
        pnlrents.add(btnreturnbike);
        pnlrents.add(btndisplayrent);
        pnlrents.add(btnoverduerent);
        pnlrents.add(btnsave); 
        pnlrents.add(btnread);
        //pnlrents.setLayout(new GridLayout(2,2,1,1));
        pnlrents.setBorder(new EmptyBorder(20, 20, 20, 20));
        txtrentdisplay.setBorder( new TitledBorder("Rents"));
        btnaddrent.addActionListener(this);
        btndisplayrent.addActionListener(this);
        btnoverduerent.addActionListener(this);
        btnread.addActionListener(this);
        btnreturnbike.addActionListener(this);
        btnsave.addActionListener(this);
        tabs.add("Customer", pnlcustomer);
        txtcustdisplay = new JTextArea(7,25);
        JLabel lblcustname = new JLabel("New Customer name: ");
        txtcustname = new JTextField(25);
        btnaddcust = new JButton("Add customer");
        btndisplaycust = new JButton("Display customers");
        pnlcustomer.add(txtcustdisplay);
        pnlcustomer.add(lblcustname);
        pnlcustomer.add(txtcustname);
        pnlcustomer.add(btnaddcust);
        pnlcustomer.add(btndisplaycust);
        //pnlcustomer.setLayout(new GridLayout(2,2,1,1));
        pnlcustomer.setBorder(new EmptyBorder(20,20,20,20));
        txtcustdisplay.setBorder(new TitledBorder("Customers"));
        btnaddcust.addActionListener(this);
        btndisplaycust.addActionListener(this);
        tabs.add("Bike", pnlbikes);
        txtbikedisplay = new JTextArea(7,25);
        JLabel lblcustid2 = new JLabel("Customer ID: ");
        txtcustid2 = new JTextField(8);
        btndisplaybike = new JButton("Display bikes");
        btnsearchbycustid = new JButton("Search by Customer ID: ");
        pnlbikes.add(txtbikedisplay);
        pnlbikes.add(lblcustid2);
        pnlbikes.add(txtcustid2);
        pnlbikes.add(btnsearchbycustid);
        pnlbikes.add(btndisplaybike);
        //pnlbikes.setLayout(new GridLayout(2,2,1,1));
        pnlbikes.setBorder(new EmptyBorder(20,20,20,20));
        txtbikedisplay.setBorder(new TitledBorder("Bikes"));
        btndisplaybike.addActionListener(this);
        btnsearchbycustid.addActionListener(this);
        add(tabs, BorderLayout.CENTER);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btndisplayrent){
            txtrentdisplay.setText(" ");
            displayRent();
        }
        if(e.getSource() == btnsave){
            writerent();
            txtrentdisplay.setText("Rent data have been saved into 'Rents.dat'");
        }
        if(e.getSource() == btnread){
            txtrentdisplay.setText("Reading from 'Rents.dat'.....");
            ReadRent();
        }
        if(e.getSource() == btnoverduerent){
            txtrentdisplay.setText(" ");
            searchbydate();
        }
        if(e.getSource() == btnreturnbike){
            ReturnBikes();
            txtrentdisplay.setText("Your Bike has been successfully returned");
        }
        if(e.getSource() == btnaddrent){
            addRent();
            txtrentdisplay.setText("your rent has been saved");
        }
        if(e.getSource() == btnaddcust){
            addCustomer();
            txtcustdisplay.setText("New customer info has been added");
        }
        if(e.getSource() == btndisplaycust){
            txtcustdisplay.setText("");
            displaycustomers();
        }
        if(e.getSource() == btndisplaybike){
            txtbikedisplay.setText(" ");
            displaybikes();
        }
        if(e.getSource() == btnsearchbycustid){
            txtbikedisplay.setText(" ");
            Customer c = searchbycustID();
            int id = c.getcopyID();
            String name = c.getname();
            txtbikedisplay.setText("Customer ID: " + id + ", name: " + name);
        }
    }
    public void displaybikes(){
        for(Bike b: bikes){
            txtbikedisplay.append(b.toString() + "\n");
        }
    }
    public void addCustomer(){
        String name = txtcustname.getText();
        customers.add(new Customer(name));
    }
    public void displaycustomers(){
        for(Customer c: customers){
            txtcustdisplay.append( c.toString() + "\n");
        }
    }
    public Customer searchbycustID(){
        int custid = Integer.parseInt(txtcustid2.getText());
        for(Customer c: customers){
            if(c.getcopyID() == custid)
                return c;
        }
        return null;
    }
    public void addRent(){
        Rent rent = null;
        int custid = Integer.parseInt(txtcustid.getText());
        Bike bikeforrent = searchavailablebikes();
        if(bikeforrent != null){
            rent = new Rent(custid, bikeforrent);
            rent.Setdate();
            rent.RentDuration();
            rents.add(rent);
            bikeforrent.setAvailability(false);
        }
        else{
            System.out.println("No bike of this type are available");
        }
    }
    public Bike searchavailablebikes(){
        Bike bchosen = null;
        String choice = (String)biketype.getSelectedItem();
        switch(choice){
            case "Normal Bike":
            String biketypes = txtbikestyle.getText();
            for(Bike b:bikes){
                if(b.getType().equalsIgnoreCase(biketypes) && b.getAvailability()){
                    bchosen = b;
                    break;
                }
            }
            break;
            case "Ebike":
            String motortype = txtbikestyle.getText();
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
            txtrentdisplay.append("\n" + r.toString() + "\n");
        }
    }
    public void ReturnBikes(){
        int bikeID = Integer.parseInt(txtbikeid.getText());
        for(int i=0; i < rents.size(); i++){
            Rent r = rents.get(i);
            Bike b = r.Getbike();
            if(b.GetCopy() == bikeID){
                b.setAvailability(true);
                rents.remove(r);
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
        for(Rent r: rents){
            LocalDate newdate = r.Getdate();
            if(currdate.isAfter(newdate) && !r.Getbike().getAvailability())
                txtrentdisplay.append("\n" + r.toString() + "\n");
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
    public static void main(String[] args){
        BikeShopGUI bs = new BikeShopGUI();
        bs.setSize(780,500);
        bs.setLocationRelativeTo(null);
        bs.setVisible(true);
        bs.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bs.setTitle("Bike Now");
    }           
}       