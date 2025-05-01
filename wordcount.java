public class wordcount {
    public static void main(String[] args) {
        String text = "this is a wordcount example.";
        int wordCount = countWords(text);
        System.out.println("Word count: " + wordCount);
    }

    public static int countWords(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }
}