package edu.eci.arsw.blueprints.persistence.impl;

import java.util.Objects;

/**
 * A generic tuple class that holds two elements of potentially different types.
 * <p>
 * This class provides a simple way to group two related objects together. It
 * includes methods for accessing the elements, as well as overridden
 * {@code hashCode} and {@code equals} methods for proper comparison and
 * hashing.
 * </p>
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 *
 * @author Diego Cardenas
 */
public class Tuple<T1, T2> {

    T1 o1;
    T2 o2;

    /**
     * Constructs a new Tuple with the specified values.
     *
     * @param o1 the first value of the tuple
     * @param o2 the second value of the tuple
     */
    public Tuple(T1 o1, T2 o2) {
        super();
        this.o1 = o1;
        this.o2 = o2;
    }

    /**
     * Returns the first element of the tuple.
     *
     * @return the first element of type T1
     */
    public T1 getElem1() {
        return o1;
    }

    /**
     * Returns the second element of the tuple.
     *
     * @return the value of the second element (o2) of type T2.
     */
    public T2 getElem2() {
        return o2;
    }

    /**
     * Generates a hash code for this Tuple object based on its two elements, o1
     * and o2. The hash code is computed using a combination of prime numbers
     * and the hash codes of the contained objects to reduce the likelihood of
     * collisions.
     *
     * @return an integer hash code representing this Tuple.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.o1);
        hash = 17 * hash + Objects.hashCode(this.o2);
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The method checks for reference equality, nullity, class type, and then
     * compares the values of the two tuple elements {@code o1} and {@code o2}
     * for equality.
     * </p>
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument;
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.o1, other.o1)) {
            return false;
        }
        if (!Objects.equals(this.o2, other.o2)) {
            return false;
        }
        return true;
    }
}
