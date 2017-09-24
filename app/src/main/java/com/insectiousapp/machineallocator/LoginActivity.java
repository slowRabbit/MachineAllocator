package com.insectiousapp.machineallocator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.AssetActivity.AssetListActivity;
import com.insectiousapp.machineallocator.Extras.Constants;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int savedPin;
    Button bLogin;
    EditText etPin;
    int enteredPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DBSqliteConnection dbSqliteConnection=new DBSqliteConnection(this);
//        boolean inserted=dbSqliteConnection.addAsset("Sony", 2022, 38, "2032");
//        if(true)
//            Toast.makeText(this, "Inserted in database", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this, "Cannot insert  in database !", Toast.LENGTH_SHORT).show();

        sharedPreferences = getSharedPreferences(Constants.SharedPreferenceFiles.LOGIN_DETAILS_SP, Context.MODE_PRIVATE);
        savedPin=sharedPreferences.getInt(Constants.SharedPreferenceFiles.LOGIN_KEY, 0);

        if(savedPin==0)
        {
            updateNewPinInSharedPreference();
            //now pin 5555 have been written in sharedpreference
            //now update the savedPin to newly written value in sp
            savedPin=sharedPreferences.getInt(Constants.SharedPreferenceFiles.LOGIN_KEY, 0);
        }

        bLogin=(Button)findViewById(R.id.bLogin);
        etPin=(EditText)findViewById(R.id.etPin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted())
                {
                String sPin = etPin.getText().toString();
                if (!sPin.isEmpty() && sPin != null) {
                    enteredPin = Integer.parseInt(sPin);
                    if (enteredPin == savedPin) {
                        //move to next activity
                        Intent i = new Intent(getApplicationContext(), AssetListActivity.class);
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "Login Done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid PIN", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "PIN cannot be null", Toast.LENGTH_SHORT).show();
                }

            }
            else
                {
                    Toast.makeText(getApplicationContext(), "Grant appropriate permission first !", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateNewPinInSharedPreference()
    {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SharedPreferenceFiles.LOGIN_DETAILS_SP, MODE_PRIVATE).edit();
        editor.putInt(Constants.SharedPreferenceFiles.LOGIN_KEY, 5555);
        editor.apply();
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //   call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
