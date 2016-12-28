import java.util.*;
public class MovieRunnerSimilarRatings
 {
    private String mv="ratedmoviesfull.csv";
    private String rtr="ratings.csv";
    // For short versions of csv file.
    //private String mv = "ratedmovies_short.csv";
    //private String rtr = "ratings_short.csv";
    public void printSimilarities()
    {
        FourthRatings fr = new FourthRatings(rtr);
        MovieDatabase.initialize(mv);
        ArrayList<Rating> Ratings = fr.getSimilarities("65");
        Collections.sort(Ratings,Collections.reverseOrder());
        for(Rating r :Ratings)
        {
            System.out.println(r);
        }
    }
    
    public void printSimilarRatings()
    {
        //printSimilarities();
        FourthRatings fr = new FourthRatings(rtr);
        MovieDatabase.initialize(mv);
        ArrayList<Rating> Ratings = fr.getSimilarRatings("337",10,3);
        //Collections.sort(Ratings,Collections.reverseOrder());
        for(Rating r :Ratings)
        {
            System.out.println(MovieDatabase.getTitle(r.getItem())+" "+ r.getValue()+"   "+MovieDatabase.getGenres(r.getItem()) );
        }
    }
    
    public void printAverageRatings()
    {
        //SecondRatings sr = new SecondRatings ("ratedmovies_short","ratings_short");
        FourthRatings fr = new FourthRatings(rtr);
        //ThirdRatings tr = new ThirdRatings("ratings.csv");
        System.out.println("Number of Raters are "+ fr.getRaterSize());
        MovieDatabase.initialize(mv);
        //MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are "+MovieDatabase.size() +" Movies");      
        ArrayList<Rating> avgRatings = fr.getAverageRatings(35);
        Collections.sort(avgRatings);
        for(Rating rats : avgRatings)
        {
            System.out.println(rats.getItem()+" "+MovieDatabase.getTitle(rats.getItem())+" "+rats.getValue());
        }
        System.out.println("Count"+avgRatings.size());
    }   
    
    public void printAverageRatingsByYearAfterAndGenre()
    {
      
        FourthRatings fr = new FourthRatings(rtr);
        //System.out.println("Number of Raters are "+ fr.getRaterSize());
        MovieDatabase.initialize(mv);
        //System.out.println("There are "+MovieDatabase.size() +" Movies");      
        AllFilters af = new AllFilters();
        //af.addFilter(new YearAfterFilter(year));
        af.addFilter(new GenreFilter("Mystery"));
        ArrayList<Rating> avgRatings = fr.getSimilarRatingsByFilter("964",20,5, af);

        for(Rating rats : avgRatings)
        {
            
            System.out.println(MovieDatabase.getTitle(rats.getItem()));
            //System.out.println(rats.getValue()+" "+MovieDatabase.getYear(rats.getItem())+" "+MovieDatabase.getTitle(rats.getItem())+" \n "+MovieDatabase.getGenres(rats.getItem()));
        }
        //System.out.println("Count"+avgRatings.size());
    }
    
    public void printSimilarRatingsByDirector()
    {
        String RaterID = "120";
        int minSim = 10;
        int min = 2;
        String Directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        FourthRatings fr = new FourthRatings(rtr);
        //System.out.println("Number of Raters are "+ fr.getRaterSize());
        MovieDatabase.initialize(mv);
        //System.out.println("There are "+MovieDatabase.size() +" Movies");      
        AllFilters af = new AllFilters();
        //af.addFilter(new YearAfterFilter(year));
        af.addFilter(new DirectorsFilter(Directors));
        ArrayList<Rating> avgRatings = fr.getSimilarRatingsByFilter(RaterID,minSim,min, af);

        for(Rating rats : avgRatings)
        {
            
            System.out.println(MovieDatabase.getTitle(rats.getItem()));
            //System.out.println(rats.getValue()+" "+MovieDatabase.getYear(rats.getItem())+" "+MovieDatabase.getTitle(rats.getItem())+" \n "+MovieDatabase.getGenres(rats.getItem()));
        }
        //System.out.println("Count"+avgRatings.size());
    }
    
     public void printSimilarRatingsByGenreAndMinutes()
    {
        //printSimilarities();
        FourthRatings fr = new FourthRatings(rtr);
        MovieDatabase.initialize(mv);
        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter("Drama"));
        af.addFilter(new MinutesFilter(80,160));
        ArrayList<Rating> Ratings = fr.getSimilarRatingsByFilter("168",10,3,af);
        //Collections.sort(Ratings,Collections.reverseOrder());
        for(Rating r :Ratings)
        {
            System.out.println(MovieDatabase.getTitle(r.getItem())+" "+ r.getValue()+"   "+MovieDatabase.getGenres(r.getItem()) );
        }
    }
    
     public void printSimilarRatingsByYearAfterAndMinutes()
    {
        //printSimilarities();
        FourthRatings fr = new FourthRatings(rtr);
        MovieDatabase.initialize(mv);
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(1975));
        af.addFilter(new MinutesFilter(70,200));
        ArrayList<Rating> Ratings = fr.getSimilarRatingsByFilter("314",10,5,af);
        //Collections.sort(Ratings,Collections.reverseOrder());
        for(Rating r :Ratings)
        {
            System.out.println(MovieDatabase.getTitle(r.getItem())+" "+ r.getValue()+"   "+MovieDatabase.getGenres(r.getItem()) );
        }
    }
    
}
