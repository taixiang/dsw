package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/15.
 id: 1100877128,
 name: "爱他，就去追 (韩)徐亨周 ,李小华 9787221067029",
 ISBN: "9787221067029",
 price_market: 19,
 price_sell: 12,
 img_url: "https://img.alicdn.com/bao/uploaded/i1/T1bPdnXpRhXXXXXXXX_!!0-item_pic.jpg",
 sale_nums: "5",
 goods_nums: "21"
 */

public class NovelItem extends BaseItem {
    public boolean result;
    public List<Novel> pros;
    public class Novel extends BaseItem{
        public String id;
        public String name;
        public String ISBN;
        public String price_market;
        public String price_sell;
        public String img_url;
        public String sale_nums;
        public String goods_nums;
    }
}
