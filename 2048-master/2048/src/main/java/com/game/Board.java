package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Board {
    public static final int LINE = 4;
    public static final int COL = 4;
    
    private final int iBlocos = 2;
    private Bloco[][] board;
    private boolean lose;
    private boolean winner;
    private BufferedImage boardJ;
    private BufferedImage endBoard;
    private int x;
    private int y;
    
    private boolean hasStarted;
    private static int pixel = 10;
    public static int BOARD_LARG = (COL + 1)*pixel+COL*Bloco.LARG;
    public static int BOARD_ALT = (LINE + 1)*pixel+LINE*Bloco.ALT;
    
    public Board(int x,int y){
        this.x = x;
        this.y = y;
        board = new Bloco[LINE][COL];
        boardJ = new BufferedImage(BOARD_LARG,BOARD_ALT,BufferedImage.TYPE_INT_RGB);
        endBoard = new BufferedImage(BOARD_LARG,BOARD_ALT,BufferedImage.TYPE_INT_RGB);
        
        printBoard();
        start();
        
    }
    private void printBoard(){
        Graphics2D g = (Graphics2D)boardJ.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0,0,BOARD_LARG,BOARD_ALT);
        g.setColor(Color.lightGray);
        
        for(int line = 0;line < LINE; line++){
            for(int col = 0;col < COL; col++){
                int X = pixel + pixel*col + Bloco.LARG*col;
                int Y = pixel + pixel*line + Bloco.ALT*line;
                g.fillRoundRect(X,Y,Bloco.LARG,Bloco.ALT,Bloco.ARC_LARG,Bloco.ARC_ALT);
            }
        }
    }
    
    public void start(){
        for(int i = 0;i < iBlocos;i++){
            bRandom();
        }
    }
    
    private void bRandom(){
        Random random = new Random();
        boolean invalido = true;
        
        while(invalido){
            int spot = random.nextInt(LINE*COL);
            int line = spot/LINE;
            int col = spot%COL;
            Bloco gerado = board[line][col];
                if(gerado == null){
                    int val = random.nextInt(10)<9 ? 2 : 4; //90% de chance de 2 / 10% de 4
                    Bloco bloco = new Bloco(val,getBlocoX(col),getBlocoY(line));
                    board[line][col] = bloco;
                    invalido = false;
        }
    }
    }
        public int getBlocoX(int col){
            return pixel + col*Bloco.LARG + col*pixel;
        }
        public int getBlocoY(int line){
            return pixel + line*Bloco.ALT + line*pixel;
        }    
        
        
    
    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D)endBoard.getGraphics();
        g2d.drawImage(boardJ,0,0,null);
        
        for(int line = 0;line<LINE;line++){//Gera os blocos
            for(int col= 0;col<COL;col++){
                Bloco gerado = board[line][col];
                if(gerado == null)continue;
                   gerado.render(g2d);
            }
        }
        g.drawImage(endBoard,x,y,null);
        g2d.dispose();
    }
    
    public void update(){
        checkKeys();
        
        for(int line = 0;line<LINE;line++){
            for(int col = 0;col<COL;col++){
                Bloco gerado = board[line][col];
                  if(gerado == null)continue;
                  gerado.update();
                  resetPosicoes(gerado,line,col);
                  if(gerado.getValor_blocos() == 2048){
                      winner = true;
                  }  
        }
    }
    }
    
    private void resetPosicoes(Bloco gerado,int line,int col){
        if(gerado == null)return;
            int x = getBlocoX(col);
            int y = getBlocoY(line);
            
            int distX = gerado.getX() - x; // Pega X do bloco
            int distY = gerado.getY() - y; // Pega Y do bloco
            
            if(Math.abs(distX) < Bloco.Slide){
                gerado.setX(gerado.getX() - distX);//Tamanho do movimento do bloco em X
            }
            if(Math.abs(distY) < Bloco.Slide){
                gerado.setY(gerado.getY() - distY);//Tamanho do movimento do bloco em Y
            }            
            
            if(distX < 0){
                gerado.setX(gerado.getX() + Bloco.Slide);
            }
            if(distY < 0){
                gerado.setY(gerado.getY() + Bloco.Slide);
            }
            if(distX > 0){
                gerado.setX(gerado.getX() + Bloco.Slide);
            }
            if(distY > 0){
                gerado.setY(gerado.getY() + Bloco.Slide);
            }            
    }    
    
    private boolean move(int line,int col,int hozirontalD,int verticalD,Lados lado){
        boolean pMove = false;
        Bloco gerado = board[line][col];
        if(gerado == null)return false;
        boolean move = true;
        int newCOL = col;
        int newLINE = line;
        
        while(move){
            newCOL += hozirontalD;
            newLINE += verticalD;
            if(checarLimites(lado,newLINE,newCOL))break; //Preve a proxima posicao
            if(board[newLINE][newCOL] == null){//Se for null mexe os blocos e continua o loop
               board[newLINE][newCOL] = gerado;
               board[newLINE - verticalD][newCOL - hozirontalD] = null;
               board[newLINE][newCOL].setGoto(new Point(newLINE,newCOL));
               pMove = true;
            }else if(board[newLINE][newCOL].getValor_blocos() == gerado.getValor_blocos() && board[newLINE][newCOL].juntarB()){//Se nao puder combinar mais
                board[newLINE][newCOL].setJuntarB(false);
                board[newLINE][newCOL].setValor_blocos(board[newLINE][newCOL].getValor_blocos()*2);
                pMove = true;
                board[newLINE - verticalD][newCOL - hozirontalD] = null;
                board[newLINE][newCOL].setGoto(new Point(newLINE,newCOL));
             //   board[newLINE][newCOL].setAnimacao
            }
            else{
                move = false;
            }
                    
        }
        return pMove;
    }
    
    private boolean checarLimites(Lados lado,int line,int col){
        if(lado == Lados.ESQUERDA){
            return col < 0;
        }
        if(lado == Lados.DIREITA){
            return col > COL-1;
        }
        if(lado == Lados.CIMA){
            return line < 0;
        }
        if(lado == Lados.BAIXO){
            return line > LINE-1;
        }         
        return false;
    }
    
    private void moveB(Lados lado){
        boolean pMove = false;
        int horizontalD = 0;
        int verticalD = 0;
        
        if(lado == Lados.ESQUERDA){
            horizontalD = -1;
            for(int line = 0;line<LINE;line++){
                for(int col = 0;col<COL;col++){
                    if(!pMove){
                        pMove = move(line,col,horizontalD,verticalD,lado);
                }else move(line,col,horizontalD,verticalD,lado);
            }
        }
    }
       else if(lado == Lados.DIREITA){
            horizontalD = 1;
            for(int line = 0;line<LINE;line++){
                for(int col = COL-1;col>=0;col--){
                    if(!pMove){
                        pMove = move(line,col,horizontalD,verticalD,lado);
                }else move(line,col,horizontalD,verticalD,lado);
            }
        }
    }
       else if(lado == Lados.CIMA){
            verticalD = -1;
            for(int line = 0;line<LINE;line++){
                for(int col = 0;col<COL;col++){
                    if(!pMove){
                        pMove = move(line,col,horizontalD,verticalD,lado);
                }else move(line,col,horizontalD,verticalD,lado);
            }
        }
    }
       else if(lado == Lados.BAIXO){
            verticalD = 1;
            for(int line = LINE-1;line>=0;line--){
                for(int col = 0;col<COL;col++){
                    if(!pMove){
                        pMove = move(line,col,horizontalD,verticalD,lado);
                }else move(line,col,horizontalD,verticalD,lado);
            }
        }
    }else{
            System.out.println(lado+"Nao eh permitido");
       }

            for(int line = 0;line<LINE;line++){
                for(int col = 0;col<COL;col++){
                    Bloco gerado = board[line][col];
                    if(gerado == null)continue;
                    gerado.setJuntarB(true);
                }
                }        
        if(pMove){
            bRandom();
            checaPerdeu();
            
        }    
        
    }
    private void checaPerdeu(){
        for(int line = 0;line<LINE;line++){
            for(int col = 0;col<COL;col++){
                if(board[line][col] == null)return;
                if(checaEmVolta(line,col,board[line][col])){
                    return;
                }  
            }
            }
        lose = true;
        //setHighScore(score);
    }
    private boolean checaEmVolta(int line,int col,Bloco gerado){
        if(line > 0){//Checa para cima
            Bloco aux = board[line - 1][col];
            if(aux == null)return true;
            if(gerado.getValor_blocos() == aux.getValor_blocos())return true;
        }
        if(line < LINE - 1){//Checa para Baixo
            Bloco aux = board[line + 1][col];
            if(aux == null)return true;
            if(gerado.getValor_blocos() == aux.getValor_blocos())return true;
        }
        if(col > 0){//Checa para Esquerda
            Bloco aux = board[line][col - 1];
            if(aux == null)return true;
            if(gerado.getValor_blocos() == aux.getValor_blocos())return true;
        }
        if(col < COL - 1){//Checa para diretia
            Bloco aux = board[line][col + 1];
            if(aux == null)return true;
            if(gerado.getValor_blocos() == aux.getValor_blocos())return true;
        }  
        return false;
    }
    
    private void checkKeys(){
        if(Controle.digita(KeyEvent.VK_LEFT)){//Move tudo
            moveB(Lados.ESQUERDA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_A)){//Move tudo
            moveB(Lados.ESQUERDA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_RIGHT)){//Move tudo
            moveB(Lados.DIREITA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_D)){//Move tudo
            moveB(Lados.DIREITA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_UP)){//Move tudo
            moveB(Lados.CIMA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_W)){//Move tudo
            moveB(Lados.CIMA);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_DOWN)){//Move tudo
            moveB(Lados.BAIXO);
            if(!hasStarted)hasStarted = true;
        }
        if(Controle.digita(KeyEvent.VK_S)){//Move tudo
            moveB(Lados.BAIXO);
            if(!hasStarted)hasStarted = true;
        }
                               
    }
}
