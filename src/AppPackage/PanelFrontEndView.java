

package AppPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelFrontEndView extends JPanel implements MouseListener, MouseMotionListener{
  
    JPopupMenu popup; 
   // BufferedImage img;
    String deviceName;
    JMenuItem drag,properties,remove;
    private int squareX = 250;
    private int squareY = 250; 
    private int MicroX=450;
    private int MicroY=100;
    private BufferedImage img1;
    private boolean drawNewOnMouseMove;
    ArrayList<led> leds=new ArrayList<led>();
    private led l;
    private BufferedImage ledImg;
    private int currentIndex;    
    int [] PORTA=new int[8];   
    int [] PORTB=new int[8];   
    int [] PORTC=new int[8];   
    int [] PORTD=new int[8];
    Object [] PORTsA=new Object[8];
    Object [] PORTsB=new Object[8];
    Object [] PORTsC=new Object[8];
    Object [] PORTsD=new Object[8];
    
    int portACordinates=198,portBCordinates=328;
    int portCCordinates=149,portDCordinates=296;
    BufferedWriter bw=null;
    BufferedReader br=null;
    File file=null;
    
    
    
    public PanelFrontEndView() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        PORTB=new int[7];
        PORTC=new int[7];
        popup = new JPopupMenu();
        remove = new JMenuItem("Remove");
        remove.addMouseListener(this);
        popup.add(remove);
        properties = new JMenuItem("Properties");
        properties.addMouseListener(this);
        popup.add(properties);
        drag = new JMenuItem("Drag me");
        drag.addMouseListener(this);
        popup.add(drag);
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        setBounds(0,0,1200,700);
        deviceName="none";
        
          try {
             file =new File("code.txt");
           
    		if(!file.exists()){
    			file.createNewFile();
                        bw = new BufferedWriter(new FileWriter(file.getName(),true));
                        bw.write("void Main()\n{\n\twhile(1)\n\t{\n\n\t}\n}");
                        bw.flush();
                }
        }catch(Exception e)
        {
            System.out.println("exception");
        } 
        for(int i=0 ; i<7 ; i++){
            PORTA[i]=PORTB[i]=PORTC[i]=PORTD[i]=-1;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked : "+e.getSource());
      //  JOptionPane.showMessageDialog(remove,e.getX()+" : "+e.getY());
         if(drawNewOnMouseMove){
             drawNewOnMouseMove=false;
         }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("pressed : "+e.getSource()); 
        
                  try {
                     JMenuItem jm=(JMenuItem)e.getSource();
                      System.out.println("Menu text : " + jm.getText());
                     if((jm.getText()).equals("Remove")){
                         JOptionPane.showConfirmDialog(popup,"Are You Sure To Remove");
                         leds.remove(currentIndex);
                         repaint();
                     }else if((jm.getText()).equals("Drag me")){
                        l=leds.get(currentIndex);
                        drawNewOnMouseMove=true;
                    }else if((jm.getText()).equals("Properties")){
                        ledProperties lp=new ledProperties(leds.get(currentIndex),this);
                        lp.setVisible(true);
                     }
                    }catch (Exception ee) {
                     System.out.println("Menu ka item click nae hua");
                 }
            
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released + "+ e.getSource());   
   
        for (int i = 0; i < leds.size(); i++) {
            led a=leds.get(i);
            int squareX=a.getX_axis();
            int squareY=a.getY_axis();
            
             if(e.getX()>squareX && (squareX+ledImg.getWidth())>e.getX()){
             if(e.getY()>squareY && (squareY+ledImg.getHeight())>e.getY())
             {
                 if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(),e.getX(), e.getY());
                    currentIndex=i;
                 }
             }    
           }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    //    System.out.println("Entered");
      
   
    }

    @Override
    public void mouseExited(MouseEvent e) {
    //    System.out.println("Exited");
   
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Dragged : x : "+e.getX()+" : y : "+e.getY()); 
        if(e.getX()>0 && e.getY()>0 && e.getX()<903 )
            moveSquare(e.getX(),e.getY());
        else
            JOptionPane.showMessageDialog(popup,"Outside of Range");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
     //   System.out.println("Moved");
        if(drawNewOnMouseMove){
            l.setX_axis(e.getX());
            l.setY_axis(e.getY());
            moveSquare(e.getX(),e.getY());
        }
    }
       
    private void moveSquare(int x, int y) {
        squareX=x;
        squareY=y;
        repaint();
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    void setcomponent(Object comp)
    {
        if(comp instanceof led)
        {
            led l=(led)comp;
           // leds.add(l);
            
     JOptionPane.showMessageDialog(null,l.getBitNo()+" =:= "+l.getPort());
           printcode(l.Setting());
            
           if((l.getPort()+"").equals("A"))
           {
               //JOptionPane.showMessageDialog(null, "inside b");
               PORTA[l.getBitNo()]=0;
               PORTsA[l.getBitNo()]=l;
               
           }
           else if((l.getPort()+"").equals("B"))
           {
               PORTB[l.getBitNo()]=0;
               PORTsB[l.getBitNo()]=l;
           }
           else if((l.getPort()+"").equals("C"))
           {
               PORTC[l.getBitNo()]=0;
               PORTsB[l.getBitNo()]=l;
           }
           else if((l.getPort()+"").equals("D"))
           {
               PORTD[l.getBitNo()]=0;
               PORTsC[l.getBitNo()]=l;
           }
            
        }repaint();
    }
     
    void setDrawingItem(String s){
        deviceName=s;
          if(deviceName.equals("led")){           
            l=new led();
            l.setId(leds.size());
            leds.add(l);        
           //     JOptionPane.showMessageDialog(this,"leds.size() : "+leds.size());
            l.returnCode();
            drawNewOnMouseMove=true;
        }
        else if(deviceName.equals("")){
        
        }
    }
    
     void printcode(String code)
    {
       
       String data=""; 
       String str="";
      
    
      try{
            
           InputStream is = new FileInputStream(file.getName());
        int size = is.available();
        JOptionPane.showMessageDialog(null,size );
        for(int i=0; i< size; i++){
         //System.out.print((char)is.read() + "");
            
            data=data+(char)is.read();
           // System.out.print(data);
        }
        file.createNewFile();
            bw = new BufferedWriter(new FileWriter(file.getName()));
        System.out.print(code+"\n"+data);
        //code+="\n"+data;
        is.close();
        bw.write(code+"\n"+data);
        bw.flush();
        bw.close();

         
         
        
       }
       catch(Exception ex)
        {
             System.out.println(ex);   
        }
       }
    
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             
        try {
            img1 = ImageIO.read(getClass().getResourceAsStream("/Drawable/Microcontroller.PNG"));
            
        } catch (IOException e) {
           JOptionPane.showMessageDialog(this,"image nae milli");           
           JOptionPane.showMessageDialog(this,"Exception :"+e);
        }
        for (int i = 0; i < leds.size(); i++) {
            led a=leds.get(i);
            try {
                ledImg= ImageIO.read(getClass().getResourceAsStream("/Drawable/led.PNG"));
      //  g.drawRect(a.getX_axis(),a.getY_axis(), ledImg.getWidth(),ledImg.getHeight());
            } catch (IOException ex) {                                
                JOptionPane.showMessageDialog(this,"Exception : "+ex);
            }
             g.drawImage(ledImg,a.getX_axis(),a.getY_axis(), this);
        }
        for (int i = 0; i < 7; i++) {
            if(PORTA[i]!=(-1)){               
                 if(PORTsA[i] instanceof led){
                     led ll=(led)PORTsA[i];
                 g.setColor(Color.red);
                     g.drawLine(ll.getX_axis()+ledImg.getWidth()/2 ,ll.getY_axis(),450,198+(16*ll.getBitNo()));
                 }
            }
            if(PORTB[i]!=(-1)){
                 g.drawLine(MicroX-100, MicroY+100,MicroX,MicroY+100);  
            }
            if(PORTC[i]!=(-1)){
                g.drawLine(MicroX-100, MicroY+100,MicroX,MicroY+100);                
            }
            if(PORTD[i]!=(-1)){
                g.drawLine(MicroX-100, MicroY+100,MicroX,MicroY+100);               
            }
        }
        
        g.drawImage(img1,MicroX,MicroY, this);
     //   g.drawRect(MicroX, MicroY, img1.getWidth(),img1.getHeight());
    } 
   
}
