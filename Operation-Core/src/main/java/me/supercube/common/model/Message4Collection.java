package me.supercube.common.model;

import com.alibaba.fastjson.JSON;

import java.util.Collection;

/**
 * 返回消息集合
 *
 * Created by chenping on 16/8/24.
 */
public class Message4Collection<T> extends Message {

    private Collection<T> entities;


    public Message4Collection(Collection<T> entities) {
        this.entities = entities;
    }

    public Message4Collection(boolean success, String msg, String code, Collection<T> entities) {
        super(success, msg, code);
        this.entities = entities;
    }

    public Message4Collection(Message message,Collection<T> entities) {
        super(message.isSuccess(),message.getMsg(),message.getCode());
        this.entities = entities;
    }

    public static void main(String[] args) {
        String s = JSON.toJSONString(Message4Collection.success());

        System.out.println(s);

    }

    public Collection<T> getEntities() {
        return entities;
    }

    public void setEntities(Collection<T> entities) {
        this.entities = entities;
    }
}
