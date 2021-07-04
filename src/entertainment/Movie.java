package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * lista de rating a unui film
     */
    private List<Double> ratings;

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    /***
     * adauga ratingul primit la lista de rating
     * @param rating un rating
     */
    public void addRating(final double rating) {
        this.getRatings().add(rating);
    }

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    /***
     * calculeaza media pentru un film
     * @param ratingsf lista de rating
     * @return
     */
    public double getAverageRatingMovie(final List<Double> ratingsf) {
        double average = 0;
        int numberRatings = 0;
        for (int i = 0; i < ratingsf.size(); i++) {
            average = average + ratingsf.get(i);
            numberRatings++;
        }
        if (numberRatings != 0) {
            average = average / numberRatings;
        }
        return average;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
