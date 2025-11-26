package entity;

import java.util.List;

public class MultipleChoiceQuestion {
    private final Vocabulary word;
    private final List<String> choices;
    private final int answerIndex;

    public MultipleChoiceQuestion(Vocabulary word, List<String> choices, int answerIndex){
        this.word = word;
        this.choices = choices;
        this.answerIndex = answerIndex;
    }

    public String getWord() {
        return word.getWord();
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
}
