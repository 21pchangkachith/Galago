import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SeanConnery here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boss extends Actor
{
    private GreenfootImage boss1;
    private GreenfootImage boss2;
    private GreenfootImage boss3;
    private GreenfootImage boss4;
    private GreenfootImage explosion1;
    private GreenfootImage explosion2 ;
    private GreenfootImage explosion3 ;
    private int moveCount  ; //for moving back and forth
    private int moveCountLimit ; //changes to 100 after first move
    private  int hitCount  ; //how many times has the beastly Sean Connery been hit?
    private int explosionCount  ;
    private boolean explode1  ; //explosion1 should be ready to go
    private boolean explode2  ;
    private boolean explode3 ;
    private boolean finishExplosion;
    private boolean move1 ; //move1 should be ready to move
    private boolean move2; //should the beastly Sean Connery move back?
    private boolean firstMove ; //is this the first time the beastly Sean Connery is moving?
    private boolean isHit; //is the beastly Sean Connery dead?
    private boolean firedRecently;
    private int currentSprite;
    private  boolean isSecondPhase;
    private boolean firing;
    private int fireLoc;
    private int gAttack;
    public Boss()
    {
        //initializing
        boss1 = new GreenfootImage("boss1.png") ;
        boss2 = new GreenfootImage("boss2.png") ;
        boss3 = new GreenfootImage("boss3.png") ;
        boss4 = new GreenfootImage("boss4.png") ;
        currentSprite=1;
        explosion1 = new GreenfootImage("seanConneryExplosion1.png") ;
        explosion2 = new GreenfootImage("seanConneryExplosion2.png") ;
        explosion3 = new GreenfootImage("seanConneryExplosion3.png") ;
        moveCount = 0 ; //for moving back and forth
        moveCountLimit = 50 ; //changes to 100 after first move
        hitCount = 0 ; //how many times has the beastly Sean Connery been hit?
        explosionCount = 0 ;
        explode1 = true ; //explosion1 should be ready to go
        explode2 = false ;
        explode3 = false ;
        finishExplosion = false ;
        move1 = true ; //move1 should be ready to move
        move2 = false ; //should the beastly Sean Connery move back?
        firstMove = true ; //is this the first time the beastly Sean Connery is moving?
        isHit = false ; //is the beastly Sean Connery dead?
        firedRecently = false;
        isSecondPhase=false;
        firing=false;
        fireLoc=0;
        gAttack=0;
        //done
    }
    /**
     * Act - do whatever the SeanConnery wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move();
        moveCount();
        explosionCount();
        shot();
        isHit();
        alterSprite();
    }    
    public void alterSprite()
    {
        currentSprite++;
        if(currentSprite>4) currentSprite=1;
        String temp = "";
        temp+= currentSprite;
        setImage("boss"+temp+".png");
    }
    public void secondAttack()
    {
        for(int i=60; i<=120; i+=4)
        {
            this.getWorld().addObject(new Enemy_Bullet(new Vector(i, 1)), getX(), getY() + 5) ;
            this.getWorld().addObject(new Enemy_Bullet(new Vector(180-i, 1)), getX(), getY() + 5) ;
        }
    }
    public void move(){
        
                if(moveCount % 2 == 0 && move1 == true){
                    this.setLocation(getX() - 1, getY()) ;
                    
        
                    if(moveCount == moveCountLimit){
                        this.setLocation(getX() - 1, getY()) ;
                        move1 = false ; //so can use move2
                        move2 = true ;
                        firstMove = false ;
                        moveCount = 0 ;
                    }
                }
            
            if(moveCount % 2 == 0 && move2 == true){
                this.setLocation(getX() + 1, getY()) ;
            
                if(moveCount == moveCountLimit){
                    move1 = true ; //goes back to normal
                    move2 = false ;
                    moveCount = 0 ;
                }
            }
        }
        
        public void moveCount(){
        
        if(firstMove == true){
            moveCountLimit = 30 ;
        }
        if(firstMove == false){
            moveCountLimit = 60 ;
        }
        if(moveCount < moveCountLimit){
            moveCount++ ;
        }
        if(moveCount == moveCountLimit){
            moveCount = moveCountLimit ;
        }
    }
    
    public void shot(){
        int randomNumber;
        if(!firing)
        {
            randomNumber = Greenfoot.getRandomNumber(45) ;
        }
        else 
        {
            randomNumber=0;
        }
        
        if(randomNumber == 1 && !isHit && !firing){
               firing=true;
               fireLoc=80;
        }
        else if(randomNumber == 2 && isSecondPhase)
        {
            secondAttack();
        }
        else if(randomNumber == 3)
        {
            this.getWorld().addObject(new Enemy_Bullet(new Vector(10+calculateVector(getX(), getY()), 1), true), getX(), getY() + 5) ;
            this.getWorld().addObject(new Enemy_Bullet(new Vector(-10+calculateVector(getX(), getY()), 1), true), getX(), getY() + 5) ;
        }
        if(fireLoc<=100&&firing)
        {
                    this.getWorld().addObject(new Enemy_Bullet(new Vector(80-fireLoc+calculateVector(getX(), getY()), 1)), getX(), getY() + 5) ;
                    this.getWorld().addObject(new Enemy_Bullet(new Vector(120-fireLoc+calculateVector(getX(), getY()), 1)), getX(), getY() + 5) ;
                    fireLoc+=4;
         }
         else 
         {
             firing=false;
             fireLoc=0;
         }
         
        
        
    }
    public int calculateVector(int x, int y)
    {
        GalagaWorld w= (GalagaWorld) getWorld();
        int[] co= w.getCoordinates();
        int o= (x- co[0]);
        int a= (y- co[1]);
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
        return vector;
    }
    
    public void hit(){
        hitCount++;
        if(hitCount>20)
            isSecondPhase=true;
        if(hitCount == 40){
            isHit = true ;
        }
    }
    
    public void die(){
    
        if(explosionCount == 5 && explode1 == true)
        {
            setImage(explosion1);
            Greenfoot.playSound("explosion.wav");
            explosionCount = 0 ;
            explode1 = false ;
            explode2 = true ;
        }
        if(explosionCount == 5 && explode2 == true){
            setImage(explosion2) ;
            explosionCount = 0 ;
            explode2 = false ;
            explode3 = true ;
        }
        if(explosionCount == 5 && explode3 == true){
            setImage(explosion3);
            explosionCount = 0 ;
            explode3 = false ;
            finishExplosion = true ;
        }
        if(explosionCount == 5 && finishExplosion == true){
            ((GalagaWorld) getWorld()).addToScore(100000);
            getWorld().removeObject(this) ;
        } 
    }
    
    public void explosionCount(){
        if(explosionCount < 5){
        explosionCount++ ;
    }
}

    public void isHit(){
        if(isHit == true){
            this.die() ;
        }
    }
    
}
