package org.wangchenlong.carddetaildemo.cards.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 卡片详情
 * <p/>
 * Created by wangchenlong on 16/8/24.
 */
public class CardInfo implements Serializable {
    // 药物信息的初始化
    public CardInfo(String title, String desc, String listTitle,
                    String listDesc, ArrayList<PharmacyInfo> pharmacyList) {
        this.title = title;
        this.desc = desc;
        this.listTitle = listTitle;
        this.listDesc = listDesc;
        this.pharmacyList = pharmacyList;
    }

    // 检查项目的初始化
    public CardInfo(String title, String desc, String listTitle,
                    String listDesc, String listPos, ArrayList<HospitalInfo> hospitalList) {
        this.title = title;
        this.desc = desc;
        this.listTitle = listTitle;
        this.listDesc = listDesc;
        this.listPos = listPos;
        this.hospitalList = hospitalList;
    }

    // 手术项目的初始化
    public CardInfo(String title, String desc, String listTitle,
                    String listDesc, String listPos,
                    ArrayList<HospitalInfo> hospitalList,
                    String list2Title,
                    ArrayList<DoctorInfo> doctorList) {
        this.title = title;
        this.desc = desc;
        this.listTitle = listTitle;
        this.listDesc = listDesc;
        this.listPos = listPos;
        this.hospitalList = hospitalList;
        this.list2Title = list2Title;
        this.doctorList = doctorList;
    }

    public String title; // 项名称
    public String desc; // 项描述
    public String listTitle; // 列表标题
    public String listDesc; // 列表描述
    public String listPos; // 列表地区
    public String list2Title; // 列表2标题
    public ArrayList<PharmacyInfo> pharmacyList; // 药店的列表
    public ArrayList<HospitalInfo> hospitalList; // 医院列表
    public ArrayList<DoctorInfo> doctorList; // 医生列表
}
