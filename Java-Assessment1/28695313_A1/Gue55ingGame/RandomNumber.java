

public class RandomNumber
{
	
	public RandomNumber() {
		// TODO Auto-generated constructor stub
	}

    /*
     * generate an random number between minNum and maxNum;
     */
    public int generateNum(int minNum, int maxNum) 
    {
	    	int gap = 0;
	    	int randomBetweenGap = 0;
	    	gap = maxNum - minNum;
	    	randomBetweenGap = (int) (Math.random() * gap) + minNum;   
	    	return randomBetweenGap;
    }
    
}
