public class IntQueueStack implements IntStack {
    // use two queues as back-end; q1 always holds elements with the top first
    private IntLinkedListQueue q1 = new IntLinkedListQueue();
    private IntLinkedListQueue q2 = new IntLinkedListQueue();

    // O(n): new element goes first, then all the old ones behind it
    @Override
    public void push(int value) {
        q2.enqueue(value);
        while (!q1.isEmpty()) q2.enqueue(q1.dequeue());
        IntLinkedListQueue t = q1;
        q1 = q2;
        q2 = t;
    }

    // O(1)
    @Override
    public int pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return q1.dequeue();
    }

    // O(1)
    @Override
    public int peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return q1.peek();
    }

    @Override
    public int size() {
        return q1.size();
    }

    @Override
    public boolean isEmpty() {
        return q1.isEmpty();
    }

    @Override
    public String toString() {
        // O(n): q1 is top-first, we need bottom-first
        int n = q1.size();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = q1.dequeue();
            q1.enqueue(a[i]);   // rotate, order is restored after n steps
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1) sb.append(", ");
            sb.append(a[i]);
        }
        return sb.append("]").toString();
    }
}
