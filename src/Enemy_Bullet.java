import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy_Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy_Bullet extends SmoothBullet
{
    boolean explosive;
    boolean fragment;
    int smallDelay=0;
    int changeTimes=0;
    Vector thisDir;
    boolean verfDest=false;
        public Enemy_Bullet(Vector speed, boolean e, boolean f){
        super(speed);
        thisDir=speed;
        explosive=false;
        fragment=true;
    }
    public Enemy_Bullet(Vector speed, boolean e){
        super(speed);
        thisDir=speed;
        explosive=true;
        fragment=false;
    }
    public Enemy_Bullet(Vector speed){
        super(speed);
        thisDir=speed;
        explosive=false;
        fragment=false;
    }
    public Enemy_Bullet()
    {
        this(new Vector(90, 1.0));
    }
    public void act() 
    {
       if(!verfDest)
       {
           smoothMoves();
           destroyIfOutOfBounds();
       }
       else getWorld().removeObject(this);
    }
    public void reflected()
    {
        verfDest=true;
    }
    public void destroyIfOutOfBounds()
    {
        if( !verfDest && (getY() >= 49||getX() <= 1||getX() >= 49||getY() <= 1))
            {
                if(explosive)
                {
                    explode();
                }
                verfDest=true;
            }
          
    }
    public void smoothMoves()
    {
        if(!verfDest)
        {
        move();
        if(fragment && (thisDir.getDirection()<70 || thisDir.getDirection()>110) )
        {
            if(thisDir.getDirection()>270||thisDir.getDirection()<90)
            {
                thisDir.setDirection(thisDir.getDirection()+10);
            }
            else thisDir.setDirection(thisDir.getDirection()-10);
            changeTimes++;
            
        }
        Ship ship = (Ship) getOneIntersectingObject(Ship.class);
        if(ship != null)
        {
             verfDest=true;
             ship.hit() ;
        }
    }
        
    }
    public void explode()
    {
       int curDir= thisDir.getDirection();
       if(curDir>90)
       {
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir-180, 1), false, true), getX()-1, getY()-1) ;
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir-150, 1), false, true), getX()-1, getY()-1) ;
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir-210, 1), false, true), getX()-1, getY()-1) ;
       }
       else
       {
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir+180, 1), false, true), getX()-1, getY()-1) ;
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir+150, 1), false, true), getX()-1, getY()-1) ;
           this.getWorld().addObject(new Enemy_Bullet(new Vector(curDir+210, 1), false, true), getX()-1, getY()-1) ;
       }
    }
    public Vector getVector()
    {
        return thisDir;
    }
}
