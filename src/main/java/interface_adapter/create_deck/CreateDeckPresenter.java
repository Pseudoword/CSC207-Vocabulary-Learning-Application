package main.java.interface_adapter.create_deck;

import main.java.use_case.create_deck.CreateDeckOutputBoundary;
import main.java.use_case.create_deck.CreateDeckOutputData;

public class CreateDeckPresenter implements CreateDeckOutputBoundary {

    @Override
    public void prepareSuccessView(CreateDeckOutputData outputData) {
        System.out.println("Deck created: " + outputData.getTitle());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Failed to create deck: " + errorMessage);
    }
}
