public class IntArray {
    private int[] data;
    private int size;

    // O(1)
    public IntArray() {
        data = new int[4];
        size = 0;
    }

    // doubling the capacity -> amortized O(1) per add
    private void ensureCapacity() {
        if (size == data.length) {
            int[] bigger = new int[data.length * 2];
            for (int i = 0; i < size; i++) bigger[i] = data[i];
            data = bigger;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    // amortized O(1)
    public void add(int value) {
        ensureCapacity();
        data[size++] = value;
    }

    // O(n): shifts elements to the right
    public void add(int index, int value) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        ensureCapacity();
        for (int i = size; i > index; i--) data[i] = data[i - 1];
        data[index] = value;
        size++;
    }

    // O(1)
    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    // O(1)
    public void set(int index, int value) {
        checkIndex(index);
        data[index] = value;
    }

    // O(n): shifts elements to the left (O(1) when removing the last one)
    public int remove(int index) {
        checkIndex(index);
        int removed = data[index];
        for (int i = index; i < size - 1; i++) data[i] = data[i + 1];
        size--;
        return removed;
    }

    // O(1)
    public int size() {
        return size;
    }

    // O(1)
    public boolean isEmpty() {
        return size == 0;
    }

    // amortized O(m), m = values.length
    public void add(int[] values) {
        for (int i = 0; i < values.length; i++) add(values[i]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }
}
