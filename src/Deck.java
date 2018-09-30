import java.util.ArrayList;

public class Deck {
    private final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private final String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
                            "Jack", "Queen", "King", "Ace"};
    ArrayList<String> deck = new ArrayList<>();

    Deck() {
        initialize();
    }
    /**
     * Initializes deck with all 52 cards
     */
    public void initialize() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                String name = values[i] + " of " + suits[j];
                deck.add(name);
            }
        }
    }

    /**
     *
     * @return number of cards left in deck
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     *
     * @return true if deck is empty, false if it still has cards
     */
    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    /**
     * Gets a random card from the current deck of cards
     * @return single card from deck
     */
    public String getRandomCard() {
        int index = (int)(Math.random() * ( deck.size() - 1 ) + 1);
        String card = deck.get(index);
        deck.remove(index);
        return card;
    }

    /**
     * Returns the numerical point value of each
     * @param randomCard card you want the value of
     * @return the point value of the card
     */
    public int getValue(String randomCard) {
        String[] cardString = randomCard.split(" ");
        if (cardString[0].length() == 1) {
            return Integer.parseInt(cardString[0]);
        } else {
            return 10;
        }

    }

    public boolean isAce(String randomCard) {
        String[] cardString = randomCard.split(" ");
        if (getValue(randomCard) == 10) {
            if (cardString[0].equalsIgnoreCase("ace")) {
                return true;
            }
        }
        return false;
    }
}