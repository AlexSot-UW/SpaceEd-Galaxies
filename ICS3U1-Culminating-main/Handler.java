package galaxyEd2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class Handler implements EventHandler<MouseEvent> {

	String typeName = "";
	String infoFileName = "";
	BorderPane layout = new BorderPane();
	Canvas canvas;	
	
	  public Handler (String partName, String fileName, BorderPane layoutVariable, Canvas canvas1) {
	    typeName = partName;
	    infoFileName = fileName;
	    layout = layoutVariable;
	    canvas = canvas1;
	  }
	  
	  

	@Override 
	   public void handle(MouseEvent e) { 
		
		BufferedReader reader = null;
		ArrayList<String> actualWords = new ArrayList<String>();
		
	     GraphicsContext gc = canvas.getGraphicsContext2D();
	     
	     gc.clearRect(0, 0, 1000, 1000);
		 
	     
		 try {
			reader = new BufferedReader(new FileReader("C:\\eclipse-workspace\\GalaxyEd\\src\\galaxyEd2\\" + infoFileName + "Info.txt"));
		} catch (FileNotFoundException e1) {
			try {
				GalaxyLog.exceptionFileWriter( e1 );
			} catch (IOException e2) {
			}
		}
		
		 HBox horizontalTextBox = new HBox();
		 
	     Text title = new Text(typeName);
	     
		 String line = "";
	     String paragraph = "";
		 int x = 0;
	     int y = 0;
		 
	     while(line != null) {
	       try {
		   line = reader.readLine();
	       if (line != null && line.equals(typeName)) {
	    	   System.out.println("Success!");   
	        	 
	        	System.out.println();
			    paragraph = reader.readLine();
			    x = Integer.parseInt(reader.readLine());
			    y = Integer.parseInt(reader.readLine());
			    
			    System.out.println("x: " + x + " y: " + y);
			    
				line = null;
				reader.close();
	       }
	     } catch (Exception e1) {
	    	 try {
					GalaxyLog.exceptionFileWriter( e1 );
			} catch (IOException e2) {
			} 
	     }
	       System.out.println("Line: " + line);
	     }
	     
				 
//		 gc.setFill(Color.BLUE);
				
		 gc.setStroke(Color.WHITE);
		 gc.setLineWidth(5);
		 
//		 gc.;
		 
		 gc.strokeOval(x, y, 100, 100);
		        
//	     pane.getChildren().add(canvas);
		 
	     Text explanation = new Text("\n" + paragraph);
	     explanation.setFill(Color.WHITE);
	     title.setFill(Color.WHITE);
	     
	     TextFlow flow = new TextFlow(title, explanation);
	     
	     title.getStyleClass().add("titleText");
	     
	     flow.setTextAlignment(null);
	     
	     flow.setMaxWidth(1100);
	     
	     System.out.println("Explanation: " + paragraph);
	     System.out.println("TypeName: " + typeName);
	     
	     horizontalTextBox.getChildren().add(flow);
	     
	     horizontalTextBox.getStyleClass().add("bottomText");
	     
//	     horizontalTextBox.setAlignment(Pos.TOP_CENTER);
//	     horizontalTextBox.setAlignment(Pos.BOTTOM_CENTER);
	     
	     
//	     horizontalTextBox.getStylesheets().add(getClass().getResource("galaxySheet.css").toExternalForm());

	     
	     
	     flow.getStyleClass().add("styledText2");
	     
	     
//         horizontalTextBox.getStyleClass().add("bottomText");
	     
//	     horizontalTextBox.setMaxWidth(500);
	     layout.setBottom(horizontalTextBox); 
	     
	   } 
	  
  }
