package g2p2.woundcare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Niklas on 4/7/2017.
 */

public class WoundView extends View {
    Bitmap wound, woundAplha, woundClean, leg;
    boolean compressionView = false, compButton = false;

    Paint coolStyle,masked,bandageStyle;
    float   X,Y,startX,startY,stopX,stopY,currentX,currentY,paintX,paintY,currentTouchX,currentTouchY,
            bandageHowFar;
    ArrayList<Float> paintXs = new ArrayList<Float>();
    ArrayList<Float> paintYs = new ArrayList<Float>();

    ArrayList<Integer> bandagePlacementXs = new ArrayList<Integer>();
    ArrayList<Integer> bandagePlacementYs = new ArrayList<Integer>();


    public WoundView(Context c, AttributeSet as){
        super(c,as);
//this is a change.
        LoggingAndUpload.Launch(c);

            wound = BitmapFactory.decodeResource(getResources(), R.drawable.maxresdefault);
            woundClean = BitmapFactory.decodeResource(getResources(), R.drawable.maxresdefault_clean);
            leg = BitmapFactory.decodeResource(getResources(), R.drawable.leg);

            coolStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
            masked = new Paint(Paint.ANTI_ALIAS_FLAG);
        bandageStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        bandageStyle.setColor(Color.argb(200,200,200,200));
        bandageStyle.setStrokeWidth(80);
            X = 0;
            Y = 0;
            coolStyle.setColor(Color.BLACK);
            coolStyle.setStrokeWidth(90);
            masked.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        bandageHowFar=1;
        makeBandageGuide();

            bitMaker();

    }

    @Override
    protected void onDraw(Canvas c){
        if (compressionView){
            drawCompression(c);

        }
        else { // normal view
            c.drawBitmap(wound, X, Y, coolStyle); //draws the wound
            c.drawBitmap(woundAplha, X, Y, coolStyle); //draws whatever is clean on top.
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (handsView.what!=5){compressionView= false;}
        currentTouchX=e.getX();
        currentTouchY=e.getY();

        if(handsView.what==0) {             //the following code moves the wound image.
            moveTool(e);
        }
        else if(handsView.what==1){
            gauzeTool(e);

        }
        else if(handsView.what==2){
            //put method here.
        }
        else if(handsView.what==3){
            //put method here.
        }
        else if(handsView.what==5){
            if (e.getX()>10 && e.getX()<110 && e.getY()<160 && e.getY()>60){
                System.out.println("teee");
                compButton = true;
            }
            else if(compButton){
                //System.out.println(e.getX()+","+e.getY());

            }
            else {compression();}
        }
        return true;
    }
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
        woundAplha = Bitmap.createBitmap(wound.getWidth(), wound.getHeight(), conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(woundAplha);
        //canvas.drawBitmap(wound,0,0,coolStyle);
        for(int i=0;i<paintXs.size();i++){
            //canvas.drawCircle(paintXs.get(i),paintYs.get(i),50,coolStyle);
            if (i>1 && paintXs.get(i-1)!=0 && paintYs.get(i-1)!=0 && paintXs.get(i)!=0 && paintYs.get(i)!=0){
                canvas.drawLine(paintXs.get(i-1),paintYs.get(i-1),paintXs.get(i),paintYs.get(i),coolStyle);
            }
        }
        canvas.drawBitmap(woundClean,0,0,masked);//wow
    }
    public void compression(){
        compressionView = true;
        this.invalidate();
    }
    public void drawCompression(Canvas c){
        c.drawBitmap(leg, -60,-90,coolStyle);
        c.drawRect(10,60,110,160,coolStyle);
        if (compButton){
            for(int i=0;i<bandageHowFar;i++) {
                if (i < bandageHowFar) {
                    c.drawLine(bandagePlacementXs.get(i), bandagePlacementYs.get(i), bandagePlacementXs.get(i+1), bandagePlacementYs.get(i+1), bandageStyle);
                }
            }
            c.drawLine(bandagePlacementXs.get((int)bandageHowFar), bandagePlacementYs.get((int)bandageHowFar), currentTouchX, currentTouchY, bandageStyle);
            //System.out.println(currentTouchX + ", "+currentTouchY);
            if(bandageHowFar+1<bandagePlacementYs.size()) {
                if (bandagePlacementYs.get((int) bandageHowFar) < bandagePlacementYs.get((int) bandageHowFar + 1)) {
                    if (bandagePlacementXs.get((int) bandageHowFar+1) > currentTouchX) {
                        bandageHowFar++;
                    }
                }

                else if(bandagePlacementXs.get((int) bandageHowFar) > bandagePlacementXs.get((int) bandageHowFar + 1)) {
                    if (bandagePlacementYs.get((int) bandageHowFar+1) > currentTouchY) {
                        bandageHowFar++;
                    }
                }
                else if (bandagePlacementYs.get((int) bandageHowFar) > bandagePlacementYs.get((int) bandageHowFar + 1)) {
                    if (bandagePlacementXs.get((int) bandageHowFar+1) < currentTouchX) {
                        bandageHowFar++;
                    }
                }
                else if(bandagePlacementXs.get((int) bandageHowFar) < bandagePlacementXs.get((int) bandageHowFar + 1)) {
                    if (bandagePlacementYs.get((int) bandageHowFar+1) < currentTouchY) {
                        bandageHowFar++;
                    }
                }
            }
        }
        this.invalidate();
    }

    private void makeBandageGuide(){
        bandagePlacementXs.add(334); //
        bandagePlacementYs.add(810); //point 0 ... needed

        //point 1
        bandagePlacementXs.add(334);
        bandagePlacementYs.add(810);

        //point 2
        bandagePlacementXs.add(510);
        bandagePlacementYs.add(810);

        //point 3
        bandagePlacementXs.add(510);
        bandagePlacementYs.add(900);

        //point 4
        bandagePlacementXs.add(360);
        bandagePlacementYs.add(900);

        //point 5
        bandagePlacementXs.add(360);
        bandagePlacementYs.add(863);

        //point 6
        bandagePlacementXs.add(360);
        bandagePlacementYs.add(780);

        //point 7
        bandagePlacementXs.add(550);
        bandagePlacementYs.add(780);

        //test


        //bandagePlacementXs.add(550);
        //bandagePlacementYs.add(780);
    }
}
