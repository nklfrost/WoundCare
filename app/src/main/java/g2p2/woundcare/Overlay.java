package g2p2.woundcare;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Niklas on 5/5/2017.
 */

public class Overlay extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //getWindow().getDecorView().setAlpha((float)0.1);
/*
        DisplayMetrics a = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(a);
        */

        //getWindow().setLayout(100,100);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switch (MainActivity.phase) {
            case 0:
                //setContentView(R.layout.);
                break;
            case 1:
                setContentView(R.layout.newturnoverlay);

                float percentFibrin = MainActivity.fibrin*100;

                TextView fibrinScore = (TextView) findViewById(R.id.fibrinscore);
                fibrinScore.setText("Amount of fibrin left after cleaning: "+(int) percentFibrin+"%");
                if (percentFibrin<10){fibrinScore.setTextColor(Color.GREEN);}
                else{fibrinScore.setTextColor(Color.RED);}
TextView woundDepth=(TextView) findViewById(R.id.woundSizeAssesment);
                woundDepth.setText("You assesed the wound to be"+EvalActivity.sliderValue+" cm.");
                TextView overAll = (TextView) findViewById(R.id.overallAssesment);
                if(percentFibrin<10){
                    overAll.setText("You treated the wound correctly!");
                    overAll.setTextColor(Color.GREEN);
                    MainActivity.level++;

                }
                else {
                    overAll.setText("You didn't treat the wound correctly.");
                    overAll.setTextColor(Color.RED);
                }
                break;
            case 2:
                setContentView(R.layout.phase1overlay);
                break;
            case 3:
                setContentView(R.layout.phase2overlay);
                break;
        }

        TextView header = (TextView) findViewById(R.id.titleinOver);
        header.setTextColor(Color.rgb(0,255,187));
        header.setTextSize(40);

        Button exit = (Button) findViewById(R.id.exitoverlay);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });

        }
}
