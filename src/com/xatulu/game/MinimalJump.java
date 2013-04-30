package com.xatulu.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 03:59
 */
public class MinimalJump extends Canvas implements Runnable {
    public static final long serialVersionUID = 1L;
    public static final int width = 640; //Breite des Fenster
    public static final int height = width / 16 * 9;
    public static final int scale = 2; //Skalierung
    public static final double tickcap = 60;//Maximale Ticks
    public static final String title = "MinimalJump";

    private boolean running = false;
    private final JFrame frame; //Objekt für Fenster
    private Thread thread; //Objekt für Threading

    private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//Erzeugt ein Buffered Image image
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();//Macht die Daten modifizierbar

    private MinimalJump() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size); //Canvas Größe

        frame = new JFrame();
    }

    synchronized void start() {
        running = true;
        thread = new Thread(this, "MinimalJump");
        thread.start();
    }

    synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / tickcap;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | " + "Updates: " + ticks + " ticks, Frames: " + frames + " fps");
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x162486;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    private void tick() {
    }

    public static void main(String[] args) {
        MinimalJump game = new MinimalJump();
        game.frame.setResizable(false);
        game.frame.setTitle(MinimalJump.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

}