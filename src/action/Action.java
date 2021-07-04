package action;


import User.User;
import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;

import java.util.*;

public class Action {

    /**
     * Action id
     */
    private final int actionId;
    /**
     * Type of action
     */
    private final String actionType;
    /**
     * Used for commands
     */
    private final String type;
    /**
     * Username of user
     */
    private final String username;
    /**
     * The type of object on which the actions will be performed
     */
    private final String objectType;
    /**
     * Sorting type: ascending or descending
     */
    private final String sortType;
    /**
     * The criterion according to which the sorting will be performed
     */
    private final String criteria;
    /**
     * Video title
     */
    private final String title;
    /**
     * Video genre
     */
    private final String genre;
    /**
     * Query limit
     */
    private final int number;
    /**
     * Grade for rating - aka value of the rating
     */
    private final double grade;
    /**
     * Season number
     */
    private final int seasonNumber;
    /**
     * Filters used for selecting videos
     */
    private final List<List<String>> filters = new ArrayList<>();

    public Action(final int actionId, final String actionType,
                  final String type, final String username, final String genre,
                  final String title) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.username = username;
        this.genre = genre;
        this.objectType = null;
        this.sortType = null;
        this.criteria = null;
        this.number = 0;
        this.title = title;
        this.grade = 0;
        this.seasonNumber = 0;

    }


    public Action(final int actionId, final String actionType, final String objectType,
                  final String genre, final String sortType, final String criteria,
                  final String year, final int number, final List<String> words,
                  final List<String> awards) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.number = number;
        this.filters.add(new ArrayList<>(Collections.singleton(year)));
        this.filters.add(new ArrayList<>(Collections.singleton(genre)));
        this.filters.add(words);
        this.filters.add(awards);
        this.title = null;
        this.type = null;
        this.username = null;
        this.genre = null;
        this.grade = 0;
        this.seasonNumber = 0;
    }

    public Action(final int actionId, final String actionType, final String type,
                  final String username, final String title, final Double grade,
                  final int seasonNumber) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.grade = grade;
        this.username = username;
        this.title = title;
        this.seasonNumber = seasonNumber;
        this.genre = null;
        this.objectType = null;
        this.sortType = null;
        this.criteria = null;
        this.number = 0;
    }


    public Action(final int actionId, final String actionType,
                  final String type, final String username, final String genre) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.username = username;
        this.genre = genre;
        this.objectType = null;
        this.sortType = null;
        this.criteria = null;
        this.number = 0;
        this.title = null;
        this.grade = 0;
        this.seasonNumber = 0;

    }


    public final int getActionId() {
        return actionId;
    }

    public final String getActionType() {
        return actionType;
    }

    public final String getType() {
        return type;
    }

    public final String getUsername() {
        return username;
    }

    public final String getSortType() {
        return sortType;
    }

    public final String getCriteria() {
        return criteria;
    }

    public final String getTitle() {
        return title;
    }

    public final String getGenre() {
        return genre;
    }

    public final int getNumber() {
        return number;
    }

    public final double getGrade() {
        return grade;
    }

    public final int getSeasonNumber() {
        return seasonNumber;
    }

    public final List<List<String>> getFilters() {
        return filters;
    }

    @Override
    public final String toString() {
        return "ActionInputData{"
                + "actionId=" + actionId
                + ", actionType='" + actionType + '\''
                + ", type='" + type + '\''
                + ", username='" + username + '\''
                + ", objectType='" + objectType + '\''
                + ", sortType='" + sortType + '\''
                + ", criteria='" + criteria + '\''
                + ", title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", number=" + number
                + ", grade=" + grade
                + ", seasonNumber=" + seasonNumber
                + ", filters=" + filters
                + '}' + "\n";
    }

    /***
     * imi intoarce lista in care toate strigurile apar o singura data
     * @param strings lista de stringuri
     * @return
     */
    public final ArrayList<String> returnStringOnce(final ArrayList<String> strings) {
        ArrayList<String> stringsOnce = new ArrayList<>();
        int i, j;
        int t = 0;
        for (i = 0; i < strings.size(); i++) {
            t = 0;
            for (j = 0; j < stringsOnce.size(); j++) {
                if (strings.get(i).equals(stringsOnce.get(j))) {
                    t = 1;
                }
            }
            if (t == 0) {
                stringsOnce.add(strings.get(i));
            }
        }
        return strings;
    }

    /***
     * iimi intoarce lista cu toate titlurile in care toate filmele apar o singura data
     * @param movies lista de fime
     * @return
     */
    public final ArrayList<String> returnMovieTitles(final List<Movie> movies) {
        int i, j;
        int m = 0;
        ArrayList<String> movieTitles = new ArrayList<>();
        for (i = 0; i < movies.size(); i++) {
            m = 0;
            for (j = 0; j < movieTitles.size(); j++) {
                if (movies.get(i).getTitle().equals(movieTitles.get(j))) {
                    m = 1;
                }
            }
            if (m == 0) {
                movieTitles.add(movies.get(i).getTitle());
            }

        }
        return movieTitles;
    }

    /***
     * imi intoarce lista cu totate titlurile in care toate serialele apar o singura data
     * @param serials lista de seriale
     * @return
     */
    public final ArrayList<String> returnSerialTitles(final List<Serial> serials) {
        int i, j;
        int s = 0;
        ArrayList<String> serialTitles = new ArrayList<>();
        for (i = 0; i < serials.size(); i++) {
            s = 0;
            for (j = 0; j < serialTitles.size(); j++) {
                if (serials.get(i).getTitle().equals(serialTitles.get(j))) {
                    s = 1;
                }
            }
            if (s == 0) {
                serialTitles.add(serials.get(i).getTitle());
            }
        }
        return serialTitles;
    }

    /***
     * imi intoarce lista in care toti userii apar o singura data
     * @param users lista de useri
     * @return
     */
    public final ArrayList<User> returnUsersOnce(final List<User> users) {
        int i, j;
        int u = 0;
        ArrayList<User> usersOnce = new ArrayList<User>();
        for (i = 0; i < users.size(); i++) {
            u = 0;
            for (j = 0; j < usersOnce.size(); j++) {
                if (users.get(i).getUsername().equals(usersOnce.get(j).getUsername())) {
                    u = 1;
                }
            }
            if (u == 0) {
                usersOnce.add(users.get(i));
            }
        }
        return usersOnce;
    }

    /***
     * aici imi intoarce lista in care toate filmele apar o singura data
     * @param movies lista de filme
     * @return
     */
    public final ArrayList<Movie> returnMoviesOnce(final List<Movie> movies) {
        int i, j;
        int m = 0;
        ArrayList<Movie> moviesOnce = new ArrayList<>();
        for (i = 0; i < movies.size(); i++) {
            m = 0;
            for (j = 0; j < moviesOnce.size(); j++) {
                if (movies.get(i).getTitle().equals(moviesOnce.get(j).getTitle())) {
                    m = 1;
                }
            }
            if (m == 0) {
                moviesOnce.add(movies.get(i));
            }
        }
        return moviesOnce;
    }

    /***
     * imi intoarce lista in care toate serialele apar o singura data
     * @param serials lista de seriale
     * @return
     */
    public final ArrayList<Serial> returnSerialsOnce(final List<Serial> serials) {
        int i, j;
        int s = 0;
        ArrayList<Serial> serialsOnce = new ArrayList<>();
        for (i = 0; i < serials.size(); i++) {
            s = 0;
            for (j = 0; j < serialsOnce.size(); j++) {
                if (serials.get(i).getTitle().equals(serialsOnce.get(j).getTitle())) {
                    s = 1;
                }
            }
            if (s == 0) {
                serialsOnce.add(serials.get(i));
            }
        }
        return serialsOnce;
    }

    /***
     * imi intoarce userul cu usernameul dat ca parametru
     * @param usersOnce lista de useri fara duplicate
     * @param usernamef numele userului care trebie gasit
     * @return
     */
    public final User findRightUser(final ArrayList<User> usersOnce, final String usernamef) {
        int i;
        User user = null;
        for (i = 0; i < usersOnce.size(); i++) {
            if (usersOnce.get(i).getUsername().equals(usernamef)) {
                user = usersOnce.get(i);
            }
        }
        return user;
    }

    /***
     * imi intoarce un array sortat format prin concatenarea arr si arr2
     * @param arr array sortat
     * @param arr2 array sortat
     * @return
     */
    public final double[] combineArray(final double[] arr, final double[] arr2) {
        int k, i, j;
        double[] combine = new double[arr.length + arr2.length];
        k = 0;
        i = 0;
        j = 0;
        int t;
        while (i < arr.length && j < arr2.length) {
            if (arr[i] > arr2[j]) {
                combine[k++] = arr[i++];
            } else {
                combine[k++] = arr2[j++];
            }
        }
        while (j < arr2.length) {
            combine[k++] = arr2[j++];
        }

        while (i < arr.length) {
            combine[k++] = arr[i++];
        }

        return combine;

    }

    /***
     * sortare dupa nume ascendenta pentru functia Average
     * @param nr
     * @param arrayList
     * @param actors
     * @param actorsNumber
     * @param arr
     * @param movies
     * @param serials
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameAsc(final int nr, final ArrayList<String> arrayList,
                                                 final List<Actor> actors, final int actorsNumber,
                                                 final double[] arr, final List<Movie> movies,
                                                 final List<Serial> serials, final int nonZeroFinal,
                                                 final double pozitieFinal) {

        //in aceasta functie arraylist este o lista sortata aflabetic de la A la Z
        //iar vectorul arr este ordonat crescator
        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        //pozitie indica catre primul element nenul din vectorul sortat arr
        double pozitie = pozitieFinal;
        int i, j;
        List<Movie> movie = new ArrayList<>();
        List<Serial> serial = new ArrayList<>();
        double d;
        int counter = 0;
        for (i = 0; i < actorsNumber; i++) {
            for (j = 0; j < actorsNumber; j++) {
                if (arrayList.get(i).equals(actors.get(j).getName())) {
                    movie = actors.get(j).actorMovies(movies, actors.get(j));
                    serial = actors.get(j).actorSeials(serials, actors.get(j));
                    d = actors.get(j).actorFilmographyAverage(actors.get(j), movie, serial);
                    //daca media este egala cu prima valoarea nenula a vectorului inseamna ca am
                    //gasit primul actor cu acea medie, dar si intr-o ordine alfabetica
                    if (arr[nonZero] == d && counter < nr) {
                        pozitie = arr[nonZero];
                        returnArray.add(actors.get(j).getName());
                        counter++;
                        if ((nonZero + 1) != (actorsNumber - 1)) {
                            nonZero++;
                        }
                        //daca urmatorul element din vector nu mai are aceeasi valoare
                        //trebuie sa incep sa parcurg lista de la inceput
                        //deoarece vreau sa gasesc elementul cu noua valoare corespunzatoare
                        //alfabetic
                        if (pozitie != arr[nonZero]) {
                            i = 0;
                            j = 0;
                        }
                    }

                }
            }
        }
        return returnArray;
    }

    /***
     * sortare dupa nume descendenta pentru functia Average
     * @param nr
     * @param arrayList
     * @param actors
     * @param actorsNumber
     * @param arr
     * @param movies
     * @param serials
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameDesc(final int nr, final ArrayList<String> arrayList,
                                                  final List<Actor> actors, final int actorsNumber,
                                                  final double[] arr, final List<Movie> movies,
                                                  final List<Serial> serials,
                                                  final int nonZeroFinal,
                                                  final double pozitieFinal) {

        //acelasi algoritm doar ca trebuie parcurs arrayList de la ultimul element
        //(fac parcurgerea de la Z la A)
        ArrayList<String> returnArray = new ArrayList<>();
        int i, j;
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        List<Movie> movie = new ArrayList<>();
        List<Serial> serial = new ArrayList<>();
        double d;
        int counter = 0;
        for (i = actorsNumber - 1; i >= 0; i--) {
            for (j = 0; j < actorsNumber; j++) {
                if (arrayList.get(i).equals(actors.get(j).getName())) {
                    movie = actors.get(j).actorMovies(movies, actors.get(j));
                    serial = actors.get(j).actorSeials(serials, actors.get(j));
                    d = actors.get(j).actorFilmographyAverage(actors.get(j), movie, serial);
                    if (arr[nonZero] == d && counter < nr) {
                        pozitie = arr[nonZero];
                        returnArray.add(actors.get(j).getName());
                        counter++;
                        if ((nonZero + 1) < (actorsNumber - 1)) {
                            nonZero++;
                        }
                        if (pozitie != arr[nonZero]) {
                            i = actorsNumber - 1;
                            j = 0;
                        }
                    }

                }
            }
        }
        return returnArray;
    }

    /***
     * sortare dupa nume pentru functia Awards
     * @param nr
     * @param arrayList
     * @param actors
     * @param arr
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameAwards(final int nr,
                                                        final ArrayList<String> arrayList,
                                                        final List<Actor> actors, final int[] arr,
                                                        final int nonZeroFinal,
                                                        final int pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int d = 0;
        int ok = 0;
        int counter = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < actors.size(); j++) {
                if (arrayList.get(i).equals(actors.get(j).getName())) {
                    d = actors.get(j).getAwards().values().stream().reduce(0, Integer::sum);
                    if (arr[nonZero] == d) {
                        pozitie = arr[nonZero];
                        for (k = 0; k < returnArray.size(); k++) {
                            if (arrayList.get(i).equals(returnArray.get(k))) {
                                ok = 1;
                            }
                        }
                        if (ok == 0) {
                            returnArray.add(arrayList.get(i));
                            counter++;
                        }
                        if ((nonZero + 1) < (arr.length)) {
                            nonZero++;
                        }
                        if (pozitie != arr[nonZero]) {
                            i = 0;
                            j = -1;
                        }
                    }

                }

            }
        }

        return returnArray;
    }


    /***
     * sortare dupa nume pentru functia Favorite
     * @param nr
     * @param arrayList
     * @param movies
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameFavorite(final int nr,
                                                      final ArrayList<String> arrayList,
                                                      final List<Show> movies, final int[] arr,
                                                      final int[] aux, final int nonZeroFinal,
                                                      final int pozitieFinal) {


        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = 0;
                    }
                }

            }

        }

        return returnArray;
    }

    /***
     * fortare dupa nume pentru functia LongestSerial
     * @param nr
     * @param arrayList
     * @param movies
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameLongestAscSerial(final int nr,
                                                           final ArrayList<String> arrayList,
                                                           final List<Serial> movies,
                                                            final int[] arr,
                                                           final int[] aux, final int nonZeroFinal,
                                                           final int pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int d = 0;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = -1;
                    }
                }

            }

        }

        return returnArray;
    }

    /***
     * sortare dupa nume pentru functia LongestMovie
     * @param nr
     * @param arrayList
     * @param movies
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameLongestAsc(final int nr,
                                                        final ArrayList<String> arrayList,
                                                        final List<Movie> movies, final int[] arr,
                                                        final int[] aux, final int nonZeroFinal,
                                                        final int pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int d = 0;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = -1;
                    }
                }

            }

        }

        return returnArray;
    }

    /***
     * sortare dupa nume penru functia numerRatings
     * @param nr
     * @param arrayList
     * @param user
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> sortbyNameUser(final int nr, final ArrayList<String> arrayList,
                                                  final List<User> user, final int[] arr,
                                                  final int[] aux, final int nonZeroFinal,
                                                  final int pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < user.size(); j++) {
                if (user.get(j).getUsername().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = -1;
                    }
                }

            }

        }

        return returnArray;
    }

    public final ArrayList<String> sortbyNameRatingMovie(final int nr,
                                                         final ArrayList<String> arrayList,
                                                  final List<Movie> movies, final double[] arr,
                                                  final double[] aux, final int nonZeroFinal,
                                                  final double pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = -1;
                    }
                }

            }

        }

        return returnArray;
    }

    public final ArrayList<String> sortbyNameRatingSerial(final int nr,
                                                         final ArrayList<String> arrayList,
                                                         final List<Serial> movies, final double[] arr,
                                                         final double[] aux, final int nonZeroFinal,
                                                         final double pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int ok = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < movies.size(); j++) {
                if (movies.get(j).getTitle().equals(arrayList.get(i)) && arr[nonZero] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (arrayList.get(i).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(arrayList.get(i));
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = -1;
                    }
                }

            }

        }

        return returnArray;
    }

    /***
     * sortare pentru functia favRec
     * @param arrayList
     * @param titles
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> favoriteRec(final ArrayList<String> arrayList,
                                               final List<String> titles, final int[] arr,
                                               final int[] aux, final int nonZeroFinal,
                                               final int pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int d = 0;
        int ok = 0;
        int counter = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < titles.size(); j++) {
                if (arr[i] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (titles.get(j).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(titles.get(j));
                        counter++;
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = 0;
                    }
                }

            }

        }

        return returnArray;
    }

    /***
     * sortare pentru functia search
     * @param arrayList
     * @param titles
     * @param arr
     * @param aux
     * @param nonZeroFinal
     * @param pozitieFinal
     * @return
     */
    public final ArrayList<String> favoriteSearch(final ArrayList<String> arrayList,
                                                  final List<String> titles, final double[] arr,
                                                  final double[] aux, final int nonZeroFinal,
                                                  final double pozitieFinal) {

        //in aceasta functie, arrayList este sortat de la A la Z sau de la Z la A,
        //depinde cum a fost cerut iar vectorul arr crescator, respectiv descrecator

        ArrayList<String> returnArray = new ArrayList<>();
        int nonZero = nonZeroFinal;
        double pozitie = pozitieFinal;
        int i, j, k;
        int d = 0;
        int ok = 0;
        int counter = 0;
        for (i = 0; i < arrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < titles.size(); j++) {
                if (arr[i] == aux[j]) {
                    pozitie = arr[nonZero];
                    for (k = 0; k < returnArray.size(); k++) {
                        if (titles.get(j).equals(returnArray.get(k))) {
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        returnArray.add(titles.get(j));
                        counter++;
                    }
                    if ((nonZero + 1) < (arr.length)) {
                        nonZero++;
                    }

                    if (pozitie != arr[nonZero]) {
                        i = 0;
                        j = 0;
                    }
                }

            }

        }

        return returnArray;
    }


}

