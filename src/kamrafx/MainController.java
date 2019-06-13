package kamrafx;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 *
 * @author Hoffmann József
 */
public class MainController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    SplitPane mainSplit;
    @FXML
    StackPane menuPane;
    @FXML
    Label fejlecLabel;

    @FXML
    Pane alapanyagPane;
    @FXML
    TableView alapanyagTabla;
    @FXML
    TextField inputAlapanyagNev;
    @FXML
    TextField inputAlapanyagMe;
    @FXML
    TextField inputAlapanyagMinMenny;
    @FXML
    ComboBox<String> alapanyagMeComboBox;
    @FXML
    Button buttonAlapanyagHozzaadas;

    @FXML
    TableView etelTabla;
    @FXML
    TextField inputEtelNev;
    @FXML
    TextField inputEtelAr;
    @FXML
    Button buttonEtelHozzaadas;
    @FXML
    TableView hozzavaloTabla;
    @FXML
    Label addHozzavaloLabel;
    @FXML
    Pane addHozzavaloPane;
    
    

    @FXML
    Button btn;

    @FXML
    StackPane messageBox;
    @FXML
    Label messageLabel;

    @FXML
    Pane receptPane;
    
    @FXML
    Label alertLabel;

    Configuration conf = new Configuration("config.ini");
    Logging naplo = new Logging(conf.getLogLevel());
    String message = "";
    public String boxMessage = "Rendszerüzenetek\n";
    boolean delOK = false;

    DBConnect db = new DBConnect();
    KamraDBImpl dbq;

    private final ObservableList<Alapanyag> dataAlapanyag = FXCollections.observableArrayList();
    private final ObservableList<Etel> dataEtel = FXCollections.observableArrayList();

    private final ObservableList<String> mennyisegList
            = FXCollections.observableArrayList("kg", "liter", "db");

    private final String MENU_TORZS = "Törzsadatok";
    private final String MENU_ALAPANYAG = "Alapanyagok";
    private final String MENU_RECEPT = "Receptek";
    private final String MENU_EXIT = "Kilépés";

    public Messages messages = new Messages();

    @FXML
    private void alapanyagMeComboBoxHandler() {

        alapanyagMeComboBox.setItems(mennyisegList);

        if (alapanyagMeComboBox.getSelectionModel().getSelectedItem() != null) {
            inputAlapanyagMe.setText(alapanyagMeComboBox.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    private void addAlapanyag(ActionEvent event) {
        boolean okAlapanyag = false;
        Messages.clearMessageBoxSzoveg();
        if (inputAlapanyagNev.getText().isEmpty()) {
            Messages.setMessageBoxSzoveg("Az alapanyag név nem lehet üres");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            inputAlapanyagNev.clear();
            okAlapanyag = false;
        } else {
            okAlapanyag = true;
        }

        boolean okMe = false;
        if (inputAlapanyagMe.getText().isEmpty()) {
            Messages.setMessageBoxSzoveg("Válasszon egy mennyiség egységet");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            inputAlapanyagMe.clear();
            okMe = false;
        } else {
            okMe = true;
        }

        boolean okMenny = false;
        try {
            double doubleMennyiseg = Double.parseDouble(inputAlapanyagMinMenny.getText());
            okMenny = true;
        } catch (NumberFormatException nfe) {
            Messages.setMessageBoxSzoveg("Számot kérek! Tizedes formátum: 0.0");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            inputAlapanyagMinMenny.clear();
            //inputAlapanyagMinMenny.setStyle("-fx-background-color: RED");
            okMenny = false;
        }

        if (okAlapanyag && okMe && okMenny) {
            Alapanyag ujAlapanyag = new Alapanyag(inputAlapanyagNev.getText(), inputAlapanyagMe.getText(), inputAlapanyagMinMenny.getText());
            dataAlapanyag.add(ujAlapanyag);
            try {
                KamraDBImpl dbq = new KamraDBImpl(db);
                dbq.addDBAlapanyag(ujAlapanyag);
            } catch (SQLException ex) {
                this.message = "Hiba történt alapanyag táblához való hozzáadás során.";
                naplo.addLog(message + ex, 2);
                Messages.setMessageBoxSzoveg(message);
            }

            inputAlapanyagNev.clear();
            inputAlapanyagMe.clear();
            inputAlapanyagMinMenny.clear();
        } else {
            alert("Hiba!", "Hibás alapanyag adatok!");
        }

    }

    public void setAlapanyagTablaData() {
        try {
            dbq = new KamraDBImpl(db);
        } catch (SQLException ex) {

        }
        TableColumn alapanyagNevCol = new TableColumn("Alapanyag név");
        alapanyagNevCol.setMinWidth(250);
        alapanyagNevCol.setCellFactory(TextFieldTableCell.forTableColumn());
        alapanyagNevCol.setCellValueFactory(new PropertyValueFactory<Alapanyag, String>("nev"));

        alapanyagNevCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Alapanyag, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alapanyag, String> t) {
                Alapanyag actualAlapanyag = (Alapanyag) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualAlapanyag.setNev(t.getNewValue());
                dbq.updateDBAlapanyag(actualAlapanyag);
            }
        }
        );

        TableColumn alapanyagMeCol = new TableColumn("Mennyiség egység");
        alapanyagMeCol.setMinWidth(120);
        alapanyagMeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        alapanyagMeCol.setCellValueFactory(new PropertyValueFactory<Alapanyag, String>("me"));

        alapanyagMeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Alapanyag, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alapanyag, String> t) {
                Alapanyag actualAlapanyag = (Alapanyag) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualAlapanyag.setMe(t.getNewValue());
                dbq.updateDBAlapanyag(actualAlapanyag);
            }
        }
        );

        TableColumn alapanyagMinCol = new TableColumn("Min. mennyiség");
        alapanyagMinCol.setMinWidth(150);
        alapanyagMinCol.setCellFactory(TextFieldTableCell.forTableColumn());
        alapanyagMinCol.setCellValueFactory(new PropertyValueFactory<Alapanyag, String>("min"));

        alapanyagMinCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Alapanyag, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alapanyag, String> t) {
                Alapanyag actualAlapanyag = (Alapanyag) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualAlapanyag.setMin(t.getNewValue());
                dbq.updateDBAlapanyag(actualAlapanyag);
            }
        }
        );

        TableColumn removeCol = new TableColumn("Törlés");
        removeCol.setMinWidth(50);

        Callback<TableColumn<Alapanyag, String>, TableCell<Alapanyag, String>> cellFactory
                = new Callback<TableColumn<Alapanyag, String>, TableCell<Alapanyag, String>>() {
            @Override
            public TableCell call(final TableColumn<Alapanyag, String> param) {
                final TableCell<Alapanyag, String> cell = new TableCell<Alapanyag, String>() {
                    final Button btn = new Button("Törlés");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                Alapanyag alapanyag = getTableView().getItems().get(getIndex());
                                delOK = dbq.removeDBAlapanyag(alapanyag);
                                if (delOK) {
                                    dataAlapanyag.remove(alapanyag);
                                } else {
                                    messageLabel.setText(Messages.getMessageBoxSzoveg());
                                    alert("Hiba!", Messages.getMessageBoxSzoveg());
                                }

                            });
                            setGraphic(btn);
                            btn.setPrefSize(60, 8);
                            btn.setPadding(Insets.EMPTY);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        removeCol.setCellFactory(cellFactory);

        alapanyagTabla.getColumns().addAll(alapanyagNevCol, alapanyagMeCol, alapanyagMinCol, removeCol);

        //KamraDBImpl dbq = new KamraDBImpl(db);
        dataAlapanyag.addAll(dbq.getAllAlapanyag());
        alapanyagTabla.setItems(dataAlapanyag);

    }

    @FXML
    private void addEtel(ActionEvent event) {
        boolean okEtelNev = false;
        Messages.clearMessageBoxSzoveg();
        if (inputEtelNev.getText().isEmpty()) {
            Messages.setMessageBoxSzoveg("Az étel név nem lehet üres");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            inputEtelNev.clear();
            okEtelNev = false;
        } else {
            okEtelNev = true;
        }

        boolean okEtelAr = false;
        try {
            int intMennyiseg = Integer.parseInt(inputEtelAr.getText());
            okEtelAr = true;
        } catch (NumberFormatException nfe) {
            Messages.setMessageBoxSzoveg("Egész számot kérek!");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            inputEtelAr.clear();
            okEtelAr = false;
        }

        if (okEtelNev && okEtelAr) {
            Etel ujEtel = new Etel(inputEtelNev.getText(), inputEtelAr.getText());
            dataEtel.add(ujEtel);
            addHozzavaloLabel.setText(ujEtel.getNev() + " hozzávalóinak hozzáadása");
            alert("Figyelmeztetés!", "Az Étel hozzáadása csak a hozzávalók megadásával válik véglegessé!");
            Messages.setMessageBoxSzoveg("Új étel csak a hozzávalók megadásával kerül mentésre!");
            messageLabel.setText(Messages.getMessageBoxSzoveg());
            try {
                KamraDBImpl dbq = new KamraDBImpl(db);
                //dbq.addDBEtel(ujEtel);
            } catch (SQLException ex) {
                this.message = "Hiba történt az Etel táblához való hozzáadás során.";
                naplo.addLog(message + ex, 2);
                Messages.setMessageBoxSzoveg(message);
            }

            inputEtelNev.clear();
            inputEtelAr.clear();

        } else {
            alert("Hiba!", "Hibás étel adatok!");
        }

    }

    private void setEtelTablaData() {
        try {
            dbq = new KamraDBImpl(db);
        } catch (SQLException ex) {

        }
        TableColumn etelNevCol = new TableColumn("Étel neve");
        etelNevCol.setMinWidth(220);
        etelNevCol.setCellFactory(TextFieldTableCell.forTableColumn());
        etelNevCol.setCellValueFactory(new PropertyValueFactory<Etel, String>("nev"));

        etelNevCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Etel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Etel, String> t) {
                Etel actualEtel = (Etel) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualEtel.setNev(t.getNewValue());
                //dbq.updAlapanyag(actualEtel);
            }
        }
        );

        TableColumn etelEladArCol = new TableColumn("Fogyasztói ára");
        etelEladArCol.setMinWidth(100);
        etelEladArCol.setCellFactory(TextFieldTableCell.forTableColumn());
        etelEladArCol.setCellValueFactory(new PropertyValueFactory<Etel, String>("elad_ar"));

        etelEladArCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Etel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Etel, String> t) {
                Etel actualEtel = (Etel) t.getTableView().getItems().get(t.getTablePosition().getRow());
                try {
                    Integer.parseInt(t.getNewValue());
                    actualEtel.setElad_ar(t.getNewValue());
                } catch (NumberFormatException nfe) {
                    alert("Figyelmeztetés!", "Egész számot kérek!");
                    actualEtel.setElad_ar(t.getOldValue());
                }
                //dbq.updAlapanyag(actualAlapanyag);
            }
        }
        );

        TableColumn removeCol = new TableColumn("Törlés");
        removeCol.setMinWidth(50);

        Callback<TableColumn<Etel, String>, TableCell<Etel, String>> cellFactory
                = new Callback<TableColumn<Etel, String>, TableCell<Etel, String>>() {
            @Override
            public TableCell call(final TableColumn<Etel, String> param) {
                final TableCell<Etel, String> cell = new TableCell<Etel, String>() {
                    final Button btn = new Button("Törlés");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                Etel etel = getTableView().getItems().get(getIndex());
                                //delOK = dbq.removeEtel(etel);
                                if (delOK) {
                                    //dataAlapanyag.removeEtel(etel);
                                } else {
                                    messageLabel.setText(Messages.getMessageBoxSzoveg());
                                    alert("Hiba!", Messages.getMessageBoxSzoveg());
                                }

                            });
                            setGraphic(btn);
                            btn.setPrefSize(60, 8);
                            btn.setPadding(Insets.EMPTY);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        removeCol.setCellFactory(cellFactory);

        etelTabla.getColumns().addAll(etelNevCol, etelEladArCol, removeCol);

        //KamraDBImpl dbq = new KamraDBImpl(db);
        dataEtel.addAll(dbq.getAllEtel());
        etelTabla.setItems(dataEtel);

    }

    private void setMenudata() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_TORZS);
        TreeItem<String> nodeItemX = new TreeItem<>(MENU_EXIT);

        Node alapanyagNode = new ImageView(new Image(getClass().getResourceAsStream("/images/alapanyagok-tr-22.png")));
        Node receptNode = new ImageView(new Image(getClass().getResourceAsStream("/images/recept-tr-22.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_ALAPANYAG, alapanyagNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_RECEPT, receptNode);

        nodeItemA.setExpanded(false);
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemX);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu = MENU_ALAPANYAG;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_TORZS:
                            selectedItem.setExpanded(true);
                            fejlecLabel.setText("Törzsállományok kezelése");

                            break;
                        case MENU_ALAPANYAG:
                            alapanyagPane.setVisible(true);
                            receptPane.setVisible(false);
                            fejlecLabel.setText("Alapanyagok kezelése");
                            Messages.clearMessageBoxSzoveg();
                            messageLabel.setText(Messages.getMessageBoxSzoveg());
                            break;
                        case MENU_RECEPT:
                            alapanyagPane.setVisible(false);
                            receptPane.setVisible(true);
                            fejlecLabel.setText("Receptek kezelése");
                            Messages.clearMessageBoxSzoveg();
                            messageLabel.setText(Messages.getMessageBoxSzoveg());
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });
    }

    private void alert(String cim, String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.5);

        Stage alertStage = new Stage();
        alertStage.setHeight(160);
        alertStage.setWidth(text.length()*8 + 10);
        alertStage.setResizable(false);
        alertStage.centerOnScreen();
        alertStage.setTitle(cim);
        alertStage.setMinWidth(200);
        
        
        String text2 ="";
//        if (text.length() > 36) {            
//            text2 = text.substring(37);
//            text = text.substring(0, 37);
//        }
        
        alertLabel = new Label(text);
        alertLabel.setPrefSize(text.length()*8, 50);
        alertLabel.setWrapText(true);
        alertLabel.wrapTextProperty().set(true);

        alertLabel.setAlignment(Pos.BASELINE_CENTER);
        alertLabel.setFont(new Font("Arial", 14));

        Label space = new Label(text2);
        space.setPrefSize(300, 5);
        space.setAlignment(Pos.BASELINE_CENTER);
        space.setFont(new Font("Arial", 14));
        
        
        Button alertButton = new Button("OK");
        alertButton.setMinSize(55, 30);
        VBox vbox = new VBox(alertLabel, space, alertButton);
        vbox.setPrefSize(text.length()*8+10, 150);
        vbox.setMinWidth(200);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.setFillWidth(true);
        Pane alertPane = new Pane(vbox);
        alertStage.setScene(new Scene(alertPane, text.length()*8, 300));        
        alertStage.show();

        alertStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
                alertStage.hide();
            }
        });

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
                alertStage.hide();
            }
        });

//        mainAnchorPane.getChildren().add(vbox);
//        mainAnchorPane.setTopAnchor(vbox, 350.0);
//        mainAnchorPane.setLeftAnchor(vbox, 550.0);
    }

//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Oldalsó benü kezelése
        setMenudata();

        // Bal alsó üzenetbox 
        messageLabel.setText(Messages.getMessageBoxSzoveg());

        //Alapanyag kezelés
        alapanyagMeComboBoxHandler();
        setAlapanyagTablaData();
        setEtelTablaData();

    }

}
