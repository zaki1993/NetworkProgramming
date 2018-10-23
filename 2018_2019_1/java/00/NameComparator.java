import java.util.Comparator;

public class NameComparator<T extends Name> implements Comparator<T> {
    @Override
    public int compare(T n1, T n2) {
        int lastCmp = n1.getLastName().compareTo(n2.getLastName());
        return (lastCmp != 0 ? lastCmp :n1.getFirstName().compareTo(n2.getFirstName()));
    }
}
