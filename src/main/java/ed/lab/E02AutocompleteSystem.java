package ed.lab;
import java.util.*;

public class E02AutocompleteSystem {
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isLast = false;
        }

        TrieNode root;
        StringBuilder SB = new StringBuilder();
        Map<String, Integer> frequency = new HashMap<>();
        TrieNode current;

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();


        for(int i = 0; i < sentences.length; i++) {
            frequency.put(sentences[i], times[i]);
            add(sentences[i]);
        }

        current = root;
    }


    public void add(String words) {
        TrieNode current = root;

        List<TrieNode> wordvisited = new ArrayList<>();
        for(char c : words.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isLast = true;
    }

    public List<String> input(char c) {

        if(c == '#') {
            String Sentence = SB.toString();
            frequency.put(Sentence, frequency.getOrDefault(Sentence, 0) + 1);
            add(Sentence);
            SB.setLength(0);
            current = root;
            return new ArrayList<>();
        }

        SB.append(c);
        if(current != null) {
            current = current.children.get(c);
        }

        if(current == null) {
            return new ArrayList<>();
        }

        List<String> matches = new ArrayList<>();
        dfs(current, SB.toString(), matches);

        PriorityQueue<String> heap = new PriorityQueue<>((a,b) -> frequency.get(a).equals(frequency.get(b)) ? a.compareTo(b) : frequency.get(b) - frequency.get(a));

        for(String match : matches) {
            heap.offer(match);
        }

        List<String> result = new ArrayList<>();
        for(int i = 0; i < 3 && !heap.isEmpty(); i++) {
            result.add(heap.poll());
        }
        return result;
    }

    private void dfs(TrieNode node, String sentence, List<String> result) {
        if(node.isLast && frequency.containsKey(sentence)) {
            result.add(sentence);
        }

        for(Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            dfs(entry.getValue(), sentence + entry.getKey(), result);
        }
    }
}
