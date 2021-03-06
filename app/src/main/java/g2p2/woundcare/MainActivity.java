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

final LoggingAndUpload upload=new LoggingAndUpload();

        final Button button = (Button) findViewById(R.id.button2); //Gauze
        button.setOnClickListener(new View.OnClickListener() {
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

        final Button button2 = (Button) findViewById(R.id.button6); //Tweezer
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==2){
                    handsView.what=0;
                    LoggingAndUpload.info("disabled tool#2,");
                } else {
                    handsView.what=2;
                    LoggingAndUpload.info("enabled tool#2,");
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

        final Button compr1 = (Button) findViewById(R.id.button7); //Compression
        compr1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==5){
                    handsView.what=0;
                } else handsView.what=5;
                WoundView a = (WoundView) findViewById(R.id.view2);
                a.compression();
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        final Button zinc = (Button) findViewById(R.id.button3); //Zinc
        zinc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==21){
                    handsView.what=0;
                } else handsView.what=21;
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
        final Button water = (Button) findViewById(R.id.button3); //Check for water
        water.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==22){
                    handsView.what=0;
                } else handsView.what=22;
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

        final Button UPLOADTEST = (Button) findViewById(R.id.button14);
        UPLOADTEST.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                upload.start();
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
                    case 1: findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
                            findViewById(R.id.phase3Buttons).setVisibility(View.GONE);
                            obsTex.setBackground(new ColorDrawable(Color.GRAY));
                            cleTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            bandTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            final WoundView a = (WoundView) findViewById(R.id.view2);
                            fibrin = a.howMuchFibrin();
                            startActivity(new Intent(getApplicationContext(),Overlay.class));
                            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                            final Runnable waitASec = new Runnable() {
                                @Override
                                public void run() {
                                    a.reset();
                                }
                            };
                            scheduler.schedule(waitASec,100,TimeUnit.MILLISECONDS);

                            break;
                    case 2: Intent i=new Intent(getApplicationContext(), EvalActivity.class);
                        startActivity(i);
                        findViewById(R.id.phase2Buttons).setVisibility(View.VISIBLE);
                            findViewById(R.id.phase3Buttons).setVisibility(View.GONE);
                            obsTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            cleTex.setBackground(new ColorDrawable(Color.GRAY));
                            bandTex.setBackground(new ColorDrawable(Color.TRANSPARENT));
                            break;
                    case 3: findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
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
