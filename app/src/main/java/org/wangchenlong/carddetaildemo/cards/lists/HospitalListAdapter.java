package org.wangchenlong.carddetaildemo.cards.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wangchenlong.carddetaildemo.R;
import org.wangchenlong.carddetaildemo.cards.data.HospitalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测项目内部的医院信息列表
 * <p/>
 * Created by wangchenlong on 16/8/29.
 */
public class HospitalListAdapter extends BaseAdapter {
    private List<HospitalInfo> mHospitals;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HospitalListAdapter(Context context, List<HospitalInfo> hospitals) {
        mContext = context;
        mHospitals = hospitals;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override public int getCount() {
        if (mHospitals != null) {
            return mHospitals.size();
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

    @Override public View getView(int pos, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_detail_hospital, viewGroup, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) view.findViewById(R.id.detail_hospital_tv_name);
            holder.tvTag1 = (TextView) view.findViewById(R.id.detail_hospital_tv_tag_1);
            holder.tvTag2 = (TextView) view.findViewById(R.id.detail_hospital_tv_tag_2);
            holder.tvAddress = (TextView) view.findViewById(R.id.detail_hospital_tv_address);
            holder.tvPhone = (TextView) view.findViewById(R.id.detail_hospital_tv_phone);
            holder.tvPrice = (TextView) view.findViewById(R.id.detail_hospital_tv_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ArrayList<TextView> tagViews = new ArrayList<>();
        tagViews.add(holder.tvTag1);
        tagViews.add(holder.tvTag2);
        HospitalInfo info = mHospitals.get(pos);

        holder.tvName.setText(info.name);
        ArrayList<String> tags = info.tags;
        for (int i = 0; i < tags.size(); ++i) {
            tagViews.get(i).setVisibility(View.VISIBLE);
            tagViews.get(i).setText(tags.get(i));
        }
        holder.tvAddress.setText(String.valueOf(info.address + " " + info.distance));
        holder.tvPhone.setText(info.phone);
        holder.tvPrice.setText(String.valueOf("¥" + info.price));

        return view;
    }

    private static class ViewHolder {
        TextView tvName; // 标题
        TextView tvTag1;
        TextView tvTag2;
        TextView tvAddress;
        TextView tvPhone;
        TextView tvPrice;
    }
}
