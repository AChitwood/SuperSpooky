package com.example.andrew.game_tutorial;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Andrew on 5/20/2015.
 */
public class MainThread extends Thread{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    //constructor, works like copy constructor
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        //this refers to the values being constructed, the values on the right side of = are passed in parameters
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        //how many milliseconds you want each iteration through the loop to take
        long targetTime = 1000/FPS;
        while(running){
            startTime = System.nanoTime();
            canvas = null;

        //try locking canvas for pixel editing
        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder)
            {
                this.gamePanel.update();
                this.gamePanel.draw(canvas);
            }}catch(Exception e) {}
            finally{
            if(canvas != null)
            {
                try{surfaceHolder.unlockCanvasAndPost(canvas);}catch(Exception e){e.printStackTrace();};
            }
        }



            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            }catch(Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b){
        running = b;
    }
}
