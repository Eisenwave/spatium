package net.grian.spatium.geo2;

import net.grian.spatium.geo3.Vectors;

public interface Polygon2 {
    
    /**
     * Returns the amount of vertices (and edges) this polygon has.
     *
     * @return the amount of vertices
     */
    abstract int getVertexCount();
    
    /**
     * Returns the vertex with given index.
     *
     * @param index the index
     * @return the corresponding vertex
     * @throws IndexOutOfBoundsException if the index is negative or >= vertex count
     */
    abstract Vector2 getVertex(int index);
    
    /**
     * Returns the edge with given index.
     *
     * @param index the index
     * @return the corresponding vertex
     * @throws IndexOutOfBoundsException if the index is negative or >= vertex count
     */
    default Ray2 getEdge(int index) {
        final Vector2
            a = getVertex(index),
            b = getVertex( (index+1)%getVertexCount() );
        return Ray2.between(a, b);
    }
    
    default Vector2[] getVertices() {
        int lim = getVertexCount();
        Vector2[] vertices = new Vector2[lim];
        
        for (int i = 0; i < lim; i++)
            vertices[i] = getVertex(i);
        
        return vertices;
    }
    
    /**
     * Returns the rectangular boundaries of this polygon.
     *
     * @return the boundaries of this polygon
     */
    default Rectangle getBoundaries() {
        Vector2[] minMax = Vectors.minMax(getVertices());
        return Rectangle.between(minMax[0], minMax[1]);
    }
    
}
