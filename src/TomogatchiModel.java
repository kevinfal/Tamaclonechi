import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Observable;
import java.util.Random;


/**
 * 
 * @author Kevin Falconett, Enrique Sanchez, Huiqi He
 * File: TomogatchiModel.java
 * Class: CSC 335
 * Instructor: Dr. Jonathon Misurda
 * Final Project-Tamaclonechi
 * Description: This is the class that holds the model for our program
 *
 */
public class TomogatchiModel extends Observable{
//	private Pet pet;
	private int age;
	private int health;
	private int happiness;
	private int hungerValue;
	private int type;
	private String name;
	private long startTime;
	private long endTime;
	private long timeDiff;
	
	/**
	 * TomogatchiModel(String fileName, int load): Method that constructs our pet model
	 * @param fileName - name of the pet/file we need to load
	 * @param load - integer that represents whether we are loading old one or making new one
	 */
	public TomogatchiModel(String fileName, int load) {
		
		this.name = fileName;
		String name1 = "";
		int age1 = 0;
		int type1 = 0;
		int health1 = 0;
		int happiness1 = 0;
		int hunger1 = 0;
		long startTime1 = 0;
		long endTime1 = 0;
		
		//Loads new pet from existing file
		if(load == 1) {
		try {
			FileInputStream saveFile = new FileInputStream("saves//" + fileName + ".txt");
			ObjectInputStream save = new ObjectInputStream(saveFile);
			
			name1 = (String) save.readObject();
			age1 = (Integer) save.readObject();
			type1 = (Integer) save.readObject();
			health1 = (Integer) save.readObject();
			happiness1 = (Integer) save.readObject();
			hunger1 = (Integer) save.readObject();
			startTime1 = (long)save.readObject();
			endTime1 = (long)save.readObject();
			
			save.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}

		this.name = name1;
		this.age = age1;
		this.type = type1;
		this.health = health1;
		this.happiness = happiness1;
		this.hungerValue = hunger1;
		this.startTime = startTime1;
		this.endTime = endTime1;
		
		int aliveBool = this.isAlive();
		if(aliveBool == 1) {
			System.exit(1);
		}
		
		}else {
			//Creates new pet
		     //Random rand = new Random(); 
		     //int upperbound = 2;
		      
		     //int int_random = rand.nextInt(upperbound); 
			
			this.name = fileName;
			this.age = 0;
			this.type = type1;
			this.health = 100;
			this.happiness = 100;
			this.hungerValue = 100;
			this.startTime = System.currentTimeMillis();
			this.endTime = 0;
		}
	}
		
	/**
	 * getAge(): Method that returns the pet age
	 * @return int
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * setAge(): Method that sets the pet age
	 * @param age - the age for the pet
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	
	/**
	 * getName(): Method that returns the pet name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * setName(): Method that sets the name of the pet
	 * @param name - the name for the pet
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getHealth(): Method that returns the pet health level
	 * @return int
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * setHealth(): Method that sets the pet health level
	 * @param health - the value for pet health
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * getHappiness(): Method that returns the pet happiness
	 * @return int
	 */
	public int getHappiness() {
		return this.happiness;
	}
	
	/**
	 * setHappiness(): Method that sets the pet happiness variable
	 * @param happiness - the value for pet happiness
	 */
	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}
	
	/**
	 * getHunger(): Method that returns the pet hunger level
	 * @return int
	 */
	public int getHungerValue() {
		return this.hungerValue;
	}
	
	/**
	 * setHunger(): Method that sets the pet hunger variable
	 * @param hungerValue - the value for pet hunger
	 */
	public void setHungerValue(int hungerValue) {
		this.hungerValue = hungerValue;
	}
	
	/**
	 * getType(): Method that returns the pet type
	 * @return int
	 */
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * setStartTime(): Method that sets the pet start time variable
	 *
	 */
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * getStartTime(): Method that returns the time the model was initialized
	 * @return long
	 */
	public long getStartTime() {
		return this.startTime;
	}
	
	/**
	 * setEndTime(): Method that sets the pet end time variable
	 * 
	 */
	public void setEndTime() {
		this.endTime = System.currentTimeMillis();
	}
	
	
	/**
	 * getEndTime(): Method that returns the time when an action is clicked
	 * @return long
	 */
	public long getEndTime() {
		return this.endTime;
	}
	
	
	/**
	 * set(): Method that sets the pet time diff variable
	 * @param myVal - time diff in milliseconds
	 */
	public void setTimeDiff(long myVal) {
		this.timeDiff = myVal;
	}
	
	
	/**
	 * getTimeDiff(): Method that returns the difference of time between actions
	 * @return long
	 */
	public long getTimeDiff() {
		return this.timeDiff;
	}
	
	/**
	 * easyHappinessUpdate(): Method that updates the model happiness by 1
	 *
	 */
	public void easyHappinessUpdate() {
		int myVal = this.happiness;
		myVal = myVal - 1;
		this.happiness = myVal;
	}
	/**
	 * easyHungerUpdate: Method that updates the model hunger by1
	 *
	 */
	public void easyHungerUpdate() {
		int myVal = this.hungerValue;
		myVal = myVal - 1;
		this.hungerValue = myVal;
	}
	/**
	 * easyHealthUpdate: Method that updates the model health by 1
	 *
	 */
	public void easyHealthUpdate() {
		int myVal = this.health;
		myVal = myVal - 1;
		this.health = myVal;
	}
	
	
	/**
	 * easyHappinessUpdate2(): Method that updates the model happiness by 5
	 *
	 */
	public void easyHappinessUpdate2() {
		int myVal = this.happiness;
		myVal = myVal - 5;
		this.happiness = myVal;
	}
	
	/**
	 * easyHungerUpdate2(): Method that updates the model hunger by 5
	 *
	 */
	public void easyHungerUpdate2() {
		int myVal = this.hungerValue;
		myVal = myVal - 5;
		this.hungerValue = myVal;
	}
	
	/**
	 * easyHealthUpdate2(): Method that updates the model health
	 *
	 */
	public void easyHealthUpdate2() {
		int myVal = this.health;
		myVal = myVal - 5;
		this.health = myVal;
	}
	
	
	
	/**
	 * printStats(): Method that prints the pet stats
	 *
	 */
	public void printStats() {
		System.out.println("name: " + this.name);
		System.out.println("Health: " + this.health);
		System.out.println("happiness: " + this.happiness);
		System.out.println("age: " + this.age);
		System.out.println("hunger val: " + this.hungerValue);
		System.out.println("start time: " + this.startTime);
		System.out.println("end time: " + this.endTime);
		long myDiff = this.endTime-this.startTime;
		System.out.println("diff: " + myDiff);
		
	}
	
	/**
	 * isAlive() : Method that returns 1 if the pet is dead, 0 if alive
	 * @return int
	 *
	 */
	public int isAlive() {
		if(this.health < 0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	/**
	 * easyHealthUpdate2(): Method that updates the model health
	 *
	 */
	public void easyAgeUpdate() {
		this.age = this.age + 1;
	}
	
}
