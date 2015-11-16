package com.example.deviceadmindemo;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdmin;
    private boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceAdmin = new ComponentName(this, MainActivity.MyDeviceAdminReceiver.class);
        mDPM = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        mDPM.setMaximumTimeToLock(mDeviceAdmin, 5);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        if(!isActive) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "device admin exp");
            startActivityForResult(intent, 1);
        }else{
            mDPM.removeActiveAdmin(mDeviceAdmin);
            isActive = false;
        }
    }

    private boolean isAdminActivity(){
        return mDPM.isAdminActive(mDeviceAdmin);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                   // mDPM.lockNow();
                    break;
            }
        }
    }

    /**
     * sample receiver
     */
    public static class MyDeviceAdminReceiver extends DeviceAdminReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            Log.d("MAIN", " device admin enabled !");
        }
    }
}
