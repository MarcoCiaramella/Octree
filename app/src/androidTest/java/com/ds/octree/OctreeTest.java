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

    private Context getContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    private Octree newOctree(){
        double[] vertices = {2,-3,5.2,3.4,6,8.2,5,1,3,3,4,1};
        int[] triangles = {1,2,3,2,3,4};
        return new Octree(vertices.length/3, vertices,
                0, null,
                triangles.length/3, triangles,
                0, null,
                0, 1);
    }

    private Octree newOctree(String filename){
        Ply ply = new Ply(getContext(), filename);
        ply.load();
        double[] vertices = new double[ply.getVertices().length];
        for (int i = 0; i < vertices.length; i++){
            vertices[i] = ply.getVertices()[i];
        }
        return new Octree(vertices.length/3, vertices,
                0, null,
                ply.getIndices().length/3, ply.getIndices(),
                0, null,
                0, 1);
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
    public void constructor_isCorrect() {
        assertNotEquals(0, newOctree().getIndex());
    }

    @Test
    public void constructorFromFile_isCorrect() {
        assertNotEquals(0, newOctree("monkey.ply").getIndex());
    }

    @Test
    public void getWithinBoundingBox_isCorrect() {
        int[] itm = new int[10];
        Ply cubeInters = new Ply(getContext(), "cube_inters.ply");
        cubeInters.load();
        Ply cubeNotInters = new Ply(getContext(), "cube_not_inters.ply");
        cubeNotInters.load();
        int n;
        Octree octree = newOctree("monkey.ply");



        n = octree.getWithinBoundingBox(Octree.VER, itm, minCoord(cubeInters.getVertices()), maxCoord(cubeInters.getVertices()), 0);
        assertTrue("n = "+n, n > 0);

        n = octree.getWithinBoundingBox(Octree.TRI, itm, minCoord(cubeInters.getVertices()), maxCoord(cubeInters.getVertices()), 0);
        assertTrue("n = "+n, n > 0);



        n = octree.getWithinBoundingBox(Octree.VER, itm, minCoord(cubeNotInters.getVertices()), maxCoord(cubeNotInters.getVertices()), 0);
        assertEquals(0, n);

        n = octree.getWithinBoundingBox(Octree.TRI, itm, minCoord(cubeNotInters.getVertices()), maxCoord(cubeNotInters.getVertices()), 0);
        assertEquals(0, n);
    }

    @Test
    public void free_isCorrect() {
        newOctree("monkey.ply").free();
    }

    @Test
    public void getNearest_isCorrect() {
        double[] projection = new double[3];
        Octree octree = newOctree("monkey.ply");
        int index;

        index = octree.getNearest(Octree.VER, new double[]{0,0,0}, projection, 0, 0);
        assertTrue("index = "+index, index != 0);

        index = octree.getNearest(Octree.TRI, new double[]{0,0,0}, projection, 0, 0);
        assertTrue("index = "+index, index != 0);
    }

    @Test
    public void projectVertex_isCorrect() {
        double[] crd = new double[3];
        Octree octree = newOctree("monkey.ply");
        int result;

        result = octree.projectVertex(new double[]{0,0,0}, Octree.VER, 5, crd, 0);
        assertEquals("result = " + result, 1, result);

        result = octree.projectVertex(new double[]{0,0,0}, Octree.TRI, 5, crd, 0);
        assertTrue("result = "+result, result == 3 || result == 1);
    }

    @Test
    public void isInside_isCorrect(){
        Octree octree = newOctree("monkey.ply");
        int intersections;

        intersections = octree.isInside(new double[]{0,0,0}, new double[]{0,1,0}, 0);
        assertTrue("intersections = "+intersections, intersections > 0);

        intersections = octree.isInside(new double[]{1000,0,0}, new double[]{0,1,0}, 0);
        assertEquals("intersections = " + intersections, 0, intersections);
    }

    @Test
    public void getIntersectedSurface_isCorrect(){
        double[] intersection = new double[3];
        int index = newOctree("monkey.ply").getIntersectedSurface(new double[]{0,0,0}, new double[]{0,1,0}, intersection, 0, 0);
        assertNotEquals(0, index);
    }
}