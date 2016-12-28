import java.util.*;
public class DirectorsFilter implements Filter {
    private ArrayList<String> mydirectors;
    public DirectorsFilter(String directors) 
    {
        mydirectors = new ArrayList<String>();
        for(String S : directors.split(","))
        {
            mydirectors.add(S.trim()); 
        }
    }
    
    @Override
    public boolean satisfies(String id) 
    {
     //   return ( mydirectors).contains(MovieDatabase.getDirector(id)) ;
     String Directors = MovieDatabase.getDirector(id);
        for(String S : mydirectors)
        {
            if(Directors.indexOf(S)>=0)
            {
                return true;
            }
        }
        return false ;
    }
}
