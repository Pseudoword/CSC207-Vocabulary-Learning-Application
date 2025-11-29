package use_case.StudyFlashCards;

import entity.Deck;
import entity.Vocabulary;

public class StudyFlashCardsInteractor implements StudyFlashCardsInputBoundary {
    private final StudyFlashCardsDataAccessInterface dataAccessObject;
    private final StudyFlashCardsOutputBoundary outputBoundary;
    private int index = 0;
    private boolean isWord = true;
    private boolean isFlag = false;
    private Deck deck;
    private String diplayText;
    public StudyFlashCardsInteractor(StudyFlashCardsDataAccessInterface dataAccessObject, StudyFlashCardsOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(StudyFlashCardsInputData inputData) { //first word when loaded
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        if (deck == null || deck.isEmpty()) {
            System.out.println("No deck found");
            // shouldn't be empty can't create empty deck
            outputBoundary.prepareFailureView("Deck is empty.");
            return;
        } else{
            this.index = 0;
            this.isWord = true;
            presentCard();
        }
        //System.out.println(inputData.getDeckName() + "interactor");
        //System.out.println(deck.getVocabularies().get(1) + "interactor");

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
        isFlag = !isFlag;
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        Vocabulary vocab = deck.getVocabularies().get(index);
        vocab.setFlagged(isFlag);
        presentCard();
    }

    public void reveal(StudyFlashCardsInputData inputData) {
        isWord = !isWord;
        deck = dataAccessObject.getDeck(inputData.getDeckName());
        presentCard();
    }

    @Override
    public void switchToReviewView() {
        // empty for now
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
        //System.out.println(output.getWord() + "interactor");
    }

}
