package use_case.StudyFlashCards;

import entity.Deck;

public interface StudyFlashCardsDataAccessInterface {
    Deck getDeck(String deckName);
}
