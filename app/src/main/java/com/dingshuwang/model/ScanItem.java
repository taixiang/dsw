package com.dingshuwang.model;

/**
 * @author tx
 * @date 2018/6/15
 */
public class ScanItem {


    /**
     * result : true
     * pros : {"row_number":1,"id":0,"ISBN":"9787810666398","name":"二手正版 蔬菜栽培学/面向21世纪课程教材 9787810666398","price_market":0,"price_sell":0,"img_url":"http://imgthird.iisbn.com/img/047207feaetb2ufcxxa___12801942.jpg","infor":"2008年06月","sale_nums":27,"goods_nums":12}
     */

    private String result;
    private ProsBean pros;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ProsBean getPros() {
        return pros;
    }

    public void setPros(ProsBean pros) {
        this.pros = pros;
    }

    public static class ProsBean {
        /**
         * row_number : 1
         * id : 0
         * ISBN : 9787810666398
         * name : 二手正版 蔬菜栽培学/面向21世纪课程教材 9787810666398
         * price_market : 0
         * price_sell : 0
         * img_url : http://imgthird.iisbn.com/img/047207feaetb2ufcxxa___12801942.jpg
         * infor : 2008年06月
         * sale_nums : 27
         * goods_nums : 12
         */

        private String row_number;
        private String id;
        private String ISBN;
        private String name;
        private String price_market;
        private String price_sell;
        private String img_url;
        private String infor;
        private String sale_nums;
        private String goods_nums;

        public String getRow_number() {
            return row_number;
        }

        public void setRow_number(String row_number) {
            this.row_number = row_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getISBN() {
            return ISBN;
        }

        public void setISBN(String ISBN) {
            this.ISBN = ISBN;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice_market() {
            return price_market;
        }

        public void setPrice_market(String price_market) {
            this.price_market = price_market;
        }

        public String getPrice_sell() {
            return price_sell;
        }

        public void setPrice_sell(String price_sell) {
            this.price_sell = price_sell;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getInfor() {
            return infor;
        }

        public void setInfor(String infor) {
            this.infor = infor;
        }

        public String getSale_nums() {
            return sale_nums;
        }

        public void setSale_nums(String sale_nums) {
            this.sale_nums = sale_nums;
        }

        public String getGoods_nums() {
            return goods_nums;
        }

        public void setGoods_nums(String goods_nums) {
            this.goods_nums = goods_nums;
        }
    }
}
