
package com.example.group14app;

public class Message {
    private String text;
    private boolean isSentByUser; // True if the user sent it, false if they received it

    public Message(String text, boolean isSentByUser) {
        this.text = text;
        this.isSentByUser = isSentByUser;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }
}
