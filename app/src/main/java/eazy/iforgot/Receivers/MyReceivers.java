package eazy.iforgot.Receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import eazy.iforgot.Database.MyDBhelper;

/**
 * Created by Administrator on 15-01-2017.
 */

public class MyReceivers extends BroadcastReceiver {
    Bundle bundle;
    StringBuffer buffer=new StringBuffer();
    Context context;
    String phone, message,condata;
    private String number,value;
    MyDBhelper myDBhelper;
    String silent,normal,contacts,divert,divretoff;
    StringBuffer homeaddress = new StringBuffer();

    @Override
    public void onReceive(final Context context, Intent intent) {
        bundle = intent.getExtras();

        myDBhelper=new MyDBhelper(context);
        Cursor cursor2=myDBhelper.getdata();



        if (cursor2.moveToFirst()){
            do{
                silent=cursor2.getString(cursor2.getColumnIndex("silenton"));
                normal=cursor2.getString(cursor2.getColumnIndex("normalmode"));

                contacts=cursor2.getString(cursor2.getColumnIndex("contacts"));
                divert=cursor2.getString(cursor2.getColumnIndex("diverton"));
                divretoff=cursor2.getString(cursor2.getColumnIndex("divertoff"));



            }while (cursor2.moveToNext());
        }

        if (bundle != null) {
            Object[] objects = (Object[]) bundle.get("pdus");
            for (int i = 0; i < objects.length; i++) {
                SmsMessage currentmsg = SmsMessage.createFromPdu((byte[]) objects[i]);
                phone = currentmsg.getDisplayOriginatingAddress();
                message = currentmsg.getDisplayMessageBody();

                if (message.equalsIgnoreCase(silent)) {
                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                if (message.equalsIgnoreCase(normal)){
                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }

                if ((containsIgnoreCase(message,contacts))==true) {
                    ContentResolver contentResolver = context.getContentResolver();


                    String[] items = message.split((contacts));
                    for (String item : items)
                    {
                        Log.d("hhhhh",item);
                        condata=item;
                    }
                    Cursor cursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                            new String[]{condata}, null);

                    while (cursor.moveToNext()) {
                        number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));



                    }

if (number==null){


}else {
    SmsManager smsManager = SmsManager.getDefault();
    smsManager.sendTextMessage(phone, null, ""+number, null, null);

}

                }


                if (message.equalsIgnoreCase(divert)) {
                    String callForwardString = "**21*" + phone + "#";


                    Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                    Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                    intentCallForward.setData(uri2);
                    intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intentCallForward);

                }

                if (message.equalsIgnoreCase(divretoff)) {

                    String callForwardStringStop = "##21#";

                    Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                    Uri uri2 = Uri.fromParts("tel", callForwardStringStop, "#");
                    intentCallForward.setData(uri2);
                    intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentCallForward);

                }

            }

    }
        }



    public static boolean containsIgnoreCase(final String str, final String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }
}
