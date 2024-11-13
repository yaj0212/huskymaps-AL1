package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.elements.addAll(terms);
        Collections.sort(elements, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matched = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return matched;
        }
        int i = Collections.binarySearch(this.elements, prefix, CharSequence::compare);
        if (i < 0) {
            i = -(i + 1);
        }
        for (int j = i; j < this.elements.size(); j++){
            if (Autocomplete.isPrefixOf(prefix, this.elements.get(j))) {
                matched.add(this.elements.get(j));
            }
        }
        return matched;
    }
}
