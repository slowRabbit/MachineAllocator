package com.insectiousapp.machineallocator.AssetActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.insectiousapp.machineallocator.EmployeeActivity.AddEmployeeActivity;
import com.insectiousapp.machineallocator.Database.DBSqliteConnection;
import com.insectiousapp.machineallocator.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AssetListActivity extends AppCompatActivity implements AssetsAdapter.onItemClickListener {

    //@BindView(R.id.rv_itemList)
    RecyclerView recyclerView;
    AssetsAdapter assetsAdapter;
    List<Asset> data;
    DBSqliteConnection dbSqliteConnection;
    int aid, aYear, aAllocatedTo;
    String amake, aallocatedTill;
    Asset tempAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);


        ButterKnife.bind(this);
        dbSqliteConnection=new DBSqliteConnection(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_itemList);
        data=new ArrayList<>();
        Asset asset1 =new Asset(1, "Sony", 2017, 0, "NA");
        Asset asset2 =new Asset(2, "JBL", 2016, -1, "2019");
        Asset asset3 =new Asset(3, "Sennheiser", 2014, 2, "NA");
        Asset asset4 =new Asset(4, "Bose", 2015, -1, "2025");
        Asset asset5 =new Asset(5, "Boat", 2012, 4, "2018");
        Asset asset6 =new Asset(6, "Phillips", 2014, 5, "NA");
        Asset asset7 =new Asset(7, "Apple", 2013, 7, "NA");

//        data.add(asset1);
//        data.add(asset2);
//        data.add(asset3);
//        data.add(asset4);
//        data.add(asset5);
//        data.add(asset6);
//        data.add(asset7);
//        data.add(asset1);
//        data.add(asset2);
//        data.add(asset3);
//        data.add(asset4);
//        data.add(asset5);
//        data.add(asset6);
//        data.add(asset7);
//
//        data.add(asset1);
//        data.add(asset2);
//        data.add(asset3);
//        data.add(asset4);
//        data.add(asset5);
//        data.add(asset6);
//        data.add(asset7);


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

        refreshAssetList();
        //readAllAsset();
        //readAllEmployee();
    }

    public void readAllEmployee()
    {
        Cursor resultCursor=dbSqliteConnection.readAllEmployees();
        //data=new ArrayList<Asset>();

        if(resultCursor!=null&&resultCursor.getCount()>0)
        {
            while(resultCursor.moveToNext())
            {

                Log.i("dbcheck", "Employee is " + resultCursor.getString(0) + "-" + resultCursor.getString(1) + "-" + resultCursor.getString(2)
                        + "-" + resultCursor.getString(3) + "-" + resultCursor.getString(4));

//                tempAsset=new Asset(resultCursor.getInt(0), resultCursor.getString(1), resultCursor.getInt(2),
//                        resultCursor.getInt(3),resultCursor.getString(4));
                //data.add(tempAsset);

            }
        }
//        assetsAdapter =new AssetsAdapter(data, this);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(assetsAdapter);

    }


    public void readAllAsset()
    {
        Cursor resultCursor=dbSqliteConnection.readAllAssets();
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
        assetsAdapter =new AssetsAdapter(data, this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(assetsAdapter);

    }

    private void refreshAssetList()
    {
        readAllAsset();
        readAllEmployee();
    }

    @Override
    public void onItemClick(int position) {

        Asset asset =data.get(position);
        //Toast.makeText(this, "Item clicked is :"+asset.getAssetId()+"-+"+asset.getAssetMake()+"-"+asset.getYearOfMaking()+"-"+
               // asset.getAllocatedTo()+"-"+asset.getAllocatedTill()+"-", Toast.LENGTH_SHORT).show();

        //check if the asset is allocated or not
        int allocatedAssetId=asset.getAllocatedTo();
        if(allocatedAssetId==-1)
        {
            //means it is already deallocated
            //call function for allocation
            allocateAsset(asset);
        }
        else
        {
            //means it is already allocated
            //call function for deallocation
            deallocateAsset(asset);
        }





//        Intent i=new Intent();
//        i.setClass(this, DetailActivity.class);
//        startActivity(i);

    }

    private void deallocateAsset(final Asset asset)
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Deallocate Asset");
        alertDialog.setMessage("Are you sure to deallocate the asset ?");
        alertDialog.setIcon(R.drawable.removeresource);
        alertDialog.setPositiveButton("Deallocate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //now we need to deallocate the asset
                boolean isDeallocated=dbSqliteConnection.updateAssetForDeallocation(asset);
                if(isDeallocated) {
                    Toast.makeText(getApplicationContext(), "Asset Deallocated !", Toast.LENGTH_SHORT).show();
                    refreshAssetList();
                }
                else
                    Toast.makeText(getApplicationContext(), "Asset cannot be Deallocated !!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.create().show();
        int id=asset.getAssetId();
    }

    private void allocateAsset(final Asset asset)
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Allocate Asset");
        alertDialog.setMessage("Are you sure to allocate the asset ?");
        alertDialog.setIcon(R.drawable.removeresource);
        alertDialog.setPositiveButton("Allocate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //now we need to deallocate the asset
            }
        });
        alertDialog.create().show();
        int id=asset.getAssetId();
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

    private void moveItem(int oldPos, int newPos) {

        Asset item = (Asset) data.get(oldPos);
        data.remove(oldPos);
        data.add(newPos, item);
        assetsAdapter.notifyItemMoved(oldPos, newPos);
    }

    private void deleteItem(final int position) {
        data.remove(position);
        assetsAdapter.notifyItemRemoved(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assetlistactivity, menu);
        Log.i("check", "menu inflated");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("check", "clickedmenu main");

        switch (item.getItemId())
        {
            case R.id.menuAddAsset:

                Log.i("check", "clickedmenu add asset");
                    Intent iAddAsset=new Intent(this, AddAssetActivity.class);
                    startActivity(iAddAsset);

                break;
            case R.id.menuRemoveAsset:
               // Toast.makeText(this, "Remove asset", Toast.LENGTH_SHORT).show();
                deleteAssetDialogBox();
                break;
            case R.id.menuAddEmployee:

                Log.i("check", "clickedmenu add emp");
                Intent iAddEmployee=new Intent(this, AddEmployeeActivity.class);
                startActivity(iAddEmployee);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteAssetDialogBox() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.etRemoveAssetId);

        dialogBuilder.setTitle("Remove Asset");
        dialogBuilder.setMessage("Enter id of asset to remove !");
        dialogBuilder.setPositiveButton("Remove Asset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String strId=edt.getText().toString();
                boolean isDeleted=dbSqliteConnection.removeAsset(strId);
                if(isDeleted) {
                    refreshAssetList();
                    Toast.makeText(getApplicationContext(), "Employee deleted with id: " + strId, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Employee cannot be deleted !", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}
