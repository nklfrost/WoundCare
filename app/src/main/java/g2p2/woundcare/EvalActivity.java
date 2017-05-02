package g2p2.woundcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class EvalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_eval);
    final Button button=(Button) findViewById(R.id.evalDone);
     button.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
        final SeekBar slider1 = (SeekBar) findViewById(R.id.evalSlide1);
        int slider1value = slider1.getProgress();
        final SeekBar slider2 = (SeekBar) findViewById(R.id.evalSlide2);
        int slider2value = slider2.getProgress();
        final Switch switcher = (Switch) findViewById(R.id.evalSwitch);
        Boolean switchState = switcher.isChecked();
    }
    });
}
}
