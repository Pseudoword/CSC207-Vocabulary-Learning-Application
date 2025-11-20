package main.java.use_case.create_deck;

public interface CreateDeckOutputBoundary {
    void prepareSuccessView(CreateDeckOutputData outputData);
    void prepareFailView(String errorMessage);
}
