public class IntLinkedList {
    Node head;
    Node tail;
    int size;

    // internal class that represents a list node.
    static class Node {
        int value;
        Node next;
        Node prev;
        Node() {
            value = 0;
            next = null;
            prev = null;
        }

        Node(int value) {
            this.value = value;
            next = null;
            prev = null;
        }

        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public IntLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // O(n/2) = O(n) in worst case, O(1) for index 0 and size-1
    private Node getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        Node cur;
        if (index < size / 2) {
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
        } else {
            cur = tail;
            for (int i = size - 1; i > index; i--) cur = cur.prev;
        }
        return cur;
    }

    // O(1)
    public void add(int value) {
        Node node = new Node(value, tail, null);
        if (tail == null) head = node;
        else tail.next = node;
        tail = node;
        size++;
    }

    // O(1) at the ends, O(n) in the middle
    public void add(int index, int value) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (index == size) {
            add(value);
            return;
        }
        Node next = getNode(index);
        Node prev = next.prev;
        Node node = new Node(value, prev, next);
        next.prev = node;
        if (prev == null) head = node;
        else prev.next = node;
        size++;
    }

    public int get(int index) {
        return getNode(index).value;
    }

    public void set(int index, int value) {
        getNode(index).value = value;
    }

    // O(1) at the ends, O(n) in the middle
    public int remove(int index) {
        Node node = getNode(index);
        if (node.prev == null) head = node.next;
        else node.prev.next = node.next;
        if (node.next == null) tail = node.prev;
        else node.next.prev = node.prev;
        size--;
        return node.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node cur = head; cur != null; cur = cur.next) {
            if (cur != head) sb.append(", ");
            sb.append(cur.value);
        }
        return sb.append("]").toString();
    }
}
