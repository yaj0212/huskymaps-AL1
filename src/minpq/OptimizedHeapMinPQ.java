package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        elements.add(new PriorityNode<>(null, -1.0));
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<> (element, priority));
        int index = swim(elements.size()-1);
        elementsToIndex.put(element, index);
    }

    private int swim(int k) {
        int parentNode = parent(k);
        while (accessible(parentNode) && (elements.get(k).priority() < elements.get(parentNode).priority())) {
            elementsToIndex.replace(elements.get(parentNode).element(), k);
            elementsToIndex.replace(elements.get(k).element(), parentNode);
            swap(k, parentNode);
            k = parentNode;
            parentNode = parent(k);
        }
        return k;
    }

    private int sink(int k) {
        int child = min(left(k), right(k));
        while(accessible(child) && (elements.get(k).priority() > elements.get(child).priority())) {
            this.elementsToIndex.replace(elements.get(child).element(), k);
            this.elementsToIndex.replace(elements.get(k).element(), child);
            swap(k, child);
            k = child;
            child = min(left(k), right(k));
        }
        return k;
    }

    private static int parent(int k) {
        return k / 2;
    }

    private static int left(int k) {
        return k * 2;
    }

    private static int right(int k) {
        return left(k) + 1;
    }

    private int min(int a, int b) {
        if(!accessible(a) && !accessible(b)) {
            return 0;
        }
        else if (accessible(a) && (!accessible(b)
                || (elements.get(a).priority() < elements.get(b).priority()))) {
            return a;
        }
        else {
            return b;
        }
    }
    private void swap(int a, int b) {
        this.elementsToIndex.replace(elements.get(a).element(), b);
        this.elementsToIndex.replace(elements.get(a).element(), b );
        PriorityNode<E> temp = elements.get(a);
        elements.set(a, elements.get(b));
        elements.set(b, temp);
    }

    private boolean accessible(int k) {
        return 1 <= k && k <= size();
    }
    @Override
    public boolean contains(E element) {
        return elementsToIndex.containsKey(element);
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return elements.get(1).element();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        E minPriority = peekMin();
        swap(0, size()-1);
        elements.remove(size()-1);
        elementsToIndex.remove(elements.get(size()).element());
        sink(0);
        return minPriority;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        int oldPriority = elementsToIndex.get(element);
        elements.get(oldPriority).setPriority(priority);
        int newPriority = sink(oldPriority);
        if(newPriority == oldPriority) {
            newPriority = swim(oldPriority);
        }
        this.elementsToIndex.replace(this.elements.get(newPriority).element(), newPriority);
    }

    @Override
    public int size() {
        return elements.size() - 1;
    }
}
