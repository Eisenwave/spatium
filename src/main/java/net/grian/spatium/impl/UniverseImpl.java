package net.grian.spatium.impl;

import net.grian.spatium.coll.Universe;

public class UniverseImpl<T> implements Universe<T> {

    private final Class<T> handleType;

    public UniverseImpl(Class<T> handleType) {
        this.handleType = handleType;
    }

    @Override
    public Class<T> getHandleType() {
        return handleType;
    }

}
