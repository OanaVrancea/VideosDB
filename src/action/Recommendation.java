package action;


import User.User;
import entertainment.Movie;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Recommendation extends Action {


    public Recommendation(final int actionId, final String actionType, final String type,
                          final String username, final String genre) {
        super(actionId, actionType, type, username, genre);
    }


    /***
     * functie auxiliara pentru popular care imi intoarce un vector pentru
     * care fiecare element reprezinta cate filme si seriale contin genul
     * respectiv
     * @param moviesOnce lista de filme
     * @param serialsOnce lista de seriale
     * @param usersOnce lista de useri
     * @param genresOnce lista de genuri
     * @return
     */
    public final int[] returnPopularArray(final ArrayList<Movie> moviesOnce,
                                          final ArrayList<Serial> serialsOnce,
                                          final ArrayList<User> usersOnce,
                                          final ArrayList<String> genresOnce) {
        int i, k, j;
        int[] arr = new int[genresOnce.size()];
        for (i = 0; i < genresOnce.size(); i++) {
            for (j = 0; j < moviesOnce.size(); j++) {
                if (moviesOnce.get(j).getGenres().contains(genresOnce.get(i))) {
                    for (k = 0; k < usersOnce.size(); k++) {
                        if (usersOnce.get(k).getHistory()
                                .containsKey(moviesOnce.get(j).getTitle())) {
                            for (Map.Entry entry : usersOnce.get(k).getHistory().entrySet()) {
                                if (entry.getKey().equals(moviesOnce.get(j).getTitle())) {
                                    arr[i] = arr[i]
                                            + Integer.valueOf(entry.getValue().toString());
                                }
                            }
                        }
                    }
                }
            }
            for (j = 0; j < serialsOnce.size(); j++) {
                if (serialsOnce.get(j).getGenres().contains(genresOnce.get(i))) {
                    for (k = 0; k < usersOnce.size(); k++) {
                        if (usersOnce.get(k).getHistory()
                                .containsKey(serialsOnce.get(j).getTitle())) {
                            for (Map.Entry entry : usersOnce.get(k).getHistory().entrySet()) {
                                if (entry.getKey().equals(serialsOnce.get(j).getTitle())) {
                                    arr[i] = arr[i]
                                            + Integer.valueOf(entry.getValue().toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return arr;
    }

    /***
     * functie care intoarce primul video nevazut de utilizator
     * @param users lista de useri
     * @param usernamef numele userului
     * @param movies lista de filme
     * @param serials lista de seriale
     * @return
     */
    public String standard(final List<User> users, final String usernamef,
                           final List<Movie> movies,  final List<Serial> serials) {

        String message = null;
        int i, j;
        ArrayList<String> seenMovies = new ArrayList<>();
        User user = null;
        ArrayList<String> movieTitles = new ArrayList<>();
        ArrayList<String> serialTitles = new ArrayList<>();
        ArrayList<User> usersOnce = new ArrayList<>();
        String name = null;
        int m = 0;
        int s = 0;
        int u = 0;

        movieTitles = returnMovieTitles(movies);
        serialTitles = returnSerialTitles(serials);
        usersOnce = returnUsersOnce(users);
        user = findRightUser(usersOnce, usernamef);

        //adaug in seenMovies toate filmele vazute de utilizator
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            seenMovies.add(entry.getKey());
        }

        m = 0;
        s = 0;
        //daca size de seen movies este egal  movies.size() + serial.size()
        //inseamna ca a vazut toate filmele
        if (seenMovies.size() == (movieTitles.size() + serialTitles.size())) {
            message = "StandardRecommendation cannot be applied!";
        } else {
            //caut in lista de filme primul film nevazut
            for (i = 0; i < movieTitles.size(); i++) {
                if (!seenMovies.contains(movieTitles.get(i))) {
                    m = 1;
                    name = movieTitles.get(i);
                    break;
                }
            }
            //daca nu l-am gasit caut in lista de seriale primul serial nevazute
            if (m == 0) {
                for (i = 0; i < serialTitles.size(); i++) {
                    if (!seenMovies.contains(serialTitles.get(i))) {
                        m = 1;
                        name = movieTitles.get(i);
                        break;
                    }
                }
            }
            message = "StandardRecommendation result: " + name;
        }

        return name;
    }

    /***
     * aceasta functie imi intoarce mesajul pentru recomandarea standard
     * nu am intors mesajul in functia standard propriu zisa pentru ca
     * am avut nevoie de ea in cadrul functiei bestUnseen
     * @param users lista de useri
     * @param usernamef numele userului
     * @param movies lista filme
     * @param serials lista seriale
     * @return
     */
    public String standardConclusion(final List<User> users, final String usernamef,
                                     final List<Movie> movies, final List<Serial> serials) {
        String message = null;
        String name = null;
        name = standard(users, usernamef, movies, serials);
        if (name == null) {
            message = "StandardRecommendation cannot be applied!";
        } else {
            message = "StandardRecommendation result: " + name;
        }
        return message;
    }

    /***
     * intoarce cel mai bun video nevizualizat de acel utilizator
     * @param users lista useri
     * @param usernamef nume user
     * @param movies lista filme
     * @param serials lista seriale
     * @return
     */
    public String bestUnseen(final List<User> users, final String usernamef,
                             final List<Movie> movies, final List<Serial> serials) {
        String message = null;
        int i, j;
        ArrayList<String> seenMovies = new ArrayList<>();
        User user = null;
        ArrayList<String> movieTitles = new ArrayList<>();
        ArrayList<String> serialTitles = new ArrayList<>();
        ArrayList<User> usersOnce = new ArrayList<>();
        ArrayList<Movie> moviesOnce = new ArrayList<>();
        ArrayList<Serial> serialsOnce = new ArrayList<>();
        String name = null;
        int m = 0;
        int s = 0;
        int u = 0;

        ArrayList<Movie> movies1 = new ArrayList<>();
        ArrayList<Serial> serials1 = new ArrayList<>();


        moviesOnce = returnMoviesOnce(movies);
        serialsOnce = returnSerialsOnce(serials);


        double[] arr = new double[moviesOnce.size()];
        double[] arr2 = new double[serialsOnce.size()];

        //calculez media pentru fiecare film si o pun pe pozitia corespunzatoare din vectorul
        //corespunzator filmelor
        int k = 0;
        for (i = 0; i < moviesOnce.size(); i++) {
            if (moviesOnce.get(i).getAverageRatingMovie(moviesOnce.get(i).getRatings()) != 0) {
                arr[k] = moviesOnce.get(i).getAverageRatingMovie(moviesOnce.get(i).getRatings());
                k++;
                movies1.add(moviesOnce.get(i));
            }
        }
        k = 0;

        //calculez media pentru fiecare serial si o pun pe pozitia corespunzatoare din vectorul
        //corespunzator serialelor
        for (i = 0; i < serialsOnce.size(); i++) {
            if (serialsOnce.get(i).getAverageRatingSerial(serialsOnce.get(i).getSeasons()) != 0) {
                arr2[k] =
                        serialsOnce.get(i).getAverageRatingSerial(serialsOnce.get(i).getSeasons());
                k++;
                serials1.add(serialsOnce.get(i));
            }
        }
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();


        bubblesortMethods.bubbleSortdescdouble(arr);
        bubblesortMethods.bubbleSortdescdouble(arr2);

        //combin cei 2 vectori sortati
        double[] combine = combineArray(arr, arr2);

        k = 0;
        ArrayList<String> titles = new ArrayList<>();
        int ok = 0;
        int oki = 0;

        //adaug titlurile in functie de rating
        while (combine[k] != 0) {
            ok = 0;
            oki = 0;
            //parcurg lista de filme
            for (i = 0; i < moviesOnce.size(); i++) {
                //daca gasesc un film care are media necesara il adaug la lista de titluri
                if (moviesOnce.get(i).getAverageRatingMovie(moviesOnce.get(i).getRatings())
                        == combine[k]) {
                    titles.add(moviesOnce.get(i).getTitle());
                    ok = 1;
                }
            }

            //asemanator pentru seriale
            for (j = 0; j < serialsOnce.size(); j++) {
                if (serialsOnce.get(j).getAverageRatingSerial(serialsOnce.get(j).getSeasons())
                        == combine[k]) {
                    titles.add(serialsOnce.get(j).getTitle());
                    oki = 1;
                }
            }
            k++;
        }


        ArrayList<String> titlesOnce = new ArrayList<>();

        //elemin duplicatele
        titlesOnce = returnStringOnce(titles);

        movieTitles = returnMovieTitles(movies);

        serialTitles = returnSerialTitles(serials);

        usersOnce = returnUsersOnce(users);

        user = findRightUser(usersOnce, usernamef);

        //construiesc seenMovies din filmele si serialele vazute de utilizator
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            seenMovies.add(entry.getKey());
        }

        m = 0;
        s = 0;
        if (seenMovies.size() == (movieTitles.size() + serialTitles.size())) {
            message = "BestRatedUnseenRecommendation cannot be applied!";
        } else if (titlesOnce.size() != 0) {
            for (i = 0; i < titlesOnce.size(); i++) {
                //daca gasesc un video care nu a fost vazut
                if (!seenMovies.contains(titlesOnce.get(i))) {
                    m = 1;
                    //opresc procesul dupa ce il pun in name
                    name = titlesOnce.get(i);
                    break;
                }
            }
            message = "BestRatedUnseenRecommendation result: " + name;
        }
        //daca nu il gasesc aplic standard pentru a lua urmatorul film din baza de date
        if (m == 0) {
            if (standard(users, usernamef, movies, serials) != null) {
                message = "BestRatedUnseenRecommendation result: "
                        + standard(users, usernamef, movies, serials);
            } else {
                message = "BestRatedUnseenRecommendation cannot be applied!";
            }
        }

        return message;
    }

    /***
     * intoarce primul video nevizualizat de cel mai popular gen
     * @param users lista de useri
     * @param usernamef numele userului
     * @param movies lista filme
     * @param serials lista seriale
     * @return
     */
    public String popular(final List<User> users, final String usernamef,
                          final List<Movie> movies,  final List<Serial> serials) {

        String message = null;
        int i, j;
        ArrayList<String> seenMovies = new ArrayList<>();
        User user = null;
        ArrayList<String> movieTitles = new ArrayList<>();
        ArrayList<String> serialTitles = new ArrayList<>();
        ArrayList<User> usersOnce = new ArrayList<>();
        ArrayList<Movie> moviesOnce = new ArrayList<>();
        ArrayList<Serial> serialsOnce = new ArrayList<>();
        String name = null;
        int m = 0;
        int s = 0;
        int u = 0;


        moviesOnce = returnMoviesOnce(movies);
        serialsOnce = returnSerialsOnce(serials);
        usersOnce = returnUsersOnce(users);
        user = findRightUser(usersOnce, usernamef);

        ArrayList<String> genres = new ArrayList<>();
        ArrayList<String> genresOnce = new ArrayList<>();
        int ok = 0;

        if (user.getSubscriptionType().equals("PREMIUM")) {

            //pentru toate filmele si serialele pun intr-o lista genurile
            for (i = 0; i < moviesOnce.size(); i++) {
                for (j = 0; j < moviesOnce.get(i).getGenres().size(); j++) {
                    genres.add(moviesOnce.get(i).getGenres().get(j));
                }
            }


            for (i = 0; i < serialsOnce.size(); i++) {
                for (j = 0; j < serialsOnce.get(i).getGenres().size(); j++) {
                    genres.add(serialsOnce.get(i).getGenres().get(j));
                }
            }

            //scap de duplicate
            genresOnce = returnStringOnce(genres);

            int k;

            //construiesc vectorii pentru sortare
            int[] arr = returnPopularArray(moviesOnce, serialsOnce, usersOnce, genresOnce);
            int[] aux = returnPopularArray(moviesOnce, serialsOnce, usersOnce, genresOnce);


            action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();

            bubblesortMethods.bubbleSort(aux);


            ArrayList<String> popularOrder = new ArrayList<>();
            ArrayList<String> popularOrderOnce = new ArrayList<>();
            ok = 0;
            i = 0;
            j = 0;

            //construiesc un vector in care adaug genurile in functie de
            //popularitate
            for (i = 0; i < aux.length; i++) {
                ok = 0;
                for (j = 0; j < arr.length; j++) {
                    if (aux[i] == arr[j]) {
                        popularOrder.add(genresOnce.get(j));
                    }
                }
            }

            popularOrderOnce = returnStringOnce(popularOrder);

            String st = null;

            ArrayList<Movie> movieGenre = new ArrayList<>();
            ArrayList<Serial> serialGenre = new ArrayList<>();

            //lista auxiliara
            ArrayList<String> help = new ArrayList<>();

            ok = 0;

            //pentru toate genurile sortate in functie de popularitate
            for (i = 0; i < popularOrderOnce.size(); i++) {

                //voi construi de fiecare data vectorul help in care voi avea
                //titlurile pentru filmele si serialele ce contin genul curent
                for (j = 0; j < moviesOnce.size(); j++) {
                    for (k = 0; k < moviesOnce.get(j).getGenres().size(); k++) {
                        if (moviesOnce.get(j).getGenres().get(k).equals(popularOrderOnce.get(i))) {
                            help.add(moviesOnce.get(j).getTitle());
                        }
                    }
                }
                for (j = 0; j < serialsOnce.size(); j++) {
                    for (k = 0; k < serialsOnce.get(j).getGenres().size(); k++) {
                        if (serialsOnce.get(j).getGenres().get(k).equals(popularOrderOnce.get(i))) {
                            help.add(serialsOnce.get(j).getTitle());
                        }
                    }
                }

                for (j = 0; j < help.size(); j++) {
                    //daca gasesc in user.getHistory un film sau serial nevizualizat
                    //salvez in st numele show-lui si opresc forul
                    if (!user.getHistory().containsKey(help.get(j))) {
                        st = help.get(j);
                        ok = 1;
                        break;
                    }
                }
                if (ok == 1) {
                    break;
                }

                //daca nu am gasit niciun show , trecem la urmatorul gen
                help.removeAll(help);
            }
            if (st == null) {
                message = "PopularRecommendation cannot be applied!";
            } else {
                message = "PopularRecommendation result: " + st;
            }
        } else {
            message = "PopularRecommendation cannot be applied!";
        }

        return message;
    }



    /***
     * intoarce videoclipul care e cel mai des intalnit in lista de favorite
     * @param users lista de useri
     * @param usernamef userul pentru care se aplica recomadrea
     * @param movies lista de filme
     * @param serials lista de seriale
     * @return
     */

    public String favRec(final List<User> users, final String usernamef,
                         final List<Movie> movies, final List<Serial> serials) {

        String message = null;
        int i, j;
        User user = null;
        ArrayList<User> usersOnce = new ArrayList<>();
        ArrayList<Movie> moviesOnce = new ArrayList<>();
        ArrayList<Serial> serialsOnce = new ArrayList<>();


        moviesOnce = returnMoviesOnce(movies);
        serialsOnce = returnSerialsOnce(serials);
        usersOnce = returnUsersOnce(users);
        user = findRightUser(usersOnce, usernamef);


        int ok = 0;

        ArrayList<String> favShows = new ArrayList<>();
        ArrayList<String> favShowsOnce = new ArrayList<>();
        ArrayList<String> favShowsOnceSorted = new ArrayList<>();
        ArrayList<String> favShowsOnceOrderd = new ArrayList<>();
        ArrayList<String> favShowsOrdered = new ArrayList<>();

        int f = 0;
        if (user.getSubscriptionType().equals("PREMIUM")) {
            //parcurg toti userii
            for (i = 0; i < usersOnce.size(); i++) {
                //pentru toti userii diferiti de userul pentru care se face recomandarea
                if (!usersOnce.get(i).getUsername().equals(usernamef)) {
                    //parcurg lista de favorite
                    for (j = 0; j < usersOnce.get(i).getFavoriteMovies().size(); j++) {
                        //adaug totate titlurile favorite in favShows
                        favShows.add(usersOnce.get(i).getFavoriteMovies().get(j));
                    }
                }
            }

            //adaug in favShows toate showrile favorite fara duplicate
            for (i = 0; i < favShows.size(); i++) {
                f = 0;
                for (j = 0; j < favShowsOnce.size(); j++) {
                    if (favShows.get(i).equals(favShowsOnce.get(j))) {
                        f = 1;
                    }
                }
                if (f == 0) {
                    favShowsOnce.add(favShows.get(i));
                }
            }


            int[] arr = new int[favShowsOnce.size()];
            int[] aux = new int[favShowsOnce.size()];

            for (i = 0; i < favShowsOnce.size(); i++) {
                for (j = 0; j < favShows.size(); j++) {
                    if (favShowsOnce.get(i).equals(favShows.get(j))) {
                        //elementul de la pozitia i din arr si aux
                        //reprezinta numarul de aparitii ale videoclipului
                        //respectiv in lista de favorite ale tuturor userilor
                        //cu exceptia acelui pentru care se face recomadarea
                        arr[i] = arr[i] + 1;
                        aux[i] = aux[i] + 1;
                    }
                }
            }

            action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();

            bubblesortMethods.bubbleSort(aux);

            for (i = 0; i < aux.length; i++) {
                for (j = 0; j < arr.length; j++) {
                    if (aux[i] == arr[j]) {
                        //in favShowsOnceSorted adaug videourile favorite in functie de
                        //cat de des a fost intalnit
                        favShowsOnceSorted.add(favShowsOnce.get(j));
                    }
                }
            }

            for (i = 0; i < favShowsOnceSorted.size(); i++) {
                ok = 0;
                for (j = 0; j < favShowsOnceOrderd.size(); j++) {
                    if (favShowsOnceSorted.get(i).equals(favShowsOnceOrderd.get(j))) {
                        ok = 1;
                    }
                }
                if (ok == 0) {
                    favShowsOnceOrderd.add(favShowsOnceSorted.get(i));
                }
            }

            int k = 0;
            ArrayList<String> titles = new ArrayList<>();
            int[] v = new int[favShowsOnce.size()];

            //parcurg toate filmele si daca apare in lista de favorite adaug
            //titlul in llista titles
            for (i = 0; i < moviesOnce.size(); i++) {
                for (j = 0; j < favShowsOnce.size(); j++) {
                    if (moviesOnce.get(i).getTitle().equals(favShowsOnce.get(j))) {
                        titles.add(moviesOnce.get(i).getTitle());
                    }
                }
            }

            //parcurg toate serialele si daca apare in lista de favorite adaug
            //titlul in llista titles
            for (i = 0; i < serialsOnce.size(); i++) {
                for (j = 0; j < favShowsOnce.size(); j++) {
                    if (serialsOnce.get(i).getTitle().equals(favShowsOnce.get(j))) {
                        titles.add(serialsOnce.get(i).getTitle());
                    }
                }
            }

            //in vectorul k o pozitie reprezinta de cate ori a aparut
            //un video in lista de favorite, doar ca ordonate in functie de
            //aparitia in baza de date
            for (i = 0; i < titles.size(); i++) {
                for (j = 0; j < favShowsOnceOrderd.size(); j++) {
                    if (titles.get(i).equals(favShowsOnceOrderd.get(j))) {
                        v[k] = aux[j];
                        k++;
                    }
                }
            }


            int nonZero = 0;
            int pozitie = aux[nonZero];

            ArrayList<String> returnArray = new ArrayList<>();

            //sortare
            returnArray = favoriteRec(favShowsOnceOrderd, titles, aux, v, nonZero, pozitie);

            String string = null;

            for (i = 0; i < returnArray.size(); i++) {
                if (!user.getHistory().containsKey(returnArray.get(i))) {
                    string = returnArray.get(i);
                    break;
                }
            }
            if (string != null) {
                message = "FavoriteRecommendation result: " + string;
            } else {
                message = "FavoriteRecommendation cannot be applied!";
            }
        }

        return message;
    }


    /***
     * functia intoarce toate videoclipurile nevazute de un user de un anumit gen
     * @param users lista de useri
     * @param usernamef numele userului
     * @param movies lista filme
     * @param serials lista seriale
     * @param genref genul dat ca parametru
     * @return
     */
    public String search(final List<User> users, final String usernamef, final List<Movie> movies,
                         final List<Serial> serials, final String genref) {

        String message = null;
        int i, j;
        User user = null;
        ArrayList<User> usersOnce = new ArrayList<>();
        ArrayList<Movie> moviesOnce = new ArrayList<>();
        ArrayList<Serial> serialsOnce = new ArrayList<>();

        ArrayList<Movie> movies1 = new ArrayList<>();
        ArrayList<Serial> serials1 = new ArrayList<>();

        moviesOnce = returnMoviesOnce(movies);
        serialsOnce = returnSerialsOnce(serials);
        usersOnce = returnUsersOnce(users);
        user = findRightUser(usersOnce, usernamef);

        int ok = 0;
        ArrayList<Movie> moviesFiltered = new ArrayList<>();
        ArrayList<Serial> serialsFiltered = new ArrayList<>();


        //parcurg lista de filme si adaug in moviesFiltered filmele care contin genul dat
        //ca parametru
        for (i = 0; i < moviesOnce.size(); i++) {
            ok = 0;
            for (j = 0; j < moviesOnce.get(i).getGenres().size(); j++) {
                if (moviesOnce.get(i).getGenres().get(j).equals(genref)) {
                    ok = 1;
                }
            }
            if (ok == 1) {
                moviesFiltered.add(moviesOnce.get(i));
            }
        }


        //parcurg lista de seriale si adaug in serialsFiltered filmele care contin genul dat
        //ca parametru
        for (i = 0; i < serialsOnce.size(); i++) {
            ok = 0;
            for (j = 0; j < serialsOnce.get(i).getGenres().size(); j++) {
                if (serialsOnce.get(i).getGenres().get(j).equals(genref)) {
                    ok = 1;
                }
            }
            if (ok == 1) {
                serialsFiltered.add(serialsOnce.get(i));
            }
        }


        ArrayList<Movie> moviesNotSeen = new ArrayList<>();
        ArrayList<Serial> serialNotSeen = new ArrayList<>();

        //in moviesNotSeen adaug filmele care sunt de acel gen
        //si nu sunt vazute de user
        for (i = 0; i < moviesFiltered.size(); i++) {
            if (!user.getHistory().containsKey(moviesFiltered.get(i).getTitle())) {
                moviesNotSeen.add(moviesFiltered.get(i));
            }
        }

        //in serialNotSeen adaug serialele care sunt de acel gen
        //si nu sunt vazute de user
        for (i = 0; i < serialsFiltered.size(); i++) {
            if (!user.getHistory().containsKey(serialsFiltered.get(i).getTitle())) {
                serialNotSeen.add(serialsFiltered.get(i));
            }
        }

        int k = 0;

        action.BubblesortMethods bubblesortMethods = new BubblesortMethods();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> titlesAux = new ArrayList<>();


        double[] combine = new double[moviesNotSeen.size() + serialNotSeen.size()];
        double[] combine2 = new double[moviesNotSeen.size() + serialNotSeen.size()];

        k = 0;

        //parcurg filmele
        for (i = 0; i < moviesNotSeen.size(); i++) {
            //adaug titlul in titles
            titles.add(moviesNotSeen.get(i).getTitle());
            //la pozitia k in combine pun media ratingurilor pentru film
            combine[k] = combine[k]
                    + moviesNotSeen.get(i).getAverageRatingMovie(moviesNotSeen.get(i).getRatings());
            combine2[k] = combine2[k]
                    + moviesNotSeen.get(i).getAverageRatingMovie(moviesNotSeen.get(i).getRatings());
        }

        //continui si pentru seriale
        for (j = 0; j < serialNotSeen.size(); j++) {
            titles.add(serialNotSeen.get(j).getTitle());
            combine[k] = combine[k]
               + serialNotSeen.get(j).getAverageRatingSerial(serialNotSeen.get(j).getSeasons());
            combine2[k] = combine2[k]
               + serialNotSeen.get(j).getAverageRatingSerial(serialNotSeen.get(j).getSeasons());
        }


        bubblesortMethods.bubbleSortascdouble(combine);

        if (combine.length != 0) {
            double c = combine[0];
            int nr = 0;
            for (i = 0; i < combine.length; i++) {
                if (c == combine[i]) {
                    //calculez numarul de elemente cu aceeasi medie intr-un vector
                    nr++;
                }
            }

            for (i = 0; i < titles.size(); i++) {
                titlesAux.add(titles.get(i));
            }

            ArrayList<String> returnArray = new ArrayList<>();

            //daca toate au aceeasi medie le sortez doar dupa nume
            if (nr == combine.length) {
                Collections.sort(titlesAux);
                int nonZero = 0;
                double pozitie = combine2[nonZero];
                returnArray =
                        favoriteSearch(titles, titlesAux, combine, combine2, nonZero, pozitie);

            } else if (nr < combine.length) {
                Collections.sort(titlesAux, Collections.reverseOrder());
                int nonZero = 0;
                double pozitie = combine2[nonZero];
                returnArray =
                        favoriteSearch(titles, titlesAux, combine, combine2, nonZero, pozitie);
            }

            if (returnArray.size() != 0) {
                message = "SearchRecommendation result: " + returnArray;
            } else {
                message = "SearchRecommendation cannot be applied!";
            }
        } else {
            message = "SearchRecommendation cannot be applied!";
        }

        return message;
    }
}


