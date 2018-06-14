package com.dingshuwang.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AddressListItem extends BaseItem {
    public List<AddressItem> address;
    public class AddressItem extends BaseItem {
        public String id;
        public String area_id;
        public String area;
        public String address;
        public String accept_name;
        public String phone;
        public String is_default;
    }

}
