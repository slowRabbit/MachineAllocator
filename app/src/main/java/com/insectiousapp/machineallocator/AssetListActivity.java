package com.insectiousapp.machineallocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);

        ButterKnife.bind(this);

        recyclerView=(RecyclerView)findViewById(R.id.rv_itemList);
        data=new ArrayList<>();
        Asset asset1 =new Asset("1", "Sony", 2017, "NA", "NA");
        Asset asset2 =new Asset("1", "JBL", 2016, "NA", "NA");
//        Asset asset3 =new Asset("1", "Sennheiser", 2014, "NA", "NA");
//        Asset asset4 =new Asset("1", "Bose", 2015, "NA", "NA");
//        Asset asset5 =new Asset("1", "Boat", 2012, "NA", "NA");
//        Asset asset6 =new Asset("1", "Phillips", 2014, "NA", "NA");
//        Asset asset7 =new Asset("1", "Apple", 2013, "NA", "NA");

        data.add(asset1);
        data.add(asset2);
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

        assetsAdapter =new AssetsAdapter(data, this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(assetsAdapter);


        //for swiping and moving items up
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);


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

}
