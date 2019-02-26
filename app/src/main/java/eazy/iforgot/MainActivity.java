package eazy.iforgot;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.appname);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Caminata-One-demo-FFP.ttf");
        textView.setTypeface(typeface);

        MyThread thread=new MyThread();
        thread.start();
    }

    class MyThread extends Thread{
        public void run(){

            try {
                Thread.sleep(3000);

                Intent intent=new Intent(MainActivity.this,SettingsScreen.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    };
}
