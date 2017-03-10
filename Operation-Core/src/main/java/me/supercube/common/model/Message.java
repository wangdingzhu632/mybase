package me.supercube.common.model;

import org.springframework.data.domain.Page;

/**
 * 操作结果消息对象
 *
 * @author chenping
 */
public class Message {

    public static final String SUCCESS = "操作成功";
    public static final String FAIL = "操作失败";

    private String code; //错误码,0为成功
    private boolean success;
    private String msg;


    public Message() {
    }

    public Message(boolean success, String msg, String code) {
        this.success = success;
        this.msg = msg;
        if (this.msg == null) {
            if (Boolean.FALSE.equals(success)) {
                this.msg = Message.FAIL;
            }
            if (Boolean.TRUE.equals(success)) {
                this.msg = Message.SUCCESS;
            }
        }
        if (Boolean.FALSE.equals(success)) {
            this.code = code;
        }

    }


    public static Message fail() {
        return fail(null);
    }

    public static Message fail(String msg) {
        return new Message(false, msg, "1");
    }

    public static Message fail(String msg, String code) {
        return new Message(false, msg, code);
    }


    public static Message success() {
        return success(null);
    }

    public static Message success(String msg) {
        return new Message(true, msg,"0");
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
