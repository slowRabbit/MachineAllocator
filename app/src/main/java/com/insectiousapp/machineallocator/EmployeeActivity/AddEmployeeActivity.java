package com.insectiousapp.machineallocator.EmployeeActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.AssetActivity.AddAssetActivity;
import com.insectiousapp.machineallocator.AssetActivity.Asset;
import com.insectiousapp.machineallocator.AssetActivity.AssetsAdapter;
import com.insectiousapp.machineallocator.Database.AssetEmployeeSQLiteConnection;
import com.insectiousapp.machineallocator.R;

import java.util.ArrayList;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText etName, etEmailId, etUnit, etPhoneNo;
    Button bAddEmp;
    AssetEmployeeSQLiteConnection assetEmployeeSQLiteConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        setTitle("Add employee");
        assetEmployeeSQLiteConnection=new AssetEmployeeSQLiteConnection(this);
        etName=(EditText)findViewById(R.id.etEmpName);
        etEmailId=(EditText)findViewById(R.id.etEmpEmailid);
        etUnit=(EditText)findViewById(R.id.etEmpUnit);
        etPhoneNo=(EditText)findViewById(R.id.etEmpPhone);
        bAddEmp=(Button)findViewById(R.id.bAddEmp);

        bAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s1=etName.getText().toString();
                String s2=etEmailId.getText().toString();
                String s3=etUnit.getText().toString();
                String s4=etPhoneNo.getText().toString();

                boolean b1=s1!=null && !s1.isEmpty();
                boolean b2=s2!=null && !s2.isEmpty();
                boolean b3=s3!=null && !s3.isEmpty();
                boolean b4=s4!=null && !s4.isEmpty();

                if(b1 && b2 && b3 && b4)
                {

                    AssetEmployeeSQLiteConnection assetEmployeeSQLiteConnection =new AssetEmployeeSQLiteConnection(getApplicationContext());
                    boolean inserted= assetEmployeeSQLiteConnection.addEmployee(s1, s2, s3, s4);

                    if(inserted){
                        Toast.makeText(getApplicationContext(), "Employee Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    else
                        Toast.makeText(getApplicationContext(), "Cannot add Employee !", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Entries ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allemployees, menu);
        Log.i("check", "menu inflated");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("check", "clickedmenu main");

        switch (item.getItemId())
        {
            case R.id.menuAllEmployees:

                Toast.makeText(this, "All Employees", Toast.LENGTH_SHORT).show();
                //now get list of all employees according to
                Intent i=new Intent(this, EmployeeListActivityForAssets.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


//    private void getGroupedAssetListAccToEmployee()
//    {
//        Cursor resultCursor=assetEmployeeSQLiteConnection.getGroupedAssetListAccToEmployee();
//
//        //data=new ArrayList<Asset>();
//
//        if(resultCursor!=null&&resultCursor.getCount()>0)
//        {
//            while(resultCursor.moveToNext())
//            {
//
//                //Log.i("allemp", "Asset Emp is : " + resultCursor.getString(0) + "-" + resultCursor.getString(1) + "-" + resultCursor.getString(2));
//                Log.i("allemp", "distinct id  : " + resultCursor.getString(0));
//
//                //tempAsset=new Asset(resultCursor.getInt(0), resultCursor.getString(1), resultCursor.getInt(2),
//                  //      resultCursor.getInt(3),resultCursor.getString(4));
//
//            }
//        }
//
//    }

}
