public class IntStackQueue implements IntQueue {
    // use two stacks as back-end
    private final IntArrayStack in;   // receives new elements
    private final IntArrayStack out;  // top = head of the queue

    public IntStackQueue() {
        in = new IntArrayStack();
        out = new IntArrayStack();
    }

    // move everything from in to out only when out is empty:
    // every element moves at most once -> amortized O(1)
    private void shift() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) out.push(in.pop());
        }
    }

    // O(1)
    @Override
    public void enqueue(int value) {
        in.push(value);
    }

    // amortized O(1)
    @Override
    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        shift();
        return out.pop();
    }

    // amortized O(1)
    @Override
    public int peek() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        shift();
        return out.peek();
    }

    @Override
    public int size() {
        return in.size() + out.size();
    }

    @Override
    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    @Override
    public String toString() {
        // O(n); stacks are restored after reading
        int n = out.size();
        int[] a = new int[n];            // a[0] = top of out = head of queue
        for (int i = 0; i < n; i++) a[i] = out.pop();
        for (int i = n - 1; i >= 0; i--) out.push(a[i]);

        int m = in.size();
        int[] b = new int[m];            // b[0] = most recently enqueued
        for (int i = 0; i < m; i++) b[i] = in.pop();
        for (int i = m - 1; i >= 0; i--) in.push(b[i]);

        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < n; i++) {
            if (!first) sb.append(", ");
            sb.append(a[i]);
            first = false;
        }
        for (int i = m - 1; i >= 0; i--) {
            if (!first) sb.append(", ");
            sb.append(b[i]);
            first = false;
        }
        return sb.append("]").toString();
    }
}
