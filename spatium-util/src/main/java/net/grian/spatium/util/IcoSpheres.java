package net.grian.spatium.util;

import net.grian.spatium.cache.CacheMath;

import java.util.ArrayList;
import java.util.List;

public class IcoSpheres {
    
    /**
     * Constructs an icosahedron with given radius.
     *
     * @param r the radius
     * @return a new icosahedron
     */
    private static Mesh icosahedron(double r) {
        final double
            t = CacheMath.GOLDEN * r,
            div = Math.sqrt(t*t + r*r),
            R = r / div,
            T = t / div;
        
        Mesh mesh = new Mesh();
        
        mesh.addVertex(-R,  T, 0);
        mesh.addVertex( R,  T, 0);
        mesh.addVertex(-R, -T, 0);
        mesh.addVertex( R, -T, 0);
        
        mesh.addVertex( 0, -R,  T);
        mesh.addVertex( 0,  R,  T);
        mesh.addVertex( 0, -R, -T);
        mesh.addVertex( 0,  R, -T);
        
        mesh.addVertex( T,  0, -R);
        mesh.addVertex( T,  0,  R);
        mesh.addVertex(-T,  0, -R);
        mesh.addVertex(-T,  0,  R);
        
        mesh.addTriangle(0, 11, 5);
        mesh.addTriangle(0, 5, 1);
        mesh.addTriangle(0, 1, 7);
        mesh.addTriangle(0, 7, 10);
        mesh.addTriangle(0, 10, 11);
        
        mesh.addTriangle(1, 5, 9);
        mesh.addTriangle(5, 11, 4);
        mesh.addTriangle(11, 10, 2);
        mesh.addTriangle(10, 7, 6);
        mesh.addTriangle(7, 1, 8);
        
        mesh.addTriangle(3, 9, 4);
        mesh.addTriangle(3, 4, 2);
        mesh.addTriangle(3, 2, 6);
        mesh.addTriangle(3, 6, 8);
        mesh.addTriangle(3, 8, 9);
        
        mesh.addTriangle(4, 9, 5);
        mesh.addTriangle(2, 4, 11);
        mesh.addTriangle(6, 2, 10);
        mesh.addTriangle(8, 6, 7);
        mesh.addTriangle(9, 8, 1);
        
        return mesh;
    }
    
    /**
     * Refines every triangle of the mesh by "splitting it in a triforce-like pattern".
     *
     * @param mesh the mesh
     */
    private static void refine(Mesh mesh) {
        final int lim = mesh.triangles.size();
        List<IndexTriplet> next = new ArrayList<>();
        
        for (int i = 0; i < lim; i++) {
            IndexTriplet triangle = mesh.triangles.get(i);
        
            final int
                ab = mesh.addMidPoint(triangle.a, triangle.b),
                bc = mesh.addMidPoint(triangle.b, triangle.c),
                ca = mesh.addMidPoint(triangle.c, triangle.a);
        
            mesh.addTriangle(triangle.a, ab, ca);
            mesh.addTriangle(triangle.b, bc, ab);
            mesh.addTriangle(triangle.c, ca, bc);
            mesh.addTriangle(ab, bc, ca);
        }
        
        mesh.triangles = next;
    }
    
    /**
     * Pushes all vertices onto a sphere with given radius.
     *
     * @param mesh the mesh
     * @param r the radius
     */
    private static void blow(Mesh mesh, double r) {
        for (Vertex v : mesh.vertices)
            v.setLength(r);
    }
    
    /**
     * Extracts all unique vertices from the mesh.
     *
     * @param mesh the msh
     * @return all unique vertices
     */
    private static Vertex[] extract(Mesh mesh) {
        Vertex[] vertices = mesh.vertices.toArray(new Vertex[mesh.vertices.size()]);
        int removals = 0;
    
        for (int i = 0; i < vertices.length; i++)
            for (int j = i+1; j < vertices.length; j++)
                if (vertices[i] != null && vertices[j] != null && vertices[i].equals(vertices[j])) {
                    vertices[j] = null;
                    removals++;
                }
        
        Vertex[] result = new Vertex[vertices.length - removals];
        int index = 0;
        for (Vertex v : vertices)
            if (v != null)
                result[index++] = v;
        
        return result;
    }
    
    /**
     * Constructs a new ico-sphere.
     *
     * @param iter the amount of iterations (at least 0, at 0 the sphere is an icosahedron)
     * @param r the radius
     * @return an array containing all sphere vertices
     */
    public static Vertex[] icoSphere(int iter, double r) {
        if (iter < 0)
            throw new IllegalArgumentException("iterations must be positive");
        if (r <= 0)
            throw new IllegalArgumentException("radius must be > 0");
        
        Mesh ico = icosahedron(r);
        
        for (int i = 0; i < iter; i++)
            refine(ico);
        
        blow(ico, r);
        return extract(ico);
    }
    
    private static class Mesh {
        private List<Vertex> vertices = new ArrayList<>();
        private List<IndexTriplet> triangles = new ArrayList<>();
    
        private int addVertex(Vertex v) {
            final int index = vertices.size();
            vertices.add(v);
            return index;
        }
    
        private int addVertex(double x, double y, double z) {
            return addVertex(new Vertex(x, y, z));
        }
        
        private void addTriangle(int a, int b, int c) {
            triangles.add(new IndexTriplet(a, b, c));
        }
    
        private int addMidPoint(int a, int b) {
            return addVertex(vertices.get(a).midPoint(vertices.get(b)));
        }
        
    }
    
    private static class IndexTriplet {
        private final int a, b, c;
        
        public IndexTriplet(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    
    private static class Vertex {
        
        private double x, y, z;
        
        public Vertex(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public double getLength() {
            return Math.sqrt(x*x + y*y + z*z);
        }
        
        public Vertex midPoint(Vertex v) {
            return new Vertex(
                (this.x + v.x) * 0.5,
                (this.y + v.y) * 0.5,
                (this.z + v.z) * 0.5);
        }
        
        public void scale(double t) {
            this.x *= t;
            this.y *= t;
            this.z *= t;
        }
        
        public void setLength(double l) {
            this.scale(l / getLength());
        }
        
    }
    
}
