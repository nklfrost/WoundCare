package g2p2.woundcare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Niklas on 4/5/2017.
 */

public class handsView extends View {
    Bitmap test,test_2;
    static int what = 0;
    static int phase = 0; //what do we use this for?

    public handsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        test = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.test1),200,200,true);
        test_2= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.handstemp),200,200,true);

                //BitmapFactory.decodeResource(getResources(),R.drawable.handstemp);
    }

    @Override
    public void onDraw(Canvas canvas){
        Paint myStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        //myStyle.setColor(0xff101010);
        //canvas.drawColor(Color.RED);
        myStyle.setColor(Color.argb(255,65,176,176));
        myStyle.setStrokeWidth(30);
        canvas.drawLine(0,0,this.getWidth(),0,myStyle);
        canvas.drawLine(this.getWidth(),0,this.getWidth(),this.getHeight(),myStyle);
        canvas.drawLine(this.getWidth(),this.getHeight(),0,this.getHeight(),myStyle);
        canvas.drawLine(0,this.getHeight(),0,0,myStyle);
        switch (what){
            case 0:
                break;
            case 1: canvas.drawBitmap(test,0,0,myStyle);
                break;
            case 2: canvas.drawBitmap(test_2,0,0,myStyle);
                break;
        }
        //canvas.drawBitmap(test,0,0,myStyle);
    }
}
