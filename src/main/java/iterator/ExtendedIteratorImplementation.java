package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ExtendedIteratorImplementation<T> implements ExtendedIterator<T> {

    private final List<T> elementuak;
    private int posizioa;

    public ExtendedIteratorImplementation(List<T> lista) {
        this.elementuak = new ArrayList<T>(lista);
        this.posizioa = 0;
    }

    @Override
    public boolean hasNext() {
        return posizioa < elementuak.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return elementuak.get(posizioa++);
    }

    @Override
    public boolean hasPrevious() {
        return posizioa > 0;
    }

    @Override
    public T previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }

        return elementuak.get(--posizioa);
    }

    @Override
    public void goFirst() {
        posizioa = 0;
    }

    @Override
    public void goLast() {
        posizioa = elementuak.size();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}