import java.util.ArrayList ;
import java.util.Collections ;
public class testing_stuff {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       ArrayList<Double>x = new ArrayList<Double>() ;
       x.add(45.2) ;
       x.add(100.234234) ;
       x.add(34523.2) ;
       x.add(1.23) ;
	   
       Collections.sort(x) ;
       
       System.out.println(" max is  " + x.get( x.size()-1 ) ) ;
	}

}
