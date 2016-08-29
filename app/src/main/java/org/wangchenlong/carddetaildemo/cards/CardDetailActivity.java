package org.wangchenlong.carddetaildemo.cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.wangchenlong.carddetaildemo.CardType;
import org.wangchenlong.carddetaildemo.R;
import org.wangchenlong.carddetaildemo.cards.data.CardInfo;
import org.wangchenlong.carddetaildemo.cards.data.DoctorInfo;
import org.wangchenlong.carddetaildemo.cards.data.HospitalInfo;
import org.wangchenlong.carddetaildemo.cards.data.PharmacyInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卡片详情页面
 * Created by wangchenlong on 16/8/24.
 */
public class CardDetailActivity extends AppCompatActivity {
    public static final String CARD_TITLE_EXTRA = "CardDetailActivity.CARD_TITLE_EXTRA";
    public static final String CARD_TYPE_EXTRA = "CardDetailActivity.CARD_TYPE_EXTRA";
    public static final String CARD_ITEM_SIZE_EXTRA = "CardDetailActivity.CARD_ITEM_SIZE_EXTRA";
    public static final String CARD_INFO_EXTRA = "CardDetailActivity.CARD_INFO_EXTRA";

    @BindView(R.id.card_detail_sv_scroll) ScrollView mSvScroll;
    @BindView(R.id.card_item_iv_help_yes) ImageView mCardItemIvHelpYes;
    @BindView(R.id.card_item_ll_help_yes) LinearLayout mCardItemLlHelpYes;
    @BindView(R.id.card_item_iv_help_no) ImageView mCardItemIvHelpNo;
    @BindView(R.id.card_item_ll_help_no) LinearLayout mCardItemLlHelpNo;
    @BindView(R.id.card_detail_fl_list) FrameLayout mFlList;

    private Context mContext;
    private int mType; // 列表匹配的类型

    private ArrayList<CardInfo> mDragInfos; // 药品信息
    private ArrayList<PharmacyInfo> mDragPharmacyInfos; // 药品内部的药店信息

    private ArrayList<CardInfo> mCheckInfos; // 检查项目
    private ArrayList<HospitalInfo> mCheckHospitalInfos; // 检查内部的医院信息

    private ArrayList<CardInfo> mOperationInfos; // 手术项目
    private ArrayList<HospitalInfo> mOperationHospitalInfos; // 手术内部的医院信息
    private ArrayList<DoctorInfo> mOperationDoctorInfos; // 手术内部的医生信息

    /**
     * 启动卡片详情页面
     *
     * @param activity 上下文
     * @param title    标题
     * @param size     数量
     */
    public static void startCardDetail(Activity activity, String title, int size, int type) {
        Intent intent = new Intent(activity, CardDetailActivity.class);
        intent.putExtra(CARD_TITLE_EXTRA, title);
        intent.putExtra(CARD_ITEM_SIZE_EXTRA, size);
        intent.putExtra(CARD_TYPE_EXTRA, type);
        activity.startActivity(intent);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_detail);
        ButterKnife.bind(this);

        mContext = getApplicationContext();
        mType = getIntent().getIntExtra(CARD_TYPE_EXTRA, CardType.TYPE_DRAG);

        CardListFragment cardListFragment = new CardListFragment();
        switch (mType) {
            case CardType.TYPE_DRAG:
                fakeDragInfo();
                Bundle dragBundle = new Bundle();
                dragBundle.putInt(CARD_TYPE_EXTRA, mType);
                dragBundle.putSerializable(CARD_INFO_EXTRA, mDragInfos);
                cardListFragment.setArguments(dragBundle);
                break;
            case CardType.TYPE_CHECK:
                fakeCheckInfo();
                Bundle checkBundle = new Bundle();
                checkBundle.putInt(CARD_TYPE_EXTRA, mType);
                checkBundle.putSerializable(CARD_INFO_EXTRA, mCheckInfos);
                cardListFragment.setArguments(checkBundle);
                break;
            case CardType.TYPE_OPERATION:
                fakeOperationInfos();
                Bundle operationBundle = new Bundle();
                operationBundle.putInt(CARD_TYPE_EXTRA, mType);
                operationBundle.putSerializable(CARD_INFO_EXTRA, mOperationInfos);
                cardListFragment.setArguments(operationBundle);
                break;
        }
        cardListFragment.setScrollView(mSvScroll);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.card_detail_fl_list, cardListFragment).commit();

        Intent intent = getIntent();
        String title = "";
        if (intent != null) {
            title = intent.getStringExtra(CARD_TITLE_EXTRA);
            int titleSize = intent.getIntExtra(CARD_ITEM_SIZE_EXTRA, 0);
            if (titleSize > 0) {
                title += "(" + titleSize + ")";
            }
        }

        initActionBar(title);

        chooseHelp(true);
    }

    @Override protected void onResume() {
        super.onResume();
        mSvScroll.fullScroll(ScrollView.FOCUS_UP);
    }

    private void chooseHelp(boolean yes) {
        if (yes) {
            mCardItemIvHelpYes.setEnabled(true);
            mCardItemIvHelpNo.setEnabled(false);
        } else {
            mCardItemIvHelpYes.setEnabled(false);
            mCardItemIvHelpNo.setEnabled(true);
        }
    }

    /**
     * 模拟药品信息列表
     */
    private void fakeDragInfo() {
        String dragDesc = getString(R.string.drag_desc);

        String listTitle = getString(R.string.drag_pharmacy_list_title);
        String listDesc = getString(R.string.drag_pharmacy_list_desc);

        fakeDragPharmacyInfos(); // 模拟药店列表

        mDragInfos = new ArrayList<>();
        mDragInfos.add(new CardInfo("罗红霉素", dragDesc, listTitle, listDesc, mDragPharmacyInfos));
        mDragInfos.add(new CardInfo("阿奇霉素分散片", dragDesc, listTitle, listDesc, mDragPharmacyInfos));
        mDragInfos.add(new CardInfo("乳酸左氧氟沙星片", dragDesc, listTitle, listDesc, mDragPharmacyInfos));
        mDragInfos.add(new CardInfo("诺氟沙星片", dragDesc, listTitle, listDesc, mDragPharmacyInfos));
    }

    /**
     * 模拟药店列表
     */
    private void fakeDragPharmacyInfos() {
        mDragPharmacyInfos = new ArrayList<>();
        mDragPharmacyInfos.add(new PharmacyInfo("健客网", 9, "当天到货"));
        mDragPharmacyInfos.add(new PharmacyInfo("壹药网", 12, "约1天到货"));
        mDragPharmacyInfos.add(new PharmacyInfo("康美网", 14, "约1天到货"));
    }

    /**
     * 模拟检测项目列表
     */
    private void fakeCheckInfo() {
        String checkDesc = getString(R.string.check_desc);

        String listTitle = getString(R.string.check_hospital_list_title);
        String listDesc = getString(R.string.check_hospital_list_desc);
        String listPos = getString(R.string.check_hospital_list_pos);

        fakeCheckHospitalInfos();

        mCheckInfos = new ArrayList<>();
        mCheckInfos.add(new CardInfo("白带异常", checkDesc, listTitle, listDesc, listPos, mCheckHospitalInfos));
        mCheckInfos.add(new CardInfo("白细胞计数", checkDesc, listTitle, listDesc, listPos, mCheckHospitalInfos));
        mCheckInfos.add(new CardInfo("红细胞计数", checkDesc, listTitle, listDesc, listPos, mCheckHospitalInfos));
        mCheckInfos.add(new CardInfo("血红蛋白测定", checkDesc, listTitle, listDesc, listPos, mCheckHospitalInfos));
    }

    /**
     * 模拟医院信息列表
     */
    private void fakeCheckHospitalInfos() {
        mCheckHospitalInfos = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("三甲医院");
        tags.add("医保");
        String address = getString(R.string.check_hospital_address);
        String distance = "500m";
        String phone = getString(R.string.check_hospital_phone);
        String price = "999";
        mCheckHospitalInfos.add(new HospitalInfo("北京大学第三医院",
                tags, address, distance, phone, price));
        mCheckHospitalInfos.add(new HospitalInfo("北京大学第三医院",
                tags, address, distance, phone, price));
    }

    /**
     * 模拟手术项目信息
     */
    private void fakeOperationInfos() {
        String operationDesc = getString(R.string.operation_desc);

        String list1Title = getString(R.string.operation_hospital_list_title);
        String list1Desc = getString(R.string.operation_hospital_list_desc);
        String list1Pos = getString(R.string.operation_hospital_list_pos);

        fakeOperationHospitalInfos();

        String list2Title = getString(R.string.operation_hospital_list_2_title);

        fakeOperationDoctorInfos();

        mOperationInfos = new ArrayList<>();
        mOperationInfos.add(new CardInfo("阑尾切除术", operationDesc, list1Title, list1Desc, list1Pos, mOperationHospitalInfos,
                list2Title, mOperationDoctorInfos));
        mOperationInfos.add(new CardInfo("胆囊切除术", operationDesc, list1Title, list1Desc, list1Pos, mOperationHospitalInfos,
                list2Title, mOperationDoctorInfos));
        mOperationInfos.add(new CardInfo("胆囊造口术", operationDesc, list1Title, list1Desc, list1Pos, mOperationHospitalInfos,
                list2Title, mOperationDoctorInfos));
        mOperationInfos.add(new CardInfo("开腹探查术", operationDesc, list1Title, list1Desc, list1Pos, mOperationHospitalInfos,
                list2Title, mOperationDoctorInfos));

    }

    /**
     * 模拟手术项目中医院信息
     */
    private void fakeOperationHospitalInfos() {
        mOperationHospitalInfos = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("三甲医院");
        tags.add("医保");
        String address = getString(R.string.operation_hospital_address);
        String distance = "500m";
        String phone = getString(R.string.operation_hospital_phone);
        String price = "999-2016";
        mOperationHospitalInfos.add(new HospitalInfo("北京大学第三医院",
                tags, address, distance, phone, price));
        mOperationHospitalInfos.add(new HospitalInfo("北京大学第三医院",
                tags, address, distance, phone, price));
    }

    /**
     * 模拟手术项目中医生信息
     */
    private void fakeOperationDoctorInfos() {
        mOperationDoctorInfos = new ArrayList<>();
        mOperationDoctorInfos.add(new DoctorInfo("王坚韧", "皮肤科", "副主任医师", "中国人民解放军总医院(301医院)", ""));
        mOperationDoctorInfos.add(new DoctorInfo("王坚韧", "皮肤科", "副主任医师", "中国人民解放军总医院(301医院)", ""));
    }


    /**
     * 初始化并设置ActionBar
     */
    private void initActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        View view = getLayoutInflater().inflate(R.layout.action_bar_main, null);
        if (actionBar != null) {
            actionBar.setCustomView(view);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        // 获取返回按钮
        ImageView ivBack = (ImageView) view.findViewById(R.id.action_bar_iv_back);

        // 获取标题按钮
        TextView tvTitle = (TextView) view.findViewById(R.id.action_bar_tv_title);

        // 点击返回上一个页面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                finish();
            }
        });

        // 设置标题
        tvTitle.setText(title);
    }
}


