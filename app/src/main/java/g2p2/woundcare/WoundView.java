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
    Bitmap wound, woundAplha, woundClean, woundClean2;

    Paint coolStyle,masked;
    float X,Y,startX,startY,stopX,stopY,currentX,currentY,paintX,paintY;
    ArrayList<Float> paintXs = new ArrayList<Float>();
    ArrayList<Float> paintYs = new ArrayList<Float>();


    public WoundView(Context c, AttributeSet as){
        super(c,as);
        wound = BitmapFactory.decodeResource(getResources(),R.drawable.maxresdefault);
        woundClean = BitmapFactory.decodeResource(getResources(),R.drawable.maxresdefault_clean);
        coolStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        masked = new Paint(Paint.ANTI_ALIAS_FLAG);
        X=0;
        Y=0;
        coolStyle.setColor(Color.BLACK);
        coolStyle.setStrokeWidth(90);
        masked.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        bitMaker();
    }

    @Override
    protected void onDraw(Canvas c){



        c.drawBitmap(wound,X,Y,coolStyle); //draws the wound

        c.drawBitmap(woundAplha,X,Y,coolStyle); //draws whatever is clean on top.
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {


        if(handsView.what==0) {             //the following code moves the wound image.
            moveTool(e);
            LoggingAndUpload.info("Moved around using move tool");
        }
        else if(handsView.what==1){
            gauzeTool(e);
            LoggingAndUpload.info("Used gauze tool");

        }
        else if(handsView.what==2){
            //put method here.
            LoggingAndUpload.info("Used tool #2");
        }
        else if(handsView.what==3){
            //put method here.
            LoggingAndUpload.info("Used tool#3");
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
}
