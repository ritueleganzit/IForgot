package eazy.iforgot;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HelpScreen extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Caminata-One-demo-FFP.ttf");
        textView= (TextView) findViewById(R.id.appinfoappname);
        textView.setTypeface(typeface);
    }

}
