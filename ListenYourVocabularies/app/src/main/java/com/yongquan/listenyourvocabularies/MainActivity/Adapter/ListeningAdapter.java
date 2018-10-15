package com.yongquan.listenyourvocabularies.MainActivity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongquan.listenyourvocabularies.MainActivity.VobModel;
import com.yongquan.listenyourvocabularies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListeningAdapter extends RecyclerView.Adapter<ListeningAdapter.MyViewHolder> {

    private List<VobModel> vobList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ListeningAdapter(Context context, List<VobModel> vobList) {
        this.vobList = vobList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.row_vob,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ListeningAdapter.MyViewHolder holder, int position) {
        VobModel vobModel = vobList.get(position);
        holder.txtName.setText(vobModel.getName());
        holder.txtType.setText(vobModel.getType());
        holder.txtPron.setText(vobModel.getPron());
        holder.txtMean.setText(vobModel.getMean());
        holder.imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Hay zo",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vobList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vob_name)
        TextView txtName;
        @BindView(R.id.vob_type)
        TextView txtType;
        @BindView(R.id.vob_pron)
        TextView txtPron;
        @BindView(R.id.vob_mean)
        TextView txtMean;
        @BindView(R.id.img_done)
        ImageView imgDone;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}

