import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Kevin Falconett, Enrique Sanchez
 * File: TomogatchiModel.java
 * Class: CSC 335
 * Instructor: Dr. Jonathon Misurda
 * Final Project-Tamaclonechi
 * Description: This is the class that create a GUI view for user
 *
 */
public class GuiView extends Application{
	
	public static final int UPDATE_FREQ = 5; //game makes updates to the model every 10 seconds
	public static final int UPDATE_DEBUG = 2;//use to test, updates faster
	public static final int UPDATE_MILISECS = 1000;
	
	
	
	//Reference to controller/model
	private TomogatchiController controller;
	private TomogatchiModel model;
	
	//Pointers to UI elements
	
	//in game scene
	private Label nameLabel; //Need to update after I get the name for the pet
	private ArrayList<Label> statLabels = new ArrayList<Label>();
	private ArrayList<Button> actionButtons = new ArrayList<Button>();
	
	
	
	//References to Scenes
	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	private Stage primaryStage;
	private ImageView toDisplay;
	
	//References for UI components in scenes
	
	//for components in newPetScreen
	private Label chooseLabel; //to be used in buildNewPetButton;
	//private TextField nameTextField;
	
	//for components in newLoadScreen
	private ScrollPane savesPane;
	
	//Animation and counters
	private int animationCounter = 0;
	
	//Data variables
	private int petType = -1;
	private boolean gameOver = false;
	
	/**
	 * start(): starts the scene
	 * on startup. All scenes are added to
	 * the scenes ArrayList, indices are
	 *0-title, 1-game screen, 2- load saves screen, 3-create new pet screen
	 * @throws Exception - bad exception
	 */
	public void start(Stage stage) throws Exception {
		//build all of the scenes
		addScenes();
		Scene scene = scenes.get(0);
		
		stage.setScene(scene);
		stage.setTitle("Tomogatchi");
		stage.show();
		this.primaryStage = stage;
	}
	
	/**
	 * addScenes(): Constructs every scene for the application
	 * on startup. All scenes are added to
	 * the scenes ArrayList, indices are
	 *0-title, 1-game screen, 2- load saves screen, 3-create new pet screen
	 * @throws FileNotFoundException
	 */
	private void addScenes() throws FileNotFoundException {
		scenes.add(buildTitleScene());
		scenes.add(buildGameScene());
		scenes.add(buildLoadScene());
		scenes.add(buildNewPetScene());
		scenes.add(buildGameOverScene());
	}
	
	//Functions for creating UI Scenes
	/**
	 * buildTitleScene(): Constructs scene that displays the title,
	 * first scene that is to be displayed
	 * @return Scene that holds a load and new game button
	 * @throws FileNotFoundException if image is not in assets folder or wrong file path
	 */
	private Scene buildTitleScene() throws FileNotFoundException {
		SplitPane pane = new SplitPane();
		//Construct Left Side VBox
		VBox leftBox = new VBox(15);
		Label titleLabel = new Label("Tamagotchi!");
		titleLabel.setFont(new Font(20));
		Button newGameBtn = new Button("New Game");
		Button loadGameBtn = new Button("Load Game");
		//Set Action for buttons
		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					toNewPetScreen();
				}
            });
		loadGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				toLoadScreen();
			}
        });
		//add all labels and buttons, also empty labels for spacing
		leftBox.getChildren().addAll(titleLabel,new Label(),new Label(),new Label(), 
									newGameBtn, loadGameBtn);
		leftBox.setAlignment( Pos.CENTER);
		pane.getItems().addAll(leftBox);
		//Construct right side icon
		Image titleImg = new Image(new FileInputStream("assets\\titleEgg.png"));
		ImageView titleImgView = new ImageView(titleImg);
		pane.getItems().add(titleImgView);
		return new Scene(pane, 500, 300);
	}
	
	/**
	 * Constructs the scene that displays the game
	 * @return The scene in which the game takes place
	 * @throws FileNotFoundException
	 */
	private Scene buildGameScene() throws FileNotFoundException {
		BorderPane pane = new BorderPane();
		
		//Top of Pane
		//Name of Pet label
		HBox top = new HBox(60);
		Button backBtn = new Button("Back to title");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				toTitle();
			} 	
        });
		
		nameLabel = new Label();//Update later once we have name
		top.getChildren().addAll(backBtn,nameLabel);
		pane.setTop(top);
		nameLabel.setFont(new Font(50));
		//pane.setAlignment(nameLabel, Pos.TOP_CENTER);
		//back button
		
		
		//Right of Pane
		//stats box
		VBox statsBox = buildStatsBox();
		pane.setRight(statsBox);
		pane.setAlignment(statsBox, Pos.CENTER_RIGHT);
		//buildBoard(controller, pane);
		
		//Center
		//Pet image
		toDisplay = buildPetImage();
		pane.setCenter(toDisplay);
		pane.setAlignment(toDisplay, Pos.CENTER);
		
		//bottom
		//Make actions bar
		HBox actions = buildActionsBox();
		pane.setBottom(actions);
		pane.setAlignment(actions, Pos.BOTTOM_CENTER);
		
		return new Scene(pane,600,600);
	}
	
	/**
	 * Constructs scene that displays saves within the saves directory
	 * TODO allow user to select save
	 * @return Scene that allows user view saved games
	 * @throws FileNotFoundException
	 */
	private Scene buildLoadScene() throws FileNotFoundException{
		VBox container = new VBox(); //holds pane and also the back button
		savesPane = new ScrollPane();
		VBox savesBox = fillSavesBox();//holds list of savesBox, to go inside pane
		
		//Fill container
		//construct button, add event, add to container
		Button backBtn = new Button("Back");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				toTitle();
			} 	
        });
		//add to container, new labels are for padding
		container.getChildren().addAll(new Label(""), backBtn, new Label(""), new Label("savesBox: ") );
		
		
		savesPane.setContent(savesBox);
		container.getChildren().add(savesPane);
		
		return new Scene(container, 500,500);
	}
	
	/**
	 * constructs VBox that holds all of the saves found in the saves folder
	 * to be displayed in the loadPet scene
	 * @return VBox filled with saves to be displayed in the Load Pet scene
	 */
	private VBox fillSavesBox() {
		VBox savesBox = new VBox(5);
		//fill savesBox
		ArrayList<String> fileNames = getSaveFiles(); //get list save file titles
		for(String fileName: fileNames) {

			HBox saveContainer = new HBox(100);
			String petName = fileName.substring(0, fileName.length() - 4);
			Label saveLabel = new Label(petName);
			Button loadButton = new Button("Load");
			loadButton.setOnAction(new EventHandler<ActionEvent> () {
				@Override
				public void handle(ActionEvent event) {
					nameLabel.setText(petName);
					model = new TomogatchiModel(petName, 1);
					controller = new TomogatchiController(model);
					
					if(model.getType() == 0) {
						try {
							toDisplay.setImage(new Image(new FileInputStream("assets\\debugPet_Neutral.png")));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else {
						try {
							toDisplay.setImage(new Image(new FileInputStream("assets\\triPet_neutral.png")));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}

					}
					updateStatLabels();
					toGameScreen();
					startTimer();
				}
			});
			saveContainer.getChildren().addAll(saveLabel, loadButton);

			savesBox.getChildren().add(saveContainer);
		}
		return savesBox;
	}
	
	/**
	 * Builds the scene for pet creation
	 * @return Scene - Scene that users create new pets in
	 * @throws FileNotFoundException
	 */
	private Scene buildNewPetScene() throws FileNotFoundException {
		VBox pane = new VBox();
		Label enterNameLabel = new Label("Name: ");
		enterNameLabel.setFont(new Font(25));
		TextField nameTextField = new TextField();
		
		//Hold the images of the pets and their buttons
		HBox imgHolder = this.petImgHolder();
		//Creates the new pet and sends the user to the game scene
		Button newPetBtn = new Button("Create New Pet");
		newPetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String petName = nameTextField.getText();
				if(petName.length() == 0)
					return;//do not do anything if field is empty
				
				nameLabel.setText(petName);
				model = new TomogatchiModel(petName, 0);
				model.setType(petType);
				controller = new TomogatchiController(model);
				updateStatLabels();
				toGameScreen(); //switch screen
				controller.save();
				savesPane.setContent(fillSavesBox()); //update load screen to include freshly made pet
				//Start timer, ticks every second (1000 milisecends)
				startTimer();
			}
        });
		pane.getChildren().addAll(enterNameLabel, nameTextField, imgHolder, chooseLabel, newPetBtn);
		
		return new Scene(pane, 500,500);
		
	}
	//Methods for switching scenes
	
	/**
	 * Builds game over scene
	 * to display when a pet dies (Health goes below 0
	 * @return Scene - Scene that redirects the user to the title screen
	 */
	private Scene buildGameOverScene() {
		VBox Pane = new VBox();
		Label gameOverLabel = new Label("Game Over, Your Pet has died");
		gameOverLabel.setFont(new Font(40));
		Button titleButton = new Button("To title Screen");
		titleButton.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				toTitle();
				gameOver = false;
			}
		});
		Pane.getChildren().addAll(gameOverLabel, titleButton);
		gameOverLabel.setAlignment(Pos.CENTER);
		titleButton.setAlignment(Pos.CENTER);
		return new Scene(Pane, 300, 300);
	}
	
	/**
	 * Sets the scene to the title screen
	 */
	private void toTitle() {
		primaryStage.setScene(scenes.get(0));
	}
	
	/**
	 * Sets the scene to the game screen
	 */
	private void toGameScreen() {
		primaryStage.setScene(scenes.get(1));
	}
	
	/**
	 * Sets the scene to the load saves screen
	 */
	private void toLoadScreen() {
		primaryStage.setScene(scenes.get(2));
	}
	
	private void toNewPetScreen() {
		primaryStage.setScene(scenes.get(3));
	}
	
	private void toGameOverScreen() {
		primaryStage.setScene(scenes.get(4));
	}
	
	//Methods for creating components for scenes
	/**
	 * Build a VBox that holds a visual for all of the stats
	 * for the pet
	 * @return VBox to display by the pet during the game
	 */
	private VBox buildStatsBox() {
		VBox statsBox = new VBox(100);
		//make labels for age, health, happiness, hunger
		//update all of them later once we actually get the values
		Label ageLabel = new Label();
		statLabels.add(ageLabel);
		Label healthLabel = new Label();
		statLabels.add(healthLabel);
		Label happinessLabel = new Label();
		statLabels.add(happinessLabel);
		Label hungerLabel = new Label();
		statLabels.add(hungerLabel);
		statsBox.getChildren().addAll(ageLabel,healthLabel,happinessLabel,hungerLabel);
		//change font size of every label
		for(Label x : statLabels)
			x.setFont(new Font(20));
		return statsBox;
	}
	
	/**
	 * Builds the box for actions in the Game screen
	 * holds the Feeding buttons, the medicine button and the walk button
	 * @return HBox as described
	 */
	private HBox buildActionsBox() {
		HBox actions = new HBox(10);
		String[] actionNames = {"Feed Dinner", "Feed Snack", "Feed Medicine", "Take on walk"};
		for(int i = 0; i < actionNames.length; i++) {
			Button btn = new Button(actionNames[i]);
			actionButtons.add(btn);
			btn.setPrefHeight(50);
			btn.setPrefWidth(100);
			actions.getChildren().add(btn);
			//feed Dinner
			if(i == 0)
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							controller.feedDinner();
							updateStatLabels();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 	
		        });
			//feed snack
			else if(i == 1) {
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							controller.feedSnack();
							updateStatLabels();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 	
		        });
			}
			else if(i == 2) {
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							controller.feedMedicine();
							updateStatLabels();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 	
		        });
			}
			else if( i == 3) {
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							controller.feedSnack();
							updateStatLabels();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} 	
		        });
			}
		}
		return actions;
	}
	
	/**
	 * @return Image of pet as an ImageView
	 * @throws FileNotFoundException
	 */
	private ImageView buildPetImage() throws FileNotFoundException {
		Image petImage = new Image(new FileInputStream("assets\\debugPet_Neutral.png"));
		ImageView returned = new ImageView(petImage);
		returned.setFitHeight(200);
		returned.setFitWidth(200);
		return returned;
	}
	
	/**
	 * petImgHolder() : Creates the HBox that holds the images of the pets to display
	 * and the buttons to choose them in the pet creation screen
	 * @return HBox containing the image and button components in the 
	 * @throws FileNotFoundException
	 */
	private HBox petImgHolder() throws FileNotFoundException {
		HBox imgHolder = new HBox();
		//Get images of the pets
		chooseLabel = new Label("");
		//Get beanPet Image
		VBox beanPetHolder = new VBox();
		Image petImage = new Image(new FileInputStream("assets\\debugPet_Neutral.png"));
		ImageView petImgView = new ImageView(petImage);
		petImgView.setFitHeight(200);
		petImgView.setFitWidth(200);
		Button selectButtonPet1 = new Button("Select");
		selectButtonPet1.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				chooseLabel.setText("Selected: Pet 1");
				try {
					toDisplay.setImage(new Image(new FileInputStream("assets\\debugPet_Neutral.png")));
					petType = 0;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		beanPetHolder.getChildren().addAll(petImgView, selectButtonPet1);
		
		//Get triPet
		VBox triPetHolder = new VBox();
		Image triPetImage = new Image(new FileInputStream("assets\\triPet_Neutral.png"));
		ImageView triPetImgView = new ImageView(triPetImage);
		triPetImgView.setFitHeight(200);
		triPetImgView.setFitWidth(200);
		Button selectButtonPet2 = new Button("Select");
		selectButtonPet2.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				chooseLabel.setText("Selected: Pet 2");
				try {
					toDisplay.setImage(new Image(new FileInputStream("assets\\triPet_Neutral.png")));
					petType = 1;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		triPetHolder.getChildren().addAll(triPetImgView, selectButtonPet2);
		imgHolder.getChildren().addAll(beanPetHolder,triPetHolder);

		return imgHolder;
	}
	
	//methods for processing info/ calculations
	
	/**
	 * Starts the 'tick' timer for the game, calls updateSec() every second(1000 miliseconds)
	 */
	private void startTimer() {
		//Start timer, ticks every second (1000 miliseconds)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> updateSec()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	/**
	 * Gets a list of filenames within the saves directory
	 * @return ArrayList<String> of the names of files within the saves folder
	 */
	private ArrayList<String> getSaveFiles() {
		File folder = new File("saves//");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		for(int i = 0; i < listOfFiles.length; i++) 
			fileNames.add(listOfFiles[i].getName());
		return fileNames;
	}

	/**
	 * Gets called every second, is responsible for calling updates
	 * like updating the labels for the ui elements
	 */
	private void updateSec() {
		//System.out.println(model.getType());
		animationCounter++;
		updateStatLabels();
		controller.save();
		
		if(animationCounter >= UPDATE_FREQ ) {
			animationCounter = 0;
			controller.update(UPDATE_MILISECS);
		}
		if(controller.model.isAlive() == 1 && gameOver == false) {
			toGameOverScreen();
			model.setHealth(100);
		}
	}
	
	/**
	 * Iterates through all of the stat labels (age, health, happiness, hunger)
	 * and updates them (sets the text to their updated values if they have changes)
	 */
	private void updateStatLabels() {
		//update all stats bars
		String[] newLabels = {"Age: "+model.getAge(), "Health: "+ model.getHealth(),"Happiness: "+model.getHappiness(), "Hunger: " + model.getHungerValue()};
		for(int i = 0; i < statLabels.size(); i++) {
			//health happiness hunger
			statLabels.get(i).setText(newLabels[i]);
		}
	}
}
