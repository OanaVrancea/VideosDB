package actor;

import entertainment.Movie;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    /***
     * calculeaza media pentru toate video-urile in care a jucat un actor
     * @param actor actor respectiv
     * @param movieList lista de filme
     * @param serialList lista de seriale
     * @return
     */
    public double actorFilmographyAverage(final Actor actor, final List<Movie> movieList,
                                          final List<Serial> serialList) {
        double average = 0;
        int counter = 0;
        for (int i = 0; i < actor.getFilmography().size(); i++) {
            for (int j = 0; j < movieList.size(); j++) {
                if (movieList.get(j).getAverageRatingMovie(movieList.get(j).getRatings()) != 0) {
                    average = average
                            + movieList.get(j).getAverageRatingMovie(movieList.get(j).getRatings());
                    counter++;
                }
            }
            for (int j = 0; j < serialList.size(); j++) {
                if (serialList.get(j).getAverageRatingSerial(serialList.get(j).getSeasons()) != 0) {
                    average = average + serialList.get(j)
                            .getAverageRatingSerial(serialList.get(j).getSeasons());
                    counter++;
                }
            }
        }
        if (counter != 0) {
            average = average / counter;
        }
        return average;
    }

    /***
     * intoarce toate filmele in care a jucat un actor
     * @param movies lista de filme
     * @param actor pentru care trebuie gasite filmele
     * @return
     */
    public List<Movie> actorMovies(final List<Movie> movies, final Actor actor) {
        List<Movie> moviesWithActor = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.get(i).getCast().size(); j++) {
                if (movies.get(i).getCast().get(j).equals(actor.getName())) {
                    moviesWithActor.add(movies.get(i));
                }
            }
        }
        return moviesWithActor;
    }

    /***
     * intoarce toate serialele in care joaca actorul respectiv
     * @param serials lista de seriale
     * @param actor actorul pentru care trebuie sa gasim serialele
     * @return
     */
    public List<Serial> actorSeials(final List<Serial> serials, final Actor actor) {
        List<Serial> serialsWithActor = new ArrayList<Serial>();
        for (int i = 0; i < serials.size(); i++) {
            for (int j = 0; j < serials.get(i).getCast().size(); j++) {
                if (serials.get(i).getCast().get(j).equals(actor.getName())) {
                    serialsWithActor.add(serials.get(i));
                }
            }
        }
        return serialsWithActor;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
