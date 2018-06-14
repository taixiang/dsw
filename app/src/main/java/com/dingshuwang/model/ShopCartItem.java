package com.dingshuwang.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ShopCartItem extends BaseItem {
    public List<ShopsItem> shops;
    public class ShopsItem extends BaseItem {
        public String supplier_id;
        public String supplier_name;
        public List<ShopItem> Shop;


    }

    public class ShopItem{
        public String innerid;
        public String user_id;
        public String pro_id;
        public String pro_name;
        public String price_sell;
        public int available_nums;
        public String img_url;
        public String order_number;
        public String remark;
        public String point;
        public String supplier_id;

    }

}
