package g2p2.woundcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        final TextView credits = (TextView) findViewById(R.id.textView2);
        credits.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), CreditsActivity.class);
                startActivity(i2);
            }
        });

    }

//    public void sendMessage(View view){
//        do something when pressing button
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
}
