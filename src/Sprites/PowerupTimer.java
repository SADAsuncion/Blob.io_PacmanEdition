package Sprites;

public class PowerupTimer extends Thread{
	private Pacman pacman;
	private int time;
	private final static int UPGRADED_TIME =5;
	private static int counter;


	PowerupTimer(Pacman pacman){
		this.pacman = pacman;
		this.time = PowerupTimer.UPGRADED_TIME;

	}

	/*
	 * This will return the countdown back to 5 so it will repeat/restart
	 * */
	void refresh(){
		this.time = PowerupTimer.UPGRADED_TIME;
	}

	/*
	 * this will be the main method here that will do the powerup countdown
	 * */
	void countDown(){
		counter = 5;
		while(this.time!=0){
			System.out.println("Powerup Countdown: "+ this.time);
			try{
				Thread.sleep(1000);
				this.time--;
				counter--;
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
		this.pacman.downgradePowerup();
	}

	public void run(){
		this.countDown();
	}

	public static int getCounter(){
		return counter;
	}
}
