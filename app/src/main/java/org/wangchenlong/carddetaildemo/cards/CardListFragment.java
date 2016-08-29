package org.wangchenlong.carddetaildemo.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.wangchenlong.carddetaildemo.CardType;
import org.wangchenlong.carddetaildemo.R;
import org.wangchenlong.carddetaildemo.cards.data.CardInfo;
import org.wangchenlong.carddetaildemo.cards.data.DoctorInfo;
import org.wangchenlong.carddetaildemo.cards.data.HospitalInfo;
import org.wangchenlong.carddetaildemo.cards.data.PharmacyInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卡片详情页面
 * Created by wangchenlong on 16/8/26.
 */
public class CardListFragment extends Fragment {
    @BindView(R.id.fragment_ll_list) LinearLayout mLlList;

    private LinearLayout mLlHeader; // 标题信息
    private LinearLayout mLlContent; // 内容信息

    private TextView mTvItemTitle;
    private TextView mTvItemDesc;
    private LinearLayout mLlSearch;
    private ImageView mIvMore;

    private TextView mTvListTitle;
    private TextView mTvListDesc;
    private TextView mTvListPos;
    private LinearLayout mLlListInList;

    private LinearLayout mLlList2Container;
    private TextView mTvList2Title;
    private LinearLayout mLlList2InList;

    private LinearLayout mLlBottomLine;

    private List<CardInfo> mCardInfos; // 卡片信息
    private List<LinearLayout> mHeaders; // 标题项
    private List<LinearLayout> mContents; // 标题项
    private List<LinearLayout> mSearchers; // 标题项
    private List<ImageView> mMores; // 标题项
    private ScrollView mScrollView; // 滚动

    private int mType;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        @SuppressWarnings("unchecked")
        ArrayList<CardInfo> cardInfos = (ArrayList<CardInfo>) bundle.getSerializable(CardDetailActivity.CARD_INFO_EXTRA);
        mType = bundle.getInt(CardDetailActivity.CARD_TYPE_EXTRA);
        updateFragment(cardInfos);
    }

    public void setScrollView(ScrollView scrollView) {
        mScrollView = scrollView;
    }

    private void updateFragment(List<CardInfo> infos) {
        if (infos == null || infos.isEmpty()) {
            return;
        }

        mCardInfos = infos;

        mLlList.removeAllViews();
        mHeaders = new ArrayList<>();
        mContents = new ArrayList<>();
        mSearchers = new ArrayList<>();
        mMores = new ArrayList<>();

        for (int i = 0; i < infos.size(); i++) {
            CardInfo info = infos.get(i);
            View view = initItemViews();
            setData(info);
            mLlList.addView(view);
        }

        chooseItem(0);
    }

    private View initItemViews() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_card, null);

        mLlHeader = (LinearLayout) view.findViewById(R.id.card_item_ll_header);
        mLlContent = (LinearLayout) view.findViewById(R.id.card_item_ll_content);

        mTvItemTitle = (TextView) view.findViewById(R.id.card_item_tv_title);
        mTvItemDesc = (TextView) view.findViewById(R.id.card_item_tv_drag_desc);
        mLlSearch = (LinearLayout) view.findViewById(R.id.card_item_ll_search);
        mIvMore = (ImageView) view.findViewById(R.id.card_item_iv_more);

        mTvListTitle = (TextView) view.findViewById(R.id.card_item_tv_list_title);
        mTvListDesc = (TextView) view.findViewById(R.id.card_item_tv_list_desc);
        mTvListPos = (TextView) view.findViewById(R.id.card_item_tv_list_position);
        mLlListInList = (LinearLayout) view.findViewById(R.id.card_item_ll_list);

        mLlList2Container = (LinearLayout) view.findViewById(R.id.card_item_ll_list_2_container);
        mTvList2Title = (TextView) view.findViewById(R.id.card_item_tv_list_2_title);
        mLlList2InList = (LinearLayout) view.findViewById(R.id.card_item_ll_list_2);

        mLlBottomLine = (LinearLayout) view.findViewById(R.id.card_item_ll_bottom_line);

        mHeaders.add(mLlHeader);
        mContents.add(mLlContent);
        mSearchers.add(mLlSearch);
        mMores.add(mIvMore);

        return view;
    }

    private void chooseItem(final int pos) {
        for (int i = 0; i < mCardInfos.size(); i++) {
            final LinearLayout header = mHeaders.get(i);
            LinearLayout content = mContents.get(i);
            LinearLayout searcher = mSearchers.get(i);
            ImageView more = mMores.get(i);

            final int num = i;

            if (pos == num) {
                content.setVisibility(View.VISIBLE);
                if (mType == CardType.TYPE_DRAG) {
                    searcher.setVisibility(View.VISIBLE);
                }
                more.setVisibility(View.GONE);

                if (pos == mCardInfos.size() - 1) {
                    mLlBottomLine.setVisibility(View.GONE);
                }
            } else {
                content.setVisibility(View.GONE);
                if (mType == CardType.TYPE_DRAG) {
                    searcher.setVisibility(View.GONE);
                }
                more.setVisibility(View.VISIBLE);
            }

            header.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    chooseItem(num);
                    mScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }
    }

    private void setData(CardInfo info) {
        mTvItemTitle.setText(info.title);
        mTvItemDesc.setText(info.desc);

        mTvListTitle.setText(info.listTitle);
        mTvListDesc.setText(info.listDesc);
        switch (mType) {
            case CardType.TYPE_DRAG:
                setPharmacyList(info);
                break;
            case CardType.TYPE_CHECK:
                setHospitalList(info);
                break;
            case CardType.TYPE_OPERATION:
                mLlList2Container.setVisibility(View.VISIBLE);
                setHospitalList(info);
                setDoctorList(info);
                break;
        }
    }

    private void setPharmacyList(CardInfo cardInfo) {
        ArrayList<PharmacyInfo> infos = cardInfo.pharmacyList;
        if (infos != null && infos.size() > 0) {
            for (int i = 0; i < infos.size(); ++i) {
                mLlListInList.addView(createPharmacyView(infos.get(i),
                        i == (infos.size() - 1)));
            }
        }
        mLlSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showToast("点击搜索");
            }
        });
    }

    private void setHospitalList(CardInfo cardInfo) {
        mTvListPos.setText(cardInfo.listPos);
        ArrayList<HospitalInfo> infos = cardInfo.hospitalList;
        if (infos != null && infos.size() > 0) {
            for (int i = 0; i < infos.size(); ++i) {
                mLlListInList.addView(createHospitalView(infos.get(i)));
            }
        }
    }

    private void setDoctorList(CardInfo cardInfo) {
        mTvList2Title.setText(cardInfo.list2Title);
        ArrayList<DoctorInfo> infos = cardInfo.doctorList;
        if (infos != null && infos.size() > 0) {
            for (int i = 0; i < infos.size(); ++i) {
                mLlList2InList.addView(createDoctorView(infos.get(i)));
            }
        }
    }


    private View createPharmacyView(PharmacyInfo info, boolean isLast) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.item_detail_pharmacy, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.detail_pharmacy_tv_title);
        TextView tvPrice = (TextView) view.findViewById(R.id.detail_pharmacy_tv_price);
        TextView tvTime = (TextView) view.findViewById(R.id.detail_pharmacy_tv_time);
        View vBottomLine = view.findViewById(R.id.detail_pharmacy_v_bottom_line);
        tvTitle.setText(info.title);
        tvPrice.setText(String.valueOf("¥" + info.price));
        tvTime.setText(info.time);
        if (isLast) {
            vBottomLine.setVisibility(View.GONE);
        } else {
            vBottomLine.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private View createHospitalView(HospitalInfo info) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.item_detail_hospital, null);
        TextView tvName = (TextView) view.findViewById(R.id.detail_hospital_tv_name);
        TextView tvTag1 = (TextView) view.findViewById(R.id.detail_hospital_tv_tag_1);
        TextView tvTag2 = (TextView) view.findViewById(R.id.detail_hospital_tv_tag_2);
        TextView tvAddress = (TextView) view.findViewById(R.id.detail_hospital_tv_address);
        TextView tvPhone = (TextView) view.findViewById(R.id.detail_hospital_tv_phone);
        TextView tvPrice = (TextView) view.findViewById(R.id.detail_hospital_tv_price);

        ArrayList<TextView> tagViews = new ArrayList<>();
        tagViews.add(tvTag1);
        tagViews.add(tvTag2);

        tvName.setText(info.name);
        ArrayList<String> tags = info.tags;
        for (int i = 0; i < tags.size(); ++i) {
            tagViews.get(i).setVisibility(View.VISIBLE);
            tagViews.get(i).setText(tags.get(i));
        }
        tvAddress.setText(String.valueOf(info.address + " " + info.distance));
        tvPhone.setText(info.phone);
        tvPrice.setText(String.valueOf("¥" + info.price));
        return view;
    }

    private View createDoctorView(DoctorInfo info) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.item_detail_doctor, null);
        TextView tvName = (TextView) view.findViewById(R.id.detail_doctor_tv_name);
        TextView tvClinic = (TextView) view.findViewById(R.id.detail_doctor_tv_clinic);
        TextView tvLevel = (TextView) view.findViewById(R.id.detail_doctor_tv_level);
        TextView tvHospital = (TextView) view.findViewById(R.id.detail_doctor_tv_hospital);

        tvName.setText(info.name);
        tvClinic.setText(info.clinic);
        tvLevel.setText(info.level);
        tvHospital.setText(info.hospital);
        return view;
    }

    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
