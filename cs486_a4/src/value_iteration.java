import java.util.ArrayList ;
import java.util.Collections ;	
public class value_iteration {
	
	static double matrix[][] = {
			{-1,-1,-1,-1},
			{-1,-1,-1,-1},
			{-1,-70,-1,-1},
			{-1,-1,-1,100}			
	} ;
	
	static double current_iteration[][] = {
			{0,0,0,0},	
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,100}
	} ;
	
	
	static double previous_iteration[][] = {
		{0,0,0,0},	
		{0,0,0,0},
		{0,0,0,0},
		{0,0,0,100}
} ;

	public static double compute_move_value(int i,int j, double matrix[][], double previous_iteration[][]){
		double gamma = 0.99 ;
	
        double reward = matrix[i][j] ;
        double intention_move_up = 0 ;
        
        if( i > 0 ){
        	intention_move_up = reward + gamma*( 
        			0.1* previous_iteration[i-1][j] + 
                    0.8* previous_iteration[i+1][j] + 
                    0.1* previous_iteration[i][j+1] + 
                    previous_iteration[i][j-1] ) ;
        }
        double intention_move_down = 0 ;
        if( i < 3 ){
        	intention_move_down = reward + gamma*( 0.1*previous_iteration[i-1][j] + 
        			                              0.8*previous_iteration[i+1][j] + 
        			                              0.1*previous_iteration[i][j+1] + 
        			                              previous_iteration[i][j-1]) ;
        }
        double intention_move_left = 0 ;
        if( j > 0 ){
        	intention_move_left = reward + gamma*( 0.1*previous_iteration[i-1][j] + 
        			                              0.8*previous_iteration[i+1][j] + 
        			                              0.1*previous_iteration[i][j+1] + 
        			                              previous_iteration[i][j-1]) ;
        }
        
        double intention_move_right = 0 ;
        if( j > 3 ){
        	intention_move_right= reward + gamma*( 0.1*previous_iteration[i-1][j] + 
        			                              0.8*previous_iteration[i+1][j] + 
        			                              0.1*previous_iteration[i][j+1] + 
        			                              previous_iteration[i][j-1]) ;
        }
                
        //double best_move = Math.max( intention_move_right,Math.max(intention_move_left, Math.max(intention_move_down, intention_move_up)) );
        ArrayList<Double> moves = new ArrayList<Double>() ;
        moves.add(intention_move_up) ;
        moves.add(intention_move_down) ;
        moves.add(intention_move_left) ;
        moves.add(intention_move_right) ;
        
        
        Collections.sort(moves) ;
        return moves.get( moves.size() -1 ) ;
	}
	
	
	public static void run_through(double matrix[][], double previous_iteration[][]){
		for(int row = matrix.length ; row > 0 ; row--){	
			for(int index = row ; index > 0 ; index--){
			   double value = compute_move_value(row,index,matrix,previous_iteration) ;
			   current_iteration[row][index] = value ;
			}
		}
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				previous_iteration[i][j] = current_iteration[i][j] ;
			}
		}
		
		
	}
	
	public static boolean check_delta( double delta){		
	      for(int i = 0 ; i < 4 ; i++ ){
	    	  for(int j = 0 ; j < 4 ; j++){
	    		  if( Math.abs(current_iteration[i][j] - previous_iteration[i][j]) <= delta )
	    			  return true ;	    		 
	    	  }
	      }
	      return false ;
	}
	
	public static void main(String args[]){
	     while( check_delta(0.99) == false){
	    	 run_through(matrix,previous_iteration) ;
	     }
		
	     
	     print_matrix(current_iteration) ;
	}
	
	public static void print_matrix(double matrix[][]){
		for(int i = 0 ; i  < 4 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				System.out.print(matrix[i][j] + "\t") ;
			}
			System.out.println() ;
		}
	}
	
}
	



