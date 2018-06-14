package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/8.
 */



public class HomeFreeItem extends BaseItem {
    public boolean result;
    public List<HomeFree> data;
    public class HomeFree extends BaseItem{
        public String id;
        public int type;
        public String name;
        public String price_market;
        public String price_sell;
        public String img_url;
    }
}
