package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/13.
 * row_number: 1,
 id: 1606271755125910,
 ISBN: "9787040360530销售:522;库存:909",
 name: "经济管理基础 单大明 9787040360530",
 price_market: 13,
 price_sell: 7,
 img_url: "http://imgthird.iisbn.com/img/1213303a30dtb2pgqxx___281611390.jpg",
 infor: "",
 sale_nums: 522,
 goods_nums: 909
 */

public class SearchItem extends BaseItem {
    public String result;
    public List<Search> pros;
    public class Search extends BaseItem{
        public String id;
        public String ISBN;
        public String name;
        public String price_market;
        public String price_sell;
        public String img_url;
        public String infor;
        public String sale_nums;
        public String goods_nums;
    }
}
