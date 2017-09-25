package com.insectiousapp.machineallocator.AssetActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.Database.AssetEmployeeSQLiteConnection;
import com.insectiousapp.machineallocator.EmployeeActivity.AddEmployeeActivity;
import com.insectiousapp.machineallocator.EmployeeActivity.EmployeeListActivity;
import com.insectiousapp.machineallocator.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AssetListActivityAccToEmployee extends AppCompatActivity{

    //@BindView(R.id.rv_itemList)
    RecyclerView recyclerView;
    AssetAdapterAccToEmployee assetsAdapter;
    List<Asset> data;
    AssetEmployeeSQLiteConnection assetEmployeeSQLiteConnection;
    Asset tempAsset;
    String allocatedTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);




        ButterKnife.bind(this);
        assetEmployeeSQLiteConnection =new AssetEmployeeSQLiteConnection(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_itemList);
        data=new ArrayList<>();


        Intent i=getIntent();
        allocatedTo=i.getStringExtra("allocatedto");
        setTitle("Assets for :"+allocatedTo);
        readAllAssetAccToAllocatedId(allocatedTo);


    }


    public void readAllAssetAccToAllocatedId(String empId)
    {
        Cursor resultCursor= assetEmployeeSQLiteConnection.readAllAssetsAccToAllocatedTo(empId);
        data=new ArrayList<Asset>();

        if(resultCursor!=null&&resultCursor.getCount()>0)
        {
            while(resultCursor.moveToNext())
            {

                Log.i("dbcheck", "Asset is " + resultCursor.getString(0) + "-" + resultCursor.getString(1) + "-" + resultCursor.getString(2)
                        + "-" + resultCursor.getString(3) + "-" + resultCursor.getString(4));

                tempAsset=new Asset(resultCursor.getInt(0), resultCursor.getString(1), resultCursor.getInt(2),
                        resultCursor.getInt(3),resultCursor.getString(4));
                data.add(tempAsset);

            }
        }
        assetsAdapter =new AssetAdapterAccToEmployee(data);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(assetsAdapter);

    }


}
