import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ship extends Actor
{
    private GreenfootImage myLook;
    private static final String[] TYPES = new String[]{"basic", "tank", "power", "speed"};
    public static int myType=0;
    public static int classLevel=1;
    private int fireCount;
    private static int endurance=1;
    private int explosionCount;
    private int powerUpCount;
    private boolean explode1;
    private boolean explode2;
    private boolean explode3;
    private boolean explode4;
    private boolean finishExplosion;
    private boolean isHit;
    private boolean isDead;
    private boolean isBig;
    private int powerUpType;
    private GalagaWorld world;
    /**
     * Act - do whatever the Ship wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Ship()
    {
        myLook = new GreenfootImage("basic.png") ;
        fireCount = 0 ;
        explosionCount = 0 ;
        powerUpCount=0;
        explode1 = true ; //explosion1 should be ready to go
        explode2 = false ;
        explode3 = false ;
        explode4 = false ;
        finishExplosion = false ; //finish explosion?
        isHit = false ; //checking if hit
        isDead = false ; //is ship dead?
        isBig= false;
        powerUpType=0;
    }
    public static void upgradeClass()
    {
        classLevel++;
        if(myType==1) endurance++;
    }
    public static void setClass(int indexOfType)
    {
        myType= indexOfType;
        upgradeClass();
    }
    public void setType(String typeName)
    {
        int typeIndex=0;
        for(int i=0; i<TYPES.length; i++)
        {
            if(TYPES[i].equals(typeName)) typeIndex=i;
        }
        myType= typeIndex;
        // add in look changes with string concatenation
    }
    public void act() 
    {
        if(classLevel!=1) setImage(TYPES[myType]+Integer.toString(classLevel-1)+".png");
        checkKey();
        fireCount();
        world= (GalagaWorld) getWorld();
        if(world.isPoweredUp())
        {
            PowerUpSymbol symbol= world.getCurrentPowerUp();
            powerUpType = symbol.getType();
            if(powerUpType==0)
            {
                poweredUp();
            }
            if(powerUpType==1) fastFire();
            if(powerUpType==2) bigLife();
            symbol.setTP(255-(powerUpCount*255/100));
        }
        explosionCount();
    }   
    public void bigLife()
    {
        if(powerUpCount<100)
        {
            isBig=true;
            powerUpCount++;
        }
        else
        {
            powerUpCount=0;
            isBig=false;
            world.getCurrentPowerUp().setType(0);
            world.endPowerUp();
        }
    }
    public void poweredUp()
    {
        PowerUpSymbol symbol= world.getCurrentPowerUp();
        symbol.rollPowerUp();
    }
    public void fastFire()
    {
        if(powerUpCount<100)
        {
            powerUpCount++;
        }
        else
        {
            powerUpCount=0;
            world.getCurrentPowerUp().setType(0);
            world.endPowerUp();
        }
    }
    
   public void checkKey(){
        
        if(isDead == false){  
            if(Greenfoot.isKeyDown("left")||Greenfoot.isKeyDown("a")){
                this.setLocation(getX() - 1, getY()) ;
            }
            if(Greenfoot.isKeyDown("right")||Greenfoot.isKeyDown("d")){
                this.setLocation(getX() + 1, getY()) ;
            }
            if(Greenfoot.isKeyDown("space") && ( fireCount >= 30 || (fireCount>=15 && powerUpType==1) ) ){
                if(myType!=2) this.getWorld().addObject(new Bullet(), getX(), getY() - 1) ;
                else
                {
                    if(classLevel==2)
                    {
                         this.getWorld().addObject(new Bullet(), getX()-1, getY() - 1) ;
                          this.getWorld().addObject(new Bullet(), getX()+1, getY() - 1) ;
                    }
                    if(classLevel==3)
                    {
                         this.getWorld().addObject(new Bullet(), getX()-1, getY() - 1) ;
                         this.getWorld().addObject(new Bullet(), getX(), getY() - 1) ;
                          this.getWorld().addObject(new Bullet(), getX()+1, getY() - 1) ;
                    }
                    if(classLevel==4)
                    {
                        this.getWorld().addObject(new Bullet(), getX()-1, getY() - 1) ;
                        this.getWorld().addObject(new Bullet(), getX()+1, getY() - 1) ;
                         this.getWorld().addObject(new Bullet(), getX()-2, getY() - 1) ;
                         this.getWorld().addObject(new Bullet(), getX()+2, getY() - 1) ;
                    }
                }
                Greenfoot.playSound("lazer.wav");
                fireCount = 0; //restarts count
            }
            if(Greenfoot.isKeyDown("up")||Greenfoot.isKeyDown("w"))
            {
                if(getY()>=35) this.setLocation(getX(), getY()-1);
            }
            if(Greenfoot.isKeyDown("down")||Greenfoot.isKeyDown("s"))
            {
                 this.setLocation(getX(), getY()+1);
            }
            GalagaWorld w = (GalagaWorld) getWorld();
            w.setNewCoordinates(getX(), getY());
            int[] temp = w.getCoordinates();
        }
        
    }
    
    //Sets limitations for firing. Firing allowed at 30.
    //Called every time during act()
    public void fireCount(){
        if(fireCount < 30)
        {
            if(TYPES[myType].equals("speed"))
            {
                fireCount+=classLevel;
            }
            else fireCount++ ;
        }
    }
    
    public void hit(){
        if(!isBig)
        {
            if(endurance==1)
            {
               if(TYPES[myType].equals("tank"))
               {
                   endurance=classLevel;
                }
               ((GalagaWorld) getWorld()).subtractFromLives() ;
               ((GalagaWorld) getWorld()).loseLife();
               checkNewLife();
               getWorld().removeObject(this) ;
            }
            else
            {
                endurance--;
                ((GalagaWorld) getWorld()).forcePowerUp();
            }
        }
        else
        {
            Enemy_Bullet reflection = (Enemy_Bullet) getOneIntersectingObject(Enemy_Bullet.class);
            if(reflection!=null)
            {
                reflectShot(reflection);
                Greenfoot.playSound("lazer.wav");
            }
        }
    }
    public void reflectShot(Enemy_Bullet reflection)
    {
        Vector temp= reflection.getVector().copy();
        temp.setDirection(temp.getDirection()+180);
        this.getWorld().addObject(new Bullet(temp), getX(), getY()-1) ;
        reflection.reflected();
    }
    public void explosionSpeed()
    {
        while(explosionCount<5)
        {
            explosionCount++;
        }
    }
    public void explosionCount(){
        if(explosionCount < 5)
        {
            explosionCount++ ;
        }
        else
        {
            explosionCount = 5;
        }
    }
    
    public void isHit(){
        if(isHit == true){
            this.hit() ;
        }
    }
    
    public void checkNewLife(){
        if(((GalagaWorld) getWorld()).getAmountOfLives() >= 0){
            ((GalagaWorld) getWorld()).makeNewShip();
        }
    }
    
    
        
    
}
