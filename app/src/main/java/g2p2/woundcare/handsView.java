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
    Bitmap test,test_2,test_3,test_4,test_5,test_6;
    static int what = 0;

    public handsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        test = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gauze),200,200,true);
        test_2= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.tweez),200,200,true);
        test_3= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.finger),200,200,true);
        test_4= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ketchup),200,200,true);
        test_5= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.primary_dressing),200,200,true);
        test_6= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.absorbant_dressing),200,200,true);
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
            case 3: canvas.drawBitmap(test_3,0,0,myStyle);
                break;
            case 4: canvas.drawBitmap(test_4,0,0,myStyle);
                break;
            case 5: canvas.drawBitmap(test_5,0,0,myStyle);
                break;
            case 6: canvas.drawBitmap(test_6,0,0,myStyle);
                break;
        }
        //canvas.drawBitmap(test,0,0,myStyle);
    }
}
