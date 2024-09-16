package galaxyEd2;

import java.awt.Dimension;


import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GalaxyMain extends Application{
	
  Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

  double screenW = size.getWidth();
  double screenH = size.getHeight();
	
  Image image;
  ImageView test;
  ImageView test2;   
  ImageView test3;  

  public void start(Stage primaryStage) throws FileNotFoundException {
	
	StackPane root = new StackPane();                  
	  
	BorderPane layout = new BorderPane();
    
	BorderPane centerLayout = new BorderPane();
    
	primaryStage.setTitle("TEST");
    layout.setCenter(centerLayout);
    layout.setLeft(typesListTree(layout, centerLayout));
    
    root.getChildren().add(layout); 

    Scene scene = new Scene(root, screenW, screenH/1.1);
    scene.getStylesheets().add(getClass().getResource("galaxySheet.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public TreeView typesListTree (BorderPane layout, BorderPane centerLayout) {
	  
	  TreeItem<String> rootItem = new TreeItem<String> ("Types of Galaxies:");
	    TreeItem<String> disk = new TreeItem<String> ("Disk Galaxies");    
	    TreeItem<String> elliptical = new TreeItem<String> ("Elliptical Galaxies");
	    TreeItem<String> irregular = new TreeItem<String> ("Irregular Galaxies");
	    TreeItem<String> peculiar = new TreeItem<String> ("Peculiar Galaxies");
	    
	    rootItem.setExpanded(true);
	            
	    rootItem.getChildren().add(disk);
	    rootItem.getChildren().add(elliptical);
	    rootItem.getChildren().add(irregular);
	    rootItem.getChildren().add(peculiar);
	    
	    TreeItem<String> spiral = new TreeItem<String> ("Spiral Galaxies");            
	    TreeItem<String> spiralBar = new TreeItem<String> ("Bar Spiral Galaxies");            
	    TreeItem<String> lenticular = new TreeItem<String> ("Lenticular Galaxies");            

	    
	    disk.getChildren().add(spiral);
	    disk.getChildren().add(spiralBar);
	    disk.getChildren().add(lenticular); 
	    
	    TreeView<String> rootView = new TreeView<String>(rootItem);
	    
	    rootView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	    @Override
	    public TreeCell<String> call(TreeView<String> p) {
	      TextFieldTreeCell cell = new TextFieldTreeCell();
	      cell.setOnMouseClicked(e -> {
	        TextFieldTreeCell treeCell = (TextFieldTreeCell)e.getSource();
	        TreeItem item = treeCell.getTreeItem();
	        
	        HBox explanatoryBox = new HBox();
	        Pane pane = new Pane();
	        
	        ImageView centerImage = new ImageView();
	        
	        String name = "";
	        
	        if (item != null) {
	          name = (item.toString()).split(" ")[3].toLowerCase();;
	        }
	                
	        try {
		        centerImage = imageReader(name);
			} catch (Exception e1) {
			}
	        
	        centerImage.fitHeightProperty().set((screenW/3.5));
	        
	        centerImage.setPreserveRatio(true);
                
	        pane.getChildren().addAll(centerImage);
	        
	        ArrayList<String> explanation = new ArrayList<String>();
	        String s1 = null;
	        
	        try {
	        	if (name != "" && name != "disk" && name != "types") {
	        	  System.out.println(name);
				  explanation = textFileReader(name, s1, true); 
	        	}
				System.out.println(explanation + " testArray" );
			} catch (Exception e1) {
				try {
					GalaxyLog.exceptionFileWriter( e1 );
				} catch (IOException e2) {
				}
			}
	        
	        String explanationString = explanation.get(0);
	        
	        Text text = new Text(explanationString);
	        
	        text.setFill(Color.WHITE);
	        
	        TextFlow flowing = new TextFlow(text);
	        
	        flowing.setMaxWidth(300);
	        
	        flowing.getStyleClass().add("styledText2");
	        
	        explanatoryBox.getChildren().add(flowing);
	        centerLayout.setLeft(pane);
	        centerLayout.setRight(explanatoryBox);

	        ArrayList<String> testArray = new ArrayList<String>();
	        String s = null;
	        
	        try {
	        	if (name != "" && name != "disk" && name != "types") {
	        	  System.out.println(name);
				  testArray = textFileReader(name, s, false); 
	        	}
				System.out.println(testArray + " testArray" );
			} catch (Exception e1) {
				try {
					GalaxyLog.exceptionFileWriter( e1 );
				} catch (IOException e2) {
				}
			}
	        
	        layout.setRight(partsListTree(testArray, name, centerLayout, pane)) ;
	        
	        });
	        return cell;
	      }
	    });
		return rootView;
	  
  }
  
  public VBox partsListTree (ArrayList<String> listOfButtons, String infoFileName, BorderPane layoutVariable, Pane pane) {
	
	VBox verticalBox = new VBox();
	
	Button[] buttons = new Button[listOfButtons.size()];
	
	Canvas canvas = new Canvas(500, 500);
	
	pane.getChildren().add(canvas);
	
	for (int i = 0; i < listOfButtons.size(); i++) {
		
	  buttons[i] = new Button(listOfButtons.get(i));
 
	  Handler handler = new Handler(listOfButtons.get(i), infoFileName, layoutVariable, canvas);
	  
	  buttons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	  
	  verticalBox.getChildren().add(buttons[i]);
	  
	  buttons[i].getStyleClass().add("propertiesBoxButtons");
	}

	Insets insets = new Insets(100, 0, 100, 0);
	
	verticalBox.setPadding(insets);
	verticalBox.setSpacing(100);
	
	verticalBox.getStyleClass().add("propertiesBox");
	
    return verticalBox;    
  }
  
  public ImageView imageReader (String name) throws FileNotFoundException {
    
	FileInputStream input = null;
	
	input = new FileInputStream("C:\\eclipse-workspace\\GalaxyEd\\src\\galaxyEd2\\" + name + ".jpg");
	
	
	Image image = new Image(input);
	ImageView test = new ImageView(image);   
	  
	return test;

  } 
    
  public ArrayList<String> textFileReader (String fileName, String chooser, boolean explanation) throws IOException{
	
      BufferedReader reader = null;
      
      try {
			reader = new BufferedReader(new FileReader("C:\\eclipse-workspace\\GalaxyEd\\src\\galaxyEd2\\" + fileName + ".txt"));
		} catch (Exception e1) {
			try {
				GalaxyLog.exceptionFileWriter( e1 );
			} catch (IOException e2) {
			}
		}
      
      ArrayList<String> arrayOfButtons = new ArrayList<String>();
      
      String line = reader.readLine();
      String text = "";
      
      
      while (line != null) {
//        line 
    	if (explanation) {
    		if(line.equals("Explanation:")) {
        		explanation = false;
    		}
            line = reader.readLine();
    		continue;
    	} else if(line.equals("Explanation:")) {
    	  break;
    	}
            arrayOfButtons.add(line);
            line = reader.readLine();
      }
	  
    return arrayOfButtons;
	  
  }
  
  public static void main(String[] args) {
    launch(args);
  }
  
}