import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PowerUp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerUp extends Actor
{
    GreenfootImage explosion1 = new GreenfootImage("explosion1.png") ;
    int moveCountLimit = 50 ; //changes to 100 after first move
    int moves=0;
    boolean isHit = false ; //is the enemy being hit?
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(getY()>=49) getWorld().removeObject(this) ;
        if(moves==5)
        {
            move();
            moves=0;
        }
        else moves++;
        isHit() ;
    }
    
    
    public void move()
    {
        this.setLocation(getX(), getY()+1) ;
        if(getY()<=0) getWorld().removeObject(this) ;
    }

    public void hit(){
        
        isHit = true ;
        GalagaWorld world= (GalagaWorld) getWorld();
        if(!world.isPoweredUp())world.givePowerUp();
        world.removeObject(this);
    }
    
    public void isHit(){
        if(isHit == true){
                    getWorld().removeObject(this) ;
        }
    }

  

     
}
