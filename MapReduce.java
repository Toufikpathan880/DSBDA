import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class MapReduce {
    public static class Mapper {
        public List<Map<String, Integer>> map(String input) {
            List<Map<String, Integer>> wordCountList = new ArrayList<>();
            Map<String, Integer> wordCountMap = new HashMap<>();
            String[] words = input.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    word = word.toLowerCase();
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
            wordCountList.add(wordCountMap);
            return wordCountList;
        }
    }

    public static class Reducer {
        public Map<String, Integer> reduce(List<Map<String, Integer>> mappedResults) {
            Map<String, Integer> finalCountMap = new HashMap<>();
            for (Map<String, Integer> map : mappedResults) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    finalCountMap.put(entry.getKey(), finalCountMap.getOrDefault(entry.getKey(), 0) +
                            entry.getValue());
                }
            }
            return finalCountMap;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        String inputText = "Hello world hello mapreduce hello Java world";
        Mapper mapper = new Mapper();
        List<Map<String, Integer>> mappedResults = mapper.map(inputText);
        Reducer reducer = new Reducer();
        Map<String, Integer> finalWordCount = reducer.reduce(mappedResults);
        System.out.println("Word Count Results:");
        for (Map.Entry<String, Integer> entry : finalWordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}