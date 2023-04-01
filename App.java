package edu.nyu.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
    String getPath = getFilepathFromUser();
    String getFile = getContentsOfFile(getPath);
    String[] getTics = getTicsFromUser();
    System.out.println();
    System.out.println("...............................Analyzing text.................................");
    System.out.println();
    int totalCount = 0;
    for(int i = 0; i < getTics.length; i++){
        totalCount += countOccurrences(getTics[i], getFile);
    }
    System.out.println("Total number of tics: " + totalCount);
    double density = calculateTicDensity(getTics, getFile);
    System.out.println("Density of tics: " + density);
    System.out.println("...............................Tic breakdown..................................");
    System.out.println();
    for(int i = 0; i < getTics.length; i++){
      int occurrences = countOccurrences(getTics[i], getFile);
      int percentage = calculatePercentage(occurrences, totalCount);
      String occ = "/ " + Integer.toString(occurrences) + " occurrences";
      String new1 = String.format("%-10s", getTics[i]);
      String new2 = String.format("%-20s ", occ);
      String ofAll = String.format("%d ", percentage);
      System.out.println(new1 + new2 + "/ " + ofAll + "% of all tics");
    }

}

public static String getFilepathFromUser() {

    String asking = "What file would you like to open?";
    System.out.println(asking);
    String filepath = scn.nextLine();
    return filepath;
}

public static String getContentsOfFile(String filepath) {
    String fullText = "";
    // opening up a file may fail if the file is not there, so use try/catch to protect against such errors
    try {
      // try to open the file and extract its contents
      Scanner scn = new Scanner(new File(filepath));
      while (scn.hasNextLine()) {
        String line = scn.nextLine();
        fullText += line + "\n"; // nextLine() removes line breaks, so add them back in
      }
      scn.close(); // be nice and close the Scanner
    }
    catch (FileNotFoundException e) {
      // in case we fail to open the file, output a friendly message.
      System.out.println("Oh no... can't find the file!");
    }
    return fullText; // return the full text
  }

  public static String[] getTicsFromUser(){
    System.out.println("What words would you like to search for?");
    String word = scn.nextLine();
    String[] tics = word.split(","); 
    for (int i = 0; i < tics.length; i++){
      tics[i] = tics[i].trim();
    }
    return tics;
}

public static int countOccurrences(String needle, String haystack){
    int count = 0;
    String[] newHaystack = haystack.split("[ .,?!-]+"); 
    for(int i = 0; i < newHaystack.length; i++){
        if(newHaystack[i].equalsIgnoreCase(needle)){
            count++;
        }
    }
    return count;
  }

  public static int calculatePercentage(int num1, int num2){
    return (int)((((double) num1) / ((double) num2)) * 100);
    }


    public static double calculateTicDensity(String[] tics, String fullText){
        String[] newText = fullText.split("[ .,?!-]+"); 
        int words = newText.length;
        int totalCount = 0;
        for(int i = 0; i < tics.length; i++){
            totalCount += countOccurrences(tics[i], fullText);
        }
        double density = calculatePercentage(totalCount, words)/100.0;
        return density;
      }


}
