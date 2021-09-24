import greenfoot.*;// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.lang.Math.*;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Enemy extends Actor
{
    GreenfootImage explosion1;
    int explosionCount;
    private int moveCount;
    private int moveCountLimit;
    private int timesMovedLeft;
    private  int timesMovedRight;
    private int attackShipCount ;
    private int attackShipMoveCount;
    private boolean move1 ;
    private boolean move2 ;
    private boolean firstMove;
    public boolean explode1;
    private int randomNumber;
    private int originalX;
    private int originalY;
    public boolean finishExplosion;
    public boolean isHit;
    private boolean firstSecond;
    private  boolean forcedShot;
    private  boolean firstAttackMove  ;
    private  boolean secondAttackMove  ;
    private  boolean thirdAttackMove  ;
    private  boolean fourthAttackMove  ;
    private  boolean fifthAttackMove  ;
    private  boolean sixthAttackMove ;
    private  boolean seventhAttackMove;
    private  boolean eigthAttackMove;
    private  boolean shouldAttackShip;
    public Enemy()
    {
        //Initializing...
     explosion1 = new GreenfootImage("explosion1.png") ;
     explosionCount = 0 ; //for explosion
     moveCount = 0 ; //for moving back and forth
     moveCountLimit = 50 ; //changes to 100 after first move
     timesMovedLeft = 0 ; //times enemy moved left
      timesMovedRight = 0 ; //times enemym moved right
     attackShipCount = 0 ; //for attacking ship
     attackShipMoveCount = 0 ; //for each movement during method
     move1 = true ; //move1 should be ready to move
     move2 = false ; //should move back?
     firstMove = true ; //is this the first time moving?
     explode1 = true ; //explos`s is the first second of the game
     randomNumber = 0 ; //changed when needed
     originalX = 0 ; //will always change first second
     originalY = 0 ; //will always change first second
     finishExplosion = false ; //finish explosion?
     isHit = false ; //is the enemy being hit?
     firstSecond = true ; //this is the first second of the game
     forcedShot = false ; //will force enenmy to shoot during dive
     firstAttackMove = true ;
     secondAttackMove = false ;
     thirdAttackMove = false ;
     fourthAttackMove = false ;
     fifthAttackMove = false ;
     sixthAttackMove = false ;
     seventhAttackMove = false ;
     eigthAttackMove = false ;
     shouldAttackShip = false ;
      //done
    }
    public boolean shouldHit(int chance)
    {
        randomNumber = Greenfoot.getRandomNumber(chance);
        return chance==1;
    }
    public void firstSecond(){ //initializes the original x and y coordinates
      if(firstSecond == true)
       {  
        originalX = this.getX() ;
        originalY = this.getY() ;
        firstSecond = false ;
       }
    }
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move();
        moveCount();
        explosionCount() ;
        attackShipCount() ;
        isHit() ;
        shot() ;
        shouldAttackShip();
        firstSecond() ;
        isCollided() ;
    }
    public boolean isDiving() {return forcedShot;}
    public boolean hasBeenHit() {return isHit;}
    public void setHit() {isHit=true;}
    public void move(){
        
                if(moveCount % 50 == 0 && move1 == true){
                    this.setLocation(getX() - 1, getY()) ;
                    timesMovedLeft++ ;
                    
                    if(moveCount == moveCountLimit){
                        this.setLocation(getX() - 1, getY()) ;
                        timesMovedLeft++ ;
                         originalY++;
                        move1 = false ; //so can use move2
                        move2 = true ;
                        firstMove = false ;
                        moveCount = 0 ;
                    }
                }
            
            if(moveCount % 50 == 0 && move2 == true){
                this.setLocation(getX() + 1, getY()) ;
                timesMovedRight++ ;
            
                if(moveCount == moveCountLimit){
                    this.setLocation(getX(), getY()) ;
                    timesMovedRight++ ;
                    originalY++;
                    move1 = true ; //goes back to normal
                    move2 = false ;
                    moveCount = 0 ;
                }
            }
            
            timesMovedLeft = 0 ;
            timesMovedRight = 0 ;
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
            ((GalagaWorld) getWorld()).addToScore(100);
            ((GalagaWorld) getWorld()).subtractFromEnemies();
            getWorld().removeObject(this) ;
        }
   
        
    }
    
    public void explosionCount(){
        if(explosionCount < 5){
        explosionCount++ ;
    }
    else{
        explosionCount = 5;
    }
    }
    
    public void moveCount(){
        
        if(firstMove == true){
            moveCountLimit = 50 ;
        }
        if(firstMove == false){
            moveCountLimit = 100 ;
        }
        if(moveCount < moveCountLimit){
            moveCount++ ;
        }
        if(moveCount == moveCountLimit){
            moveCount = moveCountLimit ;
        }
    }
    
    
    public void isHit(){
        if(isHit == true){
            this.hit() ;
        }
    }
    
       public void shot(){
        
        randomNumber = Greenfoot.getRandomNumber(900);
        
        if((randomNumber == 1 || forcedShot == true) && isHit == false)
        {
           int randomNumber2= Greenfoot.getRandomNumber(100);
           GalagaWorld world= (GalagaWorld) getWorld();
           if(world.getLevel()>=3)
           {
             Vector angle= calculateVector();
             this.getWorld().addObject(new Enemy_Bullet(angle), getX(), getY() + 1) ;
        }
        else
        {
            this.getWorld().addObject(new Enemy_Bullet(), getX(), getY() + 1) ;
        }
       }
        
    }
    public Vector calculateVector()
    {
        GalagaWorld w= (GalagaWorld) getWorld();
            int[] co= w.getCoordinates();
            if(getY()-co[1]==0) //the case that tangent will cause div by 0
            {
                return new Vector(90, 1);
            }
            else
            {
                int o= (getX()- co[0]);
                int a= (getY()- co[1]);
                double vectorNumber= (double) ((double) (o)/ (double) (a));
                 vectorNumber= Math.atan(vectorNumber);
                vectorNumber= Math.toDegrees(vectorNumber);
                if(vectorNumber!=0) 
                {
                    vectorNumber=90-vectorNumber;
                }
                if(getY()> co[1])
                {
                    vectorNumber+=180;
                }
                int vector = (int) vectorNumber;
                return new Vector(vector, 1);
            }
    }
    
    
    
    public void attackShip(){
        
        if(attackShipCount == 3 && firstAttackMove == true){ //first move turn
            if(isHit == true){
                firstAttackMove = false ;
            }
            else{
                this.setLocation(getX(), getY() - 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++;
                if(attackShipMoveCount == 3){
                    firstAttackMove = false ;
                    secondAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && secondAttackMove == true){ //second move turn
            if(isHit == true){
                secondAttackMove = false ;
            }
            else{
                this.setRotation(45);
                this.setLocation(getX() + 1, getY() - 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 3){
                    secondAttackMove = false ;
                    thirdAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && thirdAttackMove == true){ //third move turn
            if(isHit == true){
                thirdAttackMove = false ;
            }
            else{
                this.setRotation(90);
                this.setLocation(getX() + 1 , getY());
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 3){
                    thirdAttackMove = false ;
                    fourthAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && fourthAttackMove == true){ //forth move turn
            if(isHit == true){
                fourthAttackMove = false ;
            }
            else{
                this.setRotation(135);
                this.setLocation(getX() + 1, getY() + 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 3){
                    fourthAttackMove = false ;
                    fifthAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && fifthAttackMove == true){ //fifthMove down
            if(isHit == true){
                fifthAttackMove = false ;
            }
            else{
                this.setRotation(180);
                this.setLocation(getX(), getY() + 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 10){
                    forcedShot = true ;
                    fifthAttackMove = false ;
                    sixthAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && sixthAttackMove == true){ //sixth move down
            if(isHit == true){
                sixthAttackMove = false ;
            }
            else{
                forcedShot = false ;
                this.setRotation(180) ;
                this.setLocation(getX() - 1, getY() + 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 10){
                    if(((GalagaWorld) getWorld()).getAmountEnemies() <= 10){
                        forcedShot = true ;
                    }
                    sixthAttackMove = false ;
                    seventhAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && seventhAttackMove == true){ //seventh move down
            if(isHit == true){
                seventhAttackMove = false ;
            }
            else{
                forcedShot = false ;
                this.setRotation(180) ;
                this.setLocation(getX() + 1, getY() + 1) ;
                attackShipCount = 0 ;
                attackShipMoveCount++ ;
                if(attackShipMoveCount == 10){
                    seventhAttackMove = false ;
                    eigthAttackMove = true ;
                    attackShipMoveCount = 0 ;
                }
            }
        }
        if(attackShipCount == 3 && eigthAttackMove == true){
            if(isHit == true){
                eigthAttackMove = false ;
            }
            else{
                this.setRotation(180) ;
                this.setLocation(getX(), getY() + 1) ;
                attackShipCount = 0 ;
                if(getY() > 48){
                    this.setLocation(originalX - timesMovedLeft + timesMovedRight,
                                 originalY) ;
                    this.setRotation(0) ;
                    eigthAttackMove = false ;
                    firstAttackMove = true ;
                    shouldAttackShip = false ;
                }
            }
        }
    }
    
    public void attackShipCount(){
        
        if(attackShipCount < 3){
            attackShipCount++;
        }
        else{
            attackShipCount = 3 ;
        }
    }
    
    public void shouldAttackShip(){
        
        randomNumber = Greenfoot.getRandomNumber(1000) ;
        if(randomNumber == 1){
            shouldAttackShip = true ;
        }
        
        if(shouldAttackShip == true){
            this.attackShip() ;
        }
    }
    
    public void isCollided(){
        
        if(isHit == false && shouldAttackShip == true){
        Ship ship = (Ship) getOneIntersectingObject(Ship.class);
    
        if(ship != null){
            ((GalagaWorld) getWorld()).subtractFromEnemies();
            getWorld().removeObject(this) ;
            ship.hit() ;
        }
    }
    }
    
    
}
