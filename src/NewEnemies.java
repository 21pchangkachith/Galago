import greenfoot.*;// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.lang.Math.*;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NewEnemies extends Enemy
{

    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public NewEnemies()
    {
        super();
    }
    public void act() 
    {
        move();
        moveCount();
        explosionCount() ;
        attackShipCount() ;
        isHit() ;
        this.shot() ;
        shouldAttackShip();
        firstSecond() ;
        isCollided() ;
    }
    
    public void shot(){
        if( ( shouldHit(450)|| isDiving() ) && !hasBeenHit() )
        {
           int randomNumber2= Greenfoot.getRandomNumber(100);
           GalagaWorld world= (GalagaWorld) getWorld();
           Vector angle= calculateVector();
           this.getWorld().addObject(new Enemy_Bullet(angle, true), getX(), getY() + 1) ;
       }
    }
    public void hit(){
        
        isHit = true ;
            
        if(explosionCount == 5 && explode1 == true){
            setImage(explosion1);
            Greenfoot.playSound("blowUp.wav");
            explosionCount = 0 ;
            explode1 = false ;
            finishExplosion = true ;
        }
        if(explosionCount == 5 && finishExplosion == true){
            ((GalagaWorld) getWorld()).addToScore(700);
            ((GalagaWorld) getWorld()).subtractFromEnemies();
            getWorld().removeObject(this) ;
        }
   
        
    }
}
