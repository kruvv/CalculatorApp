package com.kruvv.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView resultField; // текстовое поле для вывода результата
    private EditText numberField;   // поле для ввода числа
    private TextView operationField;    // текстовое поле для вывода знака операции
    private Double operand = null;  // операнд операции
    private String lastOperation = "="; // последняя операция

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем все поля по id
        resultField = findViewById(R.id.tv_resultField);
        numberField = findViewById(R.id.et_numberField);
        operationField = findViewById(R.id.tv_operationField);



    }

    // Сохраняем состояние
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand != null) {
            outState.putString("OPERAND", String.valueOf(operand));
        }
        super.onSaveInstanceState(outState);
    }

    // Получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    // Обработка нажатия на числовую кнопку
    public void onNumberClick(View view) {

        Button button = (Button) view;
        numberField.append(button.getText());

        if(lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    // Обработка нажатия на кнопку операции
    public void onOperationClick(View view) {

        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();

        // Преверим если что то введено
        if(number.length() > 0 & Character.isDigit(Integer.parseInt(number))) {
            number = number.replace(",", ".");

            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException e) {
                numberField.setText("");
            }

        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                case "=":
                    operand =number;
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                    break;

            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    // Очистка результата на экране
    public void onResetClick(View view) {

        if(resultField != null) {
            resultField.setText("");
        }
    }

    public void onDeleteClick(View view) {

//        if(numberField != null) {
//            char[] numbers = numberField.getText().toString().toCharArray();
//            //Arrays.asList(numbers).remove(numbers.length);
//            char[] chars = Arrays.copyOf(numbers, numbers.length - 1);
//            String updateNumbers = new String(chars);
//            if(!updateNumbers.isEmpty()) {
//                numberField.setText(updateNumbers);
//            }


//        }


    }
}
