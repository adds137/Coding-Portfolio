import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        // Setting up System objects for start section
        Scanner sc = new Scanner(System.in);
        String location = "start";
        // Game Starts
        System.out.println("Welcome to My Game");
        System.out.println("Type start to begin");
        String start = sc.next();
       // while (start != "start") {
          //  System.out.println("Type start to begin");
           // start = userinput.next();
      //  }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("**Aim: To kill the final boss, The Dragon that lives in the castle**");
            System.out.println("**There are 5 items to collect**");
            System.out.println("**Some of the items are needed to finshes the game so try to collect them all**");
            System.out.println("**Good luck**");
            System.out.println("-------------------------------------------------------------------------------");
            while (true) {
                location = playerMovement(location);
            }
        }
        //playermovement methods
        static String playerMovement(String playerLoc) {
        // Setting up System objects for rest of game
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        // Setting up Game variables
        String[] inventory = new String[5];
        int userhealth = 100;
        int attackdamage = 20;
        char userinput;
        // Setting up enemy stats
        enemy e1 = new enemy("Bear", 75, 10);
        enemy e2 = new enemy("Troll", 100, 15);
        // Crossroad tile code
        if (playerLoc == "start")	{
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("You arrive at a crossroads, What would you like to do?");
            System.out.println("1. Head West towards a Town");
            System.out.println("2. Head North towards the Plains");
            System.out.println("3. Head East towards the Forest");
            System.out.println("i. Show inventory");
            System.out.println("-------------------------------------------------------------------------------");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "town";
            }
            else if (userinput == '2') {
                playerLoc = "plains";
            }
            else if (userinput == '3') {
                playerLoc = "bear";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
         }
         // Town tile Code
        if (playerLoc == "town") {
            System.out.println("You arrive at a Town");
            System.out.println("You can uses the inn to rest and recover your HP");
            System.out.println("What would you like to do?");
            System.out.println("1. Rest at the inn");
            System.out.println("2. Head East towards the Crossroads");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("You Rest at the inn");
                userhealth = 100;
                System.out.println("Your health is now: " + userhealth);
                System.out.println("-------------------------------------------------------------------------------");
                playerLoc = "town";
            }
            else if (userinput == '2') {
                playerLoc = "start";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
        // Battle #1 code
        if (playerLoc == "bear") {
            boolean Bear_alive = true;
            if (Bear_alive) {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("A killer " + e1.getname() + " wants to fight");
                int enemyhealth = e1.gethealth();
                while (enemyhealth > 0) {
                    System.out.println("Your Health is " + userhealth);
                    System.out.println("Your enemy Health is " + enemyhealth);
                    System.out.println("What would you like to do?");
                    System.out.println("1. Attack");
                    System.out.println("2. Run!");
                    System.out.println("-------------------------------------------------------------------------------");
                    userinput = sc.next().charAt(0);
                    if (userinput == '1') {
                        System.out.println("-------------------------------------------------------------------------------");
                        int damagedealt = rand.nextInt(attackdamage); 
                        int damagetaken = rand.nextInt(e1.getdamage());
                        enemyhealth = enemyhealth - damagedealt;
                        userhealth = userhealth - damagetaken;
                        System.out.println("You Strike the " + e1.getname() + " for " + damagedealt + " damage");
                        System.out.println("You recieve " + damagetaken + " damage from the " + e1.getname());
                        if (userhealth < 1) {
                            System.out.println("You have been defeated!!!");
                            break;
                        }
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                    else if (userinput == '2') {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println("You tried to run away from the " + e1.getname());
                        System.out.println("it didn't work");
                        System.out.println("You took 10 damage");
                        userhealth = userhealth - 10;
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                    else {
                        System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                }
                if (userhealth < 1) {
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println("You Lose!!");
                    System.out.println("The Program will now end, reboot to give it another try");
                    System.exit(0);
                }
                Bear_alive =false;
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("The " + e1.getname() + "has been defeated");
                System.out.println("You have " + userhealth + "HP left");
                Bear_alive =false;
                System.out.println("Continue on to the forest?");
                System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "forest";
            }
            else if (userinput == '2') {
                playerLoc = "start";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
            }
            else if (!Bear_alive) {
                System.out.println("You walk pass the remains of a bear");
                System.out.println("Continue on to the forest?");
                System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "forest";
            }
            else if (userinput == '2') {
                playerLoc = "start";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
            }
        }
        // forest tile Code
        if (playerLoc == "forest") {
            System.out.println("You arrive at the forest");
            System.out.println("Its dark and might have monster in it");
            System.out.println("What would you like to do?");
            System.out.println("1. Enter the forest");
            System.out.println("2. head back towards the Crossroads");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("You start wondering the forest");
                System.out.println("You find no monster to be seen");
                if (inventroy[0] = null) {
                System.out.println("But you do find a small wooden shield on the ground, and you take it");
                System.out.println("**You have added a shield to your inventory**");
                inventory[0] = "Shield";
                }
                else {
                    System.out.println("You find nothing of uses");
                }
                System.out.println("-------------------------------------------------------------------------------");
                playerLoc = "forest";
            }
            else if (userinput == '2') {
                playerLoc = "bear";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
        // plains tile Code
        if (playerLoc == "plains") {
            System.out.println("You arrive at the plains");
            System.out.println("It is beautiful to admire");
            System.out.println("To the East you see a Herd of horses out in the fields");
            System.out.println("To the North you see a River and just across it, a river town");
            System.out.println("What would you like to do?");
            System.out.println("1. Head East");
            System.out.println("2. Head North");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "horse";
            }
            else if (userinput == '2') {
                playerLoc = "troll";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
        // horses tile Code
        if (playerLoc == "horse") {
            System.out.println("You head towards the herd of horses");
            System.out.println("they are strolling threw the plains, eating grass");
            System.out.println("they are unware of your presense");
            System.out.println("you think to yourself, a horse to ride would make my joureny alot easier");
            System.out.println("What would you like to do?");
            System.out.println("1. Tame home");
            System.out.println("2. Head West");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                System.out.println("-------------------------------------------------------------------------------");
                if (inventroy[1] = "Apple") {
                System.out.println("You slowly walk up to one of the horse with the apple out infront of you");
                System.out.println("the horse looks curious as he slowly walks to the apple");
                System.out.println("when the horse is a foot apart he takes the apple straight out of your hand");
                System.out.println("As he eats the apple you start petting him");
                System.out.println("He likes it");
                System.out.println("You have made a best friend for live");
                System.out.println("**You have added a Horse to your inventory**");
                inventory[2] = "Horse";
                }
                else {
                    System.out.println("You want to tame a horse");
                    System.out.println("but you have nothing to tame it with");
                    System.out.println("**Try to find an item that will help tame a horse**");
                }
                System.out.println("-------------------------------------------------------------------------------");
                playerLoc = "horse";
            }
            else if (userinput == '2') {
                playerLoc = "plains";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
        // Battle #2 code
        if (playerLoc == "troll") {
            boolean troll_alive = true;
            if (troll_alive) {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("A killer " + e2.getname() + " wants to fight");
                int enemyhealth = e2.gethealth();
                while (enemyhealth > 0) {
                    System.out.println("Your Health is " + userhealth);
                    System.out.println("Your enemy Health is " + enemyhealth);
                    System.out.println("What would you like to do?");
                    System.out.println("1. Attack");
                    System.out.println("2. Run!");
                    System.out.println("-------------------------------------------------------------------------------");
                    userinput = sc.next().charAt(0);
                    if (userinput == '1') {
                        System.out.println("-------------------------------------------------------------------------------");
                        int damagedealt = rand.nextInt(attackdamage); 
                        int damagetaken = rand.nextInt(e2.getdamage());
                        enemyhealth = enemyhealth - damagedealt;
                        userhealth = userhealth - damagetaken;
                        System.out.println("You Strike the " + e2.getname() + " for " + damagedealt + " damage");
                        System.out.println("You recieve " + damagetaken + " damage from the " + e2.getname());
                        if (userhealth < 1) {
                            System.out.println("You have been defeated!!!");
                            break;
                        }
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                    else if (userinput == '2') {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println("You tried to run away from the " + e2.getname());
                        System.out.println("it didn't work");
                        System.out.println("You took 15 damage");
                        userhealth = userhealth - 15;
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                    else {
                        System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                        System.out.println("-------------------------------------------------------------------------------");
                    }
                }
                if (userhealth < 1) {
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println("You Lose!!");
                    System.out.println("The Program will now end, reboot to give it another try");
                    System.exit(0);
                }
                Bear_alive =false;
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("The " + e2.getname() + "has been defeated");
                System.out.println("You have " + userhealth + "HP left");
                Bear_alive =false;
                System.out.println("To the east is a river and to the west is a field of apple trees");
                System.out.println("What would you like to do");
                System.out.println("1. Head East");
            System.out.println("2. Head West");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "appletree";
            }
            else if (userinput == '2') {
                playerLoc = "river";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            else {
                System.out.println("I'm sorry, that's not a valid answer. Please try again.");
                System.out.println("-------------------------------------------------------------------------------");
            }
            }
            else if (!troll_alive) {
            System.out.println("You walk pass the remains of a trol");
            System.out.println("To the east is a river and to the west is a field of apple trees");
            System.out.println("What would you like to do");
            System.out.println("1. Head East");
            System.out.println("2. Head West");
            System.out.println("i. Show inventory");
            userinput = sc.next().charAt(0);
            if (userinput == '1') {
                playerLoc = "appletree";
            }
            else if (userinput == '2') {
                playerLoc = "river";
            }
            else if (userinput == 'i') {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Your Health: " + userhealth);
                System.out.println("Your Attack Damage: "+ attackdamage);
                System.out.println("Your Inventory has:");
                for (int i = 0; i < inventory.length; i++) {
                    System.out.println(inventory[i] + " ");
                }
                System.out.println("-------------------------------------------------------------------------------");
            }
            }
        }
            return playerLoc;
        }
}