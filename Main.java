package application; 


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

	public static final String STAR_FILE = "mag_5_stars.csv";

	// --------------------------
	// Application instance fields.
	// --------------------------
	// UI Related Fields
	private Stage stage;
	private BorderPane rootPane;	
	private MenuBar menuBar;
	private Scene scene;

	// Data related fields
	// An ArrayList of all Star instances loaded from the 
	private ArrayList<Star> stars;
	
	// An ArrayList of the filtered stars based on user selection.
	// This is the field that will be used to populate the ListView
	private ArrayList<Star> filteredStars;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start method for the JavaFX application
	 */
	@Override
	public void start(Stage primaryStage) {

		// Load the Star data
		this.stars = loadData();
		
		
		// Remember the primaryStage
		this.stage = primaryStage;

		// Create the Menu
		this.menuBar = new MenuBar();
		this.menuBar.getMenus().addAll(buildFileMenu(), buildModeMenu());
		this.menuBar.getStyleClass().add("menuBar");
		
		this.rootPane = new BorderPane();
		this.rootPane.setTop(menuBar);
		this.rootPane.setCenter(getStarInfoScene());

		// Create the Scene
		this.scene = new Scene(rootPane,900,450);

		// Get the scene
		primaryStage.setScene(scene);

		// set the title
		primaryStage.setTitle("Star Database");

		// set the css mode
		this.scene.getStylesheets().add("application1.css");
		primaryStage.setResizable(false);

		// Show the primary stage
		primaryStage.show();

	}

	/**
	 * Builds and returns the file Menu
	 * @return fileMenu
	 */
	private Menu buildFileMenu() {
		// Create instance of fileMenu
		Menu fileMenu = null;
		fileMenu = new Menu("File");
		
		// Add close event to file
		MenuItem CLOSE= new MenuItem("Close");
		CLOSE.setOnAction(t -> this.stage.close());
		fileMenu.getItems().add(CLOSE);
		
		return fileMenu;
	}


	/**
	 * Builds and returns the mode Menu
	 * @return modeMenu
	 */
	private Menu buildModeMenu() {
		
		Menu modeMenu = null;
		modeMenu = new Menu("Mode");
		modeMenu.getStyleClass().add("mode");
		
		// Mode Menu
		RadioMenuItem LIGHT = new RadioMenuItem("Light");
		RadioMenuItem DARK = new RadioMenuItem("Dark");
		LIGHT.getStyleClass().add("light");
		//Default Selection
		LIGHT.setSelected(true);
		// modeMenu setOnAction to change between light and dark
		LIGHT.setOnAction(t -> {
			LIGHT.setSelected(true);
			DARK.setSelected(false);
			this.scene.getStylesheets().add("application1.css");
			this.scene.getStylesheets().remove("application.css");
		});
		DARK.setOnAction(t -> {
			LIGHT.setSelected(false);
			DARK.setSelected(true);
			this.scene.getStylesheets().add("application.css");
			this.scene.getStylesheets().remove("application1.css");
		});
		//add to menu
		modeMenu.getItems().addAll(LIGHT, DARK);

		return modeMenu;
	}

		/**
		 * Build and return the main star information scene
		 * @return root
		 */
		private Pane getStarInfoScene() {
		
		//--------------------------------------------------------------------
		// Left-side Control Panel - Star filter and statistics
		// State selector using ComboBox
		// Minimum Magnitude selector using RadioButton and ToggleGroup
		//--------------------------------------------------------------------
		ComboBox<String> constellationFilterSelector = new ComboBox<String>();
		constellationFilterSelector.getItems().add("ALL");
		constellationFilterSelector.getItems().add("AQL");
		constellationFilterSelector.getItems().add("AND");
		constellationFilterSelector.getItems().add("CEP");
		constellationFilterSelector.getStyleClass().add("leftCBX");
		
		// Left Side default labels
		
		Label select = new Label("Select a Constellation: ");
		Label stat = new Label("Statistics: ");
		Label filteredTotalLabel = new Label("Total: ");
		Label filteredBrightestLabel = new Label("Brightest: ");
		Label filteredFarthestLabel = new Label("Farthest: ");
		select.getStyleClass().add("select");
		stat.getStyleClass().add("stat");
		filteredTotalLabel.getStyleClass().add("fTL");
		filteredBrightestLabel.getStyleClass().add("fBL");
		filteredFarthestLabel.getStyleClass().add("fFL");

		VBox controlColumnLeft = new VBox(
				select, 
				constellationFilterSelector, 
				stat, 
				filteredTotalLabel, 
				filteredBrightestLabel, 
				filteredFarthestLabel
				);
		controlColumnLeft.setAlignment(Pos.TOP_LEFT);
		
		// Star list view
		ListView<String> starListView = new ListView<String>();
		starListView.getStyleClass().add("sTV");
		Label selectStar = new Label("Select a Star");
		selectStar.getStyleClass().add("selectStar");


		VBox controlColumnCenter = new VBox(1, 
				selectStar, 
				starListView
				);
		controlColumnCenter.setAlignment(Pos.TOP_CENTER);
		// Star Information column
		Label starInfoLabel = new Label("Select a constellation: ");
		starInfoLabel.getStyleClass().add("sIL");
		
		// Creating the right-side column
		VBox controlColumnRight = new VBox(starInfoLabel);
		controlColumnRight.setAlignment(Pos.TOP_CENTER);
		
		HBox root = new HBox(20, 
				controlColumnLeft, 
				controlColumnCenter, 
				controlColumnRight);
		
		// select the brighter star
		StarSelector brighter = new StarSelector() {
			@Override
			public Star select(Star a, Star b) {
				Star brightest = stars.get(0);
				
				for (int i = 1; i > stars.size(); i++) {
					Star star = stars.get(i);
					if ((star.getVisualMagnitude() < brightest.getVisualMagnitude()) && brightest.getVisualMagnitude() > 0.00) {
						brightest = star;
					}
				}
				return brightest;
			}
		};
		// select the farther star
		StarSelector farther = new StarSelector() {
			@Override
			public Star select(Star a, Star b) {
				Star farther = stars.get(0);
				for (int i = 1; i > stars.size(); i++) {
					Star star = stars.get(i);
					if ((star.getDistance() > farther.getDistance())) {
						farther = star;
					}
				}
				return farther;
			}
		};
		// Reduce a star array with the given selector
		StarReducer reducer = new StarReducer() {

			@Override
			public Star reduce(ArrayList<Star> array, StarSelector selector) {
				Star selected = array.get(0);
				for (Star sizable : array) {
					selected = selector.select(selected, sizable);
				}
				return selected;
			}
		};
		
		// Filter the stars given an array and a predicate
		StarFilter filter = (a,b) -> {
			ArrayList<Star> filtered = new ArrayList<>();
			for (Star current : a) {
				if (b.isOK(current)) {
					filtered.add(current);
				}
			}
			return filtered;
		};

		//UpdateHandler to read each ListView selection over and over
		UpdateHandler handleConstellationFilter = new UpdateHandler() {
			@Override
			public void update() {
				starInfoLabel.setText("");
				String currentSelection = constellationFilterSelector.getValue();
				starInfoLabel.setText(String.format("Selected Constellation: %s", currentSelection));
				filteredStars = filter.filter(
						stars,
						(stars)->{
							return currentSelection != null && (currentSelection.equals("All") || currentSelection.equalsIgnoreCase(stars.getConstellationName()));
						});
				starListView.getSelectionModel().clearSelection();
				starListView.getItems().clear();
				for (Star star : filteredStars) {
					filteredBrightestLabel.setText(String.format("Brightest: " + star.getVisualMagnitude()));
					filteredFarthestLabel.setText(String.format("Farthest: " + star.getDistance()));
					filteredTotalLabel.setText(String.format("Total: %s", filteredStars.size()));
					String display = String.format("%s in %s, %,.2f, %,.2f", star.getName(), star.getConstellationName(), star.getVisualMagnitude(), star.getDistance());
					starListView.getItems().add(display);
				}
			}
		};
		
		// Configure the constellationFilterSelector event handler
		constellationFilterSelector.setOnAction(t -> handleConstellationFilter.update());				


		// ListView listener
		starListView.getSelectionModel().selectedItemProperty().addListener(e -> {
			int index = starListView.getSelectionModel().getSelectedIndex();
			Star star = stars.get(index);
			starInfoLabel.setText(star.toString());
		}); 

		
		// initialize the display
		handleConstellationFilter.update();
		
		
		return root;
	} 

		

	/**
	 * This method loads star data from an external file and
	 * returns it as an ArrayList of Star objects.
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Star> loadData() {
		ArrayList<Star> data = new ArrayList<>();
		try {
			 File file = new File(STAR_FILE);
		        Scanner sc = new Scanner(file);
		        sc.nextLine();
		        // Check file using Scanner
		        while (sc.hasNextLine()) {
		            Star star = new Star(sc.nextLine());
		            data.add(star);
		        }
		        // Closes scanner and returns data to loadData() method for exporting
		        sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
}
