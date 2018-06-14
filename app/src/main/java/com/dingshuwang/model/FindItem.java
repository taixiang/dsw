package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/11.
 * user_name: "soul臧",
 university: "",
 pro_id: 170516150208134,
 ISBN: null,
 pro_name: "正版 药理学——第七版/本科学 朱依谆等 人民卫生出版社 97871171",
 price_sell: 19,
 image_url: "https://img.ali
 */

public class FindItem extends BaseItem {
    public String result;
    public List<Find> pros;
    public class Find extends BaseItem{
        public String user_name;
        public String university;
        public String pro_id;
        public String ISBN;
        public String pro_name;
        public String price_sell;
        public String image_url;
    }
}
