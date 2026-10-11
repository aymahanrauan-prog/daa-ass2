public class IntLinkedListDeque implements IntDeque {
    private final IntLinkedList list;

    public IntLinkedListDeque() {
        list = new IntLinkedList();
    }

    // all operations are O(1): they touch only head or tail
    @Override
    public void addFirst(int value) {
        list.add(0, value);
    }

    @Override
    public void addLast(int value) {
        list.add(value);
    }

    @Override
    public int removeFirst() {
        if (isEmpty()) throw new RuntimeException("Deque is empty");
        return list.remove(0);
    }

    @Override
    public int removeLast() {
        if (isEmpty()) throw new RuntimeException("Deque is empty");
        return list.remove(list.size() - 1);
    }

    @Override
    public int peekFirst() {
        if (isEmpty()) throw new RuntimeException("Deque is empty");
        return list.get(0);
    }

    @Override
    public int peekLast() {
        if (isEmpty()) throw new RuntimeException("Deque is empty");
        return list.get(list.size() - 1);
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
