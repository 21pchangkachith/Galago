import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOverButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOverButton extends Button
{
    /**
     * Act - do whatever the GameOverButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void performAction()
    {
        GalagaWorld world= (GalagaWorld) this.getWorld();
        world.resetWorld();
        world.removeObject(this);
    }
}
