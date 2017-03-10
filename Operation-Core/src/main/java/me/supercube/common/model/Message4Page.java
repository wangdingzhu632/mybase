package me.supercube.common.model;

import org.springframework.data.domain.Page;

/**
 * 返回消息分页
 *
 * Created by chenping on 16/8/24.
 */
public class Message4Page<T> extends Message {

    private Page<T> data;

    public Message4Page(Message message,Page<T> page) {
        super(message.isSuccess(),message.getMsg(),message.getCode());
        this.data = page;
    }

    public Message4Page(Page<T> data) {
        this.data = data;
    }

    public Message4Page(boolean success, String msg, String code, Page<T> data) {
        super(success, msg, code);
        this.data = data;
    }

    public Page<T> getData() {
        return data;
    }

    public void setData(Page<T> data) {
        this.data = data;
    }
}
