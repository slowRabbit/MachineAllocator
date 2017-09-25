package com.insectiousapp.machineallocator.EmployeeActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.insectiousapp.machineallocator.AssetActivity.Asset;
import com.insectiousapp.machineallocator.AssetActivity.AssetListActivityAccToEmployee;
import com.insectiousapp.machineallocator.Database.AssetEmployeeSQLiteConnection;
import com.insectiousapp.machineallocator.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

public class EmployeeListActivityForAssets extends AppCompatActivity implements EmployeeAdapter.onItemClickListener {

    //@BindView(R.id.rv_itemList)
    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    List<Employee> data;
    AssetEmployeeSQLiteConnection assetEmployeeSQLiteConnection;
    int aid, aYear, aAllocatedTo;
    String amake, aallocatedTill;
    Asset tempAsset;
    Employee employee;
    String allocatedTillDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        setTitle("All Employees");


        ButterKnife.bind(this);
        assetEmployeeSQLiteConnection =new AssetEmployeeSQLiteConnection(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_employee_List);
        data=new ArrayList<>();
        readAllEmployee();
        //for swiping and moving items up
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    @Override
    protected void onResume() {
        super.onResume();

        readAllEmployee();
    }

    public void readAllEmployee()
    {
        data=new ArrayList<Employee>();

        Cursor resultCursor= assetEmployeeSQLiteConnection.readAllEmployees();
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

        Intent i=new Intent(this, AssetListActivityAccToEmployee.class);
        i.putExtra("allocatedto", String.valueOf(employee.getEmpId()));
        startActivity(i);
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
