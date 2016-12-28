import edu.duke.FileResource;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
public class FirstRatings 
{
    
    public ArrayList<Movie> loadMovies(String filename) 
    {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        FileResource fr = new FileResource("data/" + filename );
        int i = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (i == 0) {
                ++i;
                continue;
            }
            String id = rec.get(0).toString();
            String title = rec.get(1).toString();
            String year = rec.get(2).toString();
            String genre = rec.get(4).toString();
            String director = rec.get(5).toString();
            String country = rec.get(3).toString();
            String poster = rec.get(7).toString();
            int minutes = Integer.parseInt(rec.get(6));
            movies.add(new Movie(id, title, year, genre, director, country, poster, minutes));
        }
        return movies;
    }

    public void testLoadMovies() {
        String filename = "ratedmovies_short.csv";
        filename = "ratedmoviesfull.csv";
        ArrayList<Movie> result = this.loadMovies(filename);
        String genre = "Comedy";
        //System.out.println("Movies with " + genre + " as genre " + this.MovieGenreCount(genre, result).size());
        int time = 150;
        //System.out.println("Movies Longer than " + time + " " + this.MoviesLongerThan(time, result).size());
        String dir = "Charles Chaplin";
        dir = "Ridley Scott";
        ArrayList<String> directorCount = this.MovCntDirector(dir, result);
        System.out.println("Movies directed by " + dir + " " + directorCount.size() + " and the Movie(s) are " + directorCount);
        this.MaxNumberOfFilmsOfAllDirectors(result);

    }

    public void MaxNumberOfFilmsOfAllDirectors(ArrayList<Movie> result) 
    {
        HashMap<String, Integer> DirectorMap = new HashMap<String, Integer>();
        int max =0;
        for (Movie mov : result) {
            if (DirectorMap.containsKey(mov.getDirector())) {
                DirectorMap.put(mov.getDirector(), (Integer)DirectorMap.get(mov.getDirector()) + 1);
                continue;
            }
            DirectorMap.put(mov.getDirector(), 1);
        }
        for (String S : DirectorMap.keySet()) {
            System.out.println("The Director ->" + S + " Directed-> " + DirectorMap.get(S) + " Films");
            if(max < DirectorMap.get(S))
            {
                max = DirectorMap.get(S);
            }
        }
        System.out.println("The Maximum Number of films directed by one director are " + max);
    }
    
    public ArrayList<Movie> MovieGenreCount(String gen, ArrayList<Movie> result) {
        int i = 0;
        ArrayList<Movie> old = new ArrayList<Movie>();
        for (Movie mov : result) {
            for (String s : mov.getGenresList()) {
                if (!s.equals(gen)) continue;
                old.add(result.get(i));
            }
            ++i;
        }
        return old;
    }

    public ArrayList<Movie> MoviesLongerThan(int time, ArrayList<Movie> result) {
        int i = 0;
        ArrayList<Movie> old = new ArrayList<Movie>();
        for (Movie mov : result) {
            if (mov.getMinutes() > time) {
                old.add(result.get(i));
            }
            ++i;
        }
        return old;
    }

    public ArrayList<String> MovCntDirector(String director, ArrayList<Movie> result) {
        ArrayList<String> movies = new ArrayList<String>();
        for (Movie mov : result) {
            String dir = mov.getDirector();
            boolean t = dir.equals(director);
            if (mov.getDirector().indexOf(director) <= -1) continue;
            movies.add(mov.getTitle());
        }
        return movies;
    }

    public void printMovies(ArrayList<Movie> result) {
        for (Movie mov : result) {
            System.out.println(mov.toString());
        }
    }

    public ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> raters = new ArrayList<Rater>();
        ArrayList<String> rids = new ArrayList<String>();
        FileResource fr = new FileResource("data/" + filename);
        int i = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (i == 0) {
                ++i;
                continue;
            }
            String raterid = rec.get(0).toString();
            String movieItem = rec.get(1).toString();
            double rating = Double.parseDouble(rec.get(2));
            
            if(!rids.contains(raterid))
            {
                rids.add(raterid);
                raters.add(new EfficientRater(raterid));
                //raters.add(new PlainRater(raterid));
                raters.get(i-1).addRating(movieItem, rating);
                ++i;
            }
            else 
            {
                raters.get(getThisRater(raterid,raters)).addRating(movieItem,rating);
            }
            
        }
        return raters;
    }

    public int getThisRater(String raterid, ArrayList<Rater> Raters)
    {
        int i=0;
        for(Rater R : Raters)
        {

            String RID = R.getID();
            if(RID.equals( raterid))
            {
                return i;
            }
            i++;            
        }
        return(-1);
    }
    public void testLoadRaters() {
        String filename = "ratings_short.csv";
        filename = "ratings.csv";
        ArrayList<Rater> result = this.loadRaters(filename);
        System.out.println("Raters list from the new modified method " + result.size());
        HashMap<String, ArrayList<Rating>> unique = this.BuildMap(result);
        System.out.println(unique.size());
        String RaterID = "193";
        System.out.println("Total Number Of Ratings " + RaterID + " has ->" + this.NumberOfRatings(RaterID, unique));
        this.MaxNummberOfRatings(unique);
        String MovieID = "1798709";
        System.out.println("The Movie " + MovieID + " was Rated By " + this.NumberOfRatingsForAMovie(MovieID, unique));
        System.out.println("Number of Uniques movies that have been rated are ->"+UniqueMoviesRated(unique));
    }

    public HashMap<String, ArrayList<Rating>> BuildMap(ArrayList<Rater> result) {
        HashMap<String, ArrayList<Rating>> unique = new HashMap<String, ArrayList<Rating>>();
        for (Rater rat : result) {
            double ra;
            if (unique.containsKey(rat.getID())) {
                ArrayList<Rating> rtng = unique.get(rat.getID());
                for (String s : rat.getItemsRated()) {
                    ra = rat.hasRating(s) ? Double.valueOf(rat.getRating(s)) : null;
                    rtng.add(new Rating(s, ra));
                }
                unique.put(rat.getID(), rtng);
                continue;
            }
            ArrayList<Rating> rating = new ArrayList<Rating>();
            for (String s : rat.getItemsRated()) {
                ra = rat.hasRating(s) ? Double.valueOf(rat.getRating(s)) : null;
                rating.add(new Rating(s, ra));
            }
            unique.put(rat.getID(), rating);
        }
        return unique;
    }

    public int NumberOfRatings(String RaterID, HashMap<String, ArrayList<Rating>> unique) {
        return unique.containsKey(RaterID) ? unique.get(RaterID).size() : 0;
    }

    public void MaxNummberOfRatings(HashMap<String, ArrayList<Rating>> unique) {
        TreeMap<String, ArrayList<Rating>> tree = new TreeMap<String, ArrayList<Rating>>(unique);
        HashMap result = new HashMap();
        int max = 0;
        String RaterID = "";
        for (String s : tree.keySet()) {
            if (max >= tree.get(s).size()) continue;
            max = tree.get(s).size();
            RaterID = s;
        }
        System.out.println("The max number of ratings is " + max + " Given by the Rater " + RaterID);
    }

    public int NumberOfRatingsForAMovie(String MovieID, HashMap<String, ArrayList<Rating>> unique) {
        int count = 0;
        for (ArrayList<Rating> rating : unique.values()) {
            for (Rating r : rating) {
                if (!r.getItem().equals(MovieID)) continue;
                ++count;
            }
        }
        return count;
    }

    public void printRaters(ArrayList<Rater> result) {
        for (Rater rat : result) {
            System.out.println(rat.toString());
        }
    }

    public void printRatersMap(HashMap<String, ArrayList<Rating>> result) {
        for (String RaterID : result.keySet()) {
            System.out.println("Rater ID -> " + RaterID + " and the Items are -> " + result.get(RaterID));
        }
    }
   public  int UniqueMoviesRated(HashMap<String, ArrayList<Rating>> unique)
   {
       int count =0;
       ArrayList<String> UniqueMovies = new ArrayList<String>();
       for(String S : unique.keySet())
       {
           String ss =S;
           ArrayList<Rating> ratingFor = unique.get(S);
           for(Rating r : unique.get(S))
           {
               if(!UniqueMovies.contains(r.getItem()))
               {
                   UniqueMovies.add(r.getItem());
                }
              
            }
           
           
        }
       return UniqueMovies.size();
    }
}