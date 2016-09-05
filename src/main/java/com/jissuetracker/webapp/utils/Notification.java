package com.jissuetracker.webapp.utils;

/**
 * Created by jovin on 27/6/16.
 */
public class Notification {

    private String type;
    private Integer toId;
    private Integer messageId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }


    @Override
    public String toString() {
        return "Notification{" +
                "type='" + type + '\'' +
                ", toId=" + toId +
                ", messageId=" + messageId +
                '}';
    }
}
