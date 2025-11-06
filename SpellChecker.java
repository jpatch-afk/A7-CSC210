import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker{

    public static void main(String[] args) {
        
        WordValidation dict = new WordValidation();
        dict.createSet("words.txt");

        //Reading file
        if(args.length == 0){

            Scanner input = new Scanner(System.in);
            HashSet<String> mispells = new HashSet<String>();

            while(input.hasNextLine()){
                String temp = input.nextLine().toLowerCase();
                temp = temp.replaceAll("[\\p{Punct}]", "");

                //Creates an array of the individual words of the lines to check each word individually 
                String[] line = temp.split(" ");
                for(int i = 0; i < line.length; i++){
                    String word = line[i];
                    if(!dict.containsWord(word) && !mispells.contains(word)) {
                        mispells.add(word);
                        System.out.println("Not found: " + word);
                        System.out.println("Here are some suggestions: " + dict.nearMisses(word));
                    }
                }
            }
            input.close();
        }
        //Reading actual input
        else {
            for(int i = 0; i < args.length; i++){
                String word = args[i];
                if(dict.containsWord(word)){
                    System.out.println(word+" is spelled correctly");
                }
                else {
                    System.out.println("Not found: " + word);
                    System.out.println("Here are some suggestions: " + dict.nearMisses(word));
                }
            }
        }
    }
}