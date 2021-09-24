import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy3 extends Enemy
{
    private boolean hitOnce;
    private int x;
    
    public Enemy3()
    {
        super() ;
        hitOnce = false ; //hasn't been hit yet
        x = 0 ; //changes to one if hit once
    }
    
    public void act() 
    {
        move();
        moveCount();
        explosionCount() ;
        isHit() ;
        hitOnce(x) ;
        attackShipCount() ;
        shot() ;
        shouldAttackShip();
        firstSecond() ;
        isCollided();
    }
    
    public void hit(){
        
        if(hitOnce == false){
        
        this.setImage("Enemy4.gif");
        Greenfoot.playSound("blowUp.wav");
        x = 1 ;
    }
        if(hitOnce == true){
            
            setHit();
           
            
          if(explosionCount == 5 && explode1 == true){
            setImage(explosion1);
            Greenfoot.playSound("blowUp.wav");
            explosionCount = 0 ;
            explode1 = false ;
            finishExplosion = true ;
        }
        if(explosionCount == 5 && finishExplosion == true){
            ((GalagaWorld) getWorld()).addToScore(400);
            ((GalagaWorld) getWorld()).subtractFromEnemies();
            getWorld().removeObject(this) ;
        }  
    }
}
    
    void hitOnce(int x){
        if(x == 0){
            hitOnce = false ;
        }
        if(x == 1){
            hitOnce = true ;
        }
    }
    
}
