package com.ds.octreelib;

public class Octree {

    static {
        System.loadLibrary("Octree");
    }

    public native long newOctree(int NmbVer, double[] PtrCrd1, double[] PtrCrd2,
                                    int NmbEdg, int[] PtrEdg1, int[] PtrEdg2,
                                    int NmbTri, int[] PtrTri1, int[] PtrTri2,
                                    int NmbQad, int[] PtrQad1, int[] PtrQad2,
                                    int NmbTet, int[] PtrTet1, int[] PtrTet2,
                                    int NmbPyr, int[] PtrPyr1, int[] PtrPyr2,
                                    int NmbPri, int[] PtrPri1, int[] PtrPri2,
                                    int NmbHex, int[] PtrHex1, int[] PtrHex2,
                                    int BasIdx, int NmbThr);
    public native long newOctreeFromSTL(int NmbTri, double[] PtrCrd1, double[] PtrCrd2,
                                           int BasIdx, int NmbThr);
    public native int freeOctree(long OctIdx);
    public native int getWithinBoundingBox(long OctIdx, int typ, int MaxItm, int[] ItmTab,
                                        double[] MinCrd, double[] MaxCrd, int ThrIdx);
    public native int getNearest(long OctIdx, int typ, double[] VerCrd, double[] MinDis, double MaxDis, int ThrIdx);
    public native int getIntersectedSurface(long OctIdx, double[] VerCrd, double[] VerTng, double[] MinDis, double MaxDis, int ThrIdx);
    public native int isInside(long OctIdx, double[] VerCrd, double[] VerTng, int ThrIdx);
    public native int projectVertex(long OctIdx, double[] VerCrd, int typ,
                                       int MinItm, double[] MinCrd, int ThrIdx);
    public native int checkIntersections(long OctIdx, int MaxItm, int[] ItmTab);
}
