package org.wangchenlong.carddetaildemo.cards.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 医院信息
 * <p/>
 * Created by wangchenlong on 16/8/29.
 */
public class HospitalInfo implements Serializable {
    public HospitalInfo(String name, ArrayList<String> tags, String address,
                        String distance, String phone, String price) {
        this.name = name;
        this.tags = tags;
        this.address = address;
        this.distance = distance;
        this.phone = phone;
        this.price = price;
    }

    public String name;
    public ArrayList<String> tags;
    public String address;
    public String distance;
    public String phone;
    public String price;
}
