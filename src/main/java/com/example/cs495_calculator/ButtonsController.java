package com.example.cs495_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import methods.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonsController {
    final Function arithmeticLogic = new Function();
    @FXML
    private TextField equationInput;

    @FXML
    private Label error;

    @FXML
    private void handleEnter() {
        final String currentText = equationInput.getText();
        Pattern pattern = Pattern.compile("([0-3]+)\\W*([/*+-])\\W*([0-3]+)");
        Matcher matcher = pattern.matcher(currentText);
        if (!matcher.find()) { error.setText("Please enter a valid expression."); return; }

        int result = 0;
        try {
            final String operand = matcher.group(2);
            final int firstNumber = Integer.parseInt(matcher.group(1));
            final int secondNumber = Integer.parseInt(matcher.group(3));
            switch (operand) {
                case "+":
                    result = arithmeticLogic.add(firstNumber, secondNumber);
                    break;
                case "-":
                    if (firstNumber < secondNumber) {
                        error.setText("We don't have to handle negatives.");
                        return;
                    }
                    result = arithmeticLogic.subtract(firstNumber, secondNumber);
                    break;
                case "/":
                    if(secondNumber == 0){
                        error.setText("Divide by 0 error");
                        equationInput.clear();
                    }else{
                        result = arithmeticLogic.divide(firstNumber, secondNumber);
                        break;
                    }
                case "*":
                    result = arithmeticLogic.multiply(firstNumber, secondNumber);
                    break;
            }
        } catch (NumberFormatException e) {
            error.setText("Entered numbers or result is too large.");
            return;
        }

        equationInput.setText(
                String.valueOf(result)
        );
    }

    @FXML
    protected void addSymbol(String s) {
        error.setText("");
        final String currentText = equationInput.getText();
        if (isSymbol(currentText) && (
                s.equals(" + ") || s.equals(" / ") || s.equals(" - ") || s.equals(" * ")
                )){
            error.setText("Only one operand allowed.");
            return;
        }
        equationInput.setText(currentText + s);
    }


    @FXML
    private void handle0() {
        addSymbol("0");
    }
    @FXML
    private void handle1() {
        addSymbol("1");
    }
    @FXML
    private void handle2() {
        addSymbol("2");
    }
    @FXML
    private void handle3() {
        addSymbol("3");
    }
    @FXML
    private void handlePlus() {
        addSymbol(" + ");
    }
    @FXML
    private void handleSub() {
        addSymbol(" - ");
    }
    @FXML
    private void handleMult() {
        addSymbol(" * ");
    }
    @FXML
    private void handleDiv() {
        addSymbol(" / ");
    }
    @FXML
    private void handleSqr() {
        error.setText("");
        final String number = equationInput.getText();
        if(!Pattern.compile("^\\d+$").matcher(number).find()) {
            error.setText("You must only have digits to square");
            return;
        }
        try {
            equationInput.setText(String.valueOf(arithmeticLogic.square(Integer.parseInt(number))));
        } catch (NumberFormatException e) {
            error.setText("Result is too big");
        }
    }
    @FXML
    private void handleSqrt() {
        error.setText("");
        final String number = equationInput.getText();
        if(!Pattern.compile("^\\d+$").matcher(number).find()) {
            error.setText("You must only have digits to square root");
            return;
        }
        equationInput.setText(String.valueOf(arithmeticLogic.squareRoot(Integer.parseInt(number))));
    }

    private boolean isSymbol(String s) {
        Pattern pattern = Pattern.compile("[/*+-]");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    @FXML
    private void handleClear(){
        equationInput.setText("");
    }
}