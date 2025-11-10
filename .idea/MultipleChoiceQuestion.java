import java.util.List;

public class MultipleChoiceQuestion {
    private Vocabulary word;
    private List<String> choices;
    private String answer;

    public MultipleChoiceQuestion(Vocabulary word, List<String> choices, String answer){
        this.word = word;
        this.choices = choices;
        this.answer = answer;
    }

    public Vocabulary getWord() {
        return word;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean checkAnswer(String userChoice){
        return answer.equals(userChoice);
    }

    public void displayQuestion() {
        System.out.println("What is the definition of: " + word.getWord() + "?");
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i)); //prints options as "1. choice" "2. choice" etc.
        }
    }

    public int getCorrectChoiceIndex() {
        return choices.indexOf(answer);
    }

    public int getChoiceIndex(String userChoice) {
        return choices.indexOf(userChoice);
    }
}