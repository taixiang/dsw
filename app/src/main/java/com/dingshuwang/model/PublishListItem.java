package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/7/7.
 */

public class PublishListItem extends BaseItem {
    public boolean result;
    public List<Publish> trade_infor;
    public class Publish extends BaseItem{
        public String create_date;
        public String image_url;
        public String pro_name;
        public String pro_isbn;
        public String price_sell;
    }
}
