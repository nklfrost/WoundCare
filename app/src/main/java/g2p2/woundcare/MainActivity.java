package g2p2.woundcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        LoggingAndUpload.Launch();

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
                findViewById(R.id.view).postInvalidate(); //same as handsView - just the actual instance
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
                findViewById(R.id.view).postInvalidate(); //same as handsView - just the actual instance
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
                findViewById(R.id.view).postInvalidate(); //same as handsView - just the actual instance
            }
        });
    }
}
