package com.example.yongquan.couponapp.Location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yongquan.couponapp.R;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    private List<LocationModel> dataLocation = new ArrayList<>();
    onItemLocationClick onItemLocationClick;
    interface onItemLocationClick{
        public void onClick();
    }

    public LocationAdapter(ArrayList<LocationModel> dataLocation) {
        this.dataLocation = dataLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_location, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemLocationClick.onClick();
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtName1.setText(dataLocation.get(position).getName1());
        holder.txtName2.setText(dataLocation.get(position).getName2());
        holder.txtCount.setText(dataLocation.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return dataLocation.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName1;
        TextView txtName2;
        TextView txtCount;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName1 = (TextView) itemView.findViewById(R.id.txt_name1);
            txtName2 = (TextView) itemView.findViewById(R.id.txt_name2);
            txtCount = (TextView) itemView.findViewById(R.id.txt_count);
        }
    }
    public void setOnItemLocationClick(onItemLocationClick onItemLocationClick){
        this.onItemLocationClick = onItemLocationClick;
    }

}
