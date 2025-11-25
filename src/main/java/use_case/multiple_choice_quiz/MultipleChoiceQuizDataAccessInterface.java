package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import java.util.List;

public interface MultipleChoiceQuizDataAccessInterface {
    List<MultipleChoiceQuestion> getQuestionsForDeck(String deckTitle);
}
