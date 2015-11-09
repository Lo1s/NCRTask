package com.ncr.jiris.ncrtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private EditText editTextInput;
    private TextView textViewPrevious;
    private TextView textViewPresent;
    private TextView textViewDuplicate;
    private TextView textViewNumberOfDuplicates;
    private TextView textViewNumberOfNonDuplicates;

    private String presentInput;
    private String previousInput;
    private ArrayList<Integer> presentList;
    private ArrayList<Integer> previousList;
    private ArrayList<String> inputFileList;
    private ArrayList<String> invalidList;
    private ArrayList<ArrayList<Integer>> duplicateList;
    private int numberOfDuplicates = 0;
    private int numberOfNonDuplicates = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI init
        editTextInput = (EditText) findViewById(R.id.editText_input);
        textViewPresent = (TextView) findViewById(R.id.textView_presentValue);
        textViewPrevious = (TextView) findViewById(R.id.textView_previousValue);
        textViewDuplicate = (TextView) findViewById(R.id.textView_duplicateStatusValue);
        textViewNumberOfDuplicates = (TextView) findViewById(R.id.textView_duplicateNrValue);
        textViewNumberOfNonDuplicates = (TextView) findViewById(R.id.textView_nondupliateNrValue);

        presentList = new ArrayList<>();
        previousList = new ArrayList<>();
        invalidList = new ArrayList<>();
        duplicateList = new ArrayList<>();
    }

    // Tests the logic by given input.txt records (saved in res/raw folder)
    public void test(View view) {
        inputFileList = new ArrayList<>();
        readInputFileToList();

        for (int i = 0; i < inputFileList.size(); i++) {
            previousInput = presentInput;
            presentInput = inputFileList.get(i);

            stringToList(presentInput);
        }

        previousInput = "";
        presentInput = "";
    }

    // Handles users input from the EditText
    public void handleInput(View view) {
        // Comparing the previous set with the present one
        previousInput = presentInput;
        presentInput = editTextInput.getText().toString();

        stringToList(presentInput);
    }

    /** Main logic of catching the duplicates
     * I'm not sure if I've followed the exact definition of the "duplicate" given by the task
     * as it was kind of ambiguous for me. Therefore I've implemented a way where the check for
     * duplicate is done on the set right before the next one.
     * If the correct way is to check the whole history of inputs I can re-do the method if necessary
     */
    private void stringToList(String string) {
        String[] separatedString;
        separatedString = string.split(",");

        // Save the previous set
        if (presentList.size() > 0) {
            previousList.clear();
            for (Integer number :
                    presentList) {
                previousList.add(number);
            }
            textViewPrevious.setText(previousInput);
            presentList.clear();
        }

        // Parse the string list and convert it to integer list
        for (int i = 0; i < separatedString.length; i++) {
            if (isParsable(separatedString[i].trim())) {
                presentList.add(Integer.parseInt(separatedString[i].trim()));
            } else {
                invalidList.add(string);
                Toast.makeText(MainActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
                textViewDuplicate.setText("False");
                Log.d(TAG, "NumberFormatException ");
                presentList.clear();
                presentList.trimToSize();
                break;
            }
        }

        if (presentList.size() > 0)
            textViewPresent.setText(presentInput);

        // Check & Save the duplicates
        if (presentList.size() > 0 && previousList.size() > 0) {
            if (isDuplicate(presentList, previousList)) {
                duplicateList.add(new ArrayList<>(presentList));
                textViewDuplicate.setText("True");
                numberOfDuplicates++;
                textViewNumberOfDuplicates.setText(numberOfDuplicates + "");
            } else {
                textViewDuplicate.setText("False");
                numberOfNonDuplicates++;
                textViewNumberOfNonDuplicates.setText(numberOfNonDuplicates + "");
            }
        }
    }

    // Checks the string input for non-integer values
    private boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    // O(nlogn) complexity
    private boolean isDuplicate(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        Collections.sort(list1);
        Collections.sort(list2);

        return list1.equals(list2);
    }

    // Part of the test method, input.txt is loaded to the arraylist
    private void readInputFileToList() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.input));
        try {
            while (scanner.hasNext()) {
                inputFileList.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        Log.d(TAG, inputFileList.size() + "");
    }

    // Saves the most frequent set
    private ArrayList<Integer> mostFrequentSet() {
        int max = 0;
        ArrayList<Integer> mostFrequentList = new ArrayList<>();
        for (int i = 0; i < (duplicateList.size() - 1); i++) {
            int tempMax = 0;
            for (int j = i + 1; j < duplicateList.size(); j++) {
                if (duplicateList.get(i).equals(duplicateList.get(j))) {
                    tempMax++;
                }
            }
            if (tempMax > max) {
                max = tempMax;
                mostFrequentList.clear();
                mostFrequentList.addAll(duplicateList.get(i));
            }
        }
        return mostFrequentList;
    }

    // Displays all duplicate sets and the most frequent one
    public void listFrequent(View view) {
        Intent intent = new Intent(this, FrequentActivity.class);
        // Convert to stringlist for textviews within recyclerview
        ArrayList<String> stringDuplicateList = new ArrayList<>();
        for (int i = 0; i < duplicateList.size(); i++) {
            stringDuplicateList.add(duplicateList.get(i).toString());
        }
        intent.putExtra(MyConstants.FREQUENT_ARRAYLIST, stringDuplicateList);
        intent.putExtra(MyConstants.MOST_FREQUENT_LIST, mostFrequentSet());
        startActivity(intent);
    }

    // Displays all invalid inputs
    public void listInvalid(View view) {
        Intent intent = new Intent(this, InvalidActivity.class);
        intent.putExtra(MyConstants.INVALID_ARRAYLIST, invalidList);
        startActivity(intent);
    }
}
