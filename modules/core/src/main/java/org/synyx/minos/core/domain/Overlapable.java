package org.synyx.minos.core.domain;

/**
 * Basic interface for overlapable entities. Implementing this interface allows classes to be added to
 * {@link OverlapableList}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface Overlapable<T> {

    /**
     * Returns the starting point of the overlapable.
     * 
     * @return
     */
    public T getStart();


    /**
     * Returns the end point of the overlapable.
     * 
     * @return
     */
    public T getEnd();


    /** 
     * Returns, if an {@code Overlapable} overlaps another. Overlapping {@code null} has to return false.
     * 
     * @param appointment
     * @return
     */
    boolean overlaps(Overlapable<T> that);
}