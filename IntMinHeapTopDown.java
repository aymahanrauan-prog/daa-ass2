public class IntMinHeapTopDown implements IntQueue {
    // use IntArray as back-end for this heap
    private final IntArray heap;

    public IntMinHeapTopDown() {
        heap = new IntArray();
    }

    // top-down: insert elements one by one (sift-up) -> O(n log n)
    public IntMinHeapTopDown(int[] values) {
        heap = new IntArray();
        for (int i = 0; i < values.length; i++) enqueue(values[i]);
    }

    private void swap(int i, int j) {
        int t = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, t);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap.get(p) <= heap.get(i)) break;
            swap(p, i);
            i = p;
        }
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2 * i + 1, r = l + 1, m = i;
            if (l < n && heap.get(l) < heap.get(m)) m = l;
            if (r < n && heap.get(r) < heap.get(m)) m = r;
            if (m == i) break;
            swap(i, m);
            i = m;
        }
    }

    // O(log n)
    @Override
    public void enqueue(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    // O(log n)
    @Override
    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Heap is empty");
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return min;
    }

    // O(1)
    @Override
    public int peek() {
        if (isEmpty()) throw new RuntimeException("Heap is empty");
        return heap.get(0);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
