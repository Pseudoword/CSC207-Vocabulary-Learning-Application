package interface_adapter.StudyFlashCards;

public class StudyFlashCardsState {
    private String displayText;
    private String word;
    private String defn;
    private boolean flag;
    private String error = null;
    private String successMessage = null;
    private String deckName;

    public StudyFlashCardsState(StudyFlashCardsState copy){
        this.word = copy.word;
        this.defn = copy.defn;
        this.flag = false;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
    }
    public StudyFlashCardsState(){}

    public String getWord() {return word;}
    public void setWord(String word) {this.word = word;}
    public void setDeckName(String deckName) {this.deckName = deckName;}
    public String getDeckName(){return deckName;}
    public String getDefn() {return defn;}
    public void setDefn(String defn) {this.defn = defn;}
    public boolean isFlag() {return flag;}
    public void setFlag(boolean flag) {this.flag = flag;}
    public String getDisplayText() {return displayText;}
    public void setDisplayText(String displayText) {this.displayText = displayText;}
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

}
