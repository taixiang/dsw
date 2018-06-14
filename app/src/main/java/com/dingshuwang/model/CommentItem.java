package com.dingshuwang.model;

import java.util.List;

/**
 * Created by tx on 2017/6/16.
 */

public class CommentItem extends BaseItem {
    public String message;
    public boolean result;
    public List<Comment> Comment;
    public class Comment extends BaseItem{
        public String comment_content;
        public String add_time;
        public String innerid;
    }
}
