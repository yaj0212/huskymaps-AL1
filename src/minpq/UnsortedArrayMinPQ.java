package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
        PriorityNode<E> contains;
        for (int i = 0; i < elements.size(); i++) {
            contains = elements.get(i);
            if (contains.element().equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = elements.get(0).priority();
        int k = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (minPriority > elements.get(i).priority()) {
                minPriority = elements.get(i).priority();
                k = i;
            }
        }
        return elements.get(k).element();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = elements.get(0).priority();
        int k = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (minPriority > elements.get(i).priority()) {
                minPriority = elements.get(i).priority();
                k = i;
            }
        }
        E min = elements.get(k).element();
        elements.remove(k);
        return min;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        PriorityNode<E> temp= new PriorityNode<>(element, priority);
        for (int i = 0; i < elements.size(); i++) {
            if (temp.element().equals(element)) {
                elements.get(i).setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return elements.size();
    }
}
