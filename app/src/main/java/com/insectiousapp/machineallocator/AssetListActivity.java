package com.insectiousapp.machineallocator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

        readStudentData();

    }

    public void readStudentData()
    {
        Cursor resultCursor=dbSqliteConnection.readAllAssets();
        data=new ArrayList<Asset>();

        if(resultCursor!=null&&resultCursor.getCount()>0)
        {
            while(resultCursor.moveToNext())
            {

                Log.i("dbcheck", "Asset ID is " + resultCursor.getString(0) + "-" + resultCursor.getString(1) + "-" + resultCursor.getString(2)
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


    @Override
    public void onItemClick(int position) {

        Asset asset =data.get(position);
        Toast.makeText(this, "Item clicked is :"+asset.getAssetId()+"-+"+asset.getAssetMake()+"-"+asset.getYearOfMaking()+"-"+
                asset.getAllocatedTo()+"-"+asset.getAllocatedTill()+"-", Toast.LENGTH_SHORT).show();

//        Intent i=new Intent();
//        i.setClass(this, DetailActivity.class);
//        startActivity(i);

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuAddAsset:

                    Intent iAddAsset=new Intent(this, AddAssetActivity.class);
                    startActivity(iAddAsset);

                break;
            case R.id.menuRemoveAsset:
                Toast.makeText(this, "Remove asset", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuAddEmployee:
                Toast.makeText(this, "Add Employee", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
