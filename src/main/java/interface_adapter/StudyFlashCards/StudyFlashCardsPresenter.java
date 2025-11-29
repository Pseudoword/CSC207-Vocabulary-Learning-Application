package interface_adapter.StudyFlashCards;

import interface_adapter.ViewManagerModel;
import use_case.StudyFlashCards.StudyFlashCardsOutputBoundary;
import use_case.StudyFlashCards.StudyFlashCardsOutputData;

public class StudyFlashCardsPresenter implements StudyFlashCardsOutputBoundary {
    private final StudyFlashCardsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public StudyFlashCardsPresenter(ViewManagerModel viewManagerModel, StudyFlashCardsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(StudyFlashCardsOutputData outputData){
        final StudyFlashCardsState state = viewModel.getState();
        state.setDisplayText(outputData.getDisplayText());
        state.setWord(outputData.getWord());
        state.setDefn(outputData.getDefn());
        state.setFlag(outputData.isFlag());


        viewModel.setWord(outputData.getWord());
        viewModel.setDefn(outputData.getDefn());
        viewModel.setFlag(outputData.isFlag());
        viewModel.setState(state);
        viewModel.firePropertyChange("state");


        viewManagerModel.setState("StudyFlashCards");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String error){
        final StudyFlashCardsState state = viewModel.getState();

        state.setError(error);

        viewModel.setState(state);
        viewModel.firePropertyChange("state");
    }
}
