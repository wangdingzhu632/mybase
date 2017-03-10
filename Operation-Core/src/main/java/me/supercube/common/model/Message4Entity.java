package me.supercube.common.model;

/**
 * 返回消息对象
 *
 * Created by chenping on 16/8/24.
 */
public class Message4Entity<T> extends Message {

    private T entity;

    public Message4Entity() {
        super();
    }

    public Message4Entity(T entity) {
        this.entity = entity;
    }

    public Message4Entity(boolean success, String msg, String code, T entity) {
        super(success, msg, code);
        this.entity = entity;
    }


    public Message4Entity(Message message,T entity) {
        super(message.isSuccess(),message.getMsg(),message.getCode());
        this.entity = entity;
    }


    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
