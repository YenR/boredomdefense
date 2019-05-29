
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



public class BoreDomDefense extends JFrame{

    static int      w_width     = 546,                  // Window Parameters
                    w_height    = 748;                  // tatsaechliche Groesse sollte 540x720 sein
    
    static int      refreshrate = 50;
    
    static int      mousestate = 0;
    
    static int      wave = 1;
    
    static boolean  dodraw = true;
    
    static int      resource = 10000, hp = 100;
    
    static Dis      a;
    
    static BoreDomDefense bdd;
    
    
    
            
    public static void main(String[] args) {
        
        bdd = new BoreDomDefense();
    }
    
    BoreDomDefense()
    {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(w_width, w_height);
        setLocationRelativeTo(null);
        setTitle("BD TD alpha v0.3 - alternative Mode: Survival");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.png"))); 
        
        a = new Dis();
        add(a);
        
        
        setVisible(true);
    }
    
    class Tower
    {
        int type = 0;
        int level = 0;
        
        int nextShot = 0;
        
        Tower(int t)
        {
            type = t;
        }
    }
    
    class Plate extends JPanel
    {
        boolean mouse = false;
        boolean men   = false;
        
        Tower t = null;
        
        Enemy e = null;
        
        
        Plate()
        {
            super();
        }
    }
    
        Plate plates[][];
        int wi = 18, hi = 22;
    
    public void drawmyline(int i, int j, int zi, int zj, Graphics2D g2) // Tower at Coord i,j attacks Enemy at Coord zi, zj
    {
        if(plates[zi][zj].e != null)
        {
            g2.setColor((plates[i][j].t.type == 2)?Color.GREEN:(plates[i][j].t.type == 3)?Color.BLUE:Color.RED);

            g2.drawLine(i*30 + 14, j*30 + 14, zi*30 + 14, zj*30 + 14);
            g2.drawLine(i*30 + 15, j*30 + 14, zi*30 + 15, zj*30 + 14);
            g2.drawLine(i*30 + 14, j*30 + 15, zi*30 + 14, zj*30 + 15);

            plates[i][j].t.nextShot = (plates[i][j].t.type == 2)?10:5;

            plates[zi][zj].e.hp -= ( (plates[i][j].t.type == 2)?(5 + plates[i][j].t.level):(plates[i][j].t.type == 3)?2:(2 + plates[i][j].t.level));

            if(plates[i][j].t.type == 3)    // freeze!
            {
                plates[zi][zj].e.speed = 40;
                plates[zi][zj].e.slow_frames_left = 80 + plates[i][j].t.level * 20;
            }
            
            if(plates[zi][zj].e.hp <= 0)
            {
                resource += 100 + plates[zi][zj].e.pl * 4;
                plates[zi][zj].e = null;
            }

            g2.setColor(Color.black);
        }
    }
        
        
    class Dis extends JPanel    // Display
    {
        Dis()
        {
            setDoubleBuffered(true);
            setLayout(null);
            
            plates = new Plate[wi][hi];
            
            for(int j=0; j<hi; j++)
            {
                for(int i=0; i<wi; i++)
                {
                    plates[i][j] = new Plate();
                    
                    plates[i][j].setBounds(i*30, j*30, 30, 30);
                    
                    plates[i][j].addMouseListener(new PMoLi(i,j));
                    
                    
//                    if((i%2==0&&j%2==1) || (i%2==1&&j%2==0))
//                        plates[i][j].setBackground(new Color(0,0,0));
                    
                    this.add(plates[i][j]);
                }
            }
            
            JPanel t1 = new JPanel(), t2 = new JPanel(), t3 = new JPanel(), t4 = new JPanel();
            
            t1.setBounds(25, hi*30 + 10, 30, 30);
            t2.setBounds(75, hi*30 + 10, 30, 30);
            t3.setBounds(125, hi*30 + 10, 30, 30);
            t4.setBounds(175, hi*30 + 10, 30, 30);
            
            t1.addMouseListener(new MouseListener()
            {
                @Override public void mouseClicked(MouseEvent e) {
                    mousestate = 1;
                }
                @Override public void mouseEntered(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}
            });
            
            t2.addMouseListener(new MouseListener()
            {
                @Override public void mouseClicked(MouseEvent e) {
                    mousestate = 2;
                }
                @Override public void mouseEntered(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}
            });
            
            t3.addMouseListener(new MouseListener()
            {
                @Override public void mouseClicked(MouseEvent e) {
                    mousestate = 3;
                }
                @Override public void mouseEntered(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}
            });
            
            t4.addMouseListener(new MouseListener()
            {
                @Override public void mouseClicked(MouseEvent e) {
                    mousestate = 4;
                }
                @Override public void mouseEntered(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}
            });
            
            this.add(t1);
            this.add(t2);
            this.add(t3);
            this.add(t4);
            
            
            java.util.Timer timer = new java.util.Timer();
            timer.scheduleAtFixedRate(new ScheduleTask(), 10, refreshrate);
        
            java.util.Timer timer2 = new java.util.Timer();
            timer2.scheduleAtFixedRate(new EnemyTask(), 2000, 10000);
        
//            for(int i=0; i<wi; i++)
//                plates[i][i].e = new Enemy(i);
            
//            plates[2][5].e = new Enemy(50);
//            plates[1][5].e = new Enemy(50);
//            plates[1][10].e = new Enemy(100);
            
            
        }
        
        public void paint(Graphics g) {
            super.paint(g);

            for(int j=0; j<hi; j++)
            {
                for(int i=0; i<wi; i++)
                {
                    if(plates[i][j].t != null)
                        if(plates[i][j].t.nextShot>0)
                            plates[i][j].t.nextShot--;
                }
            }
            
            
            
            Graphics2D g2d = (Graphics2D)g;
            
            Image p1 = new ImageIcon(this.getClass().getResource("/img/plate1.png")).getImage();
            Image p2 = new ImageIcon(this.getClass().getResource("/img/plate2.png")).getImage();
            
            for(int j=0; j<hi; j++)
            {
                for(int i=0; i<wi; i++)
                {
                    if(plates[i][j].men == true)
                    {
                        Image men = new ImageIcon(this.getClass().getResource("/img/men.png")).getImage();
                        g2d.drawImage(men, i*30, j*30, this);
                    }
                    else
                    {
                        if(plates[i][j].mouse == false)
                        {
                            g2d.drawImage(p1, i*30, j*30, this);
                        }
                        else
                        {
                            g2d.drawImage(p2, i*30, j*30, this);
                        }

                        if(plates[i][j].t != null)
                        {
                            try
                            {
                                Image tower = new ImageIcon(this.getClass().getResource("/img/t" + plates[i][j].t.type + ".png")).getImage();
                                g2d.drawImage(tower, i*30, j*30, this);

                                if(plates[i][j].t.level > 0)
                                {
                                    Image lvl = new ImageIcon(this.getClass().getResource("/img/" + plates[i][j].t.level + ".png")).getImage();
                                    g2d.drawImage(lvl, i*30, j*30, this);
                                }

                                // Damage creeps! // nah

                            } catch(Exception e){}
                        }
                        else if(plates[i][j].e != null)
                        {
                            g2d.drawImage(plates[i][j].e.bi, i*30 + (30-Enemy.size)/2, j*30 + (30-Enemy.size)/2, this);
                        }
                    }
                    
                }
            }
            
            
            for(int j=0; j<hi; j++)
            {
                for(int i=0; i<wi; i++)
                {
                    if(plates[i][j].t != null)
                        {
                            try
                            {
                                /*
                                 *  Green Tower and Blue Tower
                                 */
                                if((plates[i][j].t.type == 2 || plates[i][j].t.type == 3)&& plates[i][j].t.nextShot == 0)    // Green Tower
                                {
                                    /*
                                     *                             o 
                                     *  Level 0 Shot     ->       oXo
                                     *                             o 
                                     */
                                    
                                    if(j<hi-1)    // 1 below
                                    {drawmyline(i,j,i,j+1,g2d);}
                                    
                                    if(plates[i][j].t.nextShot == 0 && i > 0) // 1 left
                                    {drawmyline(i,j,i-1,j,g2d);}

                                    if(plates[i][j].t.nextShot == 0 && i < wi-1) // 1 right
                                    {drawmyline(i,j,i+1,j,g2d);}
                                    
                                    if(plates[i][j].t.nextShot == 0 && j>0)    // 1 above
                                    {drawmyline(i,j,i,j-1,g2d);}
                                    
                                    if(plates[i][j].t.nextShot == 0 && plates[i][j].t.level>=1)     // extended shot
                                    {
                                        /*
                                         *                            ooo 
                                         *  Level 1 Shot     ->       oXo
                                         *                            ooo 
                                         */
                                        
                                        if(j<hi-1 && i> 0)    // 1 below 1 left
                                        {drawmyline(i,j,i-1,j+1,g2d);}

                                        if(plates[i][j].t.nextShot == 0 && j<hi-1 && i < wi-1) // 1 below 1 right
                                        {drawmyline(i,j,i+1,j+1,g2d);}

                                        if(plates[i][j].t.nextShot == 0 && i > 0 && j > 0) // 1 above 1 left
                                        {drawmyline(i,j,i-1,j-1,g2d);}

                                        if(plates[i][j].t.nextShot == 0 && j>0 && i < wi-1)    // 1 above 1 right
                                        {drawmyline(i,j,i+1,j-1,g2d);}
                                        
                                        if(plates[i][j].t.nextShot == 0 && plates[i][j].t.level>=2)     // extended shot 2
                                        {
                                            /*                             o
                                             *                            ooo 
                                             *  Level 2 Shot     ->      ooXoo
                                             *                            ooo 
                                             *///                          o
                                            
                                            if(j<hi-2)    // 2 below
                                            {drawmyline(i,j,i,j+2,g2d);}

                                            if(plates[i][j].t.nextShot == 0 && i > 1) // 2 left
                                            {drawmyline(i,j,i-2,j,g2d);}

                                            if(plates[i][j].t.nextShot == 0 && i < wi-2) // 2 right
                                            {drawmyline(i,j,i+2,j,g2d);}

                                            if(plates[i][j].t.nextShot == 0 && j>1)    // 2 above
                                            {drawmyline(i,j,i,j-2,g2d);}
                                        
                                            if(plates[i][j].t.nextShot == 0 && plates[i][j].t.level>=3)     // extended shot 3
                                            {
                                                /*                           ooo
                                                *                            ooo 
                                                *  Level 3 Shot     ->      ooXoo
                                                *                            ooo 
                                                *///                         ooo
                                                
                                                if(j<hi-2 && i> 0)    // 2 below 1 left
                                                {drawmyline(i,j,i-1,j+2,g2d);}

                                                if(plates[i][j].t.nextShot == 0 && j<hi-2 && i < wi-1) // 2 below 1 right
                                                {drawmyline(i,j,i+1,j+2,g2d);}

                                                if(plates[i][j].t.nextShot == 0 && i > 0 && j > 1) // 2 above 1 left
                                                {drawmyline(i,j,i-1,j-2,g2d);}

                                                if(plates[i][j].t.nextShot == 0 && j>1 && i < wi-1)    // 2 above 1 right
                                                {drawmyline(i,j,i+1,j-2,g2d);}
                                                
                                                if(plates[i][j].t.nextShot == 0 && plates[i][j].t.level>=4)     // extended shot 4
                                                {
                                                    /*                           ooo
                                                    *                           ooooo 
                                                    *  Level 4 Shot     ->      ooXoo
                                                    *                           ooooo 
                                                    *///                         ooo

                                                    if(j<hi-1 && i> 1)    // 1 below 2 left
                                                    {drawmyline(i,j,i-2,j+1,g2d);}

                                                    if(plates[i][j].t.nextShot == 0 && j<hi-1 && i < wi-2) // 1 below 2 right
                                                    {drawmyline(i,j,i+2,j+1,g2d);}

                                                    if(plates[i][j].t.nextShot == 0 && i > 1 && j > 0) // 1 above 2 left
                                                    {drawmyline(i,j,i-2,j-1,g2d);}

                                                    if(plates[i][j].t.nextShot == 0 && j>0 && i < wi-2)    // 1 above 2 right
                                                    {drawmyline(i,j,i+2,j-1,g2d);}
                                                    
                                                    if(plates[i][j].t.nextShot == 0 && plates[i][j].t.level>=5)     // extended shot 5
                                                    {
                                                        /*                          ooooo
                                                        *                           ooooo 
                                                        *  Level 5 Shot     ->      ooXoo
                                                        *                           ooooo 
                                                        *///                        ooooo

                                                        if(j<hi-2 && i> 1)    // 2 below 2 left
                                                        {drawmyline(i,j,i-2,j+2,g2d);}

                                                        if(plates[i][j].t.nextShot == 0 && j<hi-2 && i < wi-2) // 2 below 2 right
                                                        {drawmyline(i,j,i+2,j+2,g2d);}

                                                        if(plates[i][j].t.nextShot == 0 && i > 1 && j > 1) // 2 above 2 left
                                                        {drawmyline(i,j,i-2,j-2,g2d);}

                                                        if(plates[i][j].t.nextShot == 0 && j>1 && i < wi-2)    // 2 above 2 right
                                                        {drawmyline(i,j,i+2,j-2,g2d);}
                                                    }

                                                }
                                                
                                            }
                                            
                                            
                                        }
                                    }
                                }
                                
                                /*
                                 *  Red Tower
                                 */
                                else if(plates[i][j].t.type == 4 && plates[i][j].t.nextShot == 0)  
                                {
                                    /*
                                     *                             o 
                                     *  Level 0 Shot     ->       oXo
                                     *                             o 
                                     */
                                    
                                    if(j<hi-1)    // 1 below
                                    {drawmyline(i,j,i,j+1,g2d);}
                                    
                                    if(i > 0) // 1 left
                                    {drawmyline(i,j,i-1,j,g2d);}

                                    if(i < wi-1) // 1 right
                                    {drawmyline(i,j,i+1,j,g2d);}
                                    
                                    if(j>0)    // 1 above
                                    {drawmyline(i,j,i,j-1,g2d);}
                                    
                                    if(plates[i][j].t.level>=1)     // extended shot
                                    {
                                        /*
                                         *                            ooo 
                                         *  Level 1 Shot     ->       oXo
                                         *                            ooo 
                                         */
                                        
                                        if(j<hi-1 && i> 0)    // 1 below 1 left
                                        {drawmyline(i,j,i-1,j+1,g2d);}

                                        if(j<hi-1 && i < wi-1) // 1 below 1 right
                                        {drawmyline(i,j,i+1,j+1,g2d);}

                                        if(i > 0 && j > 0) // 1 above 1 left
                                        {drawmyline(i,j,i-1,j-1,g2d);}

                                        if(j>0 && i < wi-1)    // 1 above 1 right
                                        {drawmyline(i,j,i+1,j-1,g2d);}
                                        
                                        if(plates[i][j].t.level>=2)     // extended shot 2
                                        {
                                            /*                             o
                                             *                            ooo 
                                             *  Level 2 Shot     ->      ooXoo
                                             *                            ooo 
                                             *///                          o
                                            
                                            if(j<hi-2)    // 2 below
                                            {drawmyline(i,j,i,j+2,g2d);}

                                            if(i > 1) // 2 left
                                            {drawmyline(i,j,i-2,j,g2d);}

                                            if(i < wi-2) // 2 right
                                            {drawmyline(i,j,i+2,j,g2d);}

                                            if(j>1)    // 2 above
                                            {drawmyline(i,j,i,j-2,g2d);}
                                        
                                            if(plates[i][j].t.level>=3)     // extended shot 3
                                            {
                                                /*                           ooo
                                                *                            ooo 
                                                *  Level 3 Shot     ->      ooXoo
                                                *                            ooo 
                                                *///                         ooo
                                                
                                                if(j<hi-2 && i> 0)    // 2 below 1 left
                                                {drawmyline(i,j,i-1,j+2,g2d);}

                                                if(j<hi-2 && i < wi-1) // 2 below 1 right
                                                {drawmyline(i,j,i+1,j+2,g2d);}

                                                if(i > 0 && j > 1) // 2 above 1 left
                                                {drawmyline(i,j,i-1,j-2,g2d);}

                                                if(j>1 && i < wi-1)    // 2 above 1 right
                                                {drawmyline(i,j,i+1,j-2,g2d);}
                                                
                                                if(plates[i][j].t.level>=4)     // extended shot 4
                                                {
                                                    /*                           ooo
                                                    *                           ooooo 
                                                    *  Level 4 Shot     ->      ooXoo
                                                    *                           ooooo 
                                                    *///                         ooo

                                                    if(j<hi-1 && i> 1)    // 1 below 2 left
                                                    {drawmyline(i,j,i-2,j+1,g2d);}

                                                    if(j<hi-1 && i < wi-2) // 1 below 2 right
                                                    {drawmyline(i,j,i+2,j+1,g2d);}

                                                    if(i > 1 && j > 0) // 1 above 2 left
                                                    {drawmyline(i,j,i-2,j-1,g2d);}

                                                    if(j>0 && i < wi-2)    // 1 above 2 right
                                                    {drawmyline(i,j,i+2,j-1,g2d);}
                                                    
                                                    if(plates[i][j].t.level>=5)     // extended shot 5
                                                    {
                                                        /*                          ooooo
                                                        *                           ooooo 
                                                        *  Level 5 Shot     ->      ooXoo
                                                        *                           ooooo 
                                                        *///                        ooooo

                                                        if(j<hi-2 && i> 1)    // 2 below 2 left
                                                        {drawmyline(i,j,i-2,j+2,g2d);}

                                                        if(j<hi-2 && i < wi-2) // 2 below 2 right
                                                        {drawmyline(i,j,i+2,j+2,g2d);}

                                                        if(i > 1 && j > 1) // 2 above 2 left
                                                        {drawmyline(i,j,i-2,j-2,g2d);}

                                                        if(j>1 && i < wi-2)    // 2 above 2 right
                                                        {drawmyline(i,j,i+2,j-2,g2d);}
                                                    }

                                                }
                                                
                                            }
                                        }
                                    }
                                }
                               
                            } catch(Exception e){}
                        }
                }
            }
            
            
            
            
            g2d.setColor(Color.black);
            
            g2d.drawImage(p1, ((wi+1)/2)*30, hi*30, this);
            g2d.drawImage(p1, ((wi-1)/2)*30, hi*30, this);
            
            
            p2 = new ImageIcon(this.getClass().getResource("/img/glow.png")).getImage();
            
            if(mousestate == 1)
                g2d.drawImage(p2, 25, hi*30 + 10, this);
            p1 = new ImageIcon(this.getClass().getResource("/img/t1.png")).getImage();
            g2d.drawImage(p1, 25, hi*30 + 10, this);
            g2d.drawString("100", 28, hi*30 + 48);
            
            if(mousestate == 2)
                g2d.drawImage(p2, 75, hi*30 + 10, this);
            p1 = new ImageIcon(this.getClass().getResource("/img/t2.png")).getImage();
            g2d.drawImage(p1, 75, hi*30 + 10, this);
            g2d.drawString("1000", 75, hi*30 + 48);
            
            if(mousestate == 3)
                g2d.drawImage(p2, 125, hi*30 + 10, this);
            p1 = new ImageIcon(this.getClass().getResource("/img/t3.png")).getImage();
            g2d.drawImage(p1, 125, hi*30 + 10, this);
            g2d.drawString("1500", 125, hi*30 + 48);
            
            if(mousestate == 4)
                g2d.drawImage(p2, 175, hi*30 + 10, this);
            p1 = new ImageIcon(this.getClass().getResource("/img/t4.png")).getImage();
            g2d.drawImage(p1, 175, hi*30 + 10, this);
            g2d.drawString("1500", 175, hi*30 + 48);
            
            
            
            g2d.drawString("Money: " + resource, 320, hi*30 + 35);
            g2d.drawString("HP: " + hp, 420, hi*30 + 35);
            g2d.drawString("Wave: " + wave, 470, hi*30 + 35);
            
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
        
    }
    
    
    class PMoLi implements MouseListener{
        int tox, toy;
        
        PMoLi()
        {
            tox = toy = 0;
        }
        
        PMoLi(int ax, int ay)
        {
            tox = ax;
            toy = ay;
        }
        
        @Override public void mouseClicked(MouseEvent e) {
            
            if(mousestate!= 0 && plates[tox][toy].t == null && plates[tox][toy].e == null)
            {
                if(resource >= ((mousestate == 1)?100:(mousestate == 2)?1000:1500) )
                {
                    plates[tox][toy].t = new Tower(mousestate);

                    resource -= (mousestate == 1)?100:(mousestate == 2)?1000:1500;
                }
            }
            else if(plates[tox][toy].t != null)
            {
                mousestate = 0;
                
                if(plates[tox][toy].men == false)
                {
                    for(int j=0; j<hi; j++)
                    {
                        for(int i=0; i<wi; i++)
                        {
                            plates[i][j].men = false;
                        }
                    }
                    
                    plates[tox][toy].men = true;
                }
                else
                {
                    plates[tox][toy].men = false;
                    
                    if(e.getY()>=15)    // Sell
                    {
                         resource += (((plates[tox][toy].t.type == 1)?100:(plates[tox][toy].t.type == 2)?1000:1500)*(plates[tox][toy].t.level+1))/2; 
                         plates[tox][toy].t = null;
                    }
                    else                // LUP
                    {
                        if(plates[tox][toy].t.level < 5 && resource >= ((plates[tox][toy].t.type == 1)?100:(plates[tox][toy].t.type == 2)?1000:1500))
                        {
                            plates[tox][toy].t.level ++;
                            resource -= (plates[tox][toy].t.type == 1)?100:(plates[tox][toy].t.type == 2)?1000:1500;    
                        }
                    }
                    
                }
                
                
            }
            else
            {
                    for(int j=0; j<hi; j++)
                    {
                        for(int i=0; i<wi; i++)
                        {
                            plates[i][j].men = false;
                        }
                    }
            }
        }
        @Override public void mouseEntered(MouseEvent e) {
            
            plates[tox][toy].mouse = true;
        }
        
        @Override public void mouseExited(MouseEvent e) {
            
            plates[tox][toy].mouse = false;
        
        }
        @Override public void mousePressed(MouseEvent e) {
        
        }
        @Override public void mouseReleased(MouseEvent e) {
        
        }
    }

    class ScheduleTask extends TimerTask {

        public void run() {
//            if(dodraw == true)
                repaint();
        }
    }
    
    class EnemyTask extends TimerTask {

        public void run() {
            
            wave++;
            
            for(int i=0; i<(5+wave); i++)
            {
                int x = (int)(Math.random()*wi);
                int y = (int)(Math.random()*hi);
                
                if(plates[x][y].e == null && plates[x][y].t == null)
                {
                    plates[x][y].e = new Enemy(wave + ((int)(Math.random()*3)-1));
                }
                
            }
            
        }
    }
}
