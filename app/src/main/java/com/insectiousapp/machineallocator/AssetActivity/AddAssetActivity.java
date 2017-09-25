package com.insectiousapp.machineallocator.AssetActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.Database.AssetEmployeeSQLiteConnection;
import com.insectiousapp.machineallocator.R;

public class AddAssetActivity extends AppCompatActivity {


    EditText etAssetMake, etYearMake, etAllocateTo, etAllocatedTill;
    Button bAddAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        setTitle("Add aset");

        etAssetMake=(EditText)findViewById(R.id.etAssetMake);
        etYearMake=(EditText)findViewById(R.id.etManuYear);
        etAllocateTo=(EditText)findViewById(R.id.etAllocatedTo);
        etAllocatedTill=(EditText)findViewById(R.id.etAllocatedTill);
        bAddAsset=(Button)findViewById(R.id.bAddAsset);

        bAddAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s1=etAssetMake.getText().toString();
                String s2=etYearMake.getText().toString();
                String s3=etAllocateTo.getText().toString();
                String s4=etAllocatedTill.getText().toString();

                boolean b1=s1!=null && !s1.isEmpty();
                boolean b2=s2!=null && !s2.isEmpty();
                boolean b3=s3!=null && !s3.isEmpty();
                boolean b4=s4!=null && !s4.isEmpty();

                if(b1 && b2 && b3 && b4)
                {
                    int i2=Integer.parseInt(s2);
                    int i3=Integer.parseInt(s3);

                    AssetEmployeeSQLiteConnection assetEmployeeSQLiteConnection =new AssetEmployeeSQLiteConnection(getApplicationContext());
                    boolean inserted= assetEmployeeSQLiteConnection.addAsset(s1, i2, i3, s4);

                    if(inserted){
                        Toast.makeText(getApplicationContext(), "Asset Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    else
                        Toast.makeText(getApplicationContext(), "Cannot add Asset !", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Entries ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
