/**
 * @author MRossello11
 * @version 1.2
 * @since 14/02/2022
 * @description TicTacToe UI controller*/

package com.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //buttons or cells
    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Button restart; //restart button

    private ArrayList<Button> cells; //buttons array
    private ArrayList<Button> remainingButtons; //buttons not used

    Random rand = new Random(); //random object

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cells = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));
        cells.forEach(this::setupButton);
        remainingButtons = cells;
    }

    //configures the buttons
    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> markButton(button));
        button.setText("");
        button.setDisable(false);
    }

    //when a button is clickd
    private void markButton(Button b) {
        if (!b.getText().equals("")) return; //if the button already has an X or O (is alredy marked)
        b.setText("O"); //sets the O symbol
        remainingButtons.remove(b); //button no longer available

        if(checkWin()) return; //checks if the movement is a winning one

        if (remainingButtons.size()==0) { //if there are no more buttons, it's a tie
            winAlert(3);
            return;
        }

        //computer player move
        int index = rand.nextInt(remainingButtons.size()); //the next move is random
        cells.get(cells.indexOf(remainingButtons.get(index))).setText("X"); //sets the symbol
        remainingButtons.remove(index);
        checkWin(); //checks if it has won
    }

    //looks all posible win states for a winning combination
    private boolean checkWin(){
        for (int i = 0; i < 8; i++){ //loops through all wininig cases
            String line = switch (i) {
                //winning cases
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if(winningLine(line)){ //checks if all three symbols of the line are equal
                return true;
            }
        }
        return false;
    }

    //checks the line symbols and sends a winning signal (if the symbols are equal)
    private boolean winningLine(String line){
        if (line.equals("OOO")){ //O win
            winAlert(0);
            return true;
        }
        if (line.equals("XXX")){ //X win
            winAlert(1);
            return true;
        }
        return false;
    }

    //different alerts for different winners (or tie)
    private void winAlert(int win){
        if (win==0){ //O win
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("GAME OVER");
            alert.setContentText("Player O wins!");
            alert.showAndWait();
            cells.forEach(button -> button.setDisable(true));
        } else if (win==1){ //X win
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("GAME OVER");
            alert.setContentText("Player X wins!");
            alert.showAndWait();
            cells.forEach(button -> button.setDisable(true));
        } else if (remainingButtons.size()==0 && win==3){ //tie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("GAME OVER");
            alert.setContentText("Tie");
            alert.showAndWait();
            cells.forEach(button -> button.setDisable(true));
        }

    }

    //restarts the game (clears all buttons and variables)
    @FXML
    void restartGame() {
        initialize(null,null);
    }
}