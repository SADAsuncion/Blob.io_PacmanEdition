package Sprites;

abstract class Items extends Sprite{

	Items(int x, int y){
		super(x,y);
	}

	abstract void checkCollision(Pacman pacman);
}
