import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;

/**
 * 
 * @author Kevin Falconett, Enrique Sanchez, Huiqi He
 * File: TomogatchiModel.java
 * Class: CSC 335
 * Instructor: Dr. Jonathon Misurda
 * Final Project-Tamaclonechi
 * Description: This is the class that hold temp value for pets
 *
 */

public class TamaclonechiTest {
	private int hunger;
	private int age;
	private int weight;
	private int happiness;
	
	public static void main(String[] args) throws Exception {
		
		  
		long start = System.currentTimeMillis();
		// some time passes
		TimeUnit.SECONDS.sleep(1);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		
		//This is to show you guys a simple example of how it works.
		//System.currentTimemillis() takes the current time that has passed since Januaray 1st 1970
		//So when we create our model it initalizes the start time and whenever we do an action we
		//can update our end time and check the difference to see how many milliseconds have passed.
		//If we divide this number by 1000 we can see how many seconds have passed, and can update our model through
		//our controller to change/update the stats every 1 or 2, maybe few seconds. Then we update our start time to
		//be the end time that we just pulled, that way the next time we do an action itll be based off the first action
		//we did rather than when we first initialized our tomogatchi model.
		 System.out.println("start time in nano seconds from january 1st 1970 is: " + start);
		 System.out.println("start time in nano seconds from january 1st 1970 is: " + end);
		 System.out.println("start time in nano seconds from january 1st 1970 is: " + elapsedTime);
		 
			TomogatchiModel model = new TomogatchiModel("Matt", 0);
			TomogatchiController control = new TomogatchiController(model);
			
			//control.model.printStats();
			control.model.printStats();
			control.feedDinner();
			control.feedSnack();
			control.age();
			control.happiness();
			control.feedMedicine();
			control.walk();
			control.save();
			control.model.printStats();
			System.out.println("Diff in time is " + control.model.getTimeDiff());
			control.update(control.model.getTimeDiff());
			control.model.printStats();
			control.model.setType(1);
			control.update(1000);
			//control.model.setName("max");
			control.model.setStartTime();
			control.model.isAlive();
			
			try {
				// Open a file to write to, named SavedObj.sav.
				//String myVar = control.model.getName();
				FileOutputStream saveFile=new FileOutputStream("saves//" + control.model.getName() + ".txt");

				// Create an ObjectOutputStream to put objects into save file.
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				save.writeObject(control.model.getName());
				save.writeObject(control.model.getAge());
				save.writeObject(control.model.getType());
				save.writeObject(control.model.getHealth());
				save.writeObject(control.model.getHappiness());
				save.writeObject(control.model.getHungerValue());
				save.writeObject(control.model.getStartTime());
				save.writeObject(control.model.getEndTime());
				
				save.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			
			TomogatchiModel model2 = new TomogatchiModel("Matt", 1);
			TomogatchiController control2 = new TomogatchiController(model2);
			control2.model.setName("Max");

			
			
	}
	
}
