package client;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import validate.Validator;
import view.GameEngineCallbackImpl;

public class MyTestClient
{
   public static void main(String args[])
   {
      // instantiate the GameEngine so we can make calls
      final GameEngine gameEngine = new GameEngineImpl();

      // call method in Validator.jar to test *structural* correctness
      // just passing this does not mean it actually works .. you need to test yourself!
      // pass false if you want to show minimal logging (pass/fail) .. (i.e. ONLY once it passes)
      Validator.validate(false);
      
      // create 4 player for testing
      Player p1 = new SimplePlayer("1", "Player 1", 500);
      System.out.println("Player 1 successfully created");
      Player p2 = new SimplePlayer("2", "Player 2", 50);
      System.out.println("Player 2 successfully created");
      Player p3 = new SimplePlayer("3", "Player 3", 1000);
      System.out.println("Player 3 successfully created");
      Player p4 = new SimplePlayer("4", "Player 4", 10000000);
      System.out.println("Player 4 successfully created");

      // register the callback for notifications (all logging output is done by GameEngineCallbackImpl)
      // see provided skeleton class GameEngineCallbackImpl.java
      gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

      //add all 4 players to the gameEngineImpl
      gameEngine.addPlayer(p1);
      System.out.println("Player 1 successfully added to game");
      gameEngine.addPlayer(p2);
      System.out.println("Player 2 successfully added to game");
      gameEngine.addPlayer(p3);
      System.out.println("Player 3 successfully added to game");
      gameEngine.addPlayer(p4);
      System.out.println("Player 4 successfully added to game");

      // go thru each player one at a time to collect if the set bet worked and roll if its set
      Boolean test1 = gameEngine.placeBet(p1, 100);
      if(test1 == true) {
    	  gameEngine.rollPlayer(p1, 100, 1000, 100, 50, 500, 50);
      } else if(test1 == false) {
    	  System.out.println("roll doesnt run because place bet didnt work");
      }
      Boolean test2 = gameEngine.placeBet(p2, 100);
      if(test2 == true) {
    	  gameEngine.rollPlayer(p2, 100, 1000, 100, 50, 500, 50);
      } else if(test2 == false) {
    	  System.out.println("roll doesnt run because place bet didnt work");
      }
      Boolean test3 = gameEngine.placeBet(p3, 100);
      if(test3 == true) {
    	  gameEngine.rollPlayer(p3, 100, 1000, 100, 50, 500, 50);
      } else if(test3 == false) {
    	  System.out.println("roll doesnt run because place bet didnt work");
      }
      Boolean test4 = gameEngine.placeBet(p4, 100);
      if(test4 == true) {
    	  gameEngine.rollPlayer(p4, 100, 1000, 100, 50, 500, 50);
      } else if(test4 == false) {
    	  System.out.println("roll doesnt run because place bet didnt work");
      }

      // output the result of the test
      System.out.println("Player 1 results (Expected: true): " + test1);
      System.out.println("Player 2 results (Expected: false): " + test2);
      System.out.println("Player 3 results (Expected: true): " + test3);
      System.out.println("Player 4 results (Expected: true): " + test4);
   }
}
