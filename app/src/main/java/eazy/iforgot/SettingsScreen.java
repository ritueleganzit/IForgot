package eazy.iforgot;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import eazy.iforgot.Receivers.AdminReceiver;

public class SettingsScreen extends AppCompatActivity {


    Button btnpasscode,help;
    TextView textView,spy;
    Switch mswitch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor mpref;
    ComponentName cn;
    DevicePolicyManager mgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(SettingsScreen.this
                ,new String[]{Manifest.permission.READ_SMS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SET_ALARM,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,

                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECEIVE_SMS},1);
        setContentView(R.layout.activity_settings_screen);
        sharedPreferences=getSharedPreferences("mystate",MODE_PRIVATE);
        mpref=sharedPreferences.edit();
        mswitch= (Switch) findViewById(R.id.onff);
        if (sharedPreferences.getBoolean("itsclicked",false)==true){
            mswitch.setChecked(true);
        }
        else {
            mswitch.setChecked(false);
        }
        help=(Button)findViewById(R.id.help);
        btnpasscode= (Button) findViewById(R.id.setpasscode);
       mgr=
                (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        cn=new ComponentName(SettingsScreen.this, AdminReceiver.class);

        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    mpref.putBoolean("itsclicked",true);

                    if (mgr.isAdminActive(cn)) {
                        int msgId;

                        if (mgr.isActivePasswordSufficient()) {
                            msgId=R.string.compliant;
                        }
                        else {
                            msgId=R.string.not_compliant;
                        }


                    }
                    else {
                        mpref.putBoolean("itsclicked",true);
                        mpref.commit();
                        Intent intent=
                                new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                getString(R.string.device_admin_explanation));
                        startActivity(intent);
                    }
                }
                else {
mgr.removeActiveAdmin(cn);
                    mpref.putBoolean("itsclicked",false);
                    mpref.commit();
                }
            }
        });
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Caminata-One-demo-FFP.ttf");
        textView= (TextView) findViewById(R.id.appname5);
        spy= (TextView) findViewById(R.id.spy);
        textView.setTypeface(typeface);
        spy.setTypeface(typeface);

        btnpasscode.setTypeface(typeface);
        help.setTypeface(typeface);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SettingsScreen.this,HelpScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        btnpasscode=(Button)findViewById(R.id.setpasscode);
        btnpasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SettingsScreen.this,PasscodeScreen.class);
                startActivity(i);

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("sdfsdf","restart");
        if (sharedPreferences.getBoolean("itsclicked",false)==true){
            mswitch.setChecked(true);
        }
        else {
            mswitch.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("sdfsdf","resume");
        if (sharedPreferences.getBoolean("itsclicked",false)==true){
            Log.d("sdfsdf","resume"+sharedPreferences.getBoolean("itsclicked",false));
            mswitch.setChecked(true);
        }
        else {
            Log.d("sdfsdf","resume"+sharedPreferences.getBoolean("itsclicked",false));
            mswitch.setChecked(false);
        }
    }
}
