package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;


    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    public int get(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(overallRoot, key, 0);
        if (x == null) {
            return 0;
        }
        return x.data;
    }

    public Node get(Node x, CharSequence key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if (c < x.data) {
            return get(x.left,  key, d);
        } else if (c > x.data) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public boolean contains(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != 0;
    }
    public void put(CharSequence key) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) {
            overallRoot = put(overallRoot, key, 0);
        }
        /*if (get(key) == 0) {
            overallRoot = put(overallRoot, key, 0);
        }*/
    }

    public Node put(Node x, CharSequence key, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node(c);
        }
        if (c < x.data) {
            x.left = put(x.left, key, d);
        } else if (c > x.data) {
            x.right = put(x.right, key, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, d + 1);
        } else if (d == key.length() - 1) {
            x.isTerm = true;
        }
        return x;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence nodes: terms) {
            put(nodes);
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matched = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return  null;
        }
        Node x = get(overallRoot, prefix, 0);
        if (x == null) {
            return null;
        } else {
            if (x.isTerm) {
                matched.add(prefix);
            }
            if (x.mid != null) {
                collect(x.mid, prefix, matched);
            }
        }
        return matched;
    }

    public void collect(Node x, CharSequence prefix, List<CharSequence> matched) {
        if (x == null) return;
        collect(x.left, prefix, matched);
        if (x.isTerm) {
            matched.add(prefix);
        }
        collect(x.left, prefix, matched);
        collect(x.mid, prefix, matched);
        collect(x.right, prefix, matched);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}

