public class IntArrayStack implements IntStack {
    // uses IntArray as back-end
    private final IntArray array;

    public IntArrayStack() {
        array = new IntArray();
    }

    // amortized O(1)
    @Override
    public void push(int value) {
        array.add(value);
    }

    // O(1): removing the last element needs no shifting
    @Override
    public int pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return array.remove(array.size() - 1);
    }

    // O(1)
    @Override
    public int peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return array.get(array.size() - 1);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        return array.toString();
    }
}
