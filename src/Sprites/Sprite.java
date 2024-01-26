package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Sprite {
	protected Image img;
	protected double x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;
	protected double speed;

	protected ImageView imgView;
//	protected int size;

	public Sprite(int xPos, int yPos){
		this.x = xPos;
		this.y = yPos;
		this.visible = true;
	}

	//method to set the object's image
	protected void loadImage(Image img){
		try{
			this.img = img;
	        this.setSize();
		} catch(Exception e){}
	}

	//method to set the image to the image view node
	public void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);

    }

	//method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}
	//method that will check for collision of two sprites
	public boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	//method that will return the bounds of an image
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	//method to return the image
	Image getImage(){
		return this.img;
	}

	//getters
	public double getX() {
    	return this.x;
	}

	public double getY() {
    	return this.y;
	}

	public boolean getVisible(){
		return visible;
	}
	public boolean isVisible(){
		if(visible) return true;
		return false;
	}

	public double getSpeed(){
		return this.speed;
	}

	public double getDX(){
		return this.dx;
	}

	public double getDY(){
		return this.dy;
	}


	//setters
	public void setDX(double dx){
		this.dx = dx;
	}

	public void setDY(double dy){
		this.dy = dy;
	}

	public void setWidth(double val){
		this.width = val;
	}

	public void setHeight(double val){
		this.height = val;
	}

	public void setVisible(boolean value){
		this.visible = value;
	}

	public void vanish(){          //making the object invisible
		this.visible = false;
	}



}
