package com.example.yongquan.couponapp.ListCoupon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.yongquan.couponapp.R;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final Context mContext;
    private List<CouponModel> couponModelses = new ArrayList<>();
    private List<Integer> listTitle = new ArrayList<>();
    private ItemClickListener itemClickListener;

    interface ItemClickListener{
        public void onClick();
    }

    public CouponAdapter(Context context, ArrayList<CouponModel> couponModelses, List<Integer> listTitle) {
        mContext = context;
        this.couponModelses = couponModelses;
        this.listTitle = listTitle;
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_content_coupon, parent, false);
            return new ViewHolderContent(view);
        } else {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
            return new ViewHolderTitle(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderContent) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick();
                }
            });
        } else {
            ViewHolderTitle viewHolderTitle = (ViewHolderTitle)holder;
            viewHolderTitle.txtTitle.setText(couponModelses.get(position).getTitle());
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {
        return listTitle.contains(position);
    }

    @Override
    public int getItemCount() {
        return couponModelses.size();
    }

    public class ViewHolderContent extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final RelativeLayout rlGroupCoupon;

        public ViewHolderContent(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            rlGroupCoupon = (RelativeLayout) view.findViewById(R.id.rl_group_coupon);
        }
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_kind_coupon);
        }
    }
}