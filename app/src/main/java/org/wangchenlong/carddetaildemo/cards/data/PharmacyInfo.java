package org.wangchenlong.carddetaildemo.cards.data;

import java.io.Serializable;

/**
 * 药店信息
 * <p/>
 * Created by wangchenlong on 16/8/25.
 */
public class PharmacyInfo implements Serializable {
    public PharmacyInfo(String title, int price, String time) {
        this.title = title;
        this.price = price;
        this.time = time;
    }

    public String title;
    public int price;
    public String time;
}
