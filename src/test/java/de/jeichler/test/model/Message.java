package de.jeichler.test.model;

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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Message)) return false;

    final Message message = (Message) o;

    if (content != null ? !content.equals(message.content) : message.content != null) return false;
    if (!receiver.equals(message.receiver)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = content != null ? content.hashCode() : 0;
    result = 31 * result + receiver.hashCode();
    return result;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(final String receiver) {
    this.receiver = receiver;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }
}
