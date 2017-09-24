package com.insectiousapp.machineallocator.EmployeeActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.insectiousapp.machineallocator.AssetActivity.Asset;
import com.insectiousapp.machineallocator.R;

import java.util.List;

/**
 * Created by cyris on 1/4/17.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    List<Employee> data;

    final private onItemClickListener mOnClickListener;
    Context context;
    private int lastPosition = -1;

    public EmployeeAdapter(List<Employee> data, onItemClickListener mOnClickListener) {

        this.data = data;
        this.mOnClickListener = mOnClickListener;
    }


    public interface onItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.employee_list_item, parent, shouldAttachToParentImmediately);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        Employee employee = data.get(position);
        holder.bind(employee);
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

    class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvEmpId;
        TextView tvEmpName;
        int allocatedTo;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
           // ButterKnife.bind(context, itemView);
            itemView.setOnClickListener(this);
            tvEmpId=(TextView)itemView.findViewById(R.id.tv_empList_empId);
            tvEmpName=(TextView)itemView.findViewById(R.id.tv_empList_empName);


    }

        void bind(Employee employee) {
            //we will bind the data here with view holder
            int id=employee.getEmpId();
            tvEmpId.setText(String.valueOf(id));
            tvEmpName.setText(employee.getEmpName());

        }


        @Override
        public void onClick(View v) {

            int clickPosition=getAdapterPosition();
            mOnClickListener.onItemClick(clickPosition);

        }
    }

}
