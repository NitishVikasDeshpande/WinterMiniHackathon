ackage com.example.learn.simple;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int count = 0;
    /*public void openFB() {
        Intent openFBapp = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
        startActivity(openFBapp);

    }*/

    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        SharedPreferences saveFile = getSharedPreferences("emergency", MODE_PRIVATE);
        String s = saveFile.getString("emergency", "");
        /*SpannableString ss = new SpannableString();
        ss.append("tel:").append()*/
        String s1 = new StringBuilder().append("tel:").append(s).toString();
        callIntent.setData(Uri.parse(s1));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
  }

      private TextView xText, yText, zText;
      private Sensor mySensor;
      private SensorManager SM;
      boolean mInitialized = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
       /* xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);*/

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x,y,z,mLastx = 0.0,mLasty = 0.0,mLastz = 0.0,deltaX,deltaY,deltaZ;
        final double alpha = 0.8;
        double[] gravity = {0,0,0};
        /*gravity[0] = alpha*gravity[0] + (1-alpha)*event.values[0];
        gravity[1] = alpha*gravity[1] + (1-alpha)*event.values[1];
        gravity[2] = alpha*gravity[2] + (1-alpha)*event.values[2];*/

        x = event.values[0] - gravity[0];
        y = event.values[1] - gravity[1];
        z = event.values[2] - gravity[2];

        /*xText.setText(new StringBuilder().append("X: ").append(x).toString());
        yText.setText(new StringBuilder().append("Y: ").append(y).toString());
        zText.setText(new StringBuilder().append("Z: ").append(z).toString());*/


        if(!mInitialized)
        {
            mLastx = x;
            mLasty = y;
            mLastz = z;
            mInitialized = true;
        }
        else {

            deltaX = Math.abs(mLastx - x);
            deltaY = Math.abs(mLasty - y);
            deltaZ = Math.abs(mLastz - z);
            if (deltaX < 2.5)
                deltaX = (float) 0.0;
            if (deltaY < 2.5)
                deltaY = (float) 0.0;
            if (deltaZ < 2.5)
                deltaZ = (float) 0.0;
            mLastx = x;
            mLasty = y;
            mLastz = z;

            if (deltaZ > deltaX){
                count++;
            }

        }
        if(count >= 2){
            count = 0;
            call();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void btnClick(View view){
        SharedPreferences saveFile = getSharedPreferences("emergency", MODE_PRIVATE);
        EditText edit = (EditText) findViewById(R.id.edit);
        String s = edit.getText().toString();
        SharedPreferences.Editor a = saveFile.edit();
        a.putString("emergency", s);
        a.commit();
    }

    /*@Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }*/

}


/*<LinearLayout
       android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:orientation="vertical">
<TextView
       android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:id="@+id/xText"/>

<TextView
       android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:id="@+id/yText"/>

<TextView
       android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:id="@+id/zText"/>

</LinearLayout>*/
