package com.dingshuwang.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 *  "result": "true",
 "shops": [
 {
 "order_id_parent": 201607162044418600,
 "order_parent_amount": 39.6,
 "status": 1,
 "Order": [
 {
 "id": 201607162044418600,
 "supplier_id": 1,
 "supplier_name": "丁书网金牌卖家",
 "add_time": "2016-07-16T20:44:41",
 "order_amount": 22.6,
 "express_fee": 8,
 "express_no": "",
 "express_str": "-",
 "status": 1,
 "status_str": "待付款",
 "payment_status": 1,
 "payment_status_str": "等待付款",
 "express_status": 1,
 "express_status_str": "等待发货",
 "Goods": null
 */
public class WaitPayItem extends BaseItem {

    public List<ShopItem> shops;
    public class ShopItem {
        public String order_id_parent;
        public String order_parent_amount;
        public List<OrderItem> Order;
        public class OrderItem {
            public String id;
            public String supplier_name;
            public String add_time;
            public String order_amount;
            public String status_str;
            public List<Goods> order_goods;
            public class Goods{
                public String goods_id;
                public String goods_title;
                public String img_url;
                public String quantity;
                public String price_sell;

            }
        }
    }
}
