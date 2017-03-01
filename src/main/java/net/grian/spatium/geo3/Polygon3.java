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
    
    abstract int getVertexCount();
    
    abstract Vector3[] getVertices();
    
}
