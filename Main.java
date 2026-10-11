public class Main {
    static int passed = 0, failed = 0;

    static void check(String name, boolean ok) {
        if (ok) { passed++; System.out.println("[PASS] " + name); }
        else    { failed++; System.out.println("[FAIL] " + name); }
    }

    static void checkEq(String name, String actual, String expected) {
        boolean ok = actual.equals(expected);
        if (ok) { passed++; System.out.println("[PASS] " + name + " -> " + actual); }
        else    { failed++; System.out.println("[FAIL] " + name + " -> got " + actual + ", expected " + expected); }
    }

    static boolean throwsOnEmpty(Runnable r) {
        try { r.run(); } catch (RuntimeException e) { return true; }
        return false;
    }

    // simple pseudo-random generator (no java.util needed)
    static int seed = 12345;
    static int nextRandom(int bound) {
        seed = seed * 1103515245 + 12345;
        return ((seed >>> 16) & 0x7fff) % bound;
    }

    public static void main(String[] args) {
        testIntArray();
        testIntArrayStack();
        testIntLinkedList();
        testIntLinkedListQueue();
        testDeque("IntArrayDeque", new IntArrayDeque());
        testDeque("IntLinkedListDeque", new IntLinkedListDeque());
        testStack("IntQueueStack", new IntQueueStack());
        testQueue("IntStackQueue", new IntStackQueue());
        testHeap("IntMinHeapTopDown", new IntMinHeapTopDown(), false);
        testHeap("IntMinHeapBottomUp", new IntMinHeapBottomUp(), true);
        testHeapFromArray();

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
        if (failed > 0) System.exit(1);
    }

    // ---------- Problem 1 ----------
    static void testIntArray() {
        System.out.println("== IntArray ==");
        IntArray a = new IntArray();
        checkEq("empty toString", a.toString(), "[]");
        check("empty isEmpty", a.isEmpty() && a.size() == 0);

        a.add(7); a.add(8); a.add(6);
        checkEq("add(value)", a.toString(), "[7, 8, 6]");
        a.add(0, 1);
        a.add(4, 9);
        a.add(2, 5);
        checkEq("add(index,value) front/back/middle", a.toString(), "[1, 7, 5, 8, 6, 9]");
        a.set(0, 100);
        check("set/get", a.get(0) == 100 && a.get(5) == 9);
        check("remove middle", a.remove(2) == 5);
        check("remove first", a.remove(0) == 100);
        check("remove last", a.remove(a.size() - 1) == 9);
        checkEq("after removes", a.toString(), "[7, 8, 6]");
        a.add(new int[]{1, 2, 3});
        checkEq("add(int[])", a.toString(), "[7, 8, 6, 1, 2, 3]");

        IntArray big = new IntArray();
        for (int i = 0; i < 1000; i++) big.add(i);
        boolean ok = big.size() == 1000;
        for (int i = 0; i < 1000; i++) ok &= big.get(i) == i;
        check("growth to 1000 elements", ok);

        check("get out of bounds throws", throwsOnEmpty(() -> new IntArray().get(0)));
        check("remove out of bounds throws", throwsOnEmpty(() -> new IntArray().remove(0)));
    }

    static void testIntArrayStack() {
        System.out.println("== IntArrayStack ==");
        IntArrayStack s = new IntArrayStack();
        checkEq("empty toString", s.toString(), "[]");
        s.push(7); s.push(8); s.push(6);
        checkEq("push", s.toString(), "[7, 8, 6]");
        check("peek", s.peek() == 6 && s.size() == 3);
        check("pop order (LIFO)", s.pop() == 6 && s.pop() == 8 && s.pop() == 7);
        check("isEmpty", s.isEmpty());
        check("pop on empty throws", throwsOnEmpty(() -> new IntArrayStack().pop()));
        check("peek on empty throws", throwsOnEmpty(() -> new IntArrayStack().peek()));
    }

    // ---------- Problem 2 ----------
    static void testIntLinkedList() {
        System.out.println("== IntLinkedList ==");
        IntLinkedList l = new IntLinkedList();
        checkEq("empty toString", l.toString(), "[]");
        l.add(7); l.add(8); l.add(6);
        checkEq("add(value)", l.toString(), "[7, 8, 6]");
        l.add(0, 1);
        l.add(4, 9);
        l.add(2, 5);
        checkEq("add(index,value) front/back/middle", l.toString(), "[1, 7, 5, 8, 6, 9]");
        l.set(3, 80);
        check("get/set", l.get(3) == 80 && l.get(0) == 1 && l.get(5) == 9);
        check("remove middle", l.remove(2) == 5);
        check("remove first", l.remove(0) == 1);
        check("remove last", l.remove(l.size() - 1) == 9);
        checkEq("after removes", l.toString(), "[7, 80, 6]");
        l.remove(0); l.remove(0); l.remove(0);
        check("empty again, head/tail null", l.isEmpty() && l.head == null && l.tail == null);
        l.add(5);
        checkEq("reuse after empty", l.toString(), "[5]");
        check("get out of bounds throws", throwsOnEmpty(() -> new IntLinkedList().get(0)));
    }

    static void testIntLinkedListQueue() {
        testQueue("IntLinkedListQueue", new IntLinkedListQueue());
    }

    // ---------- generic tests ----------
    static void testQueue(String name, IntQueue q) {
        System.out.println("== " + name + " ==");
        checkEq("empty toString", q.toString(), "[]");
        q.enqueue(7); q.enqueue(8); q.enqueue(6);
        checkEq("enqueue", q.toString(), "[7, 8, 6]");
        check("peek/size", q.peek() == 7 && q.size() == 3);
        check("dequeue FIFO", q.dequeue() == 7);
        q.enqueue(1);
        checkEq("mixed enqueue/dequeue", q.toString(), "[8, 6, 1]");
        check("dequeue rest", q.dequeue() == 8 && q.dequeue() == 6 && q.dequeue() == 1);
        check("isEmpty", q.isEmpty());

        boolean ok = true;
        for (int i = 0; i < 500; i++) q.enqueue(i);
        for (int i = 0; i < 500; i++) ok &= q.dequeue() == i;
        check("500 elements keep FIFO order", ok);
        check("dequeue on empty throws", throwsOnEmpty(() -> q.dequeue()));
        check("peek on empty throws", throwsOnEmpty(() -> q.peek()));
    }

    static void testStack(String name, IntStack s) {
        System.out.println("== " + name + " ==");
        checkEq("empty toString", s.toString(), "[]");
        s.push(7); s.push(8); s.push(6);
        checkEq("push", s.toString(), "[7, 8, 6]");
        check("peek/size", s.peek() == 6 && s.size() == 3);
        check("pop LIFO", s.pop() == 6);
        s.push(1);
        checkEq("mixed push/pop", s.toString(), "[7, 8, 1]");
        check("pop rest", s.pop() == 1 && s.pop() == 8 && s.pop() == 7);
        check("isEmpty", s.isEmpty());

        boolean ok = true;
        for (int i = 0; i < 200; i++) s.push(i);
        for (int i = 199; i >= 0; i--) ok &= s.pop() == i;
        check("200 elements keep LIFO order", ok);
        check("pop on empty throws", throwsOnEmpty(() -> s.pop()));
        check("peek on empty throws", throwsOnEmpty(() -> s.peek()));
    }

    // ---------- Problem 3 ----------
    static void testDeque(String name, IntDeque d) {
        System.out.println("== " + name + " ==");
        checkEq("empty toString", d.toString(), "[]");
        d.addLast(7); d.addLast(8); d.addLast(6);
        checkEq("addLast", d.toString(), "[7, 8, 6]");
        d.addFirst(1); d.addFirst(0);
        checkEq("addFirst", d.toString(), "[0, 1, 7, 8, 6]");
        check("peekFirst/peekLast", d.peekFirst() == 0 && d.peekLast() == 6 && d.size() == 5);
        check("removeFirst", d.removeFirst() == 0);
        check("removeLast", d.removeLast() == 6);
        checkEq("after removes", d.toString(), "[1, 7, 8]");
        while (!d.isEmpty()) d.removeLast();
        check("drained", d.isEmpty());

        // many elements on both ends (forces growth and wrap-around)
        for (int i = 0; i < 100; i++) { d.addLast(i); d.addFirst(-i - 1); }
        check("200 elements size", d.size() == 200);
        check("200 elements ends", d.peekFirst() == -100 && d.peekLast() == 99);
        boolean ok = true;
        for (int i = 0; i < 100; i++) { ok &= d.removeFirst() == -100 + i; ok &= d.removeLast() == 99 - i; }
        check("200 elements removed in right order", ok && d.isEmpty());

        // wrap-around stress: sliding window
        for (int i = 0; i < 1000; i++) {
            d.addLast(i);
            if (d.size() > 5) d.removeFirst();
        }
        checkEq("sliding window", d.toString(), "[995, 996, 997, 998, 999]");

        check("removeFirst on empty throws", throwsOnEmpty(() -> new IntArrayDeque().removeFirst()));
        check("peekLast on empty throws", throwsOnEmpty(() -> new IntLinkedListDeque().peekLast()));
    }

    // ---------- Problem 5 ----------
    static void testHeap(String name, IntQueue h, boolean bottomUp) {
        System.out.println("== " + name + " ==");
        checkEq("empty toString", h.toString(), "[]");
        h.enqueue(5); h.enqueue(3); h.enqueue(8); h.enqueue(1); h.enqueue(2);
        check("peek is min", h.peek() == 1 && h.size() == 5);
        checkEq("internal array", h.toString(), "[1, 2, 8, 5, 3]");
        check("dequeue ascending", h.dequeue() == 1 && h.dequeue() == 2 && h.dequeue() == 3
                && h.dequeue() == 5 && h.dequeue() == 8);
        check("isEmpty", h.isEmpty());

        boolean ok = true;
        for (int i = 0; i < 1000; i++) h.enqueue(nextRandom(500));
        int prev = -1;
        while (!h.isEmpty()) { int m = h.dequeue(); ok &= m >= prev; prev = m; }
        check("1000 random values come out sorted", ok);
        check("dequeue on empty throws", throwsOnEmpty(() -> h.dequeue()));
        check("peek on empty throws", throwsOnEmpty(() -> h.peek()));
    }

    static void testHeapFromArray() {
        System.out.println("== Heap constructors from int[] ==");
        int[] v = {9, 4, 7, 1, 8, 2, 6, 3, 5};
        IntMinHeapTopDown td = new IntMinHeapTopDown(v);
        IntMinHeapBottomUp bu = new IntMinHeapBottomUp(v);
        System.out.println("top-down array:  " + td);
        System.out.println("bottom-up array: " + bu);
        check("input array is not modified", v[0] == 9 && v[3] == 1);
        check("both sizes", td.size() == 9 && bu.size() == 9);
        check("both peek min", td.peek() == 1 && bu.peek() == 1);

        int[] big = new int[2000];
        for (int i = 0; i < big.length; i++) big[i] = nextRandom(1000);
        IntMinHeapTopDown td2 = new IntMinHeapTopDown(big);
        IntMinHeapBottomUp bu2 = new IntMinHeapBottomUp(big);
        boolean ok = true;
        int p1 = -1, p2 = -1;
        while (!td2.isEmpty()) {
            int a = td2.dequeue(), b = bu2.dequeue();
            ok &= a >= p1 && b >= p2 && a == b;
            p1 = a; p2 = b;
        }
        check("2000 values: both heaps sort identically", ok && bu2.isEmpty());

        IntMinHeapBottomUp empty = new IntMinHeapBottomUp(new int[0]);
        check("empty array constructor", empty.isEmpty());
    }
}
