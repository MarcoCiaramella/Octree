package com.ds.octree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ds.octreelib.Octree;
import com.outofbound.meshloaderlib.ply.Ply;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OctreeTest {

    @Test
    public void constructor_isCorrect() {
        long index = newOctree().getIndex();
        assertNotEquals(0, index);
    }

    private Octree newOctree(){
        double[] vertices = {2,-3,5.2,3.4,6,8.2,5,1,3,3,4,1};
        int[] triangles = {1,2,3,2,3,4};
        return new Octree(4, vertices,
                0, null,
                2, triangles,
                0, null,
                0, 1);
    }

    private Octree newOctree(Context context, String filename){
        Ply ply = new Ply(context, filename);
        ply.load();
        double[] vertices = new double[ply.getVertices().length];
        for (int i = 0; i < vertices.length; i++){
            vertices[i] = ply.getVertices()[i];
        }
        return new Octree(vertices.length/3, vertices,
                0, null,
                2, ply.getIndices(),
                0, null,
                0, 1);
    }

    @Test
    public void constructorFromFile_isCorrect() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        long index = newOctree(context, "monkey.ply").getIndex();
        assertNotEquals(0, index);
    }

    private double[] minCoord(float[] vertices){
        double[] min = new double[3];
        min[0] = vertices[0];
        min[1] = vertices[1];
        min[2] = vertices[2];
        for (int i = 0; i < vertices.length; i += 3) {
            min[0] = Math.min(vertices[i], min[0]);
            min[1] = Math.min(vertices[i+1], min[1]);
            min[2] = Math.min(vertices[i+2], min[2]);
        }
        return min;
    }

    private double[] maxCoord(float[] vertices){
        double[] max = new double[3];
        max[0] = vertices[0];
        max[1] = vertices[1];
        max[2] = vertices[2];
        for (int i = 0; i < vertices.length; i += 3) {
            max[0] = Math.max(vertices[i], max[0]);
            max[1] = Math.max(vertices[i+1], max[1]);
            max[2] = Math.max(vertices[i+2], max[2]);
        }
        return max;
    }

    @Test
    public void getWithinBoundingBox1_isCorrect() {
        int[] itm = new int[10];
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Ply ply = new Ply(context, "cube_inters.ply");
        ply.load();
        int n = newOctree(context, "monkey.ply").getWithinBoundingBox(Octree.VER, itm, minCoord(ply.getVertices()), maxCoord(ply.getVertices()), 0);
        assertTrue("n = "+n, n > 0);
    }

    @Test
    public void getWithinBoundingBox2_isCorrect() {
        int[] itm = new int[10];
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Ply ply = new Ply(context, "cube_not_inters.ply");
        ply.load();
        int n = newOctree(context, "monkey.ply").getWithinBoundingBox(Octree.VER, itm, minCoord(ply.getVertices()), maxCoord(ply.getVertices()), 0);
        assertEquals(0, n);
    }

    @Test
    public void free_isCorrect() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        newOctree(context, "monkey.ply").free();
    }
}