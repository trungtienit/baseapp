package com.example.trantien.appreview.mvp.question;

/**
 * Created by QuocTuyen on 5/26/2018.
 */

public class QuestionModel {
    public enum Answer{
        A,
        B,
        C,
        D
    }
    private String content;
    private Answer answer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public QuestionModel(String content, Answer answer) {
        this.content = content;
        this.answer = answer;
    }
}
