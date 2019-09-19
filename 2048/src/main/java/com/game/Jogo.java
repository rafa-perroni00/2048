
package com.game;


import java.awt.Color;
import static java.awt.Color.white;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Jogo extends JPanel implements KeyListener, Runnable{
    
    private static final long serialVersionUID = 1L;
    public static int LARG = 400;
    public static int ALT = 630;
    public static final Font main = new Font("Verdana",Font.PLAIN,32);
    private Thread jogo;
    private boolean jogo_Rodando;
    private BufferedImage Tela = new BufferedImage(LARG,ALT,BufferedImage.TYPE_INT_RGB);
    private long startTime;
    private long elapsed;
    private boolean set1;
    
    public Jogo(){
    setFocusable(true);
    setPreferredSize(new Dimension(LARG,ALT));
    addKeyListener(this);
    }
    
    private void update(){
        
    }
    private void render(){//Criar o Tabuleiro
        Graphics2D g = (Graphics2D)Tela.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, LARG, ALT);
        g.dispose();
        Graphics2D g2d = (Graphics2D)getGraphics();
        g2d.drawImage(Tela,0,0,null);
        g2d.dispose();
        
        
        
    }
    
    @Override
    public void run(){
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPorUpdate = 1000000000.0/60; 
        
        
        //Ultimo update em nanosec
        double then = System.nanoTime();
        double unprocessed = 0;
        
        while(jogo_Rodando){
            boolean shouldRender = false;
            double working = System.nanoTime();
            unprocessed += (working - then)/nsPorUpdate; //qnts updates necessarios dependendo do tempo
            then = working;
        //Fila
        while(unprocessed >= 1){
            updates++;
            update();
            unprocessed--;
            shouldRender = true;
        }
        if(shouldRender){
            fps++;
            render();
            shouldRender = false;
        }
        else 
            try{                     //Debguer
                Thread.sleep(1);
            }catch(Exception e){
                e.printStackTrace();
            }
    
    //Fps Counter
    if(System.currentTimeMillis() - fpsTimer > 1000){
        System.out.printf("%d fps %d updates", fps,updates);
        System.out.println();
        fps = 0;
        updates = 0;
        fpsTimer += 1000;
    }
    }
    }
    
    public synchronized void start(){
        if(jogo_Rodando)return;
        jogo_Rodando = true;
        jogo = new Thread(this,"game");
        jogo.start();
    }
    
    public synchronized void stop(){
        if(!jogo_Rodando)return;
        jogo_Rodando = false;
        System.exit(0);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
