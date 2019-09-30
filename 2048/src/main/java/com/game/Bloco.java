package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bloco {
    public static final int LARG = 100;
    public static final int ALT = 100;
    public static final int Slide = 30;
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
    
    private boolean sAnimacao = true;
    private double tBloco = 0.1;
    private BufferedImage sBloco;
    private boolean jAnimacao = false;
    private double tJuncao = 1.2;
    private BufferedImage bJunto;
    
    private boolean juntarB = true; //Precisa comecar true para a primeira juncao
    
    public Bloco(int valor_blocos,int x,int y){
        this.valor_blocos = valor_blocos;
        this.x = x;
        this.y = y;
        Goto = new Point(x,y);
        blocos = new BufferedImage(LARG, ALT, BufferedImage.TYPE_INT_ARGB);
        sBloco = new BufferedImage(LARG,ALT,BufferedImage.TYPE_INT_ARGB);
        bJunto = new BufferedImage(LARG*2,ALT*2,BufferedImage.TYPE_INT_ARGB);
                
        printaBloco();
    }
    
    private void printaBloco(){
      Graphics2D g = (Graphics2D)blocos.getGraphics();
      
      switch(valor_blocos){
          case 2:{
          fundo = new Color(233,233,233);
          string = new Color(0,0,0);
          break;
          }
      
          case 4:{
          fundo = new Color(230,218,171);
          string = new Color(0,0,0);
          break;
          }
          case 8:{
          fundo = new Color(247,157,61);
          string = new Color(255,255,255);
          break;
          }
          case 16:{
          fundo = new Color(242,128,7);
          string = new Color(255,255,255); 
          break;
          }
          case 32:{
          fundo = new Color(245,94,59);
          string = new Color(255,255,255);
          break;
          }
          case 64:{
          fundo = new Color(255,0,0);
          string = new Color(255,255,255); 
          break;
          }
          case 128:{
          fundo = new Color(233,222,132);
          string = new Color(255,255,255);
          break;
          }
          case 256:{
          fundo = new Color(246,232,115);
          string = new Color(255,255,255);
          break;
          }
          case 512:{
          fundo = new Color(245,228,85);
          string = new Color(255,255,255);
          break;
          }
          case 1024:{
          fundo = new Color(255,255,0);
          string = new Color(255,255,255); 
          break;
          }     
          case 2048:{
          fundo = new Color(0,255,0);
          string = new Color(255,255,255);
          break;
          }
          default:{
          fundo = new Color(0,0,0);
          string = new Color(255,255,255);
          break;
          }
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
    int blocoY = ALT/2 + MetodosUteis.getMensagemBlocoAlt(""+valor_blocos, fonte, g)/2;
    g.drawString(""+valor_blocos,blocoX,blocoY);
    g.dispose();
    }
    
    public void update(){//Todas as animacoes
        if(sAnimacao){
            AffineTransform transforma = new AffineTransform();//Transformacao de escala
            transforma.translate(LARG/2 - tBloco * LARG/2,ALT/2 - tBloco * ALT/2);
            transforma.scale(tBloco, tBloco);
            Graphics2D g2d = (Graphics2D)sBloco.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0,0,LARG,ALT);
            g2d.drawImage(blocos,transforma,null);
            tBloco += 0.1;
            g2d.dispose();
            if(tBloco>=1)
                sAnimacao = false;
        }else if(sAnimacao){
            AffineTransform transforma = new AffineTransform();//Transformacao de escala
            transforma.translate(LARG/2 - tJuncao * LARG/2,ALT/2 - tJuncao * ALT/2);
            transforma.scale(tJuncao, tJuncao);
            Graphics2D g2d = (Graphics2D)bJunto.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0,0,LARG,ALT);
            g2d.drawImage(blocos, transforma, null);
            tJuncao -= 0.05;
            g2d.dispose();
            if(tJuncao<=1)
                jAnimacao = false;           
            
        }
    }
    public void render(Graphics2D g){
        if(sAnimacao){
            g.drawImage(sBloco,x,y,null);
        }else if(jAnimacao){
            g.drawImage(bJunto,(int)(x + LARG/2 - tJuncao*LARG/2),
                               (int)(y + ALT/2 - tJuncao*ALT/2),null);
        }
        else{//Se nao tiver juncao
            g.drawImage(blocos,x,y,null);
        }
        
    }
    
    public int getValor_blocos(){
        return valor_blocos;
    } 

    public void setValor_blocos(int valor_blocos) {
        this.valor_blocos = valor_blocos;
        printaBloco();//Redesenha tudo
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isjAnimacao() {
        return jAnimacao;
    }

    public void setjAnimacao(boolean jAnimacao) {
        this.jAnimacao = jAnimacao;
    }
  


}