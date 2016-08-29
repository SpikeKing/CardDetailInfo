package org.wangchenlong.carddetaildemo.cards.data;

/**
 * 手术项目内部的医生信息
 * <p/>
 * Created by wangchenlong on 16/8/29.
 */
public class DoctorInfo {
    public DoctorInfo(String name, String clinic, String level, String hospital, String image) {
        this.name = name;
        this.clinic = clinic;
        this.level = level;
        this.hospital = hospital;
        this.image = image;
    }

    public String name;
    public String clinic;
    public String level;
    public String hospital;
    public String image;
}
