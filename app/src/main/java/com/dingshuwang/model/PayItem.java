package com.dingshuwang.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PayItem extends BaseItem {

    public Order order;
    public class Order {
        public String order_no;
        public String express_fee;
        public String real_amount;
        public String order_amount;
        public String area;
        public String address;
        public String accept_name;
        public String telphone;
        public String order_status;
        public String express_title;
        public String point;
        public String favourable_amount;
        public String groups_favourable;
        public List<OrderGood> order_goods;
        public class OrderGood{
            public String goods_title;
            public String img_url;
            public String real_price;
            public String quantity;
        }


    }


}
