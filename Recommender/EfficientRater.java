import java.util.*;

public class EfficientRater implements Rater
{
    private String myID;
    private HashMap<String,Rating> myRatings;
    
    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item,new Rating(item,rating));
    }

    public boolean hasRating(String item) 
    {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        return (myRatings.containsKey(item))? myRatings.get(item).getValue():-1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String keys : myRatings.keySet())
        {
            list.add(myRatings.get(keys).getItem());
        }
        
        return list;
    }
    public String toString()
    {
     return "Rater ID: "+myID + myRatings;
    }
}
