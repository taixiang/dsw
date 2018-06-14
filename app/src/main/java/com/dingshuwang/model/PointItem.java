package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/22.
 */

public class PointItem extends BaseItem {
    public boolean result;
    public List<Point> message;
    public class Point extends BaseItem{
        public double value;
        public String remark;
        public String add_time;
    }
}
