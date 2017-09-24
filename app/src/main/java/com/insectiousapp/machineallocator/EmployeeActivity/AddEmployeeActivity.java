package com.insectiousapp.machineallocator.EmployeeActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.Database.DBSqliteConnection;
import com.insectiousapp.machineallocator.R;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText etName, etEmailId, etUnit, etPhoneNo;
    Button bAddEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

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

                    DBSqliteConnection dbSqliteConnection=new DBSqliteConnection(getApplicationContext());
                    boolean inserted=dbSqliteConnection.addEmployee(s1, s2, s3, s4);

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
}
