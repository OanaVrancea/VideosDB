package User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {

    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    /**
     * o mapa in care se retin filmele carora a dat rating
     */
    private final Map<String, Integer> ratingMovie = new HashMap<String, Integer>();
    /**
     * o mapa in care se retin serialele carora a dat rating
     * si numarul sezonului
     */
    private final Map<String, Integer> ratingSerial = new HashMap<String, Integer>();



    public User(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public Map<String, Integer> getRatingMovie() {
        return ratingMovie;
    }

    public Map<String, Integer> getRatingSerial() {
        return ratingSerial;
    }


    /***
     * aici
     * @param title
     */
    public void addRatedMovie(final String title) {
        //pun valoarea 1 ce marcheaza faptul ca filmul a primit rating
        this.ratingMovie.put(title, 1);
    }

    /***
     * aici
     * @param title
     * @param numberSeason
     */
    public void addRatedSerial(final String title, final int numberSeason) {
        //in dreptul serialului pun sezonul care a primit rating
        this.ratingSerial.put(title, numberSeason);
    }


    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
