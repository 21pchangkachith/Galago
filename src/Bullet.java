import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends SmoothBullet
{
    Vector thisDir;
    public Bullet()
    {
    }
    public Bullet(Vector dir)
    {
        super(dir);
        thisDir=dir;
    }
    public void act() 
    {
        this.move();
        if(oOB())
        {
            this.getWorld().removeObject(this);
        }
        else
        {
            checkHits();
        }
    }    
    public void move()
    {   if(thisDir==null)
            {
                this.setLocation(getX(), getY() - 1) ;
            }
            else
            {
                super.move();
            }
    }
    //out of bounds?
    public boolean oOB()
    {
        if(getY() <= 1 || getY() >= 49 || getX() <= 1 || getX() >= 49)
            {
                return true;
            }
        return false;
    }
    public void checkHits()
    {
        Enemy enemy = (Enemy) getOneIntersectingObject(Enemy.class);
       Boss boss = (Boss) getOneIntersectingObject(Boss.class);
        PowerUp powerup = (PowerUp) getOneIntersectingObject(PowerUp.class);
        if(enemy != null)
        {
            if(!enemy.hasBeenHit())
            {
                getWorld().removeObject(this);
                enemy.hit() ;
            }
        }
        
        else if(boss != null){
            getWorld().removeObject(this);
            boss.hit();
        }
        else if(powerup != null)
        {
            getWorld().removeObject(this);
            powerup.hit();
        }
    }
}
