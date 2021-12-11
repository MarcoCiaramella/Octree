package com.ds.octree;

import static org.junit.Assert.assertNotEquals;


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
        double[][] vertices = {{2,-3,5.2}, {3.4,6,8.2}, {5,1,3}, {3,4,1}};
        int[][] triangles = {{1,2,3}, {2,3,4}};
        long index = new Octree(4, vertices[0], vertices[1],
                0, null, null,
                2, triangles[0], triangles[1],
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, 1).getIndex();
        assertNotEquals(0, index);
    }

    private double[][] toArrOfArr(float[] arr, int groupSize){
        double[][] arrOfArr = new double[arr.length / groupSize][];
        for (int i = 0; i < arrOfArr.length; i++){
            arrOfArr[i] = new double[groupSize];
            for (int j = 0; j < arrOfArr[i].length; j++){
                arrOfArr[i][j] = arr[i*groupSize + j];
            }
        }
        return arrOfArr;
    }

    private int[][] toArrOfArr(int[] arr, int groupSize){
        int[][] arrOfArr = new int[arr.length / groupSize][];
        for (int i = 0; i < arrOfArr.length; i++){
            arrOfArr[i] = new int[groupSize];
            for (int j = 0; j < arrOfArr[i].length; j++){
                arrOfArr[i][j] = arr[i*groupSize + j];
            }
        }
        return arrOfArr;
    }

    @Test
    public void constructorFromFile_isCorrect() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Ply ply = new Ply(appContext, "monkey.ply");
        ply.load();
        double[][] vertices = toArrOfArr(ply.getVertices(), 3);
        int[][] triangles = toArrOfArr(ply.getIndices(), 3);
        long index = new Octree(vertices.length, vertices[0], vertices[1],
                0, null, null,
                2, triangles[0], triangles[1],
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, null, null,
                0, 1).getIndex();
        assertNotEquals(0, index);
    }
}