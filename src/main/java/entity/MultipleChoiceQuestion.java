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

    public String getAnswerIndex() {
        return answerIndex;
    }

    public boolean checkAnswer(int userChoiceIndex){
        return userChoiceIndex == answerIndex;
    }

    public void displayQuestion() {
        System.out.println("What is the definition of: " + word.getWord() + "?");
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i)); //prints options as "1. choice" "2. choice" etc.
        }
    }

    public int getChoiceIndex(String userChoice) {
        return choices.indexOf(userChoice);
    }
}
