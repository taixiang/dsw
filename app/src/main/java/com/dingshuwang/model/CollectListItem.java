package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/7/7.
 */

public class CollectListItem extends BaseItem {
    public boolean result;
    public List<CollectList> favorites;
    public class CollectList extends BaseItem{
        public String pro_id;
        public String name;
        public String ISBN;
        public String img_url;
        public String create_time;

    }
}
