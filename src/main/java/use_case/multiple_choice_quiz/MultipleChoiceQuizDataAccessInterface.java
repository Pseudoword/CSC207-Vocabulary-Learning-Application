package use_case.multiple_choice_quiz;

import entity.Deck;
import entity.MultipleChoiceQuestion;
import java.util.List;

/**
 * Data Access Interface for the Multiple Choice Quiz use case.
 * Defines methods for retrieving quiz data.
 */
public interface MultipleChoiceQuizDataAccessInterface {

    /**
     * Get a deck by its title.
     * @param deckTitle the title of the deck
     * @return the Deck object, or null if not found
     */
    Deck getDeck(String deckTitle);

    /**
     * Get all questions for a given deck.
     * @param deckTitle the title of the deck
     * @return a list of multiple choice questions generated from the deck's vocabulary
     */
    List<MultipleChoiceQuestion> getQuestionsForDeck(String deckTitle);

    /**
     * Save a deck (updates vocabulary incorrect flags and quiz taken status).
     * @param deck the deck to save
     */
    void saveDeck(Deck deck);
}
