package data_access;

import entity.Deck;
import entity.MultipleChoiceQuestion;
import entity.Vocabulary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizDataAccessInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Data Access Object for Multiple Choice Quiz use case.
 * Provides questions and deck data from existing Deck objects.
 */
public class MultipleChoiceQuizDataAccessObject implements MultipleChoiceQuizDataAccessInterface {

    private final List<Deck> decks; // reference to all decks in your app

    public MultipleChoiceQuizDataAccessObject(List<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public Deck getDeck(String deckTitle) {
        for (Deck deck : decks) {
            if (deck.getTitle().equals(deckTitle)) {
                return deck;
            }
        }
        return null; // not found
    }

    @Override
    public List<MultipleChoiceQuestion> getQuestionsForDeck(String deckTitle) {
        Deck deck = getDeck(deckTitle);
        if (deck == null) {
            return new ArrayList<>();
        }

        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        Random random = new Random();

        // Gather all definitions
        List<String> allDefinitions = new ArrayList<>();
        for (Vocabulary vocab : deck.getVocabularies()) {
            allDefinitions.add(vocab.getDefinition());
        }

        // Generate questions
        for (Vocabulary vocab : deck.getVocabularies()) {
            // Get 3 random wrong answers
            List<String> wrongDefinitions = new ArrayList<>();
            for (String def : allDefinitions) {
                if (!def.equals(vocab.getDefinition())) {
                    wrongDefinitions.add(def);
                }
            }
            Collections.shuffle(wrongDefinitions, random);
            int numWrong = Math.min(3, wrongDefinitions.size());

            List<String> choices = new ArrayList<>();
            choices.add(vocab.getDefinition()); // correct
            choices.addAll(wrongDefinitions.subList(0, numWrong)); // wrong answers

            Collections.shuffle(choices, random); // shuffle choices

            int correctIndex = choices.indexOf(vocab.getDefinition());

            questions.add(new MultipleChoiceQuestion(vocab, choices, correctIndex));
        }

        return questions;
    }

    @Override
    public void saveDeck(Deck deck) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getTitle().equals(deck.getTitle())) {
                decks.set(i, deck);
                return;
            }
        }
        decks.add(deck);
    }
}