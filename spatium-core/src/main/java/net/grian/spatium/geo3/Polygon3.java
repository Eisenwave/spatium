package net.grian.spatium.geo3;

public interface Polygon3 {
    
    /**
     * Returns the vertex with given index.
     *
     * @param index the index
     * @return the corresponding vertex
     * @throws IndexOutOfBoundsException if the index is negative or >= vertex count
     */
    abstract Vector3 getVertex(int index);
    
    /**
     * Returns the amount of vertices (and edges) this polygon has.
     *
     * @return the amount of vertices
     */
    abstract int getVertexCount();
    
    default Vector3[] getVertices() {
        int lim = getVertexCount();
        Vector3[] vertices = new Vector3[lim];
    
        for (int i = 0; i < lim; i++)
            vertices[i] = getVertex(i);
    
        return vertices;
    }
    
    /**
     * Returns the polygon's normal.
     *
     * @return the polygon's normal.
     */
    default Vector3 getNormal() {
        if (getVertexCount() < 3)
            throw new IllegalStateException("polygon3's normal is ambiguous");
        
        Vector3 a = getVertex(0), b = getVertex(1), c = getVertex(2);
        return Vector3.between(a, b).cross(Vector3.between(a, c));
    }
    
    /**
     * Returns the plane in which this polygon lies.
     *
     * @return the plane in which this polygon lies
     */
    default Plane getPlane() {
        return Plane.fromPointNormal(getVertex(0), getNormal());
    }
    
    /**
     * Returns the boundaries of this polygon.
     *
     * @return the boundaries of this polygon
     */
    default AxisAlignedBB getBoundaries() {
        Vector3[] minMax = Vectors.minMax(getVertices());
        
        return AxisAlignedBB.between(minMax[0], minMax[1]);
    }
    
}
