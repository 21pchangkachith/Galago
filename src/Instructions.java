import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructions extends Display
{
    private boolean gameStarted;
    private String type;
    /**
     * Act - do whatever the Instructions wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Instructions(String instructionName) 
    {
        gameStarted=true;
        type=instructionName;
        setImage(instructionName+".PNG");
    }
    public void act() 
    {
        if(gameStarted && type.equals("gameInstructions"))
        {
            gameStarted=false;
            getWorld().removeObject(this);
        }
       
        // Add your action code here.
    }    
}
