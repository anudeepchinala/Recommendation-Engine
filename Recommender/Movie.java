import java.util.ArrayList;
import java.util.Arrays;

// An immutable passive data object (PDO) to represent item data
public class Movie {
    private String id;
    private String title;
    private int year;
    private ArrayList<String> genres;
    private String director;
    private String country;
    private String poster;
    private int minutes;

    public Movie (String anID, String aTitle, String aYear, String theGenres) {
        // just in case data file contains extra whitespace
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = new ArrayList<String>();
        for(String gen:theGenres.split(","))
        {
         genres.add(gen);   
        }
        if(genres.size()<1)genres.add(theGenres);
    }

    public Movie (String anID, String aTitle, String aYear, String theGenres, String aDirector,
    String aCountry, String aPoster, int theMinutes) {
        // just in case data file contains extra whitespace
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = new ArrayList<String>();
        director = aDirector;
        country = aCountry;
        poster = aPoster;
        minutes = theMinutes;
        for(String gen:theGenres.split(","))
        {
         genres.add(gen.trim());   
        }
        if(genres.size()<1){genres.add(theGenres.trim());}
    }

    // Returns ID associated with this item
    public String getID () {
        return id;
    }

    // Returns title of this item
    public String getTitle () {
        return title;
    }

    // Returns year in which this item was published
    public int getYear () {
        return year;
    }

    // Returns genres associated with this item
    public String getGenres () 
    {
        StringBuilder sb = new StringBuilder();
        for(String gen: genres)
        {
            sb.append(gen);
            sb.append(" ");
        }
        //return genres;
        return sb.toString().trim();
    }

    public ArrayList<String> getGenresList () 
    {
        return genres;
    }
    
    public String getCountry(){
        return country;
    }

    public String getDirector(){
        return director;
    }

    public String getPoster(){
        return poster;
    }

    public int getMinutes(){
        return minutes;
    }

    // Returns a string of the item's information
    public String toString () {
        String result = "Movie [id=" + id + ", title=" + title + ", year=" + year;
        result += ", genres= " + genres + "]";
        return result;
    }
}
