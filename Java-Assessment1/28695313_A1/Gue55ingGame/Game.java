import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.omg.CORBA.Request;

/**
 * This is a samll game called Gue55ing game. Player and computer take turns to guess a hidden number.
 * Both of them will have 3 time to guess the hidden number in each round. The whole game contains 4 round.
 * Player is human player, computer is computer player.
 * @author  Junyan Wang
 * @student ID  28695313
 * @version 18/04/2018
 */

public class Game 
{
    // field: plyaer is the object of human player, computer is the object of computer player, hidden is the hidden number, min and max is the range.
    private Player player;
    private Player computer;
    private int hidden;
    private static int min;
    private static int max;
    
    public Game() 
    {
        player = new Player();
        computer = new Player();
        hidden = 0;
        min = 1;
        max = 100;
        player.setName("player");
    }
    
    /*
     * get the abandonIndicator each round.
     * compare abandonindicator with computerAbandonNumber. if eaqual, return false, computer abandon this round
     */
    public boolean computerAbandonGame(int abandonIndicator) 
    {
        boolean value = true;
        int computerAbandonNum;
        RandomNumber r = new RandomNumber();
        
        computerAbandonNum = r.generateNum(1, 20);  // 1 - 20 random number that computer generate befor each guess
        if (computerAbandonNum == abandonIndicator) 
            value = false;
        
        return value;
    }
    
    /*
     * call computerAbandonGame to generate a computerAbandonNumber. if equal, get the value of false;
     * result = 999 abandon; 666 got number; 0 didn't get number
     */
    public int computerGuessNumber(int min, int max, int abandonIndicator) 
    {
        int result = 0;
        int computerGuess = 0;
        RandomNumber r = new RandomNumber();
        computerAbandonGame(abandonIndicator);
        if (computerAbandonGame(abandonIndicator) == false) 
            result = 999;
        else 
        {
            computerGuess = r.generateNum(Game.min, Game.max);
            System.out.println("\n" + "The computer guess: "+ computerGuess);
            computer.setGuesses(computerGuess);
            if (computerGuess > hidden) 
            {
                Game.max = computerGuess - 1;
                System.out.println("The hidden number is smaller! Now the range is becoming to " + Game.min + " to " + Game.max + "\n");
            }
            else if (computerGuess < hidden) 
            {
                Game.min = computerGuess + 1;
                System.out.println("The hidden number is bigger! Now the range is becoming to " + Game.min + " to " + Game.max + "\n");
            }
            else if (computerGuess == hidden) 
                result = 666;
        }
        return result;
    }
    
     /*
     * 1. ask player to enter his/her username and stored in username
     * 2. if player enter an valid name, game moves on; otherwise, ask for him/her again.
     */
    public void enterName() 
    {
        System.out.println("Please enter your username: ");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine().trim();
        while (true) 
        {
            if (username.length() > 8 || 
                username.length() < 1) 
            {
                System.out.println("Please enter an valid name.");
                enterName();
            } 
            else 
            { 
            	player.setName(username);
            	System.out.println("\n" + "*******GAME START!*******");
            }
            break;
        }
    }
    
    // generate an random number between 0 and 1. if the number is bigger than 0.5, return 1, player first. Otherwise, computer first.
    public int first() 
    {
        int firturn = 0;
        firturn = Math.random() > 0.5 ? 1 : 0;
        if (firturn == 1) 
            System.out.println("\n" + "You will take the first turn." + "\n");
        else 
            System.out.println("\n" + "Computer will take the first turn." + "\n");
        return firturn;
    }
    
    /*
     * This is the main method. The method will have 4 round. Each round has maximum 3 guess times.
     */
    public void gue55ingGame() 
    {
        RandomNumber r = new RandomNumber();
        int abandonIndicator = 0;
        printWelcomeMessage();
        enterName();
        
        for (int round = 1; round <= 4; round++) 
        {
            System.out.println("****************************");
            System.out.println("------Round " + round + "------");
            hidden = r.generateNum(1, 100);                                // 1 - 100
            abandonIndicator = r.generateNum(1, 20);                       // 1 - 20
            
            switch (first()) 
            {
            case 1:                 //player first
                for (int count = 1; count<= 3; count++) 
                {
                    System.out.println("-----------------------------------");
                    System.out.println("Guess time: " + count + " of 3 for both players");
                    int result = playerGuessNumber(Game.min, Game.max);
                    if (result == 666) 
                    {
                        System.out.println("Congratulations! You have got the hidden number: " + hidden + "\n");
                        player.setScore(player.getScore() + scoreCalculator(count + (count - 1), player.getGuesses()));
                        System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                        break;
                    }
                    else if (result == 999) 
                    {
                        System.out.println("This round has been abandoned by player." + "\n");
                        //player.setScore(player.getScore() + 0);
                        System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                        break;
                    }else       // computer guess next
                    {
                        int result2 = computerGuessNumber(Game.min, Game.max, abandonIndicator);
                        if (result2 == 666) 
                        {
                            System.out.println("The computer got the hidden number: " + hidden + "\n");
                            computer.setScore(computer.getScore() + scoreCalculator(count * 2, computer.getGuesses()));
                            System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            break;
                        }
                        else if (result2 == 999) 
                        {
                            System.out.println("This round has been abandoned by computer." + "\n");
                            //computer.setScore(computer.getScore() + 0);
                            System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            break;
                        }
                        else 
                        {
                            if (count == 3)    // no player guess right
                            {   
                                System.out.println("This round no player got the hidden number. The hidden number is: " + hidden);
                                player.setScore(player.getScore() + scoreCalculator(7, player.getGuesses()));
                                computer.setScore(computer.getScore()+ scoreCalculator(7, computer.getGuesses()));
                                System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            }
                        }
                    }
                }
                Game.min = 1;
                Game.max = 100;
                break;
                
            case 0:     //computer first
                for (int count2 = 1; count2 <= 3; count2++) 
                {
                    System.out.println("-----------------------------------");
                    System.out.println("Guess time: " + count2 + " of 3 for both players");
                    int result3 = computerGuessNumber(Game.min, Game.max, abandonIndicator);
                    if (result3 == 666) 
                    {
                        System.out.println("The computer got the hidden number: " + hidden + "\n");
                        computer.setScore(computer.getScore() + scoreCalculator(count2 + (count2 - 1), computer.getGuesses()));
                        System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                        break;
                    }
                    else if (result3 == 999) 
                    {
                        System.out.println("This round has been abandoned by computer." + "\n");
                        //computer.setScore(computer.getScore() + 0);
                        System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                        break;
                    }
                    else    //player guess next
                    {
                        int result4 = playerGuessNumber(Game.min, Game.max);
                        if (result4 == 666) 
                        {
                            System.out.println("Congratulations! You have got the hidden number: " + hidden + "\n");
                            player.setScore(player.getScore() + scoreCalculator(count2 * 2, player.getGuesses()));
                            System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            break;
                        }
                        else if (result4 == 999) 
                        {
                            System.out.println("This round has been abandoned by player." + "\n");
                            //player.setScore(player.getScore() + 0);
                            System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            break;
                        }
                        else 
                        {
                            if (count2 == 3)        //no one guess right
                            {   
                                System.out.println("This round no player got the hidden number. The hidden number is: " + hidden);
                                player.setScore(player.getScore() + scoreCalculator(7, player.getGuesses()));
                                computer.setScore(computer.getScore()+ scoreCalculator(7, computer.getGuesses()));
                                System.out.println("Player score: " + player.getScore() + "  vs   Computer Score: " + computer.getScore() + "\n");
                            }
                        }
                    }
                }
                Game.min = 1;
                Game.max = 100;
                break;
            } 
         }
     }
    
    /*
     *  ask player to enter a guess number, if it is not an numeric number, ask player again until player enter a valid number.
     *  return that number as player's guessNumber.
     */
    public int numericCheck() {
        int guessNum = 0;
        while (true) {
            try 
            {
                System.out.println("Please enter your guess number: "+"\n");
                Scanner sc = new Scanner(System.in);
                guessNum = sc.nextInt();
                break;
            } 
            catch (Exception e) 
            {
                System.out.println("This is not an numeric number! please enter an numeric number.");
                continue;
            }
        }
        return guessNum;
    }
    
    // print the welcome message for this game
    public void printWelcomeMessage() 
    {
        System.out.println("|------------Gue55ing Game------------|"+"\n");
        System.out.println("      Welcome to the Gue55ing Game! ");
        System.out.println(" -------------------------------------"+"\n");
    }
    
    public void playGame() 
    {
        player.setScore(0);
        computer.setScore(0);
        gue55ingGame();
		whoWin();
    }
    
    /*
     * Get min and max number as range. Use result as an indicator, if resutl = 0, player didn't get the number
     * if result = 666, player got the number
     * if result = 999, player abandon this round.
     */
    public int playerGuessNumber(int min, int max) 
    {
        int result = 0;
        int guessNum = numericCheck();
        
        if (guessNum <= Game.max && 
            guessNum >= Game.min) 
        {
            if (guessNum > hidden) 
            {
                Game.max = guessNum - 1;
                player.setGuesses(guessNum);
                System.out.println("The hidden number is smaller! Now the range is becoming to " + Game.min + " to " + Game.max + "\n");
            }
            else if (guessNum < hidden) 
            {
                Game.min = guessNum + 1;
                player.setGuesses(guessNum);
                System.out.println("The hidden number is bigger! Now the range is becoming to " + Game.min + " to " + Game.max + "\n");
            }
            else 
            {
                player.setGuesses(guessNum);
                result = 666;
            }
        }
        else if ((guessNum < Game.min || guessNum > Game.max) && 
                 (guessNum >= 1 && guessNum <= 100)) 
        {
            System.out.println("Warning: Please guess within the correct range! Sorry, you have no extra chance for this guess." + "\n");
            player.setGuesses(guessNum);
        }
        else if (guessNum == 999) 
            result = 999;
        else 
        {
            System.out.println("Warning: The reasonable range is 1 to 100. Please enter an number again:" + "\n");
            playerGuessNumber(Game.min, Game.max);
        }
        return result;
    }
    
    /*
     * receive count to calculator score. Receive guessNumber to calculator proximity-of-last-guess.
     * if count is more than 6, calculator the score depends on proximity.
     */
    public int scoreCalculator(int count, int guessNumber) 
    {
        int score = 0;
        int proximity = 0;
        proximity = Math.abs(guessNumber - hidden);
        
        if (count == 1) 
            score = 20;
        else if (count == 2) 
            score = 15;
        else if (count == 3) 
            score = 11;
        else if (count == 4) 
            score = 8;
        else if (count == 5) 
            score = 6;
        else if (count == 6) 
            score = 5;
        else 
        {
            if (proximity >= 10) 
                score = 0;
            else 
                score = 10 - proximity;
        }
        return score;
    }
    
    // print who win or draw
    public void whoWin() 
    {
        player.updateInfo();
        computer.updateInfo();
        int playerSum = player.getScore();
        int computerSum = computer.getScore();
        if (playerSum > computerSum) 
            System.out.println("------------ Player Win! ------------");
        else if (playerSum < computerSum) 
            System.out.println("------------ Computer Win! ------------");
        else 
            System.out.println("------------ This is a draw! ------------");
    }
        
}
    
    