public class IntLinkedListQueue implements IntQueue {
    private final IntLinkedList list;

    public IntLinkedListQueue() {
        list = new IntLinkedList();
    }

    // O(1): add to tail
    @Override
    public void enqueue(int value) {
        list.add(value);
    }

    // O(1): remove from head
    @Override
    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.remove(0);
    }

    // O(1)
    @Override
    public int peek() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.get(0);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
