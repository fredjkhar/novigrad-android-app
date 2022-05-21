package com.example.novigradv2.classes;

import java.io.Serializable;

public class Entry implements Serializable {
    private String description;
    private String answer;
    private String type;

    public Entry(String description, String answer, String type) {
        this.description = description;
        this.answer = answer;
        this.type = type;
    }

    public Entry() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
