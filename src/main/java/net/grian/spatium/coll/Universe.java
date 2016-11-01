package net.grian.spatium.coll;

import net.grian.spatium.impl.UniverseImpl;

public interface Universe<T> {

    /**
     * Creates a new universe containing a certain type of handles.
     * @param handleType the type of handles
     * @return a new universe
     * @see #getHandleType()
     */
    public static <T> Universe<T> create(Class<T> handleType) {
        return new UniverseImpl<T>(handleType);
    }

    /**
     * Returns the type of objects contained in this universe. For example,
     * entities, blocks, etc.
     *
     * @return the type of objects in the universe
     */
    public abstract Class<T> getHandleType();

}
