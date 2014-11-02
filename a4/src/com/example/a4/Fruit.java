package com.example.a4;

import android.graphics.*;
import android.util.Log;
import android.graphics.Matrix ;
import android.graphics.Region ;
public class Fruit {
    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix transform = new Matrix();
    private boolean is_piece = false ;
    private boolean is_fruit = true ;
    
    Fruit(float[] points) {
        init();
        this.path.reset();
        this.path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            this.path.lineTo(points[i], points[i + 1]);
        }
        this.path.moveTo(points[0], points[1]);
    }

    Fruit(Region region) {
        init();
        this.path = region.getBoundaryPath();
    }

    Fruit(Path path) {
        init();
        this.path = path;
    }

    private void init() {
        this.paint.setColor(Color.BLUE);
        this.paint.setStrokeWidth(5);
    }

    public int getFillColor() { return paint.getColor(); }
    public void setFillColor(int color) { paint.setColor(color); }

    public double getOutlineWidth() { return paint.getStrokeWidth(); }
    public void setOutlineWidth(float newWidth) { paint.setStrokeWidth(newWidth); }

    public void rotate(float theta) { transform.postRotate(theta); }
    public void scale(float x, float y) { transform.postScale(x, y); }
    public void translate(float tx, float ty) { transform.postTranslate(tx, ty); }
    public Matrix getTransform() { return transform; }
  
    public Path getTransformedPath() {
        Path originalPath = new Path(path);
        Path transformedPath = new Path();
        originalPath.transform(transform, transformedPath);
        return transformedPath;
    }
    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Canvas canvas) {
    	//Matrix current_transform = getTransform() ;
    	
    	if(this.is_fruit == false){return ;} 
    	
    	canvas.drawPath(getTransformedPath(), paint) ;
    	
        // TODO BEGIN CS349
        // tell the shape to draw itself using the matrix and paint parameters
        // TODO END CS349
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */ 
    public boolean intersects(PointF p1, PointF p2) {
    	if( this.is_piece == true) return false ;
    	if( p1.x == p2.x && p1.y == p2.y) return false ;
    	Fruit newfruit = new Fruit(this.getTransformedPath()) ;    	
    	if( newfruit.contains(p1) == true && newfruit.contains(p2) == true) return false ;    	
    	float current_angle  = Graphics2D.findAngle(p1,p2) ;    	    
    	Matrix blade_path_matrix = new Matrix() ;    	
    	PointF newp1 , newp2 ;    	
    	blade_path_matrix.postRotate(current_angle) ;
    	newp1 = Graphics2D.translate(blade_path_matrix, p1) ;
        newp2 = Graphics2D.translate(blade_path_matrix, p2) ;
        	
        Path fruit_path = new Path(this.getTransformedPath() ) ;
        fruit_path.transform( blade_path_matrix) ;    	    	
    	System.out.println("********* new Y COORDINATES are " + newp1.y + " = " + newp2.y) ;
    	
    	RectF rectangle_around_blade_float = new RectF( newp1.x, newp1.y-1, newp2.x,newp2.y+1 ) ;
    	
    	Rect rectangle_around_blade = new Rect() ;
    	rectangle_around_blade_float.round(rectangle_around_blade) ;    	
    	Region blade_region = new Region(rectangle_around_blade) ;    	
    	Region fruit_region = new Region() ;    	    	    
    	Region clip = new Region(
    			Integer.MIN_VALUE, 
    			Integer.MIN_VALUE, 
    			Integer.MAX_VALUE, 
    			Integer.MAX_VALUE);
    	    	
    	fruit_region.setPath( fruit_path , clip ) ;    	
    	if( blade_region.op( fruit_region,Region.Op.INTERSECT) ) {
    			System.out.println("FINALLY YES THEY INTERSECT ! AAAAAAAAAAAAAAA") ;
    			return true ;
    			}    	
        return false;
    }
    
    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(PointF p1) {
        Region region = new Region();
        boolean valid = region.setPath(getTransformedPath(), new Region());
        return valid && region.contains((int) p1.x, (int) p1.y);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(PointF p1, PointF p2) {
    	Path topPath = null;
    	Path bottomPath = null;
    	// TODO BEGIN CS349
        // calculate angle between points
        // rotate and flatten points passed in
        // rotate region
        // define region masks and use to split region into top and bottom
        // TODO END CS349
    	
    	float current_angle  = Graphics2D.findAngle(p1,p2) ;    	    
    	Matrix blade_path_matrix = new Matrix() ;    	
    	PointF newp1 , newp2 ;
    	
    	blade_path_matrix.postRotate(current_angle) ;
    	newp1 = Graphics2D.translate(blade_path_matrix, p1) ;
        newp2 = Graphics2D.translate(blade_path_matrix, p2) ;
        	
        Path fruit_path = new Path(this.getTransformedPath() ) ;
        
        fruit_path.transform( blade_path_matrix) ;    	    	    	    	
    	Region top_area_region = new Region()	 ;    	
    	
    	
    	Region fruit_region = new Region() ;    	    	    
    	Region clip = new Region(
    			Integer.MIN_VALUE, 
    			Integer.MIN_VALUE, 
    			Integer.MAX_VALUE, 
    			Integer.MAX_VALUE);
    	    	
    	fruit_region.setPath( fruit_path , clip ) ;
    	
    	
    	Rect area_rect = new Rect( fruit_region.getBounds()) ;
    	Rect top_area_rect =new Rect( 
    			area_rect.left, 
    			area_rect.top,
    			area_rect.right,
    			(int)newp1.y ) ;
    	
    	top_area_region.setPath( fruit_path , new Region(top_area_rect ) ) ;    	
    	
    	Region bottom_area_region = new Region() ;
    	Rect bottom_area_rect = new Rect(
    			area_rect.left, 
    			(int)newp1.y,
    			area_rect.right,
    			area_rect.bottom ) ;
    	
    	bottom_area_region.setPath( fruit_path , new Region(bottom_area_rect ) ) ;
    	
    	topPath = top_area_region.getBoundaryPath() ;
    	bottomPath = bottom_area_region.getBoundaryPath() ;
    	
    	Matrix bottom_unrotate = new Matrix() ;
    	Matrix top_unrotate = new Matrix() ;
    	
    	top_unrotate.preTranslate(0,-4) ;
    	bottom_unrotate.preTranslate(0,4) ;
    	
    	top_unrotate.preRotate(-current_angle) ;
    	bottom_unrotate.preRotate(-current_angle) ;
    	
    	topPath.transform(top_unrotate) ;
    	bottomPath.transform(bottom_unrotate) ;
    	
    	
    	
    	
    	
        if (topPath != null && bottomPath != null) {
        	
        	Fruit newUpper = new Fruit(topPath) ;
        	Fruit newLower = new Fruit(bottomPath) ;
        	
        	this.is_fruit = false ;
        	newUpper.is_piece = true ;
        	newUpper.is_fruit = true ;
        	
        	newLower.is_fruit = true ;
        	newLower.is_piece = true ;
        	
           return new Fruit[] { newUpper, newLower };
        }
        return new Fruit[0];
    }
}
