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
}