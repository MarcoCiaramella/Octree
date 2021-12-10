package com.ds.octreelib;

import static org.junit.Assert.assertNotEquals;


import androidx.test.ext.junit.runners.AndroidJUnit4;

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
    public void newOctree_isCorrect() {
        double[][] vertices = {{2,-3,5.2}, {3.4,6,8.2}, {5,1,3}, {3,4,1}};
        int[][] triangles = {{1,2,3}, {2,3,4}};
        Octree octree = new Octree();
        long index = octree.newOctree(4, vertices[0], vertices[1],
                0, null, null,
                2, triangles[0], triangles[1],
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, 1);
        assertNotEquals(0, index);
    }
}