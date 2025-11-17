# Dictionary API Integration Guide

## Overview
This project now includes integration with the Merriam-Webster Learner's Dictionary API to fetch word definitions for the vocabulary learning application.

## API Information
- **API Provider**: Merriam-Webster
- **API Type**: Learner's Dictionary
- **Documentation**: https://dictionaryapi.com/products/api-learners-dictionary
- **Base URL**: `https://dictionaryapi.com/api/v3/references/learners/json/`

## Setup Instructions

### 1. Get an API Key
1. Visit https://dictionaryapi.com/
2. Click "Sign Up" to create a free account
3. After logging in, go to "My Keys"
4. Subscribe to the **Learner's Dictionary** API (free tier available)
5. Copy your API key

### 2. Set Environment Variable
Set your API key as an environment variable named `DICTIONARY_API_KEY`:

**macOS/Linux:**
```bash
export DICTIONARY_API_KEY=your-api-key-here
```

**Windows (Command Prompt):**
```cmd
set DICTIONARY_API_KEY=your-api-key-here
```

**Windows (PowerShell):**
```powershell
$env:DICTIONARY_API_KEY="your-api-key-here"
```

**Permanent Setup (macOS/Linux):**
Add to your `~/.zshrc` or `~/.bashrc`:
```bash
echo 'export DICTIONARY_API_KEY=your-api-key-here' >> ~/.zshrc
source ~/.zshrc
```

### 3. Install Dependencies

This project uses Maven for dependency management. The required dependencies are:
- **OkHttp** (version 4.12.0) - for HTTP requests
- **org.json** (version 20240303) - for JSON parsing

To install dependencies:
```bash
mvn clean install
```

If you're using IntelliJ IDEA, it should automatically detect the `pom.xml` and download dependencies.

## Usage Examples

### Basic Word Lookup

```java
import data_access.DictionaryAPIDataAccess;
import entity.Vocabulary;

public class Example {
    public static void main(String[] args) {
        DictionaryAPIDataAccess api = new DictionaryAPIDataAccess();
        
        // Get a word definition
        Vocabulary word = api.getWordDefinition("hello");
        System.out.println("Word: " + word.getWord());
        System.out.println("Definition: " + word.getDefinition());
        System.out.println("Flagged: " + word.getFlagged());
    }
}
```

### Multiple Words Lookup

```java
import data_access.DictionaryAPIDataAccess;
import entity.Vocabulary;
import java.util.Arrays;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        DictionaryAPIDataAccess api = new DictionaryAPIDataAccess();
        
        // Get multiple word definitions
        List<String> words = Arrays.asList("apple", "banana", "orange");
        List<Vocabulary> vocabularies = api.getAllDefinitions(words);
        
        for (Vocabulary vocab : vocabularies) {
            System.out.println(vocab.getWord() + ": " + vocab.getDefinition());
        }
    }
}
```

### Detailed Definition

```java
import data_access.DictionaryAPIDataAccess;
import entity.Vocabulary;

public class Example {
    public static void main(String[] args) {
        DictionaryAPIDataAccess api = new DictionaryAPIDataAccess();
        
        // Get all definitions for a word (not just the first one)
        Vocabulary word = api.getDetailedDefinition("run");
        System.out.println("Word: " + word.getWord());
        System.out.println("All Definitions: " + word.getDefinition());
        // Output will show multiple definitions separated by semicolons
    }
}
```

## Class: DictionaryAPIDataAccess

### Methods

#### `Vocabulary getWordDefinition(String word)`
Fetches the first definition for a given word.

**Parameters:**
- `word` - the word to look up

**Returns:**
- `Vocabulary` object containing the word, its first definition, and flagged status (false by default)

**Throws:**
- `RuntimeException` if the word is not found or the API call fails

#### `List<Vocabulary> getAllDefinitions(List<String> words)`
Fetches definitions for multiple words.

**Parameters:**
- `words` - list of words to look up

**Returns:**
- `List<Vocabulary>` containing all successfully fetched word definitions

**Note:** If a word is not found, it will be skipped and a warning will be printed to stderr.

#### `Vocabulary getDetailedDefinition(String word)`
Fetches all available definitions for a word and combines them into one string.

**Parameters:**
- `word` - the word to look up

**Returns:**
- `Vocabulary` object with all definitions combined (separated by semicolons)

**Throws:**
- `RuntimeException` if the word is not found or the API call fails

## API Response Format

The Merriam-Webster Learner's Dictionary API returns JSON data in the following format:

```json
[
  {
    "meta": {
      "id": "hello",
      "uuid": "...",
      "src": "learners",
      "section": "alpha",
      "stems": ["hello", "hellos"],
      "offensive": false
    },
    "hwi": {
      "hw": "hel*lo",
      "prs": [...]
    },
    "fl": "noun",
    "shortdef": [
      "an expression or gesture of greeting",
      "a response on the telephone"
    ]
  }
]
```

Our implementation extracts data from the `shortdef` array.

## Testing

Run the tests to verify the integration:

```bash
mvn test
```

**Note:** Tests require:
1. Active internet connection
2. Valid API key set as environment variable
3. API rate limits not exceeded

## Error Handling

The API handles the following error scenarios:

1. **Word Not Found**: Throws `RuntimeException` with suggestion message
2. **Network Error**: Throws `RuntimeException` with error details
3. **Invalid API Response**: Throws `RuntimeException` with error message
4. **Missing API Key**: Returns `null` from `getAPIKey()` method

## API Rate Limits

Free tier limits (as of 2024):
- 1,000 requests per day
- Consider implementing caching for production use

## Integration with Vocabulary Learning App

The `DictionaryAPIDataAccess` class can be used to:
1. Automatically fetch definitions when users add new words
2. Populate decks with vocabulary
3. Verify word spellings
4. Provide learning materials

Example integration:
```java
public Deck createDeckWithDefinitions(String title, List<String> words) {
    Deck deck = new Deck(title, "Auto-generated deck");
    DictionaryAPIDataAccess api = new DictionaryAPIDataAccess();
    
    List<Vocabulary> vocabularies = api.getAllDefinitions(words);
    for (Vocabulary vocab : vocabularies) {
        deck.addWord(vocab);
    }
    
    return deck;
}
```

## Troubleshooting

### "Cannot resolve symbol" errors
- Run `mvn clean install` to download dependencies
- Reload Maven project in IntelliJ IDEA

### "API key not set" error
- Verify environment variable: `echo $DICTIONARY_API_KEY`
- Restart your IDE after setting the environment variable

### "Word not found" errors
- Check word spelling
- Try the word at https://learnersdictionary.com/ to verify it exists
- Some proper nouns and very rare words may not be in the learner's dictionary

### Connection errors
- Check internet connection
- Verify the API is accessible: `curl https://dictionaryapi.com/`
- Check if you've exceeded your API rate limit

## Additional Resources

- [Merriam-Webster API Documentation](https://dictionaryapi.com/products/api-learners-dictionary)
- [API Usage Examples](https://dictionaryapi.com/products/json)
- [OkHttp Documentation](https://square.github.io/okhttp/)

