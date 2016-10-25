package net.grian.spatium.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.grian.spatium.coll.Collideable;
import net.grian.spatium.coll.Collisions;
import net.grian.spatium.coll.RayCollision;
import net.grian.spatium.coll.Universe;
import net.grian.spatium.geo.Ray;

public class UniverseImpl<T> implements Universe<T> {
	
	private final Class<T> handleType;
	private final Map<Collideable, T> content = new HashMap<>();
	
	public UniverseImpl(Class<T> handleType) {
		this.handleType = handleType;
	}

	@Override
	public Class<T> getHandleType() {
		return handleType;
	}
	
	@Override
	public Set<Collideable> getContent() {
		return content.keySet();
	}

	@Override
	public List<RayCollision<T>> castRay(Ray ray) {
		Objects.requireNonNull(ray);
		List<RayCollision<T>> path = new ArrayList<>();
		
		for (Map.Entry<Collideable, T> entry : content.entrySet()) {
			try {
				float t = Collisions.rayCast(ray, entry.getKey());
				path.add( new RayCollision<T>(entry.getKey(), entry.getValue(), t) );
			} catch (UnsupportedOperationException ex) {
				continue;
			}
		}
		
		Collections.sort(path);
		return path;
	}
	
	
}
