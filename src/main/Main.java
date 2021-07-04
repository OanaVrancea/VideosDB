package main;

import action.*;
import User.*;
import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        int poz1, poz2, poz3;
        Actor actor;
        Movie movie = null;
        Movie movie1 = null;
        Serial serial = null;
        Serial serial1;
        Show showType;
        User user = null;
        int j = 0;
        int k;
        int oki = 0;
        int index = 0;
        int moviesListNumber = 0;
        int serialsListNumber = 0;
        int actorsListNumber = 0;
        int userListNumber = 0;
        ArrayList<String> listTitle = new ArrayList<String>();
        ArrayList<String> listUserName = new ArrayList<String>();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        ArrayList<Serial> serials = new ArrayList<Serial>();
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        ArrayList<Integer> listSeasons = new ArrayList<Integer>();
        String message = null;

        if (input.getUsers().size() != 0) {
            userListNumber = input.getCommands().size() / input.getUsers().size();
        }
        actorsListNumber = input.getActors().size();
        if (input.getMovies().size() != 0) {
            moviesListNumber = input.getCommands().size() / input.getMovies().size();
        }
        if (input.getSerials().size() != 0) {
            serialsListNumber = input.getCommands().size() / input.getSerials().size();
        }


        //TODO add here the entry point to your implementation
        for (int i = 0; i < input.getCommands().size(); i++) {


            for (j = 0; j < input.getUsers().size(); j++) {
                user = new User(input.getUsers().get(j).getUsername(),
                        input.getUsers().get(j).getSubscriptionType(),
                        input.getUsers().get(j).getHistory(),
                        input.getUsers().get(j).getFavoriteMovies());
                users.add(user);
            }

            for (j = 0; j < input.getMovies().size(); j++) {
                movie = new Movie(input.getMovies().get(j).getTitle(),
                        input.getMovies().get(j).getCast(),
                        input.getMovies().get(j).getGenres(),
                        input.getMovies().get(j).getYear(), input.getMovies().get(j).getDuration());
                movies.add(movie);
            }

            for (j = 0; j < input.getSerials().size(); j++) {
                serial = new Serial(input.getSerials().get(j).getTitle(),
                        input.getSerials().get(j).getCast(),
                        input.getSerials().get(j).getGenres(),
                        input.getSerials().get(j).getNumberSeason(),
                        input.getSerials().get(j).getSeasons(),
                        input.getSerials().get(j).getYear());
                serials.add(serial);
            }

            for (j = 0; j < actorsListNumber; j++) {
                actor = new Actor(input.getActors().get(j).getName(),
                        input.getActors().get(j).getCareerDescription(),
                        input.getActors().get(j).getFilmography(),
                        input.getActors().get(j).getAwards());
                actors.add(actor);
            }


            if (input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {


                Command command = new Command(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getActionType(),
                        input.getCommands().get(i).getType(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getGenre(),
                        input.getCommands().get(i).getTitle());

                if (input.getCommands().get(i).getType().equals("favorite")) {

                    for (k = 0; k < users.size(); k++) {
                        if (command.getUsername().equals(users.get(k).getUsername())) {
                            index = k;
                            break;
                        }
                    }

                     message = command.favorite(command.getTitle(), users.get(index));

                }
                if (input.getCommands().get(i).getType().equals("view")) {

                    index = 0;

                    for (k = 0; k < users.size(); k++) {
                        if (command.getUsername().equals(users.get(k).getUsername())) {
                            index = k;
                            break;
                        }
                    }

                     message = command.view(command.getTitle(), users.get(index));

                }


                if (input.getCommands().get(i).getType().equals("rating")) {
                    oki = 0;
                    poz1 = -1;
                    poz2 = -1;
                    poz3 = -1;

                    for (k = 0; k < users.size(); k++) {
                        if (command.getUsername().equals(users.get(k).getUsername())) {
                            poz1 = k;
                            break;
                        }
                    }

                    for (k = 0; k < movies.size(); k++) {
                        if (command.getTitle().equals(movies.get(k).getTitle())) {
                            poz2 = k;
                            break;
                        }
                    }

                    for (k = 0; k < serials.size(); k++) {
                        if (command.getTitle().equals(serials.get(k).getTitle())) {
                            poz3 = k;
                            break;
                        }
                    }

                    if (poz2 != -1) {

                         message = command.ratingMovie(command.getTitle(), users.get(poz1),
                                input.getCommands().get(i).getGrade(),
                                movies.get(poz2));


                    }
                    if (poz3 != -1) {

                         message = command.ratingSerial(command.getTitle(), users.get(poz1),
                                input.getCommands().get(i).getGrade(),
                                serials.get(poz3),
                                input.getCommands().get(i).getSeasonNumber());

                    }
                }
            } else if (input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
                Query query = new Query(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getActionType(),
                        input.getCommands().get(i).getObjectType(),
                        input.getCommands().get(i).getFilters().toString(),
                        input.getCommands().get(i).getSortType(),
                        input.getCommands().get(i).getCriteria(),
                        input.getCommands().get(i).getFilters().get(0).toString(),
                        input.getCommands().get(i).getNumber(),
                        input.getCommands().get(i).getFilters().get(2),
                        input.getCommands().get(i).getFilters().get(3));

                if (input.getCommands().get(i).getObjectType().equals("actors")) {
                    if (input.getCommands().get(i).getCriteria().equals("average")) {
                         message = query.average(input.getCommands().get(i).getNumber(),
                                actors, movies, serials, actorsListNumber, moviesListNumber,
                                serialsListNumber,
                                input.getCommands().get(i).getSortType());

                    }
                }
                if (input.getCommands().get(i).getObjectType().equals("actors")) {
                    if (input.getCommands().get(i).getCriteria().equals("awards")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.awards(input.getCommands().get(i).getFilters().get(3).size(),
                                        actors, input.getCommands().get(i).getFilters().get(3),
                                        sortType);
                    }

                }

                if (input.getCommands().get(i).getObjectType().equals("actors")) {
                    if (input.getCommands().get(i).getCriteria().equals("filter_description")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message = query.filterDescription(actors,
                                 input.getCommands().get(i).getFilters().get(2), sortType);

                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("movies")) {
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.videoRatingMovie(input.getCommands().get(i).getNumber(),
                                        movies, input.getMovies().size(),
                                        input.getCommands().get(i).getFilters().get(0),
                                        input.getCommands().get(i).getFilters().get(1), sortType);
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("shows")) {
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.videoRatingSerial(input.getCommands().get(i).getNumber(),
                                        serials, input.getSerials().size(),
                                        input.getCommands().get(i).getFilters().get(0),
                                        input.getCommands().get(i).getFilters().get(1),
                                        sortType);
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("movies")) {
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message = query.favoriteMovie(input.getCommands().get(i).getNumber(),
                                users, input.getUsers().size(),
                                input.getCommands().get(i).getFilters().get(0),
                                input.getCommands().get(i).getFilters().get(1), sortType,
                                movies, input.getMovies().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("shows")) {
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.favoriteSerial(input.getCommands().get(i).getNumber(),
                                        users, input.getUsers().size(),
                                        input.getCommands().get(i).getFilters().get(0),
                                        input.getCommands().get(i).getFilters().get(1), sortType,
                                        serials, input.getSerials().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("movies")) {
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message = query.longestMovie(input.getCommands().get(i).getNumber(),
                                input.getCommands().get(i).getFilters().get(0),
                                input.getCommands().get(i).getFilters().get(1), sortType,
                                movies, input.getMovies().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("shows")) {
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message = query.longestSerial(input.getCommands().get(i).getNumber(),
                                input.getCommands().get(i).getFilters().get(0),
                                input.getCommands().get(i).getFilters().get(1), sortType,
                                serials, input.getSerials().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("movies")) {
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.mostViewedMovies(input.getCommands().get(i).getNumber(),
                                        input.getCommands().get(i).getFilters().get(0),
                                        input.getCommands().get(i).getFilters().get(1), sortType,
                                        users,
                                        input.getUsers().size(), movies, input.getMovies().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("shows")) {
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message =
                                query.mostViewedSerials(input.getCommands().get(i).getNumber(),
                                        input.getCommands().get(i).getFilters().get(0),
                                        input.getCommands().get(i).getFilters().get(1), sortType,
                                        users,
                                        input.getUsers().size(), serials,
                                        input.getSerials().size());
                    }
                }

                if (input.getCommands().get(i).getObjectType().equals("users")) {
                    if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {
                        String sortType = input.getCommands().get(i).getSortType();
                         message = query.numberRatings(input.getCommands().get(i).getNumber(),
                                sortType, users, input.getUsers().size());
                    }
                }

            } else if (input.getCommands().get(i).getActionType()
                    .equals(Constants.RECOMMENDATION)) {
                Recommendation recommendation = new Recommendation(
                        input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getActionType(),
                        input.getCommands().get(i).getType(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getGenre());
                if (input.getCommands().get(i).getType().equals("standard")) {
                     message = recommendation
                            .standardConclusion(users, input.getCommands().get(i).getUsername(),
                                    movies, serials);
                }
                if (input.getCommands().get(i).getType().equals("best_unseen")) {
                     message = recommendation
                            .bestUnseen(users, input.getCommands().get(i).getUsername(),
                                    movies, serials);
                }
                if (input.getCommands().get(i).getType().equals("popular")) {
                     message =
                            recommendation.popular(users, input.getCommands().get(i).getUsername(),
                                    movies, serials);
                }
                if (input.getCommands().get(i).getType().equals("favorite")) {
                     message =
                            recommendation.favRec(users, input.getCommands().get(i).getUsername(),
                                    movies, serials);
                }
                if (input.getCommands().get(i).getType().equals("search")) {
                     message =
                            recommendation.search(users, input.getCommands().get(i).getUsername(),
                                    movies, serials, input.getCommands().get(i).getGenre());
                }
            }

            JSONObject object =
                    fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getCriteria(),
                            message);
            arrayResult.add(object);
        }

        fileWriter.closeJSON(arrayResult);
    }
}

