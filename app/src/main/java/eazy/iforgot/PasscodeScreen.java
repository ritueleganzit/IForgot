package eazy.iforgot;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import eazy.iforgot.Database.MyDBhelper;
import eazy.iforgot.Receivers.MyReceivers;


public class PasscodeScreen extends AppCompatActivity {
    TextView tvname;
    EditText edsilent,ednormal,ed_divert,ed_divertOff,getcontact;
    Button btnsave;
ImageButton img;

    Button btnclear;
    MyDBhelper myDBhelper;
    MyReceivers mMyReceivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passcode_screen);
        myDBhelper=new MyDBhelper(PasscodeScreen.this);
        btnclear= (Button) findViewById(R.id.clear);
        mMyReceivers=new MyReceivers();

        tvname = (TextView) findViewById(R.id.passcodeappname);
        img= (ImageButton) findViewById(R.id.edit);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Caminata-One-demo-FFP.ttf");
        tvname.setTypeface(face);
        edsilent=(EditText)findViewById(R.id.silentmode);
        ednormal=(EditText)findViewById(R.id.normalmode);
        getcontact=(EditText)findViewById(R.id.getcontact);


        ed_divert=(EditText)findViewById(R.id.diverton);
        ed_divertOff=(EditText)findViewById(R.id.divertoff);
        getData();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edsilent.setEnabled(true);
                ednormal.setEnabled(true);
                ed_divert.setEnabled(true);
                ed_divertOff.setEnabled(true);
                getcontact.setEnabled(true);


                edsilent.setInputType(InputType.TYPE_CLASS_TEXT);
                ednormal.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_divert.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_divertOff.setInputType(InputType.TYPE_CLASS_TEXT);
                getcontact.setInputType(InputType.TYPE_CLASS_TEXT);


            }
        });

btnclear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        myDBhelper.truncatedata();
        try {
            if (mMyReceivers != null) {
                unregisterReceiver(new MyReceivers());
            }
        } catch (IllegalArgumentException e) {
            Log.i("dddd","epicReciver is already unregistered");
            mMyReceivers = null;
        }


    }
});
btnsave=(Button)findViewById(R.id.save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=myDBhelper.getdata();
                if (c.getCount() > 0){
                    if (getcontact.getText().toString().matches(".*\\d.*")){
                        getcontact.setError("You cannot insert number format");
                    }
                    else {
                        myDBhelper.updatedata(edsilent.getText().toString().trim(),
                                ednormal.getText().toString().trim(),

                                getcontact.getText().toString().trim(),

                                ed_divert.getText().toString().trim(),
                                ed_divertOff.getText().toString().trim()
                        );
                        Toast.makeText(PasscodeScreen.this, "Data Saved..", Toast.LENGTH_SHORT).show();
                        finish();
                    }



                }else {

                    if (getcontact.getText().toString().matches(".*\\d.*")){
                        getcontact.setError("You cannot insert number format");
                    }else {
                        myDBhelper.insertData(edsilent.getText().toString().trim(),
                                ednormal.getText().toString().trim(),

                                getcontact.getText().toString().trim(),

                                ed_divert.getText().toString(),
                                ed_divertOff.getText().toString());
                        Toast.makeText(PasscodeScreen.this, "Data Saved..", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }


            }
        });
    }

    private void getData() {
        Cursor c=myDBhelper.getdata();
        if (c!=null){
        if (c.moveToFirst()){

            do {
                edsilent.setEnabled(false);
                ednormal.setEnabled(false);
                ed_divert.setEnabled(false);
                ed_divertOff.setEnabled(false);
                getcontact.setEnabled(false);

                edsilent.setText(c.getString(1));
                ednormal.setText(c.getString(2));
                        ed_divert.setText(c.getString(4));
                ed_divertOff.setText(c.getString(5));
                        getcontact.setText(c.getString(3));





            }while (c.moveToNext());
        }
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


}
