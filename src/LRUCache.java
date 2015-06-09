import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private static class LinkedListNode {
        int key, value;
        LinkedListNode prev, next;
    }

    private int capacity;
    private LinkedListNode head, tail;
    private Map<Integer, LinkedListNode> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    private void append(LinkedListNode node) {
        if (map.size() == 1) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
        }
    }

    private void remove(LinkedListNode node) {
        LinkedListNode prev = node.prev;
        LinkedListNode next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
    }

    public int get(int key) {
        LinkedListNode node = map.get(key);
        if (node == null) {
            return -1;
        } else {
            remove(node);
            append(node);
            return node.value;
        }
    }

    public void set(int key, int value) {
        if (map.containsKey(key)) {
            LinkedListNode node = map.get(key);
            node.value = value;
            remove(node);
            append(node);
        } else {
            LinkedListNode node = new LinkedListNode();
            node.key = key;
            node.value = value;
            map.put(key, node);
            append(node);
            if (map.size() > capacity) {
                map.remove(head.key);
                remove(head);
            }
        }
    }

    public static void main(String[] args) {
        LRUCache l = new LRUCache(2);
        l.set(2, 1);
        l.set(1, 1);
        System.out.println(l.get(2));
        l.set(4, 1);
        System.out.println(l.get(1));
        System.out.println(l.get(2));
    }
}
