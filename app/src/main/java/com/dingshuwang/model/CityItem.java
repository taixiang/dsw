package com.dingshuwang.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 *  "id": "5",
 "province_name": "上海市",
 "City": [
 {
 "id": "51",
 "city_name": "上海市"
 }
 */
public class CityItem extends BaseItem {

    public String id;
    public String province_name;
    public List<CityName> City;

    public class CityName extends BaseItem {
        public String id;
        public String city_name;

        @Override
        public String toString() {
            return "CityName{" +
                    "id='" + id + '\'' +
                    ", city_name='" + city_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CityItem{" +
                "id='" + id + '\'' +
                ", province_name='" + province_name + '\'' +
                ", City=" + City +
                '}';
    }
}
