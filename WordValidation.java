import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import a5.SpellingOperations;

public class WordValidation implements SpellingOperations{

    private HashSet<String> words;

    /**
     * Constructor that creates a HashSet of words from a file input
     * @param input file that contains the words
     * @return HashSet of words from inported file
     */
    public HashSet<String> createSet(String input){
        Scanner file = null;

        if(input.length() > 0) {

            try {
                file = new Scanner(new File(input));
            }
            catch(FileNotFoundException e){
                System.out.println("File not found. Try again.");
                e.printStackTrace();
            }
        }
        else {
            file = new Scanner(System.in);
        }

        HashSet<String> words = new HashSet<String>();

        while(file.hasNext()){
            String word = file.nextLine();
            String temp = word.replaceAll("[\\p{Punct}]", "");
            words.add(temp);
        }

        this.words = words;
        file.close();

        return words;
    }

    /**
     * Checks to see if a word is in the word list 
     * @param query word to check
     * @returns true if the word is spelled correctly
     */
    public boolean containsWord(String query){
        return this.words.contains(query);
    }

    public ArrayList<String> nearMisses(String query){

        query = query.toLowerCase();

        ArrayList<String> misses = new ArrayList<String>();

        for(int i = 0; i < query.length(); i++){

            //Deletion 
            String del = query.substring(0, i) + query.substring(i+1, query.length());
            if(containsWord(del) && !misses.contains(del)) {
                misses.add(del);
            }
        
            //Replace
            for(char letter = 'a'; letter <= 'z'; letter++){
                String replace = query.substring(0, i) + letter + query.substring(i+1, query.length());
                if(containsWord(replace) && !misses.contains(replace)){
                    misses.add(replace);
                }
            }

            //Swapping Characters
            char a = query.charAt(i);
            char b = query.charAt(i + 1); 
            String c;
            if(i == query.length() - 2) {
                c = query.substring(0, i) + b + a;
            }
            else {
                c = query.substring(0, i) + b + a + query.substring(i + 2, query.length());
            }
            if(containsWord(c) && !misses.contains(c)){
                misses.add(c);
            }

            //Splits
            String begin = query.substring(0, i);
            String end = query.substring(i+1, query.length());
            String word = begin + "" + end;

            if(containsWord(begin) && containsWord(end) && !misses.contains(word)){
                misses.add(word);
            }

            //Insertion
            for(char letter = 'a'; letter <= 'z'; letter++){
                String insert = query.substring(0, i) + letter + query.substring(i, query.length());
                if(containsWord(insert) && !misses.contains(insert)){
                    misses.add(insert);
                }
            }
        }

        return misses;
    }

    public static void main(String[] args) {

        WordValidation dictionary = new WordValidation();

        dictionary.createSet("words.txt");

        //Tests

        //Deletions
        if(!dictionary.nearMisses("catte").contains("cattle")) {
            System.out.println(dictionary.nearMisses("catte"));
            throw new AssertionError("Failed deletion test");
        }

        //Replace 
        if(!dictionary.nearMisses("catble").contains("cattle")) {
            System.out.println(dictionary.nearMisses("catble"));
            throw new AssertionError("Failed replacement test");
        }

        //Swaps
        if(!dictionary.nearMisses("acttle").contains("cattle")) {
            System.out.println(dictionary.nearMisses("acttle"));
            throw new AssertionError("Failed swap test");
        }

        //Splits
        if(!dictionary.nearMisses("cattell").contains("cattle")) {
            System.out.println(dictionary.nearMisses("cattell"));
            throw new AssertionError("Failed split test");
        }

        //Insertion
        if(!dictionary.nearMisses("cattlle").contains("cattle")) {
            System.out.println(dictionary.nearMisses("cattlle"));
            throw new AssertionError("Failed insertion test");
        }
        
    }
    
}