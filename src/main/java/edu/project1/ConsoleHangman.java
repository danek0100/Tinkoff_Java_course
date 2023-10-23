package edu.project1;

/**
 * Console-based implementation of the Hangman game.
 * This class provides methods to interact with the user and manage the game session.
 */
public class ConsoleHangman {
    private final Dictionary dictionary;
    private static final InputReader INPUT_READER = new InputReader();
    private final Output logger;

    /**
     * Constructor for the ConsoleHangman game.
     *
     * @param dictionary The dictionary from which words are chosen.
     */
    public ConsoleHangman(Dictionary dictionary) {
        this.dictionary = dictionary;
        logger = new StandardOutput();
    }

    /**
     * Constructor for the ConsoleHangman game.
     *
     * @param dictionary The dictionary from which words are chosen.
     * @param logger The logger to output text.
     */
    public ConsoleHangman(Dictionary dictionary, Output logger) {
        this.dictionary = dictionary;
        this.logger = logger;
    }

    /**
     * Display the initial menu to the user.
     */
    private void showInitialMenu() {
        logger.log("> Welcome to Hangman!");
        logger.log("> 1. View rules");
        logger.log("> 2. Start game");
        logger.log("> Enter your choice (1/2): ");
    }

    /**
     * Display the rules of the Hangman game to the user.
     */
    private void showRules() {
        logger.log("> Rules of the Hangman game:");
        logger.log("> 1. A word is selected at random.");
        logger.log("> 2. You have to guess the word letter by letter.");
        logger.log("> 3. You can give up anytime by typing 'give_up'.");
        logger.log("> 4. Enjoy the game!\n");
    }

    /**
     * Begin a new game session. This method initializes a session, interacts with the user,
     * and controls the game flow.
     */
    public void run() {
        logger.log("> Word loading...");
        try {
            Session session = new Session(dictionary.randomWord());
            playGame(session);
        } catch (IllegalArgumentException e) {
            logger.log("> Error: " + e.getMessage());
        }
    }

    /**
     * Manage a game session. This method prompts the user for guesses and updates
     * the game state until the game is won or lost.
     *
     * @param session The current game session.
     */
    private void playGame(Session session) {
        GuessResult result = null;
        String input;
        do {
            logger.log("> Guess a letter: ");
            try {
                input = INPUT_READER.readUserSingleCharInput();
                result = tryGuess(session, input);
                printState(result);
            } catch (IllegalArgumentException e) {
                logger.log(e.getMessage());
            }
        } while (!(result instanceof GuessResult.PlayerWin) && !(result instanceof GuessResult.PlayerDefeat));
    }

    private GuessResult tryGuess(Session session, String input) {
        if ("give_up".equals(input)) {
            return session.giveUp();
        }
        return session.guess(input.charAt(0));
    }

    private void printState(GuessResult guess) {
        logger.log("> " + guess.message());
        logger.log("> The word: " + new String(guess.state()));
    }

    private boolean askPlayAgain() {
        logger.log("> Do you want to play again? (yes/no): ");
        String response = INPUT_READER.readUserInput().trim().toLowerCase();
        return "yes".equals(response);
    }

    /**
     * The main method to start the Hangman game.
     *
     * @param args Command line arguments. Optionally, the path to a dictionary file can be provided.
     */
    public void execute(String[] args) {
        ConsoleHangman game;
        if (args.length == 1) {
            game = new ConsoleHangman(new FileDictionary(args[0]));
        } else {
            game = new ConsoleHangman(new SimpleDictionary());
        }

        showInitialMenu();

        String choice = INPUT_READER.readUserInput();
        if ("1".equals(choice)) {
            showRules();
        } else if (!"2".equals(choice)) {
            logger.log("> Invalid choice. Exiting.");
            return;
        }

        do {
            game.run();
        } while (game.askPlayAgain());
    }
}
