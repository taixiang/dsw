package com.dingshuwang.model;

import java.util.List;

/**
 */
public class CouponsItem extends BaseItem {
    public boolean result;
    public List<Coupon> coupons;
    public class Coupon {
        public String special_price;
        public String coupons_name;
        public String innerid;
        public String start_date;
        public String end_date;
    }
}
