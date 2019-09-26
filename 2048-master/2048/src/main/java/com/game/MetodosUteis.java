package com.game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class MetodosUteis {
    private MetodosUteis(){}
    
    public static int getMensagemBlocoLarg(String mensagem,Font fonte,Graphics2D g){
        g.setFont(fonte);
        Rectangle2D medidas = g.getFontMetrics().getStringBounds(mensagem, g);
        return(int)medidas.getWidth();
    }
    public static int getMensagemBlocoAlt(String mensagem,Font fonte,Graphics2D g){
        g.setFont(fonte);
        if(mensagem.length()== 0)return 0;
        TextLayout tl = new TextLayout(mensagem,fonte,g.getFontRenderContext());
        return(int)tl.getBounds().getHeight();
    }
    
}
