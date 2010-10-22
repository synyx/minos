package org.synyx.minos.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * List of {@link Appointment}s. Allows more sophisticated queries to the list's elements than a basic {@link List}
 * implementation would allow. E.g. {@code AppointmentList} allows detection of overlaps on the {@link Appointment}.
 * <p>
 * This class does <em>not</em> the {@link List} interface as this would expose a too technical API. Nevetheless we
 * expose the most relevant API of the underlying {@link List}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class OverlapableList<T extends Overlapable<S>, S> implements Iterable<T> {

    protected List<T> overlapables;


    /**
     * Creates a new {@code OverlapableList}.
     */
    public OverlapableList() {

        this.overlapables = new ArrayList<T>();
    }


    /**
     * Creates a new {@code OverlapableList} with the given list of {@link Overlapable}.
     * 
     * @param overlapable
     */
    public OverlapableList(List<T> overlapable) {

        this.overlapables = overlapable;
    }


    public static <S, T extends Overlapable<S>> OverlapableList<T, S> create() {

        return new OverlapableList<T, S>();
    }


    /**
     * Adds an {@link Overlapable} to the list.
     * 
     * @param overlapable
     * @return
     */
    public boolean add(T overlapable) {

        return this.overlapables.add(overlapable);
    }


    /**
     * Returns all overlaps from the list that overlap the given {@code Overlapable}. If the given {@code Overlapable}
     * is from the list, the overlap with itself will not be considered.
     * 
     * @param overlapable
     * @return
     */
    public OverlapableList<T, S> getOverlapsFor(T overlapable) {

        List<T> result = new ArrayList<T>();

        for (T element : this.overlapables) {

            boolean sameInstance = element == overlapable;

            if (!sameInstance && overlapable.overlaps(element)) {
                result.add(element);
            }
        }

        return new OverlapableList<T, S>(result);
    }


    /**
     * Returns whether at least one of the {@link Overlapable}s in the list overlaps another.
     * 
     * @return
     */
    public boolean hasOverlaps() {

        Set<T> ref = new HashSet<T>();
        ref.addAll(overlapables);

        for (T appointment : overlapables) {

            ref.remove(appointment);

            for (T toCompare : ref) {
                if (appointment.overlaps(toCompare)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Returns the size of the list.
     * 
     * @see List#size()
     * @return
     */
    public int size() {

        return this.overlapables.size();
    }


    /**
     * Returns the element at the index position.
     * 
     * @see List#get(int)
     * @param index
     * @return
     */
    public T get(int index) {

        return this.overlapables.get(index);
    }


    /**
     * Returns whether the list contains the given {@link Overlapable}.
     * 
     * @see List#contains(Object)
     * @param overlapables
     * @return
     */
    public boolean contains(T... overlapables) {

        return this.overlapables.containsAll(Arrays.asList(overlapables));
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<T> iterator() {

        return overlapables.iterator();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return overlapables.toString();
    }
}
