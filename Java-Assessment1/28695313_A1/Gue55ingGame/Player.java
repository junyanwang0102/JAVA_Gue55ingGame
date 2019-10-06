public class Player
{
    private String name;
    private int score;
    private int guesses;
    
    // A default constructor to initail the state of the fileds.
    public Player() 
    {
        name = "computer";
        score = 0;
        guesses = 0;
    }
    
    // A constructor accepts the value of the player.
    public Player(String playername)
    {
       name = playername;
       score = 0;
       guesses = 0;
    }
    
    // Get the name of the player
    public String getName() 
    {
        return name;
    }
    
    // set the name of the player object
    public void setName(String nameSet) 
    {
        name = nameSet;
    }
    
    // Get the current score
    public int getScore() 
    {
        return score;
    }
    
    // set the score if you want
    public void setScore(int scoreSet)
    {
        score = scoreSet;
    }
    
    // Get the last guesses
    public int getGuesses() 
    {
        return guesses;
    }
    
    // set the last guess if you want
    public void setGuesses(int guessSet)
    {
        guesses = guessSet;
    }

    /*
     * show the information
     * Example: The player is andrew, the score is 100, and the last guess is 100.
     */
    public void updateInfo() 
    {
        System.out.println("The player is "+ name + ", the score is: "+score+
                           ", and the last guess is: "+guesses+"."+"\n");
    }
    
}