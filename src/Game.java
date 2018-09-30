import java.util.Scanner;
public class Game {

    Player dealer;
    Player user;
    Deck deck;
    Player splitHand  = new Player("Your Split Hand");
    Scanner scan = new Scanner(System.in);
    final int MAX_SUM = 21;


    public Game(Player user, Player dealer, Deck deck) {
        this.user = user;
        this.dealer = dealer;
        this.deck = deck;
    }

    public void play(Player name) {
        getOriginalCards(name);
        ///user.cards.set(0, "ACE of Hearts");
        ///user.cards.set(1, "JACK of Spades");
        String dealerCard1 = dealer.draw(deck);
        String dealerCard2 = dealer.draw(deck);
        System.out.println("\nThe dealer drew:\n   " +
                dealerCard1.toUpperCase() + "\n   [hidden]");
        if (name.hasBlackjack(deck)) {
            name.win();
        } else {
            this.getPlayerMoves(name);
            this.determineWinner(dealerCard2, name);
        }
    }

    public void getPlayerBet() {
        double playerBet;
        System.out.println("You have $" + user.money + ".  How much would you like to bet?");
        do {
            while (!scan.hasNextDouble()) {
                System.out.println("Your response is invalid. Please respond with a numerical value.");
                scan.next();
            }
            playerBet = scan.nextDouble();
            if (playerBet > user.money) {
                System.out.println("You cannot bet more money than you have. Please try again. ");
            }
        } while (playerBet > user.money);
        user.setBet(playerBet);
    }

    public boolean keepPlaying() {
        System.out.println("Do you want to play again? (Y/N): ");
        String response = scan.next();
        while (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("N")) {
            System.out.println("Your response is invalid. Please use either Y or N as your response: ");
            response = scan.next();
        }
        if (response.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param name
     */
    public void getOriginalCards(Player name) {
        //user draws two cards
        String card1;
        String card2;
        if (name.cards.isEmpty()) {
            card1 = name.draw(deck);
            card2 = name.draw(deck);
        } else {
            card1 = name.cards.get(0);
            card2 = name.cards.get(1);
        }
        System.out.println(name.name + " drew:\n   " +
                card1.toUpperCase() + "\n   " +
                card2.toUpperCase());
        System.out.println("Your hand is currently valued at: " + name.sum);
    }

    /**
     *
     * @param name
     */
    public void getPlayerMoves(Player name) {
        boolean playerMovesDone = false;
        boolean firstRound = true;

        while (!playerMovesDone) {
            boolean doubleAllowed = false;
            boolean splitAllowed = false;
            System.out.println("\nChoose play (enter number only): " +
                    "\n1. Hit" +
                    "\n2. Stand");
            if (firstRound) {
                System.out.println("3. Double");
                doubleAllowed = true;
                if (deck.getValue(name.cards.get(0)) == deck.getValue(name.cards.get(1)) && name != splitHand) {
                    System.out.println("4. Split");
                    splitAllowed = true;
                }
            }
            int option;
            do {
                while (!scan.hasNextInt()) {
                    System.out.println("Please respond with a valid numerical value.");
                    scan.next();
                }
                option = scan.nextInt();
                if ((option == 3 && !doubleAllowed) || (option == 4 && !splitAllowed) || option > 4) {
                    System.out.println("You are not allowed to play that option. Please try again.");
                }
            } while ((option == 3 && !doubleAllowed) || (option == 4 && !splitAllowed) || option > 4);

            playerMovesDone = playMove(option, name, playerMovesDone);

            if (name.sum > 21) {
                playerMovesDone = true;
            }
            firstRound = false;
        }
    }

    /**
     *
     * @param option
     * @param name
     * @param playerMovesDone
     * @return
     */
    public boolean playMove(int option, Player name, boolean playerMovesDone) {
        switch (option) {
            case 1:
                name.hit(deck);
                break;
            case 2:
                playerMovesDone = true;
                break;
            case 3:
                user.bet *= 2;
                name.hit(deck);
                playerMovesDone = true;
                break;
            case 4:
                split();
                break;
        }
        return playerMovesDone;
    }

    /**
     *
     * @param dealerCard2
     */
    public void determineWinner(String dealerCard2, Player name) {
        if (name.sum <= 21) {
            System.out.println("Dealer drew: " + dealerCard2);
            if (dealer.hasBlackjack(deck) && !name.hasBlackjack(deck)) {
                dealer.win();
                name.lose();
            } else if (dealer.hasBlackjack(deck) && name.hasBlackjack(deck)) {
                name.tie();
            } else {
                while (dealer.sum < 17) {
                    dealer.hit(deck);
                }

                if (dealer.sum > MAX_SUM) {
                    name.win();
                } else if (dealer.sum > name.sum) {
                    dealer.win();
                    name.lose();
                } else if (dealer.sum == name.sum) {
                    name.tie();
                } else {
                    name.win();
                }

            }
        } else {
            name.lose();
            dealer.win();
        }
    }

    public void split() {
        splitHand.cards.add(user.cards.get(1));
        splitHand.sum += deck.getValue(user.cards.get(1));
        splitHand.draw(deck);

        user.sum -= deck.getValue(user.cards.get(1));
        user.cards.remove(1);
        user.draw(deck);

        play(splitHand);
        System.out.println();
        getOriginalCards(user);
    }
}
