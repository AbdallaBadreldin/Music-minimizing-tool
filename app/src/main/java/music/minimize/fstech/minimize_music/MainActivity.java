package music.minimize.fstech.minimize_music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button theButton = (Button) findViewById(R.id.theButton);
        Button theButton2 = (Button) findViewById(R.id.theButton2);
        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification.minimizemusic(getBaseContext());
                finish();
            }
        });
        theButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification.minimizemusic(getBaseContext());
                finish();
            }
        });
    }
}