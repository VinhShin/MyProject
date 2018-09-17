package com.example.yongquan.couponapp.ListCoupon;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.yongquan.couponapp.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final Context mContext;
    private List<CouponModel> couponModelses = new ArrayList<>();
    private List<Integer> listTitle = new ArrayList<>();
    private ItemClickListener itemClickListener;

    interface ItemClickListener {
        public void onClick();
    }

    public CouponAdapter(Context context, ArrayList<CouponModel> couponModelses, List<Integer> listTitle) {
        mContext = context;
        this.couponModelses = couponModelses;
        this.listTitle = listTitle;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
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

            holder.itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = holder.itemView.getMeasuredWidth();
            int height = holder.itemView.getMeasuredHeight();

             ((ViewHolderContent) holder).cardView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width1 = ((ViewHolderContent) holder).cardView.getMeasuredWidth();
            int height2 = ((ViewHolderContent) holder).cardView.getMeasuredHeight();

            ViewHolderContent viewHolderContent = (ViewHolderContent) holder;

            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
//            layoutParams.width = width/2;
//            layoutParams.height = height/2;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick();
                }
            });
            String url = "https://images.pexels.com/photos/458766/pexels-photo-458766.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940";
            setImageView(mContext, url, viewHolderContent.image);

        } else {
            ViewHolderTitle viewHolderTitle = (ViewHolderTitle) holder;
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
//        public final RelativeLayout rlGroupCoupon;
        public final CardView cardView;
        public ViewHolderContent(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            image = (ImageView) view.findViewById(R.id.image);
//            rlGroupCoupon = (RelativeLayout) view.findViewById(R.id.rl_group_coupon);
        }
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_kind_coupon);
        }
    }

    private void setImageView(Context context, String url, ImageView imageView) {
        Picasso
                .with(context)
                .load(url)
                .resize(500, 500)
                .into(imageView);
    }
    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}