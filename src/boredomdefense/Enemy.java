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

public class Enemy{// extends JPanel{
    
    int hp;
    int pl;
    
    int speed;  // frames needed to move to next square -> 20 = normal, 40 = slow
    int slow_frames_left = 0;
    
    int next_step = 20;
    
    BufferedImage bi;
    
    static int size=19;
    
    boolean up = false;
    boolean right = false;
    boolean down = true;
    boolean left = false;
    
//    boolean lmu = false, lmd = true, lml = false, lmr = false;
    
    
    int enrage = 200;   // frames until enrage
    
    int frenzy = 8000;
    
    public static void main(String[] args) {
        
//        for(int i=0; i<10; i++)
//            new Enemy(i);
        
        JFrame a = new JFrame();
        
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(100, 100);
        a.setLocationRelativeTo(null);
        a.setTitle("Creeeeeps");
        a.setResizable(false);
        
        a.setVisible(true);
        
        a.setLayout(null);
        
        
        
//        a.add(new Enemy());
        
    }
    
    Enemy(int powerlevel)
    {
        speed = 20;
        pl = powerlevel;
        hp = (10+5*powerlevel*((powerlevel+10)/10) + (int)(Math.random()*(powerlevel+1))* (((int)(Math.random()*10)%2 == 0)?1:-1) )* ((powerlevel>=100)?(powerlevel/10):(powerlevel>=50)?3:(powerlevel>=30)?2:1);
//        System.out.println(hp);
//        setBounds(2,2,26,26);
        
        bi = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        
        
        
        Graphics2D g = bi.createGraphics();
        int a = (int)(Math.random()*255);
        int b = (int)(Math.random()*255);
        int c = (int)(Math.random()*255);
        g.setBackground(new Color(a,b,c));
        for(int i =0; i< ((powerlevel<50)?powerlevel:50); i++)
        {
            
            a = (int)(Math.random()*255);
            b = (int)(Math.random()*255);
            c = (int)(Math.random()*255);
            g.setColor(new Color(a,b,c));
            
            g.drawLine((int)(Math.random()*size), (int)(Math.random()*size), (int)(Math.random()*size), (int)(Math.random()*size));
            
        }
        
        g.setColor(Color.black);
        g.fillOval((size-1)/2 - 7, (size-1)/2 - 7, 7, 9);
        g.fillOval((size-1)/2 + 1, (size-1)/2 - 7, 7, 9);
        
        g.setColor(Color.white);
        g.fillOval((size-1)/2 - 6, (size-1)/2 - 6, 5, 7);
        g.fillOval((size-1)/2 + 2, (size-1)/2 - 6, 5, 7);
        
        g.setColor(Color.red);
        g.fillOval((size-1)/2 - 5, (size-1)/2 - 5, 3, 5);
        g.fillOval((size-1)/2 + 3, (size-1)/2 - 5, 3, 5);
        
//        bi.setForeground();
        
    }
    
    Enemy()
    {
        this(0);
    }
    
//    public void paint(Graphics g) 
//    {
//        super.paint(g);
//
//        int a = (int)(Math.random()*255);
//        int b = (int)(Math.random()*255);
//        int c = (int)(Math.random()*255);
//        setBackground(new Color(a,b,c));
//        
//        Graphics2D g2d = (Graphics2D)g;
//        for(int i =0; i< 30; i++)
//        {
//            
//            a = (int)(Math.random()*255);
//            b = (int)(Math.random()*255);
//            c = (int)(Math.random()*255);
//            setForeground(new Color(a,b,c));
//            
//            g2d.drawLine((int)(Math.random()*26), (int)(Math.random()*26), (int)(Math.random()*26), (int)(Math.random()*26));
//            
//        }
//        
//    }
    
    
    
}
