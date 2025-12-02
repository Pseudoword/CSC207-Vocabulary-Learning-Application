package use_case.StudyFlashCards;

import entity.Deck;
import entity.Vocabulary;

public class StudyFlashCardsInteractor implements StudyFlashCardsInputBoundary {
    private final StudyFlashCardsDataAccessInterface dataAccessObject;
    private final StudyFlashCardsOutputBoundary outputBoundary;
    private int index = 0;
    private boolean isWord = true;
    private Deck deck;
    private String diplayText;
    public StudyFlashCardsInteractor(StudyFlashCardsDataAccessInterface dataAccessObject,
                                     StudyFlashCardsOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(StudyFlashCardsInputData inputData) {
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        if (deck == null) {
            outputBoundary.prepareFailureView("noDeck");
        } else if (deck.isEmpty()) {
            outputBoundary.prepareFailureView("noWords");
        } else {
            this.index = 0;
            this.isWord = true;
            presentCard();
        }
    }

    public void next(StudyFlashCardsInputData inputData) {
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        if (index < deck.getVocabularies().size() - 1) {
            index++;
        }
        isWord = true;
        presentCard();
    }

    public void prev(StudyFlashCardsInputData inputData) {
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        if (index > 0) {
            index--;
        }
        isWord = true;
        presentCard();
    }

    public void flag(StudyFlashCardsInputData inputData) {
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        Vocabulary vocab = deck.getVocabularies().get(index);
        vocab.setFlagged(!vocab.getFlagged());
        presentCard();
    }

    public void reveal(StudyFlashCardsInputData inputData) {
        isWord = !isWord;
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        presentCard();
    }

    private void presentCard() {
        Vocabulary vocab = deck.getVocabularies().get(index);
        if (isWord) {diplayText = vocab.getWord();}
        else {diplayText = vocab.getDefinition();}
        StudyFlashCardsOutputData output =
                new StudyFlashCardsOutputData(
                        vocab.getWord(),
                        vocab.getDefinition(),
                        vocab.getFlagged(),
                        diplayText
                );
        outputBoundary.prepareSuccessView(output);
    }
}
