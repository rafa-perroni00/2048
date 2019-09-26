package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bloco {
    public static final int LARG = 80;
    public static final int ALT = 80;
    public static final int Slide = 15;
    public static final int ARC_LARG = 15;
    public static final int ARC_ALT = 15;
    
    private int valor_blocos;
    private BufferedImage blocos;
    private Color fundo;
    private Color string;
    private Font fonte;
    private int x;
    private int y;
    private Point Goto;
    
    private boolean juntarB;
    
    public Bloco(int valor_blocos,int x,int y){
        this.valor_blocos = valor_blocos;
        this.x = x;
        this.y = y;
        blocos = new BufferedImage(LARG, ALT, BufferedImage.TYPE_INT_ARGB);
        printaBloco();
    }
    
    private void printaBloco(){
      Graphics2D g = (Graphics2D)blocos.getGraphics();
      if(valor_blocos == 2){
          fundo = new Color(233,233,233);
          string = new Color(0,0,0);
      }
      else if(valor_blocos == 4){
        fundo = new Color(230,218,171);
        string = new Color(0,0,0);
    }
      else if(valor_blocos == 8){
        fundo = new Color(247,157,61);
        string = new Color(255,255,255);      
    }
      else if(valor_blocos == 16){
        fundo = new Color(242,128,7);
        string = new Color(255,255,255); 
    }
      else if(valor_blocos == 32){
        fundo = new Color(245,94,59);
        string = new Color(255,255,255);
    }
      else if(valor_blocos == 64){
        fundo = new Color(255,0,0);
        string = new Color(255,255,255);      
    }
      else if(valor_blocos == 128){
        fundo = new Color(233,222,132);
        string = new Color(255,255,255); 
    }
      else if(valor_blocos == 256){
        fundo = new Color(246,232,115);
        string = new Color(255,255,255);
    }
      else if(valor_blocos == 512){
        fundo = new Color(245,228,85);
        string = new Color(255,255,255);      
    }
      else if(valor_blocos == 1024){
        fundo = new Color(255,255,0);
        string = new Color(255,255,255); 
    }     
      else if(valor_blocos == 2048){
        fundo = new Color(0,255,0);
        string = new Color(255,255,255); 
    }
      else{
          fundo = new Color(0,0,0);
          string = new Color(255,255,255);
      }
      g.setColor(new Color(0,0,0,0));
      g.fillRect(0,0,LARG,ALT);
      
      g.setColor(fundo);
      g.fillRoundRect(0,0,LARG,ALT,ARC_LARG,ARC_ALT);
      
      g.setColor(string);
      
      if(valor_blocos <= 64){
          fonte = Jogo.main.deriveFont(36f);
      }
      else{
          fonte = Jogo.main;
      }
      g.setFont(fonte);

    int blocoX = LARG/2 - MetodosUteis.getMensagemBlocoLarg(""+valor_blocos, fonte, g)/2;
    int blocoY = ALT/2 - MetodosUteis.getMensagemBlocoAlt(""+valor_blocos, fonte, g)/2;
    g.drawString(""+valor_blocos,blocoX,blocoY);
    g.dispose();
    }
    
    public void update(){
        
    }
    public void render(Graphics2D g){
        g.drawImage(blocos,x,y,null);
        
    }
    
    public int getValor_blocos(){
        return valor_blocos;
    } 

    public void setValor_blocos(int valor_blocos) {
        this.valor_blocos = valor_blocos;
    }
    
    public boolean juntarB() {
        return juntarB;
    }

    public Point getGoto() {
        return Goto;
    }

    public void setGoto(Point Goto) {
        this.Goto = Goto;
    }
    
    public void setJuntarB(boolean juntarB) {
        this.juntarB = juntarB;
    }
  


}