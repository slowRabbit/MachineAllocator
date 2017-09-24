package com.insectiousapp.machineallocator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int savedPin;
    Button bLogin;
    EditText etPin;
    int enteredPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                String sPin=etPin.getText().toString();
                if(!sPin.isEmpty() && sPin!=null)
                {
                    enteredPin= Integer.parseInt(sPin);
                    if(enteredPin==savedPin)
                    {
                        //move to next activity
                        Intent i=new Intent(getApplicationContext(), AssetListActivity.class);
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "Login Done", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid PIN", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "PIN cannot be null", Toast.LENGTH_SHORT).show();
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

}
