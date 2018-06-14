package com.dingshuwang.model;

/**
 * Created by Steven on 2015/10/23 0023.
 */
public class AccessTokenItem extends APIResult
{
    public AccessToken accessToken;

    public static class AccessToken extends BaseItem
    {
        public String accessToken;
        public String refreshToken;
        public String expiresIn;
        public String tokenType;
        public String uid;
    }

}
