package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/8.
 */

public class HomeMiddleItem extends BaseItem {

    public boolean result;
    public List<HomeMiddle> data;

    public class HomeMiddle extends BaseItem{
        public String keywords;
        public String image_url;
        public String url;

    }


}
