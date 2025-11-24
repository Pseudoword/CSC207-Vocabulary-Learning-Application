package main.java.interface_adapter.multiple_choice_quiz;

import java.util.List;

public class MultipleChoiceQuizViewModel {
    private String currentWord;
    private List<String> choices;
    private String correctAnswer;
    private boolean quizFinished = false;

    public String getCurrentWord() { return currentWord; }
    public List<String> getChoices() { return choices; }
    public String getCorrectAnswer() { return correctAnswer; }
    public boolean isQuizFinished() { return quizFinished; }

    public void setCurrentWord(String currentWord) { this.currentWord = currentWord; }
    public void setChoices(List<String> choices) { this.choices = choices; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public void setQuizFinished(boolean quizFinished) { this.quizFinished = quizFinished; }
}
