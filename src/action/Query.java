package action;


import User.User;
import actor.Actor;
import actor.ActorsAwards;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query extends Action {

    public Query(final int actionId, final String actionType, final String objectType,
                 final String genre, final String sortType, final String criteria,
                 final String year, final int number, final List<String> words,
                 final List<String> awards) {
        super(actionId, actionType, objectType, genre, sortType, criteria, year, number, words,
                awards);
    }


    /***
     * sortare actori dupa media filmelor si serialelor in care au jucat
     * @param nr numarul de actori ceruti
     * @param actors lista de actori
     * @param movies lista de filme
     * @param serials lista de seriale
     * @param actorsNumber numarul de actori
     * @param moviesNumber numarul de filme
     * @param serialsNumber numar de seriale
     * @param sortTypef tipul sortarii
     * @return
     */
    public String average(final int nr, final List<Actor> actors, final List<Movie> movies,
                          final List<Serial> serials, final int actorsNumber,
                          final int moviesNumber, final int serialsNumber, final String sortTypef) {


        List<String> actorN = new ArrayList<String>();
        List<Movie> movieI = new ArrayList<>();
        List<Serial> serialI = new ArrayList<>();
        int i;
        double dI = 0;
        double pozitie;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Actor> actorsNotNull = new ArrayList<>();


        //pentru toti actorii
        for (i = 0; i < actorsNumber; i++) {
            //pun in movieI toate filmele in care a jucat
            movieI = actors.get(i).actorMovies(movies, actors.get(i));
            //pun in serialI toate serialele in care a jucat
            serialI = actors.get(i).actorSeials(serials, actors.get(i));
            //calculez media actorului pentru toate filmele si serialele in care a jucat
            dI = actors.get(i).actorFilmographyAverage(actors.get(i), movieI, serialI);
            if (dI != 0) {
                //daca media este diferita de 0 pun actorul intr-o lista speciala
                //pentru toti actorii cu media != 0
                actorsNotNull.add(actors.get(i));
            }
        }

        double[] arr = new double[actorsNotNull.size()];


        //pentru toti actorii cu media != 0
        for (i = 0; i < actorsNotNull.size(); i++) {
            movieI = actorsNotNull.get(i).actorMovies(movies, actorsNotNull.get(i));
            serialI = actorsNotNull.get(i).actorSeials(serials, actorsNotNull.get(i));
            dI = actorsNotNull.get(i)
                    .actorFilmographyAverage(actorsNotNull.get(i), movieI, serialI);
            //pun media fiecaruia intr-un vector ce ma va ajuta la sortare
            arr[i] = actorsNotNull.get(i)
                    .actorFilmographyAverage(actorsNotNull.get(i), movieI, serialI);
            //adaug numele actorului intr-o lista de nume
            arrayList.add(actorsNotNull.get(i).getName());

        }

        //sortez alfabetic numele actorilor
        Collections.sort(arrayList);

        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();


        if (sortTypef.equals("asc")) {
            //daca tipul de sortare este ascendent sortez crescator arr
            bubblesortMethods.bubbleSortascdouble(arr);
            int nonZero = 0;
            //ajung la primul element nenul din vecor, nu ma intereseaza actorii cu media 0
            for (int k = 0; k < actorsNotNull.size(); k++) {
                if (arr[k] == 0) {
                    nonZero++;
                }
            }
            pozitie = arr[nonZero];
            //apelez functia pentru sortare in functie de medie si nume

            actorN = sortbyNameAsc(nr, arrayList, actorsNotNull, actorsNotNull.size(), arr, movies,
                    serials, nonZero, pozitie);
        } else {

            //folosesc acelasi procedeu si pentru sortare descrescatoare
            bubblesortMethods.bubbleSortdescdouble(arr);

            int nonZero = 0;
            pozitie = arr[0];

            actorN = sortbyNameDesc(nr, arrayList, actorsNotNull, actorsNotNull.size(), arr, movies,
                    serials, nonZero, pozitie);
        }

        String message = "Query result: " + actorN.toString();

        return message;
    }

    /***
     * functia intoarce toti actorii cu premiile din awards
     * @param nr actori care trebuie intorsi
     * @param actors lista de actori
     * @param awards lista de premii
     * @param sortTypef tipul de sortare
     * @return
     */
    public String awards(final int nr, final ArrayList<Actor> actors,
                         final List<String> awards, final String sortTypef) {

        int i, j, k;
        int count = 0;
        int ok = 0;
        String message = null;
        ArrayList<Actor> actorArrayList = new ArrayList<>();
        ArrayList<String> actorName = new ArrayList<>();

        //pentru toti actorii
        for (i = 0; i < actors.size(); i++) {
            count = 0;
            //parcurg mapa de premii
            for (Map.Entry<ActorsAwards, Integer> entry : actors.get(i).getAwards().entrySet()) {
                for (j = 0; j < awards.size(); j++) {
                    //daca unul dintre premii coincide cu unul din premiile date ca parametru
                    if (entry.getKey().toString().equals(awards.get(j))) {
                        //incrementez count
                        count++;
                    }
                }
            }
            //daca la final count este egal cu awards.size() inseamna ca
            //actorul respectib are toate premiile cerute si
            //il adaugam intr-o lista
            if (count == awards.size()) {
                actorArrayList.add(actors.get(i));
            }
        }

        ArrayList<Actor> onceActor = new ArrayList<>();

        for (i = 0; i < actorArrayList.size(); i++) {
            ok = 0;
            for (j = 0; j < actorName.size(); j++) {
                if (actorArrayList.get(i).getName().equals(actorName.get(j))) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                actorName.add(actorArrayList.get(i).getName());
                onceActor.add(actorArrayList.get(i));
            }
        }

        //incep algoritmul de sortare dupa numarul de premii al fiecarui actor

        int[] arr = new int[actorName.size()];

        for (i = 0; i < actorName.size(); i++) {
            for (j = 0; j < actors.size(); j++) {
                if (actorName.get(i).equals(actors.get(j).getName())) {
                    //voi pune intr-un vector numarul de premii pentru fiecare actor
                    arr[i] = actors.get(j).getAwards().values().stream().reduce(0, Integer::sum);
                }
            }
        }

        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();

        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();

        //fac cazuri pentru ambele variante de sortare si intorc vectorul de nume
        //sortat

        if (sortTypef.equals("asc")) {
            Collections.sort(actorName);
            bubblesortMethods.bubbleSortdesc(arr);

            int nonZero = 0;
            for (k = 0; k < actorName.size(); k++) {
                if (arr[k] == 0) {
                    nonZero++;
                }
            }
            if (arr.length >= 1) {
                pozitie = arr[nonZero];
                actorNsort = sortbyNameAwards(nr, actorName, onceActor, arr, nonZero, pozitie);
            }
        } else {
            Collections.sort(actorName, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
            int nonZero = 0;
            if (arr.length >= 1) {
                pozitie = arr[0];
                actorNsort = sortbyNameAwards(nr, actorName, onceActor, arr, nonZero, pozitie);
            }
        }

        message = "Query result: " + actorNsort;
        return message;
    }

    /***
     * intoarce toti actorii in descrierea carora gasim cuvintele din words
     * @param actors lista de actori
     * @param words lista de cuvinte
     * @param sortTypef tipul de sortare
     * @return
     */
    public String filterDescription( final ArrayList<Actor> actors,
                                    final List<String> words, final String sortTypef) {

        int i, j, k, t, m;
        int count = 0;
        int ok = 0;
        int ok1 = 0;
        String message = null;
        ArrayList<Actor> actorArrayList = new ArrayList<>();
        ArrayList<String> actorName = new ArrayList<>();
        ArrayList<String> words2 = new ArrayList<>();


        int[] arr = new int[words.size()];

        for (i = 0; i < actors.size(); i++) {
            count = 0;
            ok = 0;

            //pentru fiecare cuvant intializez un arr[m] corespunxator cu 0
            for (m = 0; m < words.size(); m++) {
                arr[m] = 0;
            }

            for (j = 0; j < words.size(); j++) {

                //ma folosesc de regex pentru a fi sigura ca iau cuvantul independent
                //ci nu ca parte din altul

                String regex = "\\b" + words.get(j) + "\\b";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher =
                        pattern.matcher(actors.get(i).getCareerDescription().toLowerCase());
                //daca mather imi gaseste un cuvant in descrierea actorului
                while (matcher.find()) {
                    //ii incrementeaza numarul de cuvinte j gasite cu 1
                    arr[j] = arr[j] + 1;
                }
            }

            //pentru ca actorul sa fie unul care indelineste conditia
            //trebuie ca toate valorile vectorului sa fie != 0
            //(adica am gasit cel putin o aparitie pentru fiecare cuvant in descriere)
            for (m = 0; m < words.size(); m++) {
                if (arr[m] != 0) {
                    count++;
                }
            }

            //daca atorul indeplineste conditia il adaug intr-o lista speciala
            if (count == words.size()) {
                for (k = 0; k < actorArrayList.size(); k++) {
                    if (actors.get(i).getName().equals(actorArrayList.get(k).getName())) {
                        ok = 1;
                    }
                }
                if (ok == 0) {
                    actorArrayList.add(actors.get(i));
                }
            }
        }
        for (i = 0; i < actorArrayList.size(); i++) {
            actorName.add(actorArrayList.get(i).getName());
        }

        //realizez sortarea

        if (sortTypef.equals("asc")) {
            Collections.sort(actorName);
        } else {
            Collections.sort(actorName, Collections.reverseOrder());
        }

        message = "Query result: " + actorName;
        return message;
    }

    /***
     * functie care imi intoarce primele nr filme in functie de rating
     * @param nr numar filme de intors
     * @param movies lista filme
     * @param movieNumber  numar filme
     * @param year fltru ce reprezinta anul
     * @param genref filtru ce reprezinta genul
     * @param sortTypef tip de sortare
     * @return
     */
    public String videoRatingMovie(final int nr, final List<Movie> movies, final int movieNumber,
                                   final List<String> year, final List<String> genref,
                                   final String sortTypef) {
        String message = null;
        int i, j;
        ArrayList<Movie> movieI = new ArrayList<>();
        ArrayList<String> movieTitles = new ArrayList<>();
        ArrayList<Movie> movies1 = new ArrayList<>();
        double dI;
        int k = 0;


        //pun in movies1 filmele filtrate
        for (i = 0; i < movies.size(); i++) {
            if (year.get(0) == null) {
                if (movies.get(i).getGenres().contains(genref.get(0))) {
                    movies1.add(movies.get(i));
                }
            } else if (genref.get(0) == null) {
                if (String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    movies1.add(movies.get(i));
                }
            } else {
                if (movies.get(i).getGenres().contains(genref.get(0))
                        && String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    movies1.add(movies.get(i));
                }
            }
        }

        double[] arr = new double[movies1.size()];
        double[] aux = new double[movies1.size()];

        //calculez media fiecarui film pe care o pun pe pozitia corespunzatoare
        //filmului din cei 2 vectori
        for (i = 0; i < movies1.size(); i++) {
            if (movies1.get(i).getAverageRatingMovie(movies1.get(i).getRatings()) != 0) {
                arr[k] = movies1.get(i).getAverageRatingMovie(movies1.get(i).getRatings());
                aux[k] = movies1.get(i).getAverageRatingMovie(movies1.get(i).getRatings());
                k++;
                //adaug in movieI filmele care nu au media 0
                movieI.add(movies1.get(i));
                //adaug in movieTitles titlurile de filmele care nu au media 0
                movieTitles.add(movies1.get(i).getTitle());
            }
        }

        //aplic sortarea
        double pozitie;
        ArrayList<String> videoNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(movieTitles);
            bubblesortMethods.bubbleSortdescdouble(arr);
        } else {
            Collections.sort(movieTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSortascdouble(arr);
        }

        if (arr.length >= 1) {
            nonZero = 0;
            pozitie = arr[0];
            videoNsort = sortbyNameRatingMovie(nr, movieTitles, movieI, arr, aux, nonZero,
                    pozitie);
        }

        //intorc numarul cerut de filme dac acesta este mai mic decat videoNsort.size()
        ArrayList<String> last = new ArrayList<>();
        if (videoNsort.size() > nr) {
            for (i = 0; i < nr; i++) {
                last.add(videoNsort.get(i));
            }
            message = "Query result: " + last;
        }

        message = "Query result: " + videoNsort;

        return message;
    }

    /***
     * functie similara cu videoRatingSerial
     * @param nr numarul de seriale intoarse
     * @param serials lista de seriale
     * @param serialsNumber numar de serial
     * @param year filtru ce reprezinta anul
     * @param genref filtru ce reprezinta genul
     * @param sortTypef tip de sortare
     * @return
     */
    public String videoRatingSerial(final int nr, final List<Serial> serials,
                                    final int serialsNumber, final List<String> year,
                                    final List<String> genref,
                                    final String sortTypef) {
        String message = null;
        int i, j;
        ArrayList<Serial> serials1 = new ArrayList<>();
        ArrayList<String> serialsTitles = new ArrayList<>();
        ArrayList<Serial> serialI = new ArrayList<>();
        double dI;
        int k = 0;

        for (i = 0; i < serials.size(); i++) {
            if (year.get(0) == null) {
                if (serials.get(i).getGenres().contains(genref.get(0))) {
                    serials1.add(serials.get(i));
                }
            } else if (genref.get(0) == null) {
                if (String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    serials1.add(serials.get(i));
                }
            } else {
                if (serials.get(i).getGenres().contains(genref.get(0))
                        && String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    serials1.add(serials.get(i));
                }
            }
        }

        double[] arr = new double[serials1.size()];
        double[] aux = new double[serials1.size()];

        for (i = 0; i < serials1.size(); i++) {
            if (serials1.get(i).getAverageRatingSerial(serials1.get(i).getSeasons()) != 0) {
                arr[k] = serials1.get(i).getAverageRatingSerial(serials1.get(i).getSeasons());
                aux[k] = serials1.get(i).getAverageRatingSerial(serials1.get(i).getSeasons());
                k++;
                serialI.add(serials1.get(i));
                serialsTitles.add(serials1.get(i).getTitle());
            }
        }

        double pozitie;
        ArrayList<String> videoNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(serialsTitles);
            bubblesortMethods.bubbleSortascdouble(arr);
        } else {
            Collections.sort(serialsTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSortdescdouble(arr);
        }

        if (arr.length >= 1) {
            nonZero = 0;
            pozitie = arr[0];
            videoNsort = sortbyNameRatingSerial(nr, serialsTitles, serialI, arr, aux, nonZero,
                    pozitie);
        }

        ArrayList<String> last = new ArrayList<>();
        if (videoNsort.size() > nr) {
            for (i = 0; i < nr; i++) {
                last.add(videoNsort.get(i));
            }
            message = "Query result: " + last;
        }


        message = "Query result: " + videoNsort;
        return message;
    }


    /***
     * functia imi intoarce primele nr filme
     * @param nr numarul de filme care trebuie intoarse
     * @param users lista de useri
     * @param usersNumber numar useri
     * @param year filtru care reprezinta anul
     * @param genref filtru care reprezinta genul
     * @param sortTypef tip de sortare
     * @param movies lista de filme
     * @param moviesNumber numar filme
     * @return
     */
    public String favoriteMovie(final int nr, final List<User> users, final int usersNumber,
                                final List<String> year, final List<String> genref,
                                final String sortTypef, final List<Movie> movies,
                                final int moviesNumber) {

        String message = null;
        int i, j;
        int ok = 0;
        ArrayList<Movie> moviesFavorite = new ArrayList<>();
        ArrayList<Movie> moviesFavoriteOnce = new ArrayList<>();
        ArrayList<Movie> movieTitles = new ArrayList<>();
        ArrayList<Show> once = new ArrayList<>();
        ArrayList<String> movieTitlesOnce = new ArrayList<>();
        int k = 0;

        // parcurg toti userii si adaug in lista de filme favorite toate filmele
        //favorite pentru toti userii
        for (i = 0; i < usersNumber; i++) {
            for (j = 0; j < users.get(i).getFavoriteMovies().size(); j++) {
                for (k = 0; k < moviesNumber; k++) {
                    if (movies.get(k).getTitle().equals(users.get(i).getFavoriteMovies().get(j))) {
                        moviesFavorite.add(movies.get(k));
                    }
                }
            }
        }

        //elimin duplicatele
        moviesFavoriteOnce = returnMoviesOnce(moviesFavorite);

        //filtrez filmele corespunzator dupa gen si an
        i = 0;
        while (i < moviesFavoriteOnce.size()) {
            for (j = 0; j < moviesFavoriteOnce.get(i).getGenres().size(); j++) {
                if (genref.get(0) == null
                    && String.valueOf(moviesFavoriteOnce.get(i).getYear()).equals(year.get(0))) {
                    movieTitles.add(moviesFavoriteOnce.get(i));
                } else if (year.get(0) == null
                        && (moviesFavoriteOnce.get(i).getGenres().get(j).equals(genref.get(0)))) {
                    movieTitles.add(moviesFavoriteOnce.get(i));
                } else if (moviesFavoriteOnce.get(i).getGenres().get(j).equals(genref.get(0))
                     && String.valueOf(moviesFavoriteOnce.get(i).getYear()).equals(year.get(0))) {
                    movieTitles.add(moviesFavoriteOnce.get(i));
                } else if (year.get(0) == null && genref.get(0) == null) {
                    movieTitles.add(moviesFavoriteOnce.get(i));
                }
            }
            i++;
        }

        //elimin duplicatele
        ok = 0;
        for (i = 0; i < movieTitles.size(); i++) {
            ok = 0;
            for (j = 0; j < once.size(); j++) {
                if (movieTitles.get(i).getTitle().equals(once.get(j).getTitle())) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                once.add(movieTitles.get(i));
            }
        }

        //pun toate titlurile de filme in movieTitleOnce fara duplicate
        for (i = 0; i < once.size(); i++) {
            movieTitlesOnce.add(once.get(i).getTitle());
        }


        int[] arr = new int[once.size()];
        int[] aux = new int[once.size()];

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (i = 0; i < once.size(); i++) {
            arr[i] = 0;
        }

        for (i = 0; i < once.size(); i++) {
            hashMap.put(once.get(i).getTitle(), 0);
        }

        int count = 0;
        ok = 0;

        //calculez de cate ori apare un film favorit in lista de favorite
        //a tuturor userilor
        for (j = 0; j < once.size(); j++) {
            count = 0;
            for (i = 0; i < users.size(); i++) {
                if (users.get(i).getFavoriteMovies().contains(once.get(j).getTitle())) {
                    arr[j] = arr[j] + 1;
                    aux[j] = aux[j] + 1;
                }
            }
        }

        int pozitie;

        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();

        ArrayList<String> actorNsort = new ArrayList<>();

        int nonZero = 0;

        //realizez fortarea
        if (sortTypef.equals("asc")) {
            Collections.sort(movieTitlesOnce);
            bubblesortMethods.bubbleSortdesc(arr);
            for (k = 0; k < once.size(); k++) {
                if (arr[k] == 0) {
                    nonZero++;
                }
            }
            if (arr.length >= 1) {
                pozitie = arr[nonZero];
                actorNsort =
                        sortbyNameFavorite(nr, movieTitlesOnce, once, arr, aux, nonZero, pozitie);
            }
        } else {
            Collections.sort(movieTitlesOnce, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
            nonZero = 0;
            if (arr.length >= 1) {
                pozitie = arr[0];
                actorNsort =
                        sortbyNameFavorite(nr, movieTitlesOnce, once, arr, aux, nonZero, pozitie);
            }
        }

        message = "Query result: " + actorNsort;

        return message;
    }

    /***
     *  asemantor ca la favoriteMovie doar ca aici trebuie sa caut in lista de seriale
     * @param nr numarul de seriale care trebuie intors
     * @param users lista de useri
     * @param usersNumber numar useri
     * @param year filtru care reprezinta anul
     * @param genref filtru care reprezinta genul
     * @param sortTypef tip sortare
     * @param serials lista seriale
     * @param serialsNumber numar seriale
     * @return
     */

    public String favoriteSerial(final int nr, final List<User> users, final int usersNumber,
                                 final List<String> year, final List<String> genref,
                                 final String sortTypef, final List<Serial> serials,
                                 final int serialsNumber) {


        String message = null;
        int i, j;
        ArrayList<Serial> serialsFavorite = new ArrayList<>();
        ArrayList<Serial> serialsFavoriteOnce = new ArrayList<>();
        ArrayList<Serial> serialTitles = new ArrayList<>();
        ArrayList<Show> once = new ArrayList<>();
        ArrayList<String> serialTitlesOnce = new ArrayList<>();
        int k = 0;
        int ok = 0;

        for (i = 0; i < usersNumber; i++) {
            for (j = 0; j < users.get(i).getFavoriteMovies().size(); j++) {
                for (k = 0; k < serialsNumber; k++) {
                    if (serials.get(k).getTitle().equals(users.get(i).getFavoriteMovies().get(j))) {
                        serialsFavorite.add(serials.get(k));
                    }
                }
            }
        }


        serialsFavoriteOnce = returnSerialsOnce(serialsFavorite);

        i = 0;
        while (i < serialsFavoriteOnce.size()) {
            for (j = 0; j < serialsFavoriteOnce.get(i).getGenres().size(); j++) {
                if (genref.get(0) == null
                    && String.valueOf(serialsFavoriteOnce.get(i).getYear()).equals(year.get(0))) {
                    serialTitles.add(serialsFavoriteOnce.get(i));
                } else if (year.get(0) == null
                        && (serialsFavoriteOnce.get(i).getGenres().get(j).equals(genref.get(0)))) {
                    serialTitles.add(serialsFavoriteOnce.get(i));
                } else if (serialsFavoriteOnce.get(i).getGenres().get(j).equals(genref.get(0))
                    && String.valueOf(serialsFavoriteOnce.get(i).getYear()).equals(year.get(0))) {
                    serialTitles.add(serialsFavorite.get(i));
                }
            }
            i++;
        }


        for (i = 0; i < serialTitles.size(); i++) {
            ok = 0;
            for (j = 0; j < once.size(); j++) {
                if (serialTitles.get(i).getTitle().equals(once.get(j).getTitle())) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                once.add(serialTitles.get(i));
            }
        }


        for (i = 0; i < once.size(); i++) {
            serialTitlesOnce.add(once.get(i).getTitle());
        }

        int[] arr = new int[once.size()];
        int[] aux = new int[once.size()];


        for (i = 0; i < once.size(); i++) {
            arr[i] = 0;
        }


        int count = 0;
        ok = 0;
        for (j = 0; j < once.size(); j++) {
            count = 0;
            for (i = 0; i < users.size(); i++) {
                if (users.get(i).getFavoriteMovies().contains(once.get(j).getTitle())) {
                    arr[j] = arr[j] + 1;
                    aux[j] = aux[j] + 1;
                }
            }
        }

        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(serialTitlesOnce);
            bubblesortMethods.bubbleSortdesc(arr);
            for (k = 0; k < once.size(); k++) {
                if (arr[k] == 0) {
                    nonZero++;
                }
            }
            if (arr.length >= 1) {
                pozitie = arr[nonZero];
                actorNsort =
                        sortbyNameFavorite(nr, serialTitlesOnce, once, arr, aux, nonZero, pozitie);
            }
        } else {
            Collections.sort(serialTitlesOnce, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
            nonZero = 0;
            if (arr.length >= 1) {
                pozitie = arr[0];
                actorNsort =
                        sortbyNameFavorite(nr, serialTitlesOnce, once, arr, aux, nonZero, pozitie);
            }
        }

        message = "Query result: " + actorNsort;
        return message;
    }


    /***
     * intoarce primele nr videouri sortate dupa durata
     * @param nr filme care trebuie intoarse
     * @param year filtru ce reprezinta anul
     * @param genref filtru ce reprezinta genul
     * @param sortTypef tipul de sortare
     * @param movies lista de filme
     * @param moviesNumber numar filme
     * @return
     */
    public String longestMovie(final int nr, final List<String> year, final List<String> genref,
                               final String sortTypef, final List<Movie> movies,
                               final int moviesNumber) {

        String message = null;
        int i, j;
        ArrayList<Movie> moviesLongest = new ArrayList<>();
        ArrayList<String> movieTitles = new ArrayList<>();
        ArrayList<Movie> moviesLongestOnce = new ArrayList<>();
        int k = 0;

        //filtrez filemele dupa gen si an
        for (i = 0; i < movies.size(); i++) {
            for (j = 0; j < movies.get(i).getGenres().size(); j++) {
                if (movies.get(i).getGenres().get(j).equals(genref.get(0))
                        && String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    moviesLongest.add(movies.get(i));
                } else if (genref.get(0) == null
                        && String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    moviesLongest.add(movies.get(i));
                } else if (movies.get(i).getGenres().get(j).equals(genref.get(0))
                        && year.get(0) == null) {
                    moviesLongest.add(movies.get(i));
                } else if (genref.get(0) == null && year.get(0) == null) {
                    moviesLongest.add(movies.get(i));
                }
            }
        }

        int ok = 0;

        //elimin duplicatele
        moviesLongestOnce = returnMoviesOnce(moviesLongest);


        int[] arr = new int[moviesLongestOnce.size()];
        int[] aux = new int[moviesLongestOnce.size()];

        //pentru fiecare film calculez durata si o pun pe pozitia corespunzatoare din vector
        for (i = 0; i < moviesLongestOnce.size(); i++) {
            //construiesc un arraylist cu titlurile filmelor
            movieTitles.add(moviesLongestOnce.get(i).getTitle());
            arr[i] = moviesLongestOnce.get(i).getDuration();
            aux[i] = moviesLongestOnce.get(i).getDuration();
        }

        //realizez sortarea
        int pozitie;
        ArrayList<String> actornmb = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();
        int nonZero = 0;

        if (sortTypef.equals("asc")) {
            Collections.sort(movieTitles);
            bubblesortMethods.bubbleSortdesc(arr);

        } else {
            Collections.sort(movieTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
        }

        if (arr.length >= 1) {
            nonZero = 0;
            pozitie = arr[0];
            actornmb =
                    sortbyNameLongestAsc(nr, movieTitles, moviesLongestOnce, arr, aux, nonZero,
                            pozitie);
        }

        message = "Query result: " + actornmb;

        //daca nr este mai mic decat numarul de elemente din actornmb le intorc doar pe primele nr
        ArrayList<String> nFirst = new ArrayList<>();
        if (actornmb.size() > nr) {
            for (i = 0; i < nr; i++) {
                nFirst.add(actornmb.get(i));
                message = "Query result: " + nFirst;
            }
        }

        return message;
    }


    /***
     * asemanator ca longestMovie, difera in principal doar modul de calculare a duratei
     * @param nr numarul de seriale care trebuie intoarse
     * @param year filtu ce reprezinta anul
     * @param genref filtru ce reprezinta genul
     * @param sortTypef tip sortare
     * @param serials lista seriale
     * @param serialsNumber numar seriale
     * @return
     */
    public String longestSerial(final int nr, final List<String> year, final List<String> genref,
                                final String sortTypef, final List<Serial> serials,
                                final int serialsNumber) {
        String message = null;
        int i, j;
        ArrayList<Serial> serialLongest = new ArrayList<>();
        ArrayList<String> serialTitles = new ArrayList<>();
        ArrayList<Serial> serialLongestOnce = new ArrayList<>();
        int k = 0;


        for (i = 0; i < serials.size(); i++) {
            for (j = 0; j < serials.get(i).getGenres().size(); j++) {
                if (serials.get(i).getGenres().get(j).equals(genref.get(0))
                        && String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    serialLongest.add(serials.get(i));
                } else if (genref.get(0) == null
                        && String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    serialLongest.add(serials.get(i));
                } else if (serials.get(i).getGenres().get(j).equals(genref.get(0))
                        && year.get(0) == null) {
                    serialLongest.add(serials.get(i));
                }
            }
        }


        int ok = 0;
        serialLongestOnce = returnSerialsOnce(serialLongest);



        int[] arr = new int[serialLongestOnce.size()];
        int[] aux = new int[serialLongestOnce.size()];


        if (serialLongestOnce.size() < nr) {
            for (i = 0; i < serialLongestOnce.size(); i++) {
                serialTitles.add(serialLongestOnce.get(i).getTitle());
                arr[i] = serialLongestOnce.get(i)
                        .getSerialDuration(serialLongestOnce.get(i).getSeasons());
                aux[i] = serialLongestOnce.get(i)
                        .getSerialDuration(serialLongestOnce.get(i).getSeasons());
            }
        }


        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(serialTitles);
            bubblesortMethods.bubbleSortdesc(arr);
        } else {
            Collections.sort(serialTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
        }

        if (arr.length >= 1) {
            nonZero = 0;
            pozitie = arr[0];
            actorNsort =
                    sortbyNameLongestAscSerial(nr, serialTitles, serialLongestOnce, arr, aux,
                            nonZero, pozitie);
        }

        message = "Query result: " + actorNsort;
        return message;
    }

    /***
     * functia intoarce primele nr videouri sortate dupa nr de vizualizari
     * @param nr numarul de filme care trebuie intoarse
     * @param year filtru ce reprezinta anul
     * @param genref filtru ce reprezinta genul
     * @param sortTypef tip sortare
     * @param users lista de utilizatori
     * @param numberUsers numar utilizatori
     * @param movies lista de filme
     * @param numberMovies numar filme
     * @return
     */
    public String mostViewedMovies(final int nr, final List<String> year,
                                   final List<String> genref, final String sortTypef,
                                   final List<User> users, final int numberUsers,
                                   final List<Movie> movies, final int numberMovies) {
        String message = null;
        int i, j, k;
        ArrayList<Movie> mostViewdMovies = new ArrayList<>();
        ArrayList<String> mostViewedMoviesTitles = new ArrayList<>();

        //selectez serialele in functie de filtre
        for (i = 0; i < numberMovies; i++) {
            for (j = 0; j < movies.get(i).getGenres().size(); j++) {
                if (movies.get(i).getGenres().get(j).equals(genref.get(0))
                        && String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    mostViewdMovies.add(movies.get(i));
                } else if (genref.get(0) == null
                        && String.valueOf(movies.get(i).getYear()).equals(year.get(0))) {
                    mostViewdMovies.add(movies.get(i));
                } else if (movies.get(i).getGenres().get(j).equals(genref.get(0))
                        && year.get(0) == null) {
                    mostViewdMovies.add(movies.get(i));
                }
            }
        }

        ArrayList<Movie> mostViewedMoviesOnce = new ArrayList<>();
        int ok = 0;

        //elimin duplicatele
        mostViewedMoviesOnce = returnMoviesOnce(mostViewdMovies);

        //construiesc un vector doar cu titlurile serialelor
        for (i = 0; i < mostViewedMoviesOnce.size(); i++) {
            mostViewedMoviesTitles.add(mostViewedMoviesOnce.get(i).getTitle());
        }

        int[] viewsNumberAllUsers = new int[mostViewdMovies.size()];
        int[] aux = new int[mostViewdMovies.size()];

        //parcurg toti userii si lista de filme filtrate
        for (i = 0; i < users.size(); i++) {
            for (j = 0; j < mostViewedMoviesOnce.size(); j++) {
                for (Map.Entry<String, Integer> entry : users.get(i).getHistory().entrySet()) {
                    //daca gasim in mapa de filme vazute a userului filmul curent
                    if (entry.getKey().equals(mostViewedMoviesOnce.get(j).getTitle())) {
                        // adaugam pentru pozitia din vector corespunzatoare fimului
                        //j numarul de vizualizari
                        viewsNumberAllUsers[j] = viewsNumberAllUsers[j] + entry.getValue();
                        aux[j] = aux[j] + entry.getValue();
                    }
                }
            }
        }

        //sortare
        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(mostViewedMoviesTitles);
            bubblesortMethods.bubbleSortdesc(viewsNumberAllUsers);
        } else {
            Collections.sort(mostViewedMoviesTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(viewsNumberAllUsers);
        }

        if (viewsNumberAllUsers.length >= 1) {
            nonZero = 0;
            pozitie = viewsNumberAllUsers[0];
            actorNsort = sortbyNameLongestAsc(nr, mostViewedMoviesTitles, mostViewedMoviesOnce,
                    viewsNumberAllUsers, aux, nonZero, pozitie);
        }

        i = 0;

        ArrayList<String> last = new ArrayList<>();
        while (i < viewsNumberAllUsers.length) {
            if (viewsNumberAllUsers[i] != 0) {
                last.add(actorNsort.get(i));
            }
            i++;
        }

        message = "Query result: " + last;

        ArrayList<String> m = new ArrayList<>();

        if (last.size() > nr) {
            for (i = 0; i < nr; i++) {
                m.add(last.get(i));
            }
            message = "Query result: " + m;
        }

        return message;
    }

    /***
     * functia intoarce primele nr videouri sortate dupa nr de vizualizari
     * @param nr numarul care trebuie intors de seriale
     * @param year filtru care reprezinta anul
     * @param genref filtru care reprezinta genul
     * @param sortTypef tip de sortare
     * @param users lista de useri
     * @param numberUsers numarul useri
     * @param serials lista de seriale
     * @param numberSerials numar seriale
     * @return
     */
    public String mostViewedSerials(final int nr, final List<String> year,
                                    final List<String> genref, final String sortTypef,
                                    final List<User> users, final int numberUsers,
                                    final List<Serial> serials, final int numberSerials) {
        String message = null;
        int i, j, k;
        ArrayList<Serial> mostViewedSerial = new ArrayList<>();
        ArrayList<Serial> mostViewedSerialOnce = new ArrayList<>();
        ArrayList<String> mostViewedSerialTitles = new ArrayList<>();

        for (i = 0; i < numberSerials; i++) {
            for (j = 0; j < serials.get(i).getGenres().size(); j++) {
                if (serials.get(i).getGenres().get(j).equals(genref.get(0))
                        && String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    mostViewedSerial.add(serials.get(i));
                } else if (genref.get(0) == null
                        && String.valueOf(serials.get(i).getYear()).equals(year.get(0))) {
                    mostViewedSerial.add(serials.get(i));
                } else if (serials.get(i).getGenres().get(j).equals(genref.get(0))
                        && year.get(0) == null) {
                    mostViewedSerial.add(serials.get(i));
                }
            }
        }

        int ok = 0;


        mostViewedSerialOnce = returnSerialsOnce(mostViewedSerial);


        for (i = 0; i < mostViewedSerialOnce.size(); i++) {
            mostViewedSerialTitles.add(mostViewedSerialOnce.get(i).getTitle());
        }


        int[] viewsNumberAllUsers = new int[mostViewedSerial.size()];
        int[] aux = new int[mostViewedSerial.size()];;

        for (i = 0; i < users.size(); i++) {
            for (j = 0; j < mostViewedSerialOnce.size(); j++) {
                for (Map.Entry<String, Integer> entry : users.get(i).getHistory().entrySet()) {
                    if (entry.getKey().equals(mostViewedSerialOnce.get(j).getTitle())) {
                        viewsNumberAllUsers[j] = viewsNumberAllUsers[j] + entry.getValue();
                        aux[j] = aux[j] + entry.getValue();
                    }
                }
            }
        }

        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new action.BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(mostViewedSerialTitles);
            bubblesortMethods.bubbleSortdesc(viewsNumberAllUsers);
        } else {
            Collections.sort(mostViewedSerialTitles, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(viewsNumberAllUsers);

        }

        if (viewsNumberAllUsers.length >= 1) {
            nonZero = 0;
            pozitie = viewsNumberAllUsers[0];
            actorNsort =
                    sortbyNameLongestAscSerial(nr, mostViewedSerialTitles, mostViewedSerialOnce,
                            viewsNumberAllUsers, aux, nonZero, pozitie);
        }
        i = 0;

        ArrayList<String> last = new ArrayList<>();
        while (i < viewsNumberAllUsers.length) {
            if (viewsNumberAllUsers[i] != 0) {
                last.add(actorNsort.get(i));
            }
            i++;
        }


        message = "Query result: " + last;

        ArrayList<String> m = new ArrayList<>();

        if (last.size() > nr) {
            for (i = 0; i < nr; i++) {
                m.add(last.get(i));
            }
            message = "Query result: " + m;
        }

        return message;
    }



    /***
     * functia intoarce nr utilizatori sortati dupa nr rating acordat
     * @param nr numarul de useri care trebuie intorsi
     * @param sortTypef tipul de sortare
     * @param users lista de useri
     * @param numberUsers numar useri
     * @return
     */
    public String numberRatings(final int nr, final String sortTypef,
                                final List<User> users, final int numberUsers) {

        String message = null;
        int i, j, k;
        ArrayList<User> activeUser = new ArrayList<User>();
        ArrayList<String> activeUserName = new ArrayList<>();
        HashMap<String, Integer> usernameRatings = new HashMap<>();
        int activity = 0;

        //parcurg toti userii si daca au dat cel putin un rating
        //retin intr-o mapa numele userului si cate ratinguri a dat
        for (i = 0; i < users.size(); i++) {
            activity = 0;
            activity = users.get(i).getRatingMovie().size() + users.get(i).getRatingSerial().size();
            if (activity != 0) {
                activeUser.add(users.get(i));
                usernameRatings.put(users.get(i).getUsername(), activity);
            }
        }

        int[] arr = new int[activeUser.size()];
        int[] auxv = new int[activeUser.size()];

        for (i = 0; i < activeUser.size(); i++) {
            arr[i] = activeUser.get(i).getRatingMovie().size()
                    + activeUser.get(i).getRatingSerial().size();
            auxv[i] = activeUser.get(i).getRatingMovie().size()
                    + activeUser.get(i).getRatingSerial().size();
        }

        for (i = 0; i < activeUser.size(); i++) {
            activeUserName.add(activeUser.get(i).getUsername());
        }

        //sortare
        int pozitie;
        ArrayList<String> actorNsort = new ArrayList<>();
        action.BubblesortMethods bubblesortMethods = new BubblesortMethods();
        int nonZero = 0;
        if (sortTypef.equals("asc")) {
            Collections.sort(activeUserName);
            bubblesortMethods.bubbleSortdesc(arr);
        } else {
            Collections.sort(activeUserName, Collections.reverseOrder());
            bubblesortMethods.bubbleSort(arr);
        }

        if (arr.length >= 1) {
            nonZero = 0;
            pozitie = arr[0];
            actorNsort = sortbyNameUser(nr, activeUserName, activeUser, arr, auxv, nonZero,
                    pozitie);
        }

        message = "Query result: " + actorNsort;

        ArrayList<String> u = new ArrayList<>();
        if (actorNsort.size() > nr) {
            for (i = 0; i < nr; i++) {
                u.add(actorNsort.get(i));
            }
            message = "Query result: " + u;
        }

        return message;
    }
}
