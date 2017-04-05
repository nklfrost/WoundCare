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
    Bitmap test;

    public handsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        test = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.handstemp),300,300,true);

                //BitmapFactory.decodeResource(getResources(),R.drawable.handstemp);
    }

    @Override
    public void onDraw(Canvas canvas){
        Paint myStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        //myStyle.setColor(0xff101010);
        //canvas.drawColor(Color.RED);
        myStyle.setColor(Color.rgb(65,176,176));
        canvas.drawBitmap(test,0,0,myStyle);
    }
}
