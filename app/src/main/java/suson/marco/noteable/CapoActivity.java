package suson.marco.noteable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RadioButton;

/**
 * Created by Marco on 3/1/2016.
 */
public class CapoActivity extends AppCompatActivity {
    private RadioButton currentChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_capo);

        currentChoice = (RadioButton) findViewById(R.id.no_capo);
        currentChoice.toggle();
    }
}
