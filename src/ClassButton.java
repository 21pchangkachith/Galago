import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ClassButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ClassButton extends Button
{
    private GreenfootImage image;
    private String type;
    /**
     * Act - do whatever the ClassButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public ClassButton(String newType)
    {
        type=newType;
        image = new GreenfootImage(newType+"button.png") ;
        setImage(image);
    }
     public void performAction()
     {
       if(type=="tank") 
       {   
           Ship.setClass(1);
       }
       else if(type=="power") 
       {
           Ship.setClass(2);
        }
       if(type=="speed") 
       {
           Ship.setClass(3);
        }
     }
}
