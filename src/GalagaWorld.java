import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)



/**
 * Write a description of class GalagaWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GalagaWorld extends World
{
    private Ship player;
    public static final int[] upgradeLevels= new int[]{2,5,8};
    public static int[] playerCo;
    public static boolean poweredUp;
    private int score; //score of game
    private int level; //level player is on
    private int amountOfEnemies; //amount of enemies on screen, this is for levels 1-3
    private int amountOfLives; //amount of lives, may go up or down
    private int extraLifeScore;//amount of points needed to get an extra life
    private int powerUpCounter;
    private boolean gameOverCheck;//changes false so game over displayed only once
    private Score ActorScore ;
    private Level ActorLevel ;
    private DescriptionBox tankBox,powerBox,speedBox ;
    private Instructions gameInstructions, respawnInstructions, upgradeInstructions;
    private NextLevelImage nextLevelImage;
    private GameOverImage gameOverImage;
    private Life life1; //always start with this life
    private Life life2; //always start with this life
    private Life life3; //gained if earned extra life
    private PowerUpSymbol symbol;
    private int levelScore; //the score earned on the level (used to avoid farming score by killing oneself)
    private Boss boss;//the most deadly object ever
    private Button restartButton, powerButton, tankButton, speedButton;
    private boolean readyToStart;
    /**
     * Constructor for objects of class GalagaWorld.
     * 
     */
    public GalagaWorld()
    {    
        
        // Create a new world with 20x20 cells with a cell size of 10x10 pixels.
        super(50, 50, 10);
        //begin initializing variables
        gameInstructions= new Instructions("gameInstructions");
        respawnInstructions= new Instructions("respawnInstructions");
        upgradeInstructions= new Instructions("upgradeInstructions");
        tankBox = new DescriptionBox();
        speedBox = new DescriptionBox();
        powerBox = new DescriptionBox();
        ActorLevel = new Level();
        ActorScore = new Score();
        score = 0; 
        level = 1 ;
        amountOfEnemies = 0 ;
        amountOfLives = 2 ;
         extraLifeScore = 5000 ;
        player= new Ship();
        poweredUp= false;
        playerCo= new int[]{25, 45};
        powerUpCounter= 0;
        gameOverCheck = true;
        nextLevelImage = new NextLevelImage();
        gameOverImage = new GameOverImage();
        life1 = new Life();
        life2 = new Life();
        life3 = new Life();
        levelScore=0;
        symbol = new PowerUpSymbol();
        boss = new Boss() ;
        restartButton = new GameOverButton();
        tankButton = new ClassButton("tank");
        speedButton = new ClassButton("speed");
        powerButton = new ClassButton("power");
        readyToStart=true;
        //end initialization
        addObject(symbol, 5, 1);
        repaintEnemies();
        addObject(player, 25, 45) ;
        addObject(this.life1, 1, 48);
        addObject(this.life2, 4, 48);
        addObject(this.gameInstructions, 29, 25);
        // Put in your own background image by replacing "greenfoot.png" with the
        // image that should be used as background and uncommenting the line below.
        
        setBackground("space.jpg"); 
        
    }
    public int getLevel()
    {
        return level;
    }
    public boolean shouldAddPowerUp()
    {
        if(score>((powerUpCounter+1)*1000))
        {
            powerUpCounter++;
            return true;
        }
        return false;
    }
    public void addPowerUp()
    {
        addObject(new PowerUp(), (int) Greenfoot.getRandomNumber(50),(int) 1 ); 
    }
    
    
    public void act(){
            drawScore();
            drawLevel();
            checkIfNextLevel();
            checkIfGameOver();
            if(shouldAddPowerUp()&&level>=2)
            {
                addPowerUp();
            }
            if(score >= extraLifeScore){
                checkExtraLife();
            }
            if(!readyToStart)
            {
                drawText();
                if(Ship.myType!=0)
                {
                    cleanUpClasses();
                    readyToStart=true;
                    displayNextLevel();
                    addToLevel();
                    repaintEnemies();
                }
            }
    }
    public void cleanUpClasses()
    {
        removeObject(this.upgradeInstructions);
        removeObject(this.tankButton);
        removeObject(this.speedButton);
        removeObject(this.powerButton);
        removeObject(this.tankBox);
        removeObject(this.speedBox);
        removeObject(this.powerBox);
    }
    public boolean isPoweredUp()
    {
        return poweredUp;
    }
 
    
    public void addToScore(int x){
        score = score + x ; // x may change depending on enemy
        levelScore+=x;
    }
    
    public void addToLevel(){
        level++; //level can only go up one at a time
    }
    
    public void givePowerUp()
    {
        poweredUp=true;
        //Ship checks poweredUp in act() to test if it is poweredup.
    }
    public PowerUpSymbol getCurrentPowerUp()
    {
        return symbol;
    }
    public void endPowerUp()
    {
        poweredUp=false;
        symbol.nullPowerUp();
    }
    
    public void drawScore(){
        addObject(this.ActorScore, 3, 1) ;
        ActorScore.getImage().clear();
        ActorScore.getImage().drawString("" + score, 20, 20) ;
    }
    
    public void drawLevel(){
        addObject(this.ActorLevel, 45, 1) ;
        ActorLevel.getImage().clear();
        ActorLevel.getImage().drawString("Level " + level, 10, 20);
    }

    public void subtractFromEnemies(){
        amountOfEnemies--;
    }
    
    public int getAmountEnemies(){
        return amountOfEnemies ;
    }
    
    public void checkIfNextLevel(){
        if(amountOfEnemies == 0 && level < 10){
            removeObjects(getObjects(Bullet.class));
            removeObjects(getObjects(Enemy_Bullet.class));
            removeObjects(getObjects(PowerUp.class));
            if(level==2)
            {
                showClassButtons();
                readyToStart=false;
            }
            if(level==5||level==8)
            {
                Ship.upgradeClass();
            }
            if(readyToStart)
            {
                levelScore=0;
                displayNextLevel();
                addToLevel();
                repaintEnemies();
            }
           
        }
    }
    public void showClassButtons()
    {
        addObject(this.tankButton, 10, 25);
        addObject(this.powerButton, 25, 25);
        addObject(this.speedButton, 40, 25);
        addObject(this.upgradeInstructions, 25, 40);
    }
    public void drawText()
    {
        addObject(this.tankBox, 10, 20);
        tankBox.getImage().clear();
        tankBox.getImage().drawString("Stronger Lives", 10, 20);
        addObject(this.powerBox, 25, 20);
        powerBox.getImage().clear();
        powerBox.getImage().drawString("More Bullets", 10, 20);
        addObject(this.speedBox, 40, 20);
        speedBox.getImage().clear();
        speedBox.getImage().drawString("Faster Reload", 10, 20);
    }
    public void checkIfGameOver(){
        if(amountOfLives < 0 && gameOverCheck == true){
            displayGameOver();
            removeObjects(getObjects(Enemy.class));
            removeObjects(getObjects(Boss.class));
            removeObjects(getObjects(PowerUp.class));
            gameOverCheck = false ;
        }
    }
    
    public void repaintEnemies(){
        if(level >= 1 && level < 10){
            for(int i = 0 ; i < 8 ; i++){
                addObject(new Enemy1(), 7 + 5*i, 15) ;
                addObject(new Enemy2(), 7 + 5*i, 10) ;
                amountOfEnemies+=2;
                if(level == 3 || level == 4){ //enemy amount increases after level three
                    addObject(new Enemy1(), 7 + 5*i, 20) ;
                    amountOfEnemies++;
                }
                if(level >= 5 && level < 10){
                    addObject(new Enemy1(), 7 + 5*i, 20) ;
                    amountOfEnemies++;
                    if(i % 2 == 0){
                        addObject(new Enemy4(), 9 + 5*i, 2) ;
                        amountOfEnemies++;
                    }
                }
                if(level >= 10){
                    addObject(new Enemy1(), 7 + 5*i, 20) ;
                    addObject(new Enemy1(), 7 + 5*i, 25) ;
                    amountOfEnemies+=2;
                    if(i % 2 == 0){
                        addObject(new Enemy3(), 9 + 5*i, 2) ;
                        amountOfEnemies++;
                    }
                }
                if(i % 2 == 0){
                    addObject(new Enemy3(), 9 + 5*i, 5) ;
                    amountOfEnemies++;
                }
            }
        }
        if(level == 10){
            addObject(this.boss, 25, 15) ;
            amountOfEnemies++;
            Greenfoot.playSound("seanConnerySound1.wav");
            Greenfoot.delay(350);
        }
    }
    
    public void displayNextLevel(){
        addObject(this.nextLevelImage, 25, 25);
        this.nextLevelImage.displayNextLevel();
        removeObject(this.nextLevelImage);
    }
    
    public void displayGameOver(){
        addObject(this.gameOverImage, 25, 25);
        this.gameOverImage.displayGameOver();
        removeObject(this.gameOverImage);
        addObject(this.restartButton, 25, 25);
        addObject(this.respawnInstructions, 25, 35);
    }
    
    public void subtractFromLives(){
        amountOfLives--;
    }
    
    public int getAmountOfLives(){
        return amountOfLives ;
    }
    
    public void loseLife(){
        if(amountOfLives == 2){
            removeObject(this.life3);
        }
        if(amountOfLives == 1){ 
            removeObject(this.life2);
        }
        if(amountOfLives == 0){
            removeObject(this.life1);
        }
    }
    
    public void checkExtraLife(){
        if(amountOfLives < 3){
            addExtraLife();
        }
        else{
            extraLifeScore = extraLifeScore + 5000 ;
        }
    }
    
    public void addExtraLife(){
        if(amountOfLives == 2){
            addObject(this.life3, 7, 48) ;
        }
        if(amountOfLives == 1){
            addObject(this.life2, 4, 48) ;
        }
        if(amountOfLives == 0){
            addObject(this.life1, 1, 48) ;
        }
        Greenfoot.playSound("extraLifeNoise.wav");
        amountOfLives++;
        extraLifeScore = extraLifeScore + 5000;
    }
    public void forcePowerUp()
    {
        symbol.setType(2);
        givePowerUp();
    }
    public void makeNewShip(){
        removeObjects(getObjects(Enemy_Bullet.class));
        removeObjects(getObjects(SmoothBullet.class));
        Ship newOne= new Ship();
        addObject(newOne, 25, 45) ;
        symbol.setType(2);
        givePowerUp();
    }
    public static void setNewCoordinates(int x, int y)
    {
        playerCo[0]=x;
        playerCo[1]=y;
    }
    public static int[] getCoordinates()
    {
        return playerCo;
    }
    public void resetWorld()
    {
        removeObject(this.respawnInstructions);
        addExtraLife();
        addExtraLife();
        addExtraLife();
        score-=levelScore;
        levelScore=0;
        amountOfLives+=3;
        if(level>8) level=9;
        else if(level>5) level=6;
        else if(level>2) level=3;
        amountOfEnemies=0;
        gameOverCheck = true;
        repaintEnemies();
        Ship newOne= new Ship();
        addObject(newOne, 25, 45) ;
    }
    
}
