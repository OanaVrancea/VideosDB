package action;


import User.User;
import entertainment.Movie;
import entertainment.Serial;

import java.util.Map;

public class Command extends Action {

    public Command(final int actionId, final String actionType, final String type,
                   final String username, final String genre, final String title) {
        super(actionId, actionType, type, username, genre, title);
    }

    /***
     * functia verifica daca videoul poate fi adaugat in lista de favorite
     * a userului sau nu
     * @param show numele videoului care ar trebui adaugat la favorie
     * @param user numele utilizatorului pentru care trebuie efectuata actiunea
     * @return
     */
    public String favorite(final String show, final User user) {

        String message = null;
        int ok = 0;

        //parcurg lista de favorite, iar daca gasesc numele videoclipului
        //deja acolo initializez ok = 1
        for (int i = 0; i < user.getFavoriteMovies().size(); i++) {
            if (user.getFavoriteMovies().get(i).equals(show)) {
                ok = 1;
                break;
            }
        }

        //daca videoclipul nu a fost gasit la lista de favorite
        //cautam sa vedem daca a fost vizualizat
        if (ok == 0) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (entry.getKey().equals(show)) {
                    //daca a fost vizualizat il adaugam in lista de favorite
                    user.getFavoriteMovies().add(show);
                    message = "success -> " + show + " was added as favourite";
                }
            }
        }

        //daca videoclipul a fost gasit in lista de favorite
        //inttoarcem error
        if (ok == 1) {
            message = "error -> " + show + " is already in favourite list";
        }

        // daca nu l-am gasit nici in lista de favorite,
        //nici in history inseamna ca nu a fost vazut
        if (message == null) {
            message = "error -> " + show + " is not seen";
        }
        return message;
    }

    /***
     * adauga un video in lista de history a unui user sau
     * incerementeaza numarul de vizualizari daca a fost vazut deja
     * @param show numele videoului care trebuie marcat ca vazut
     * @param user utilizatorul pentru care se face operatia
     * @return
     */
    public String view(final String show, final User user) {

        String message = null;
        int ok = 0;

        //cautam videoul in mapa de videouri vazute ale utilizatorului
        //si setam ok = 1 daca il gasim
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(show)) {
                ok = 1;
                //daca il gasim incrementam cu 1 numarul de vizualizari
                user.getHistory().merge(entry.getKey(), 1, Integer::sum);
                message = "success -> " + show + " was viewed with total views of "
                        + entry.getValue();
            }
        }

        //daca nu l-am gasit il adaugam in mapa cu numrul de vizualizari = 1
        if (ok == 0) {
            user.getHistory().put(show, 1);
            message = "success -> " + show + " was viewed with total views of 1";
        }

        return message;
    }

    /***
     * In aceasta functie dau un rating unui film
     * @param show numele videoclipului care primeste un rating
     * @param user numele utilizatorului care efectueaza actiunea
     * @param nota nota data
     * @param movie filmul pentru care vom adauga nota
     * @return
     */
    public String ratingMovie(final String show, final User user, final double nota,
                              final Movie movie) {

        String message = null;
        int ok = 0;

        //caut numele videoului in map-ul history al userului
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            //daca il gasesc
            if (entry.getKey().equals(show)) {
                //setez 2 valoarea lui ok pentru a indica asta
                ok = 2;
                //caut in mapa userului pentru filme daca deja a fost notat
                for (Map.Entry<String, Integer> entry1 : user.getRatingMovie().entrySet()) {
                    if (entry1.getValue().equals(1) && entry1.getKey().equals(movie.getTitle())) {
                        //daca a fost notat setez ok = 1
                        ok = 1;
                    }
                }
            }
        }

        //daca ok = 2 adaug nota in lista de rating pentru Movie
        if (ok == 2) {
            movie.addRating(nota);
            message =
                    "success -> " + show + " was rated with " + nota + " by " + user.getUsername();

            //adaug in mapa de videouri vizualizate acest film pentru a nu ii mai putea acorda o
            //alta nota de catre acelasi user
            user.addRatedMovie(movie.getTitle());

        } else if (ok == 1) {
            //cazul in care am gasit videoul in mapa userului pentru videouri notate
            message = "error -> " + movie.getTitle() + " has been already rated";
        } else if (ok == 0) {
            //daca nu se schimba valoarea lui ok de la 0 inseamna ca nu l-am gasit
            //in mapa de videoclipuri vizualizate
            message = "error -> " + movie.getTitle() + " is not seen";
        }

        return message;
    }

    /***
     *  In aceasta functie dau un rating unui serial pentru un anumit sezon
     * @param show  numele videoclipului care primeste un rating
     * @param user numele utilizatorului care efectueaza actiunea
     * @param nota  nota data
     * @param serial movie filmul pentru care vom adauga nota
     * @param seasonNumberf numarul sezonului pentru care se acorda nota
     * @return
     */
    public String ratingSerial(final String show, final User user, final double nota,
                               final Serial serial, final int seasonNumberf) {

        String message = null;
        int ok = 0;

        //aplic acelasi algoritm ca la ratingMovie

        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(show)) {
                ok = 2;
                for (Map.Entry<String, Integer> entry1 : user.getRatingSerial().entrySet()) {
                    if (entry1.getValue().equals(seasonNumberf)
                            && entry1.getKey().equals(serial.getTitle())) {
                        ok = 1;
                    }
                }
            }
        }

        if (ok == 2) {

            serial.getSeasons().get(seasonNumberf - 1).addRating(nota);
            message =
                    "success -> " + show + " was rated with " + nota + " by " + user.getUsername();

            user.addRatedSerial(serial.getTitle(), seasonNumberf);

        } else if (ok == 1) {

            message = "error -> " + serial.getTitle() + " has been already rated";

        } else if (ok == 0) {

            message = "error -> " + serial.getTitle() + " is not seen";

        }

        return message;
    }
}
