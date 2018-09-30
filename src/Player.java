import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<String> cards = new ArrayList<>();
    double money = 100.00;
    double bet = 0;
    int sum = 0;
    boolean blackjack = false;


    /**
     * Constructer initializes Person's name
     */
    Player(String setName) {
        name = setName;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getMoney() {
        return money;
    }

    /**
     * The player draws a new card from the deck and increases their sum.
     * @param deck deck to draw card from
     * @return the newly drawn card
     */
    public String draw(Deck deck) {
        String card = deck.getRandomCard();
        cards.add(card);
        sum += deck.getValue(card);

        return card;
    }

    public void hit(Deck deck) {
        String drawnCard = draw(deck);
        System.out.println(name + " drew: " + drawnCard);
        System.out.println("Hand value: " + sum);
    }

    public void win() {
        System.out.println(name.toUpperCase() + " WINS!");
        money += bet;
        sum = 0;
    }

    public void lose() {
        money -= bet;
        sum = 0;
    }
    public void tie() {
        System.out.println("IT'S A TIE!");
        sum = 0;
    }

    /**
     *
     * @param deck deck of cards to look at
     * @return true if player has a blackjack, false if they don't
     */
    public boolean hasBlackjack(Deck deck) {
        if ((deck.isAce(cards.get(0)) && deck.getValue(cards.get(1)) == 10)) {
            System.out.println("BLACKJACK!");
            return true;
        } else if (deck.isAce(cards.get(1)) && deck.getValue(cards.get(0)) == 10) {
            System.out.println("BLACKJACK!");
            return true;
        } else {
            return false;
        }
    }


}
