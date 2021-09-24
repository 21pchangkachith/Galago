import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor

{
    private GreenfootImage button_1 = new GreenfootImage("blue-draught.png"); 
  
     private boolean mouseDown;
    public Button() { 
        mouseDown=false;
        setImage(button_1); 
    } 
    public void act()
    {
        checkPressed();
    }
        public void checkPressed() 
    {
        if (Greenfoot.mousePressed(this)) {	
            mouseDown = true;
        }	
        if (mouseDown && Greenfoot.mouseClicked(this)) { 
            performAction();
        }
    }
    public void performAction()
    {
        
    }
}


