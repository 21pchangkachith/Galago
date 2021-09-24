import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerUpSymbol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerUpSymbol extends Display
{
    GreenfootImage nothing= new GreenfootImage("blank.png");
    GreenfootImage fastFire = new GreenfootImage("ammopowerup.PNG") ;
    GreenfootImage bigLife = new GreenfootImage("shieldpowerup.png") ;
    GreenfootImage[] possiblePowerUps= new GreenfootImage[]{nothing, fastFire, bigLife};
    int currentPowerUp=0;
    boolean rolling= false;
    int numRolls=0;
    int curRolls=0;
    boolean finishedRolls=false;
    /**
     * Act - do whatever the PowerUpSymbol wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(rolling==true)
        {
            if(currentPowerUp>=possiblePowerUps.length-1)
            {
                currentPowerUp=1;
            }
            else
            {
                currentPowerUp++;
            }
            curRolls++;
            if(curRolls>=numRolls)
            {
                rolling=false;
                finishedRolls=true;
                curRolls=0;

            }
        }
        setImage(possiblePowerUps[currentPowerUp]);
        
    }    
    public int getType()
    {
        return currentPowerUp;
    }
    public void rollPowerUp()
    {
        if(!rolling)
        {
          numRolls= (int) Greenfoot.getRandomNumber(30);
          numRolls+=30;
          currentPowerUp++;
          finishedRolls=false;
          rolling=true;
        }
    }
    public void setTP(int newTP)
    {
        possiblePowerUps[currentPowerUp].setTransparency(newTP);
    }
    public int getTP()
    {
        return possiblePowerUps[currentPowerUp].getTransparency();
    }
    public void setType(int newType)
    {
        currentPowerUp=newType;
    }
    public void nullPowerUp()
    {
        currentPowerUp=0;
    }
}
