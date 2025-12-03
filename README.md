# Project Blueprint

## Project Specification for Group TUT5101-BA2200-18

* **Team Name:** Team 18
* **Domain:** Vocabulary Learning Application

### Overview

A desktop application to help users expand and reinforce vocabulary with flashcards, quizzes, and staged progression. Definitions are fetched via an external dictionary API.

---

## User Stories

1. As a user, I want to create an account and log in so my progress and decks are stored.
2. As a user, I want to have a main menu.
3. As a learner, I want to create decks to categorize vocabulary.
4. As a learner, I want to edit decks to remove or add vocabulary.
5. As a learner, I want to study the definitions of vocabulary words by selecting a deck of flashcards presented in order.
6. As a learner, I want to take multiple-choice quizzes by selecting a deck of multiple-choice questions to test my knowledge of vocabulary words.
7. As a learner, I want to master decks over time so I can see how much I’ve improved.

---

## Use Cases

### Use Case 1: Log in / Sign up

**Main flow**

* User enters the correct username and password.
* User logs in.

**Alternative flows**

* User creates a unique username and password(s).

  * User proceeds to login page.
* Incorrect password.

  * Prompt user to retry or create a new account.

---

### Use Case 2: Navigating Main Menu

**Main flow**

* User selects “new deck”.

  * User is sent to Use Case 3.

**Alternative flows**

* User selects “deck” → “study all”.

  * User is sent to Use Case 5.
* User selects “deck” → “edit”.

  * User is sent to Use Case 4.
* User selects “take quiz”.

  * User is sent to Use Case 6.

---

### Use Case 3: Create Decks

**Main flow**

* User enters deck name.
* User clicks “Create”.
* System stores the deck.

**Alternative flow**

* User selects “New Deck”.
* User clicks “exit”.

---

### Use Case 4: Edit Decks

*(two separate actions: add word, delete word)*

**Main flow (add word)**

* User clicks “add word”.
* User types desired word into search bar.
* System calls external API.
* System returns definition and adds it to the deck.

**Alternative flow (API failure)**

* User clicks “add word”.
* User types desired word into search bar.
* System calls external API.
* API call fails.
* User is prompted to type a definition and add it to the list.

**Alternative flow (delete word)**

* User clicks delete button for a word.
* Word is removed from the deck.

---

### Use Case 5: Study Definition from Flash Cards

**Main flow**

* System displays word.
* User is comfortable with definition.
* User selects “Next Word”.
* Next word is displayed.

**Alternative flow (flag for review)**

* System displays word.
* User finds word difficult.
* User marks the word by selecting “Flag”.
* Next word is displayed.

**Alternative flow (empty deck)**

* There are no cards in the deck.
* User is taken to “edit decks” view for the deck.
* System displays “no cards found” user is given the option to return to main menu.

---


### Use Case 6: Taking Multiple-Choice Quiz

**Main flow**

* System displays a word with multiple definition choices.
* User selects the correct definition for the given word.
* User selects “Next”.
* Next question pops up.
* User gets every question right.
* System stores current deck as “Mastered”.

**Alternative flow (incorrect answer)**

* System displays a word with multiple definitions.
* User selects the incorrect definition.
* System highlights the correct definition.
* User selects “Next”.
* Next question pops up.
* User is prompted to retake a quiz generated from incorrect answers.

---

## MVP

| Use Case                    | User Story | Lead        |
| --------------------------- |------------| ----------- |
| Log in / Sign up            | 1          | Jiamei      |
| Navigating main menu        | 2          | Jiamei      |
| Create Deck                 | 3          | Nerissa     |
| Edit Deck                   | 4          | Jinuo       |
| Study Flash Cards           | 5          | Kaiyang     |
| Taking Multiple-choice Quiz | 6          | Lan         |

---

## Proposed Entities for the Domain

* **User**

  * `username: String`
  * `password: String`
  * `reviewList: List<ReviewList>`

* **MultipleChoiceQuestion**

  * `word: Vocabulary`
  * `choices: List<String>`
  * `answer: String`

* **Vocabulary**

  * `word: String`
  * `definition: String`
  * `Flagged: Boolean`

* **Deck**

  * `Words: List<Vocabulary>`
  * `Mastered: Boolean`

---

## Proposed API for the Project

* **API Name:** Merriam-Webster’s Learner’s Dictionary with Audio
* **Link:** [https://dictionaryapi.com/products/api-learners-dictionary](https://dictionaryapi.com/products/api-learners-dictionary)
* **Main service:** Provides words with related definitions and other dictionary features.

---

