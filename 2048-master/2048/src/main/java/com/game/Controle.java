
package com.game;

import java.awt.event.KeyEvent;


public class Controle {
    public static boolean[]apertado = new boolean[256];
    public static boolean[]semAcao = new boolean[256];
    
            
    private Controle(){}
    //Dps de apertar a tecla e fazer a acao ele volta as teclas no estado 0
    public static void update(){
        for(int i = 0;i < 8;i++){
            if(i==0)semAcao[KeyEvent.VK_LEFT] = apertado[KeyEvent.VK_LEFT];
            if(i==1)semAcao[KeyEvent.VK_RIGHT] = apertado[KeyEvent.VK_RIGHT];
            if(i==2)semAcao[KeyEvent.VK_UP] = apertado[KeyEvent.VK_UP];
            if(i==3)semAcao[KeyEvent.VK_DOWN] = apertado[KeyEvent.VK_DOWN];
            if(i==4)semAcao[KeyEvent.VK_A] = apertado[KeyEvent.VK_A];
            if(i==5)semAcao[KeyEvent.VK_D] = apertado[KeyEvent.VK_D];
            if(i==6)semAcao[KeyEvent.VK_W] = apertado[KeyEvent.VK_W];
            if(i==7)semAcao[KeyEvent.VK_S] = apertado[KeyEvent.VK_S];
        }
    }
    
    public static void keyPressed(KeyEvent e){
        apertado[e.getKeyCode()] = true;
    }
    public static void keyReleased(KeyEvent e){
        apertado[e.getKeyCode()] = false;
    }
    //Para manter o comando com apenas uma tecla de cada vez
    public static boolean digita(int keyEvent){
        return !apertado[keyEvent]&&semAcao[keyEvent];
    }
}
