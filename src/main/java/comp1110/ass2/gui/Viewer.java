package comp1110.ass2.gui;

import comp1110.ass2.FitGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * A very simple viewer for piece placements in the IQ-Fit game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 * </p>
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final String URI_BASE = "assets/";

    //Changed root to board for easier reading -- Peter Zhao
    private final Group board = new Group();
    private final Group controls = new Group();
    private String currentPlacement = "";
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        if (FitGame.isPlacementValid(placement+currentPlacement)) {
            int numberOfPieces = placement.length() / 4;
            for (int i = 0; i < numberOfPieces; i++) {
                getPieces(placement.substring(4 * i, 4 * i + 4));
            }
            currentPlacement+=placement;
        } else textField.clear();
        // FIXME Task 4: implement the simple placement viewer
    }
    // Helper function for Task 4, this will print the actual images
    // The makePlacement command can just split the string into an array.
    // Command Created by Peter Zhao and coded 100% by Peter Zhao

    /** Draws a placement in the window ontop of the board
     *
     * @param piece A valid Piece
     */
    void getPieces (String piece) {
        ImageView placepiece = new ImageView();
        String piecetype = String.valueOf(piece.charAt(0));
        String column = String.valueOf(piece.charAt(1));
        String row = String.valueOf(piece.charAt(2));
        String direction = String.valueOf(piece.charAt(3));
        String piecepng = "";
        // Determine whether it is an uppercase or lowercase shape -- Peter Zhao
        String UpperCase = "BGILNOPRSY";
        for (int k = 0; k < UpperCase.length(); k++) {
            // Index of taken from stack overflow (Public Domain): https://stackoverflow.com/questions/506105/how-can-i-check-if-a-single-character-appears-in-a-string
            // Peter Zhao
            if (UpperCase.indexOf(piece.charAt(0)) >= 0) {
                piecepng = piecetype + "2";
            } else {
                // Differentiate between lowercase and uppercase shapes -- Peter Zhao
                piecepng = piecetype + "1";
            }
        }
        // This below line was given to me by Matthew Chen the Viewer.class.getResource -- Peter Zhao
        Image filename = new Image(Viewer.class.getResource("/assets-components/"+piecepng+".png").toString());
        placepiece.setImage(filename);

        // place at the correct x coordinate -- Peter Zhao
        String[] Column = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] Row = {"0", "1", "2", "3", "4"};
        final boolean b = (piecetype.equals("P") || piecetype.equals("p")) && (direction.equals("E") || direction.equals("W"));
        final boolean b1 = (piecetype.equals("r") || piecetype.equals("R")) && (direction.equals("E") || direction.equals("W"));
        for (int i = 0; i < Column.length; i++) {
            if (Column[i].contains(column) && (direction.equals("N") || direction.equals("S"))) {
                placepiece.setX((i*60) + 55.38); //59.07
            }
            else if (b) {
                placepiece.setX((i*60) -435);
            }
            else if (b1) {
                placepiece.setX((i*60) -295);
            }
            else if (Column[i].contains(column)) {
                placepiece.setX((i*60) + 27.69);
            }
        }
        // place at the correct y coordinate -- Peter Zhao
        for (int j = 0; j < Row.length; j++) {
            if (Row[j].contains(row) && (direction.equals("N") || direction.equals("S"))) {
                placepiece.setY((j*63) + 27.78); //59.26
            } else if (b) {
                placepiece.setY((j*63) -152);
            }
            else if (b1) {
                placepiece.setY((j*63) -94.6);
            }
            else if (Row[j].contains(row) && (direction.equals("E") || direction.equals("W"))) {
                placepiece.setY((j*63) + 58);
            }
        }
        // Piece Images that are 400 by 200 pixels and 300 by 200 pixels will have different scaling -- Peter Zhao
        String fbt = "bBoOpPrRsSyY";
        String NorthSouth = "NS";
        if (fbt.indexOf(piece.charAt(0)) >= 0) {
            placepiece.setFitWidth(250);
            placepiece.setFitHeight(125);
        } else {
            placepiece.setFitWidth(187.5);
            placepiece.setFitHeight(125);
        }

        // Rotate the images -- Peter Zhao
        switch (direction) {
            case "E":
                placepiece.setRotate(90);
                break;
            case "S":
                placepiece.setRotate(180);
                break;
            case "W":
                placepiece.setRotate(270);
                break;
            default:
                placepiece.setRotate(0);
                break;
        }
        // Print the image
        System.out.println(piecepng);
        System.out.println(NorthSouth.indexOf(piece.charAt(3)));
        board.getChildren().add(placepiece);
        // Something worth mentioning, even if the piece is not well formed the piece will still print, this needs a fix -- Peter Zhao
        // In addition to this, pieces that are facing either East or West do not place properly, I am trying to fix this error, but have had no luck -- Peter Zhao
        // THIS HAS ONLY BEEN TESTED WITH THESE TWO SEQUENCES
        // B03SG70Si52SL00Nn01Eo63Sp20Er41WS40Ny62N
        // b52Ng82EI63SL02Wn12So40NP60Sr00Ns23SY11N
    }
    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FitGame Viewer");
        // Print onto the board instead of a blank white canvas -- Peter Zhao
        ImageView boardPng = new ImageView();
        Image boardImage = new Image(Viewer.class.getResource("/assets/board.png").toString());
        boardPng.setImage(boardImage);
        boardPng.setFitWidth(720);
        boardPng.setFitHeight(400);
        board.getChildren().add(boardPng);
        Scene scene = new Scene(board, VIEWER_WIDTH, VIEWER_HEIGHT);
        board.getChildren().add(controls);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}