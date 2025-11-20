package main.java.view;

import main.java.entity.MultipleChoiceQuestion;
import main.java.entity.Vocabulary;

import java.util.List;
import java.util.Scanner;

public class QuizView{
    private Scanner scanner;

    public QuizView(){
        scanner = new Scanner(System.in);
    }

    //Displays a multiple-choice question and returns the user's selected index
    public int askQuestion(MultipleChoiceQuestion question){
        System.out.println("\nWhat is the definition of: " + question.getWord().getWord() + "?");

        List<String> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++){
            System.out.println((i + 1) + ". " + choices.get(i));
        }

        int userChoice = -1;
        while (userChoice < 1 || userChoice > choices.size()){
            System.out.print("Enter your choice (1-" + choices.size() + "): ");
            if (scanner.hasNextInt()) {
                userChoice = scanner.nextInt();
            } else {
                scanner.next(); // discard invalid input
            }
        }

        return userChoice -1;
    }

    public void showCorrectFeedback() {
        System.out.println("Correct!\n");
    }

    public void showCorrectAnswer(MultipleChoiceQuestion question) {
        System.out.println("Incorrect! Correct answer: " +
                question.getChoices().get(question.getAnswerIndex()) + "\n");
    }

    public void showFinalResult(List<Vocabulary> incorrectWords) {
        if (incorrectWords.isEmpty()) {
            System.out.println("Congratulations! You mastered this deck.\n");
        } else {
            System.out.println("You have some words to review:");
            for (Vocabulary vocab : incorrectWords) {
                System.out.println("- " + vocab.getWord());
            }
            System.out.println();
        }
    }
}