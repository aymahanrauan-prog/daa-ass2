public class IntArrayDeque implements IntDeque {
    // IntArray is used as a circular buffer: its size() is the buffer capacity
    private IntArray array;
    private int head;   // index of the first element
    private int count;  // number of stored elements

    public IntArrayDeque() {
        array = newBuffer(8);
        head = 0;
        count = 0;
    }

    private static IntArray newBuffer(int capacity) {
        IntArray a = new IntArray();
        for (int i = 0; i < capacity; i++) a.add(0);
        return a;
    }

    // O(n), but happens only when full -> amortized O(1)
    private void growIfFull() {
        int cap = array.size();
        if (count < cap) return;
        IntArray bigger = newBuffer(cap * 2);
        for (int i = 0; i < count; i++) bigger.set(i, array.get((head + i) % cap));
        array = bigger;
        head = 0;
    }

    private void checkNotEmpty() {
        if (count == 0) throw new RuntimeException("Deque is empty");
    }

    @Override
    public void addFirst(int value) {
        growIfFull();
        int cap = array.size();
        head = (head - 1 + cap) % cap;
        array.set(head, value);
        count++;
    }

    @Override
    public void addLast(int value) {
        growIfFull();
        array.set((head + count) % array.size(), value);
        count++;
    }

    @Override
    public int removeFirst() {
        checkNotEmpty();
        int v = array.get(head);
        head = (head + 1) % array.size();
        count--;
        return v;
    }

    @Override
    public int removeLast() {
        checkNotEmpty();
        count--;
        return array.get((head + count) % array.size());
    }

    @Override
    public int peekFirst() {
        checkNotEmpty();
        return array.get(head);
    }

    @Override
    public int peekLast() {
        checkNotEmpty();
        return array.get((head + count - 1) % array.size());
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            if (i > 0) sb.append(", ");
            sb.append(array.get((head + i) % array.size()));
        }
        return sb.append("]").toString();
    }
}
