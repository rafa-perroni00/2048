
package com.game;

import javax.swing.JFrame;


public class Init {
        public static void main(String[]args){
            Jogo game = new Jogo();
            JFrame window = new JFrame("2048");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.add(game);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            
            game.start();
        }
}
