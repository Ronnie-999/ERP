package de.buw.se;

public class Queries {
    private String question;
    private String answer;
    private String userId;
    private boolean isAnswered;

    // Constructor
    public Queries(String question, String answer, String userId, boolean isAnswered) {
        this.question = question;
        this.answer = answer;
        this.userId = userId;
        this.isAnswered = isAnswered;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answer) {
        isAnswered = answer;
    }
}

