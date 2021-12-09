package com.ds.octreelib;

public class Octree {

    static {
        System.loadLibrary("Octree");
    }

    public native long LolNewOctree(int NmbVer, double[] PtrCrd1, double[] PtrCrd2,
                                    int NmbEdg, int[] PtrEdg1, int[] PtrEdg2,
                                    int NmbTri, int[] PtrTri1, int[] PtrTri2,
                                    int NmbQad, int[] PtrQad1, int[] PtrQad2,
                                    int NmbTet, int[] PtrTet1, int[] PtrTet2,
                                    int NmbPyr, int[] PtrPyr1, int[] PtrPyr2,
                                    int NmbPri, int[] PtrPri1, int[] PtrPri2,
                                    int NmbHex, int[] PtrHex1, int[] PtrHex2,
                                    int BasIdx, int NmbThr);
    public native long LolNewOctreeFromSTL(int NmbTri, double[] PtrCrd1, double[] PtrCrd2,
                                           int BasIdx, int NmbThr);
    public native int LolFreeOctree(long OctIdx);
    public native int LolGetBoundingBox(long OctIdx, int typ, int MaxItm, int[] ItmTab,
                                        double[] MinCrd, double[] MaxCrd, int ThrIdx);
    public native int LolGetNearest();
    public native int LolIntersectSurface();
    public native int LolIsInside(long OctIdx, double[] VerCrd, double[] VerTng, int ThrIdx);
    public native int LolProjectVertex(long OctIdx, double[] VerCrd, int typ,
                                       int MinItm, double[] MinCrd, int ThrIdx);
    public native int LolCheckIntersections(long OctIdx, int MaxItm, int[] ItmTab);
}
