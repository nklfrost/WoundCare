package g2p2.woundcare;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static g2p2.woundcare.R.id.activity_main;
import static g2p2.woundcare.R.id.view;


public class MainActivity extends AppCompatActivity {
    public static int level=1;
    public static int phase=1;
    public static int turn=0;
    public static float fibrin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        final TextView tweeze=(TextView) findViewById(R.id.tweezText);
       final TextView watertxt=(TextView) findViewById(R.id.waterText);
final LoggingAndUpload upload=new LoggingAndUpload();
//Observation tools
        final Button tweezer = (Button) findViewById(R.id.button1); //Tweezer
        tweezer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==2){
                    handsView.what=0;
                    tweeze.setText("");
                    LoggingAndUpload.info("disabled tweezers,");
                } else {
                    handsView.what=2;
                    switch (MainActivity.level) {
                        case 1: tweeze.setText("4 CM");
                            break;
                        case 2: tweeze.setText("3 CM");
                            break;
                        case 3: tweeze.setText("2 CM");
                            break;
                        case 4: tweeze.setText("1 CM");
                            break;
                        case 5: tweeze.setText("0 CM");
                            break;
                    }
                    LoggingAndUpload.info("enabled tweezers,");
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        final Button water = (Button) findViewById(R.id.button2); //Check for water
        water.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==22){
                    handsView.what=0;
                    watertxt.setText("");
                } else handsView.what=22;
                switch (MainActivity.level) {
                    case 1:
                        watertxt.setText("MOIST");
                        break;
                    case 2:
                        watertxt.setText("MOIST");
                        break;
                    case 3:
                        watertxt.setText("DRY");
                        break;
                    case 4:
                        watertxt.setText("DRY");
                        break;
                    case 5:
                        watertxt.setText("DRY");
                        break;
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        //Cleaning tool
        final Button gauze = (Button) findViewById(R.id.button3); //Gauze
        gauze.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==1){
                    handsView.what=0;
                    LoggingAndUpload.info("disabled gauze tool,");
                } else {
                    handsView.what = 1;
                    LoggingAndUpload.info("enabled gauze tool,");
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

//Bandaging tools
        final Button zinc = (Button) findViewById(R.id.button4); //Zinc
        zinc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==21){
                    handsView.what=0;
                } else handsView.what=21;
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        final Button absorb = (Button) findViewById(R.id.button5); //Absorbant dressing
        absorb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==3){
                    handsView.what=0;
                } else handsView.what=3;
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        final Button moisture = (Button) findViewById(R.id.button6); //Moisturizing dressing
        moisture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==5){
                    handsView.what=0;
                } else handsView.what=5;
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

        final Button next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phase<3){phase++;}
                else{phase=1;
                    turn++;
                    TextView days = (TextView) findViewById(R.id.days);
                    days.setText(""+turn*3);}
                TextView obsTex = (TextView) findViewById(R.id.observationText);
                TextView cleTex = (TextView) findViewById(R.id.cleaningText);
                TextView bandTex = (TextView) findViewById(R.id.bandagingText);

                switch (phase){
                    case 1:

                        findViewById(R.id.phase1Buttons).setVisibility(View.VISIBLE);
                        findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
                            findViewById(R.id.phase3Buttons).setVisibility(View.GONE);
                            obsTex.setBackground(new ColorDrawable(Color.GRAY));
                            cleTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            bandTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                        final WoundView a = (WoundView) findViewById(R.id.view2);
                        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        final Runnable waitASec = new Runnable() {
                            @Override
                            public void run() {
                                a.reset();
                            }
                        };
                        scheduler.schedule(waitASec,100,TimeUnit.MILLISECONDS);
                            fibrin = a.howMuchFibrin();

                            break;
                    case 2: Intent i=new Intent(getApplicationContext(), EvalActivity.class);
                        startActivity(i);
                        findViewById(R.id.phase1Buttons).setVisibility(View.GONE);
                        findViewById(R.id.phase2Buttons).setVisibility(View.VISIBLE);
                            findViewById(R.id.phase3Buttons).setVisibility(View.GONE);
                            obsTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            cleTex.setBackground(new ColorDrawable(Color.GRAY));
                            bandTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            break;
                    case 3: findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
                        findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
                            findViewById(R.id.phase3Buttons).setVisibility(View.VISIBLE);
                            obsTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            cleTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            bandTex.setBackground(new ColorDrawable(Color.GRAY));

                            break;
                }
                handsView.what=0;
                findViewById(R.id.view).postInvalidate();
                findViewById(R.id.view2).postInvalidate();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
