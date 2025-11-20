package main.java.use_case;

import main.java.entity.Deck;
import main.java.entity.Vocabulary;
import main.java.entity.MultipleChoiceQuestion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizUseCase {
    private Deck deck;
    private List<Vocabulary> incorrectWords;

    public QuizUseCase(Deck deck) {
        this.deck = deck;
        this.incorrectWords = new ArrayList<>();
    }

    public List<MultipleChoiceQuestion> generateQuestion(){
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        List<Vocabulary> vocabularies = deck.getVocabularies();
        Random rand  = new Random();

        for (Vocabulary vocab :  vocabularies) {
            List<String> choices = new ArrayList<>();
            choices.add(vocab.getDefinition());

            // pick random wrong definitions
            while (choices.size() < 4 && vocabularies.size() > 1) {
                Vocabulary randomVocab = vocabularies.get(rand.nextInt(vocabularies.size()));
                if (!randomVocab.equals(vocab) && !choices.contains(randomVocab.getDefinition())) {
                    choices.add(randomVocab.getDefinition());
                }
            }

            Collections.shuffle(choices);
            int answerIndex = choices.indexOf(vocab.getDefinition());
            questions.add(new MultipleChoiceQuestion(vocab, choices, answerIndex));
        }

        return questions;
    }

    public void markIncorrect(Vocabulary vocab){
        incorrectWords.add(vocab);
        vocab.setFlagged(true);
    }

    public List<Vocabulary> getIncorrectWords(){
        return incorrectWords;
    }

    public void markDeckIfMastered() {
        if (incorrectWords.isEmpty()) {
            deck.markAsMastered();
        }
    }
}