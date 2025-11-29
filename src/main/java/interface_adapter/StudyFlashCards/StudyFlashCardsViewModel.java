package interface_adapter.StudyFlashCards;

import interface_adapter.ViewModel;

public class StudyFlashCardsViewModel extends ViewModel<StudyFlashCardsState> {
    private String word;
    private String defn;
    private boolean flag ;

    public StudyFlashCardsViewModel() {
        super("Study Flash Cards");
        setState(new StudyFlashCardsState());
    }
    public void setWord(String word) {this.word = word;}
    public String getWord() {return word;}
    public void setDefn(String defn){this.defn = defn;}
    public String getDefn() {return defn;}
    public void setFlag(boolean flag) {this.flag = flag;}
    public boolean getFlag() {return flag;}

}
