package g2p2.woundcare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Niklas on 4/7/2017.
 */

public class WoundView extends View {
    Bitmap wound;
    Paint coolStyle;
    float X,Y,startX,startY,stopX,stopY,currentX,currentY;
    int lasttime=0;
    public WoundView(Context c, AttributeSet as){
        super(c,as);
        wound = BitmapFactory.decodeResource(getResources(),R.drawable.maxresdefault);
        coolStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        X=0;
        Y=0;
        coolStyle.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas c){
        c.drawBitmap(wound,X,Y,coolStyle);
        c.drawCircle(X,Y,10,coolStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (lasttime - System.currentTimeMillis() < 100) {
            //Trying to do the move event thing...

            System.out.println(e.getAction());
            if (e.getAction() == 0) {
                if (X != currentX) {
                    currentX = X;
                    currentY = Y;
                }
                startX = currentX + e.getX();
                startY = currentY + e.getY();
            } else {
                //System.out.println("action_down: "+MotionEvent.ACTION_DOWN);
                stopX = currentX + e.getX();
                stopY = currentY + e.getY();
                X = stopX - startX;
                Y = stopY - startY;
            }
        /*
        if(e.getAction()==0){
            currentY = Y;
            currentX = X;
        }
        */
            System.out.println(startX - X);

            this.invalidate();

            lasttime=(int)System.currentTimeMillis();
        }
        return true;
    }
}
