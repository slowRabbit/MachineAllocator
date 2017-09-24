package com.insectiousapp.machineallocator.EmployeeActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.AssetActivity.AddAssetActivity;
import com.insectiousapp.machineallocator.AssetActivity.Asset;
import com.insectiousapp.machineallocator.AssetActivity.AssetsAdapter;
import com.insectiousapp.machineallocator.Database.DBSqliteConnection;
import com.insectiousapp.machineallocator.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeAdapter.onItemClickListener {

    //@BindView(R.id.rv_itemList)
    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    List<Employee> data;
    DBSqliteConnection dbSqliteConnection;
    int aid, aYear, aAllocatedTo;
    String amake, aallocatedTill;
    Asset tempAsset;
    Employee employee;
    Asset recievedAsset;
    String allocatedTillDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        Intent i=getIntent();
        recievedAsset= (Asset) i.getSerializableExtra("assetobject");

        ButterKnife.bind(this);
        dbSqliteConnection=new DBSqliteConnection(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_employee_List);
        data=new ArrayList<>();

        //for swiping and moving items up
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    int assetId;
    String assetMake;
    int yearOfMaking;
    int allocatedTo;
    String allocatedTill;

    @Override
    protected void onResume() {
        super.onResume();

        readAllEmployee();
    }

    public void readAllEmployee()
    {
        Cursor resultCursor=dbSqliteConnection.readAllEmployees();
        if(resultCursor!=null&&resultCursor.getCount()>0)
        {
            while(resultCursor.moveToNext())
            {
                Log.i("dbcheck", "Employee is " + resultCursor.getString(0) + "-" + resultCursor.getString(1) + "-" + resultCursor.getString(2)
                        + "-" + resultCursor.getString(3) + "-" + resultCursor.getString(4));

                Employee tempEmp=new Employee(resultCursor.getInt(0),resultCursor.getString(1),
                        resultCursor.getString(2),resultCursor.getString(3),resultCursor.getString(4));
                data.add(tempEmp);

            }
        }
        employeeAdapter =new EmployeeAdapter(data, this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(employeeAdapter);

    }


    @Override
    public void onItemClick(int position) {

        employee =data.get(position);
        allocateEmployeeToCurrentAsset(employee);
        sendMessageToEmployee(employee);
        finish();
    }

    private void allocateEmployeeToCurrentAsset(Employee employee)
    {
        //now we have both asset and employee
        //first add employee id to the asset

        //get current date and add 6 months to it
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 6);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        allocatedTillDate = df.format(c.getTime());

        boolean isAllocated=dbSqliteConnection.updateAssetForAllocation(recievedAsset, employee.getEmpId(), allocatedTillDate);

        if(isAllocated)
        {
            Toast.makeText(this, "Asset Allocated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Asset cannot be allocated !! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessageToEmployee(Employee employee)
    {

        String strPhone = employee.getPhNumber();
        String strMessage = "Hey "+employee.getEmpName()+ " you have been allocated :"+recievedAsset.getAssetMake()+"-"
                +recievedAsset.getAssetId()+" till "+allocatedTillDate;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(strPhone, null, strMessage, null, null);
        Toast.makeText(this, "Sent Message to : " + strPhone, Toast.LENGTH_SHORT).show();

    }

//    private ItemTouchHelper.Callback createHelperCallback() {
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
//                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
//                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//                    @Override
//                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
//                                          RecyclerView.ViewHolder target) {
//                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//                        return true;
//                    }
//
//                    @Override
//                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                        //we dont want to delete on swipe
//                        //deleteItem(viewHolder.getAdapterPosition());
//                    }
//                };
//        return simpleItemTouchCallback;
//    }

//    private void addItemToList() {
//
//        //listData.add(item);
//        // adapter.notifyItemInserted(listData.indexOf(item));
//    }



}
