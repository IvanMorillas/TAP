package MapReduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapReduce {

    /**
     * Applies a word counting function to a text input and returns a map of words and their corresponding counts.
     */
    public static Function<Object, Object> wordCount = (Object text) -> {
        String inputText = (String) text;
        Map<String, Integer> wordCounts = new HashMap<>();
        String[] words = inputText.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }
        return wordCounts;
    };





    /**
     * Counts the number of words in a text input.
     */
    public static Function<Object, Object> countWords = (Object input) -> {
        String stringText = (String) input;
        if (stringText == null || stringText.isEmpty()) {
            return 0;
        }
        return stringText.split("\\s+").length;
    };

    /**
     * Reads the contents of a file and returns the entire text as a single string.
     *
     * @param title The title of the file to read
     * @return The contents of the file as a single string
     * @throws IOException If an I/O error occurs while reading the file
     */
    public static String leer(String title) throws IOException {
        File file = new File(title);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder content = new StringBuilder();
        while((line = bufferedReader.readLine()) != null){
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }
}
