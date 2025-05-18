package ed.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class E01TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> frequent = new HashMap<>();

            for(int num : nums) {
                frequent.putIfAbsent(num, 1);
                frequent.put(num, frequent.get(num) + 1);
            }

            Queue<Integer> heap = new PriorityQueue<Integer>((a, b) -> frequent.get(b) - frequent.get(a));
            heap.addAll(frequent.keySet());

            int[] result = new int[k];

            for(int i = k-1; i >= 0; i--) {
                result[i] = heap.poll();
            }
            return result;
    }
}
