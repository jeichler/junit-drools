package com.github.jeichler.junit.drools.model;

import org.junit.Assert;

public class Message {

    private String content, receiver;

    public Message(final String receiver) {
        Assert.assertNotNull(receiver);
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
