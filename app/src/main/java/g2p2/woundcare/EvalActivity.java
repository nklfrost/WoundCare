package g2p2.woundcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class EvalActivity extends AppCompatActivity {
    static int sliderValue;
    static boolean switchState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_eval);

       final TextView txt=(TextView) findViewById(R.id.woundDepth);
    final Button button=(Button) findViewById(R.id.evalDone);
        final SeekBar slider1 = (SeekBar) findViewById(R.id.evalSlide1);
        slider1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar slider1, int progress, boolean fromUser) {
                txt.setText(progress+" cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
     button.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){

        sliderValue = slider1.getProgress();
        final Switch switcher = (Switch) findViewById(R.id.evalSwitch);
        switchState = switcher.isChecked();
            finish();
    }
    });
}
}
