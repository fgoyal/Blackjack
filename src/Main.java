public class Main {

    public static void main(String[] args) {
        System.out.println("*******************************************\n" +
                           "                 BLACKJACK                 \n" +
                           "*******************************************\n");
        Player user = new Player("You");
        Player dealer = new Player("Dealer");
        Deck deck = new Deck();
        boolean keepPlaying;
        boolean hasMoney = true;

        do {
            Game currentGame = new Game(user, dealer, deck);
            currentGame.getPlayerBet();
            currentGame.play(user);
            keepPlaying = currentGame.keepPlaying();
            if (deck.isDeckEmpty()) {
                deck = new Deck();
            }

        } while (hasMoney && keepPlaying);

        if (user.getMoney() == 0) {
            System.out.println("GAME OVER. You have lost all of your money.");
        } else {
            System.out.println("You ended the game with $" + user.getMoney());
        }



    }
}
