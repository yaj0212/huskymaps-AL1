package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.elements.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        ArrayList<CharSequence> matched = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return matched;
        }
        //for(CharSequence match: this.elements) {
        //if (Autocomplete.isPrefixOf(prefix, match)) {
        //matched.add(match);
        //} else {
        //return matched;
        //}
        //}
        for (int i = 0; i < this.elements.size(); i++){
            if (Autocomplete.isPrefixOf(prefix, this.elements.get(i))) {
                matched.add(this.elements.get(i));
            }
        }
        return matched;
    }
}
