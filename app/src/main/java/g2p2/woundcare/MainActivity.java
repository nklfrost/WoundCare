package g2p2.woundcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import static g2p2.woundcare.R.id.view;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);



        final Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==1){
                    handsView.what=0;
                    LoggingAndUpload.info("disabled gauze tool");
                } else {
                    handsView.what = 1;
                    LoggingAndUpload.info("enabled gauze tool");
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

        final Button button2 = (Button) findViewById(R.id.button6);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(handsView.what==2){
                    handsView.what=0;
                    LoggingAndUpload.info("disabled tool#2");
                } else {
                    handsView.what=2;
                    LoggingAndUpload.info("enabled tool#2");
                }
                findViewById(view).postInvalidate(); //same as handsView - just the actual instance
            }
        });

        final Button compr1 = (Button) findViewById(R.id.button7);
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

        final Button UPLOADTEST = (Button) findViewById(R.id.button5);
        UPLOADTEST.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoggingAndUpload.Upload();
            }
        });

        final SeekBar phaseSelector = (SeekBar) findViewById(R.id.seekBar);
        phaseSelector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                handsView.phase = progress;
                if (progress == 1) {
                    findViewById(R.id.phase1Buttons).setVisibility(View.GONE);
                    findViewById(R.id.phase2Buttons).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.phase1Buttons).setVisibility(View.VISIBLE);
                    findViewById(R.id.phase2Buttons).setVisibility(View.GONE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
