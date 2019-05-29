/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boredomdefense;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.*;

public class BDMain {
    
    public static void main(String[] args) {
        new BDMain();
    }
    
    BDMain()
    {
        
        Object[] options = {"How to Play",
                    "Play Tower Defense", "Play Survival"};
        
        int n = 0;
        
        while(n==0)
        {
            n = JOptionPane.showOptionDialog(null,
            "What do you want to play?",
            Global.title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.png"))),
            options,  //the titles of buttons
            options[0]); //default button title
            
            if(n==JOptionPane.CLOSED_OPTION)
                System.exit(0);
            
            if(n==0)
                JOptionPane.showMessageDialog(null, "How to Play: Tower Defense\n"
                        + "Protect yourself from evil monsters by building towers into a twisted maze!\n\n"
                        + "Black towers are cheap and can be used to build a labyrinth.\n"
                        + "Green towers deal high damage but can only fire at one target at once.\n"
                        + "Blue towers slow enemies down and deal a small amount of damage.\n"
                        + "Red towers reload fast and damage every enemy in their radius.\n\n" 
                        + "Upgrade towers to increase their radius and damage output.\n" 
                        + "1 level costs as much as the tower's base cost.\n" 
                        + "Sell towers to regain 50 percent of your investment.\n\n"
                        + "If an enemy feels trapped, it might start attacking your towers!\n"
                        + "<Warning: Enemies in this version are still stupid and might act unreasonable!>\n\n\n"
                        + "How to Play: Survival Mode\n" 
                        + "Use towers to defeat evil monsters and prevent the field from filling up!\n\n" 
                        + "Black Towers do nothing except for blocking space.\n"
                        + "Green towers deal high damage but can only fire at one target at once.\n" 
                        + "Blue towers deal procentual damage to nearby enemies but can't kill them.\n" 
                        + "Red towers reload fast and damage every enemy in their radius.\n\n" 
                        + "Upgrade towers to increase their radius and damage output.\n" 
                        + "1 level costs as much as the tower's base cost.\n" 
                        + "Sell towers to regain 50 percent of your investment. "
                        ,
                        Global.title,
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.png")))
                        );
        }
        
        if(n==2)
        {
            SVMOde.bdd = new SVMOde();
        }
        else if(n==1)
        {
            TDMOde.bdd = new TDMOde();
        }
       
       
    }
    
}
