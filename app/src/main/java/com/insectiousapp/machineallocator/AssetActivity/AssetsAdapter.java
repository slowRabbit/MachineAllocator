package com.insectiousapp.machineallocator.AssetActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.insectiousapp.machineallocator.R;

import java.util.List;

/**
 * Created by cyris on 1/4/17.
 */

public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.AssetViewHolder> {

    List<Asset> data;

    final private onItemClickListener mOnClickListener;
    Context context;
    private int lastPosition = -1;

    public AssetsAdapter(List<Asset> data, onItemClickListener mOnClickListener) {

        this.data = data;
        this.mOnClickListener = mOnClickListener;
    }


    public interface onItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.asset_list_item, parent, shouldAttachToParentImmediately);
        AssetViewHolder viewHolder = new AssetViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {

        Asset asset = data.get(position);
        holder.bind(asset);
        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.move_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
        else if(position<lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.move_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class AssetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        @BindView(R.id.tv_assetId) TextView tvAssetId;
//        @BindView(R.id.tv_assetMake) TextView tvAssetMake;
//        @BindView(R.id.tv_yearOfMaking) TextView tvYearOfMaking;
//        @BindView(R.id.tv_allocatedTo) TextView tvAllocatedTo;
//        @BindView(R.id.tv_allocatedTill) TextView tvAllocatedTill;

        TextView tvAssetId;
        TextView tvAssetMake;
        TextView tvYearOfMaking;
        TextView tvAllocatedTo;
        TextView tvAllocatedTill;
        ImageView ivAssetImage;
        int allocatedTo;

        public AssetViewHolder(View itemView) {
            super(itemView);
           // ButterKnife.bind(context, itemView);
           // itemView.setOnClickListener(this);

            tvAssetId=(TextView)itemView.findViewById(R.id.tv_assetId);
            tvAssetMake=(TextView)itemView.findViewById(R.id.tv_assetMake);
            tvYearOfMaking=(TextView)itemView.findViewById(R.id.tv_yearOfMaking);
            tvAllocatedTo=(TextView)itemView.findViewById(R.id.tv_allocatedTo);
            tvAllocatedTill=(TextView)itemView.findViewById(R.id.tv_allocatedTill);
            ivAssetImage=(ImageView)itemView.findViewById(R.id.iv_assetImage);
            //for setting individual click listeners :
//            itemView.setOnClickListener(this);
            tvAllocatedTo.setOnClickListener(this);
//           iconImageView.setOnLongClickListener(this);
            //

    }

        void bind(Asset asset) {
            //we will bind the data here with view holder
            int id=asset.getAssetId();
            tvAssetId.setText(String.valueOf(id));

            tvAssetMake.setText(asset.getAssetMake());

            int year=asset.getYearOfMaking();
            tvYearOfMaking.setText(String.valueOf(year));

            allocatedTo=asset.getAllocatedTo();

            if(allocatedTo==-1) {
                ivAssetImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.asset_notallocated_image1));
                tvAllocatedTo.setText("NA");
                tvAllocatedTill.setText("NA");
            }
            else {
                ivAssetImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.asset_allocated_image1));
                tvAllocatedTo.setText(String.valueOf(allocatedTo));
                tvAllocatedTill.setText(asset.getAllocatedTill());
            }


        }


        @Override
        public void onClick(View v) {

            int clickPosition=getAdapterPosition();
            mOnClickListener.onItemClick(clickPosition);
//            if(v.getId()==R.id.tv_firstName)
//            {
//                mOnClickListener.onItemClick(data.get(clickPosition).firstName);
//            }
//            else
//            {
//                mOnClickListener.onItemClick(data.get(clickPosition).lastName);
//            }



        }
    }

}
