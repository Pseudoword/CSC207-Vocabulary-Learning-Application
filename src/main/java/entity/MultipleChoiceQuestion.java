package main.java.entity;

import main.java.entity.Vocabulary;

import java.util.List;

public class MultipleChoiceQuestion {
    private Vocabulary word;
    private List<String> choices;
    private int answerIndex;

    public MultipleChoiceQuestion(Vocabulary word, List<String> choices, int answerIndex){
        this.word = word;
        this.choices = choices;
        this.answerIndex = answerIndex;
    }

    public Vocabulary getWord() {
        return word;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public boolean checkAnswer(int userChoiceIndex){
        return userChoiceIndex == answerIndex;
    }

    public int getChoiceIndex(String userChoice) {
        return choices.indexOf(userChoice);
    }
}
