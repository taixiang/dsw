package com.dingshuwang.model;

/**
 * Created by Administrator on 2016/7/24.
 */
public class AddressDetailItem extends BaseItem {
   public Item address;

    public class Item{
        public String id;
        public String area_id;
        public String area;
        public String address;
        public String accept_name;
        public String phone;
    }
}
