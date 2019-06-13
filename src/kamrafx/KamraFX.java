package kamrafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hoffmanj
 */
public class KamraFX extends Application {
   
    //public static Logging naplo;
    //public Configuration conf;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("KamraFX - Éttermi készletkezelő program");
        stage.setScene(scene);
        stage.show();
        
        
//        Pane pane = new Pane();
//        Stage stage2 = new Stage();
//        stage2.minHeightProperty().set(200);
//        stage2.minWidthProperty().set(200);
//        stage2.maxWidthProperty().set(500);
//        stage2.maxHeightProperty().set(500);
////        Label label = new Label();
////        label.setText("askdkwajdékdsdkasdaédkad");
////        label.setVisible(true);
//        Circle kor = new Circle();
//        kor.setRadius(50);
//        Color szin = new Color(0.4, 0.7, 0.3, 0.5);
//        kor.setFill(szin);
//        
//        kor.centerXProperty().bind(pane.widthProperty().divide(2));
//        kor.centerYProperty().bind(pane.heightProperty().divide(2));
//        pane.getChildren().add(kor);
//        stage2.setScene(new Scene(pane,300,300));
//        stage2.show();
    }

    
    public static void main(String[] args) {        
        launch(args);
    }
    
}
