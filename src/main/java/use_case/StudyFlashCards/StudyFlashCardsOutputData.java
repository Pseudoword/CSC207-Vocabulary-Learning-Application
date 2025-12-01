package use_case.StudyFlashCards;

public class StudyFlashCardsOutputData {
    private final String word;
    private final String defn;
    private final String displayText;
    private boolean flag;


    public StudyFlashCardsOutputData(String word, String defn, boolean flag, String displayText) {
        this.word = word;
        this.defn = defn;
        this.flag = flag;
        this.displayText = displayText;
    }
    public String getWord() {return word;}
    public String getDefn() {return defn;}
    public boolean isFlag() {return flag;}
    public String getDisplayText() {return displayText;}
}
