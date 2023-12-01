package com.litao.train.business.config;

import com.google.code.kaptcha.BackgroundProducer;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class KaptchaNoBackground extends Configuration implements BackgroundProducer {
    public KaptchaNoBackground(){

    }

    @Override
    public BufferedImage addBackground(BufferedImage bufferedImage) {
        int width= bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage image=new BufferedImage(width,height,1);
        Graphics2D graph=(Graphics2D)image.getGraphics();
        graph.fill(new Rectangle2D.Double(0.0D, 0.0D, (double) width, (double) height));
        graph.drawImage(bufferedImage,0,0,null);
        return image;
    }


    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        return new AppConfigurationEntry[0];
    }
}
