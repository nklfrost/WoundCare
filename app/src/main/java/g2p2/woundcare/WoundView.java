package g2p2.woundcare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Niklas on 4/7/2017.
 */

public class WoundView extends View {
    Bitmap wound, woundAlpha, woundClean, woundClean2,fibrin, woundAlpha2;

    Paint coolStyle,masked,zincStyle;
    float   X,Y,startX,startY,stopX,stopY,currentX,currentY,paintX,paintY,currentTouchX,currentTouchY, whatLevelDoIThinkItIs;
    ArrayList<Float> paintXs = new ArrayList<Float>();
    ArrayList<Float> paintYs = new ArrayList<Float>();
    boolean wetWound;


    public WoundView(Context c, AttributeSet as){
        super(c,as);
        whatLevelDoIThinkItIs=1;
        LoggingAndUpload.Launch(c);

        wound = BitmapFactory.decodeResource(getResources(), R.drawable.wound);

        fibrin= BitmapFactory.decodeResource(getResources(),R.drawable.fibirin);
        woundClean = BitmapFactory.decodeResource(getResources(),R.drawable.maxresdefault_clean);
        coolStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        masked = new Paint(Paint.ANTI_ALIAS_FLAG);
        X = -300;
        Y = -300;
        coolStyle.setColor(Color.BLACK);
        coolStyle.setStrokeWidth(90);
        masked.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));


        bitMaker();

    }

    @Override
    protected void onDraw(Canvas c){
        if(whatLevelDoIThinkItIs!=MainActivity.level){
            whatLevelDoIThinkItIs=MainActivity.level;
            this.reset();
        }

        else { // normal view
            c.drawBitmap(wound, X, Y, coolStyle); //draws the wound
            c.drawBitmap(woundAlpha, X, Y, coolStyle); //draws whatever is clean on top.
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        currentTouchX=e.getX();
        currentTouchY=e.getY();

        if(handsView.what==0) {             //the following code moves the wound image.
            moveTool(e);
        }
        //observation
        //cleaning
        else if(handsView.what==1){//gauze
            gauzeTool(e);

        }
        //bandaging
        else if(handsView.what==21)//zinc
        {
            zincCremeTool(e);//this is the zinc cream
        }





        return true;
    }
    //MOVE
    public void moveTool(MotionEvent e){
        if (e.getAction() == 0) {
            if (X != currentX) {
                currentX = X;
                currentY = Y;
            }
            startX = e.getX();
            startY = e.getY();
        } else {
            stopX = currentX + e.getX();
            stopY = currentY + e.getY();
            X = stopX - startX;
            Y = stopY - startY;
        }

        this.invalidate(); //refreshes the view ("this" view).
    }

    //GAUZE
    public void gauzeTool(MotionEvent e){
        if (e.getAction() == 0){ // whenever the hands are put on the screen, it makes a 0,0 vector so the program knows that it shouldn't draw it
            paintXs.add((float) 0);
            paintYs.add((float) 0);
        }

        else if (paintX!=e.getX() | paintY!=e.getY()){
            paintXs.add(e.getX()-X);
            paintYs.add(e.getY()-Y);
        }
        bitMaker();
        this.invalidate(); //refreshes the view ("this" view).
    }

    void bitMaker(){ //for gauze.
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        woundAlpha = Bitmap.createBitmap(wound.getWidth(), wound.getHeight(), conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(woundAlpha);
        //canvas.drawBitmap(wound,0,0,coolStyle);
        for(int i=0;i<paintXs.size();i++){
            //canvas.drawCircle(paintXs.get(i),paintYs.get(i),50,coolStyle);
            if (i>1 && paintXs.get(i-1)!=0 && paintYs.get(i-1)!=0 && paintXs.get(i)!=0 && paintYs.get(i)!=0){
                canvas.drawLine(paintXs.get(i-1),paintYs.get(i-1),paintXs.get(i),paintYs.get(i),coolStyle);
            }
        }
        canvas.drawBitmap(fibrin,0,0,masked);//wow
    }
    //ZINC
    public void zincCremeTool(MotionEvent e){
        if (e.getAction() == 0){ // whenever the hands are put on the screen, it makes a 0,0 vector so the program knows that it shouldn't draw it
            paintXs.add((float) 0);
            paintYs.add((float) 0);
        }
        else if (paintX!=e.getX() | paintY!=e.getY()){
            paintXs.add(e.getX()-X);
            paintYs.add(e.getY()-Y);
        }
        zincBitMaker();
        this.invalidate(); //refreshes the view ("this" view).
    }
    void zincBitMaker()//for zinc.
    {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        woundAlpha2 = Bitmap.createBitmap(wound.getWidth(), wound.getHeight(), conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(woundAlpha2);
        //canvas.drawBitmap(wound,0,0,coolStyle);
        for(int i=0;i<paintXs.size();i++){
            //canvas.drawCircle(paintXs.get(i),paintYs.get(i),50,coolStyle);
            if (i>1 && paintXs.get(i-1)!=0 && paintYs.get(i-1)!=0 && paintXs.get(i)!=0 && paintYs.get(i)!=0){
                canvas.drawLine(paintXs.get(i-1),paintYs.get(i-1),paintXs.get(i),paintYs.get(i),zincStyle);
            }
        }
        //canvas.drawBitmap(woundClean,0,0,masked);//wow
    }




    public float howMuchFibrin(){
        int skipFactor=10;
        float howBig=0;
        float howMuchAlphaOriginal=0;
        float howMuchAlphaModified=0;
        for (int i=0;i<wound.getHeight();i=i+skipFactor){
            for (int ii=0;ii<wound.getWidth();ii=ii+skipFactor){
                howBig++;
                if (Color.alpha(fibrin.getPixel(ii,i))==0){
                    howMuchAlphaOriginal++;
                }
            }
        }
        for (int i=0;i<wound.getHeight();i=i+skipFactor){
            for (int ii=0;ii<wound.getWidth();ii=ii+skipFactor){
                if (Color.alpha(woundAlpha.getPixel(ii,i))==0){
                    howMuchAlphaModified++;
                }
            }
        }
        System.out.println(howMuchAlphaModified);

        float result= (howBig-howMuchAlphaModified) / (howBig-howMuchAlphaOriginal)  ;

        return result;
    } // returns how much fibrin there is left in a % of the original amount


   public void reset(){
        paintXs.clear();
        paintYs.clear();
        X=-300;
        Y=-300;
        switch (MainActivity.level) {
            case 1:
                wound = BitmapFactory.decodeResource(getResources(),R.drawable.wound);
                fibrin = BitmapFactory.decodeResource(getResources(),R.drawable.fibirin);
                break;
            case 2:
                wound = BitmapFactory.decodeResource(getResources(),R.drawable.wound_lvl2);
                fibrin = BitmapFactory.decodeResource(getResources(),R.drawable.fibrin_lvl2);
                break;
            case 3:
                wound = BitmapFactory.decodeResource(getResources(),R.drawable.wound_lvl3);
                fibrin = BitmapFactory.decodeResource(getResources(),R.drawable.fibrin_lvl3);
                break;
            case 4:
                wound = BitmapFactory.decodeResource(getResources(),R.drawable.wound_lvl4);
                fibrin = BitmapFactory.decodeResource(getResources(),R.drawable.fibrin_lvl4);
                break;
            case 5:
                wound = BitmapFactory.decodeResource(getResources(),R.drawable.wound_lvl5);
                fibrin = BitmapFactory.decodeResource(getResources(),R.drawable.fibrin_lvl5);
                break;
        }


        bitMaker();
        this.invalidate();
    }
}
