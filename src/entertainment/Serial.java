package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /***
     * calculeaza media pentru un serial
     * @param seasonsf lista de sezoane
     * @return
     */
    public double getAverageRatingSerial(final ArrayList<Season> seasonsf) {
        double average = 0;
        int numberRatings = 0;
        for (int i = 0; i < seasonsf.size(); i++) {
            average = average
               + seasonsf.get(i).getAverageRatingSeason(seasonsf.get(i).getRatings());
            numberRatings++;
        }
        if (numberRatings != 0) {
            average = average / numberRatings;
        }
        return average;
    }

    /***
     * calculeaza cat dureaza un serial
     * @param seasonsf lista de sezoane
     * @return
     */
    public int getSerialDuration(final List<Season> seasonsf) {
        int duration = 0;
        for (int i = 0; i < seasonsf.size(); i++) {
            duration = duration + seasonsf.get(i).getDuration();
        }
        return duration;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
