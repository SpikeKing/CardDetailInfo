package org.wangchenlong.carddetaildemo.cards.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wangchenlong.carddetaildemo.R;
import org.wangchenlong.carddetaildemo.cards.data.PharmacyInfo;

import java.util.List;

/**
 * 药店列表的适配器
 * <p/>
 * Created by wangchenlong on 16/8/25.
 */
public class PharmacyListAdapter extends BaseAdapter {

    private List<PharmacyInfo> mPharmacies;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PharmacyListAdapter(Context context, List<PharmacyInfo> pharmacies) {
        mContext = context;
        mPharmacies = pharmacies;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override public int getCount() {
        if (mPharmacies != null) {
            return mPharmacies.size();
        } else {
            return 0;
        }
    }

    @Override public Object getItem(int i) {
        return null;
    }

    @Override public long getItemId(int i) {
        return 0;
    }

    @Override public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_detail_pharmacy, viewGroup, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.detail_pharmacy_tv_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.detail_pharmacy_tv_price);
            holder.tvTime = (TextView) view.findViewById(R.id.detail_pharmacy_tv_time);
            holder.vBottomLine = view.findViewById(R.id.detail_pharmacy_v_bottom_line);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        PharmacyInfo info = mPharmacies.get(i);
        holder.tvTitle.setText(info.title);
        holder.tvPrice.setText(String.valueOf("¥" + info.price));
        holder.tvTime.setText(info.time);

        if (i == (mPharmacies.size() - 1)) {
            holder.vBottomLine.setVisibility(View.GONE);
        } else {
            holder.vBottomLine.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private static class ViewHolder {
        TextView tvTitle; // 标题
        TextView tvPrice;
        TextView tvTime;
        View vBottomLine;
    }
}
