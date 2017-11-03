package net.grian.spatium.matrix;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.geo2.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatricesTest {
    
    @Test
    public void eigenvalues2() {
        {
            Matrix matrix = Matrix.identity(2);
        
            double[] lambda = Matrices.eigenvalues2(matrix);
            assertEquals(1, lambda[0], Spatium.EPSILON);
            assertEquals(1, lambda[1], Spatium.EPSILON);
        }
        {
            Matrix matrix = Matrix.create(2, 2);
        
            double[] lambda = Matrices.eigenvalues2(matrix);
            assertEquals(0, lambda[0], Spatium.EPSILON);
            assertEquals(0, lambda[1], Spatium.EPSILON);
        }
        {
            Matrix matrix = Matrix.create(2, 2,
                1, 2,
                3, 4);
    
            double[] lambda = Matrices.eigenvalues2(matrix);
            assertEquals((5+Math.sqrt(33))/2, lambda[0], Spatium.EPSILON);
            assertEquals((5-Math.sqrt(33))/2, lambda[1], Spatium.EPSILON);
        }
    }
    
    @Test
    public void eigenvectors2() {
        {
            Matrix matrix = Matrix.identity(2);
        
            Vector2[] actual = Matrices.eigenvectors2(matrix);
            Vector2[] expected = {
                Vector2.fromXY(1, 0),
                Vector2.fromXY(0, 1)
            };
            
            for (int i = 0; i < expected.length; i++)
                if (!actual[i].isMultipleOf(expected[i]))
                    throw new AssertionError("expected "+expected[i]+" != actual "+actual[i]);
        }
        {
            Matrix matrix = Matrix.create(2, 2);
    
            Vector2[] actual = Matrices.eigenvectors2(matrix);
            Vector2[] expected = {
                Vector2.fromXY(1, 0),
                Vector2.fromXY(0, 1)
            };
    
            for (int i = 0; i < expected.length; i++)
                if (!actual[i].isMultipleOf(expected[i]))
                    throw new AssertionError("expected "+expected[i]+" != actual "+actual[i]);
        }
        {
            Matrix matrix = Matrix.create(2, 2,
                1, 2,
                3, 4);
    
            Vector2[] actual = Matrices.eigenvectors2(matrix);
            Vector2[] expected = {
                Vector2.fromXY((-3+Math.sqrt(33))/6, 1),
                Vector2.fromXY((-3-Math.sqrt(33))/6, 1)
            };
            
            for (int i = 0; i < expected.length; i++)
                if (!actual[i].isMultipleOf(expected[i]))
                    throw new AssertionError("expected "+expected[i]+" != actual "+actual[i]);
        }
    }
    
    
}