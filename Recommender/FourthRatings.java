import java.util.*;
public class FourthRatings
 {
     private ArrayList<Rater> myRaters;
    public FourthRatings() {
        // default constructor
        //this("ratedmovies_short","ratings_short");
        this("ratings.csv");
    }
    
    public FourthRatings(String ratingsfile) {
        // default constructor
        RaterDatabase.initialize(ratingsfile);
        FirstRatings fr = new FirstRatings();
        myRaters = RaterDatabase.getRaters();//fr.loadRaters(ratingsfile);
        
    }
    
    public double getAverageByID(String ID,int minimalRaters)
    {
        double avg=0.0;
        double sum=0.0;
        int total=0;
        for(Rater R: myRaters)
        {
            total = R.getRating(ID) > 0 ? total+1 : total;
            sum = R.getRating(ID) > 0 ? sum+R.getRating(ID) : sum ;
        }
        avg = (total>=minimalRaters)? sum/total : avg ;
        return avg;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters)
    {
        ArrayList<Rating> ratings = new ArrayList<Rating>();   
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String mov : movies)
        {
            
            double average = getAverageByID(mov,minimalRaters);
            if(average!=0.0)
            {
                ratings.add(new Rating(mov,average));
            }
        }
        
        return ratings;
    }
    
    public int getRaterSize ()
    {
        return myRaters.size();
    }
    
    public double dotProduct(Rater me , Rater r)
    {
        double product = 0.0;
        for(String movie : me.getItemsRated() )
        {
            if(r.hasRating(movie))
            {
                product = product + (me.getRating(movie)-5)*(r.getRating(movie)-5);
            }
        }
        return product;
    }
    
    public ArrayList<Rating> getSimilarities (String id)
    {
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for(Rater r : RaterDatabase.getRaters())
        {
            if(r!=me)
            {
                list.add(new Rating(r.getID(),dotProduct(me,r)));
            }
        }
        Collections.sort(list,Collections.reverseOrder());
        int size  = list.size();
        return list;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id , int numSimilarRaters, int minimalRaters)
    {
        // this method is more similar to getAverageRatings method in earlier versions
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<Rating> list = getSimilarities(id); 
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String mov : movies)
        {
            double weight =0.0;
            int count =0;
            for(int k =0; k<numSimilarRaters;k++)
            {   
                Rating R = list.get(k);
                Rater rat = RaterDatabase.getRater(R.getItem());
                if(rat.hasRating(mov))
                {
                    count++;
                    double similar = R.getValue();
                    double actual = rat.getRating(mov);
                    weight = weight + similar*actual;  
                }
                                
            }
            double avg =  count>=minimalRaters? weight/count: 0;
            if(avg>0)
            {
                ret.add(new Rating(mov,avg));
            }
        }
        Collections.sort(ret,Collections.reverseOrder());
        return ret;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id , int numSimilarRaters, int minimalRaters,Filter filterCriteria)
    {
        // this method is more similar to getAverageRatings method in earlier versions
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<Rating> list = getSimilarities(id); 
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        for(String mov : movies)
        {
            double weight =0.0;
            int count =0;
            for(int k =0; k<numSimilarRaters;k++)
            {   
                Rating R = list.get(k);
                Rater rat = RaterDatabase.getRater(R.getItem());
                if(rat.hasRating(mov))
                {
                    count++;
                    double similar = R.getValue();
                    double actual = rat.getRating(mov);
                    weight = weight + similar*actual;  
                }
                                
            }
            double avg =  count>=minimalRaters? weight/count: 0;
            if(avg>0)
            {
                ret.add(new Rating(mov,avg));
            }
        }
        Collections.sort(ret,Collections.reverseOrder());
        return ret;
    }
}
