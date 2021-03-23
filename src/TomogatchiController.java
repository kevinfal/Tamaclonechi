import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


/**
 * 
 * @author Kevin Falconett, Enrique Sanchez, Huiqi He
 * File: TomogatchiController.java
 * Class: CSC 335
 * Instructor: Dr. Jonathon Misurda
 * Final Project-Tamaclonechi
 * Description: This is the class that holds the controller for our program
 *
 */
public class TomogatchiController {
	
	TomogatchiModel model;
	
	/**
	 * TomogatchiModel(TomogatchiModel model): Method that constructs our pet controller
	 * @param model - A model of out TomogatchiModel, used in our construction of the controller
	 *
	 */
	TomogatchiController(TomogatchiModel model){
		this.model = model;
		
	}
	
	
	/**
	 * age(): is simply a counter/score, does not
	 * affect stats stuff
	 * @throws InterruptedException -in case the input is interrupted
	 */
	public void age() throws InterruptedException {
		int myAge = this.model.getAge();
		this.model.setAge(myAge + 1);
	}
	
	/**
	 * health(): is a method that increases the health, interacts with the View button
	 * @throws InterruptedException -in case the input is interrupted
	 */
	public void feedMedicine() throws InterruptedException {
		this.age();
		int myHealth = this.model.getHealth();
		this.model.setHealth(myHealth + 10);
		TimeUnit.SECONDS.sleep(1);
		this.model.setEndTime();
		this.model.setTimeDiff(this.model.getEndTime() - this.model.getStartTime());
		//this.update(this.model.getTimeDiff());
	}
	
	public void walk() {
		model.setHappiness(model.getHappiness() + 10);
		//decrease weight
		model.setHealth(model.getHealth() + 5);
		model.setHungerValue(model.getHungerValue() - 10);
	}
	
	/**
	 * feed(): is a method that increases the the hunger value, interacts with the View button
	 * @throws InterruptedException -in case the input is interrupted
	 */
	public void feedDinner() throws InterruptedException {
		int feedPet = this.model.getHungerValue();
		this.model.setHappiness(model.getHappiness()+ 5);
		this.model.setHungerValue(feedPet + 10);
		TimeUnit.SECONDS.sleep(1);
		this.model.setEndTime();
		this.model.setTimeDiff(this.model.getEndTime() - this.model.getStartTime());
		//this.update(this.model.getTimeDiff());
}
	
	public void feedSnack() throws InterruptedException{
		int feedPet = this.model.getHungerValue();
		this.model.setHungerValue(feedPet + 5);
		this.model.setHappiness(model.getHappiness()+ 10);
		TimeUnit.SECONDS.sleep(1);
		this.model.setEndTime();
		this.model.setTimeDiff(this.model.getEndTime() - this.model.getStartTime());
		//this.update(this.model.getTimeDiff());
}
	
	/**
	 * happiness(): is a method that increases the pet happiness, interacts with the View button
	 * @return int
	 * @throws InterruptedException -in case the input is interrupted
	 */
	public int happiness() throws InterruptedException {
		this.age();
		int petHappiness = this.model.getHappiness();
		this.model.setHappiness(petHappiness + 10);
		TimeUnit.SECONDS.sleep(1);
		this.model.setEndTime();
		this.model.setTimeDiff(this.model.getEndTime() - this.model.getStartTime());
		//this.update(this.model.getTimeDiff());
		
		return 0;
}
	
	/**
	 * update(long myVal): is a method that updates the model based off the time passed
	 * @param myVal - val that represents time in milliseconds
	 */
	public void update(long myVal) {
		if(this.model.getType() == 0) {
			int easyVal = (int) (myVal / 1000);
			//System.out.println("Easy val is " + easyVal);
			int i = 0;
			for(i = 0; i < easyVal; i++) {
				this.model.easyHappinessUpdate();
				this.model.easyHealthUpdate();
				this.model.easyHungerUpdate();
				this.model.easyAgeUpdate();
			}
		}else {
			int easyVal = (int) (myVal / 1000);
			//System.out.println("Easy val is " + easyVal);
			int i = 0;
			for(i = 0; i < easyVal; i++) {
				this.model.easyHappinessUpdate2();
				this.model.easyHealthUpdate2();
				this.model.easyHungerUpdate2();
				this.model.easyAgeUpdate();
			}
		}
	}
	
	/**
	 * save(): is a method that saves the current stats of our pet to a file named after the pet Name
	 */
	public void save() {
		
		try {
			// Open a file to write to.
			FileOutputStream saveFile=new FileOutputStream("saves//" + this.model.getName() + ".txt");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(this.model.getName());
			save.writeObject(this.model.getAge());
			save.writeObject(this.model.getType());
			save.writeObject(this.model.getHealth());
			save.writeObject(this.model.getHappiness());
			save.writeObject(this.model.getHungerValue());
			save.writeObject(this.model.getStartTime());
			save.writeObject(this.model.getEndTime());
			
			save.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
