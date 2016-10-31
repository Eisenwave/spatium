package net.grian.spatium.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import net.grian.spatium.coll.Universe;
import net.grian.spatium.geo.Ray;

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
