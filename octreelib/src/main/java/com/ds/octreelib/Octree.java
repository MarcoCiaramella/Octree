package com.ds.octreelib;

public class Octree {

    static {
        System.loadLibrary("Octree");
    }

    public native long LolNewOctree();
    public native long LolNewOctreeFromSTL();
    public native int LolFreeOctree();
    public native int LolGetBoundingBox();
    public native int LolGetNearest();
    public native int LolIntersectSurface();
    public native int LolIsInside();
    public native int LolProjectVertex();
    public native int LolCheckIntersections();
}
