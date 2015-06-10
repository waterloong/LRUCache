import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private static class DoublyLinkedListNode {
        int key, value;
        DoublyLinkedListNode prev, next;
    }

    private int capacity;
    private DoublyLinkedListNode head, tail; // works like a queue, push to tail & pop from head
    private Map<Integer, DoublyLinkedListNode> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    private void append(DoublyLinkedListNode node) {
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

    private void remove(DoublyLinkedListNode node) {
        DoublyLinkedListNode prev = node.prev;
        DoublyLinkedListNode next = node.next;
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
        DoublyLinkedListNode node = map.get(key);
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
            DoublyLinkedListNode node = map.get(key);
            node.value = value;
            remove(node);
            append(node);
        } else {
            DoublyLinkedListNode node = new DoublyLinkedListNode();
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
        // simple test case
        LRUCache l = new LRUCache(2);
        l.set(2, 1);
        l.set(1, 1);
        System.out.println(l.get(2));
        l.set(4, 1);
        System.out.println(l.get(1));
        System.out.println(l.get(2));
    }
}
