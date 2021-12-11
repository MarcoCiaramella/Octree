package com.ds.octreelib;

public class Octree {

    static {
        System.loadLibrary("Octree");
    }

    private final long index;

    /**
     * Builds an octree from a mesh made of vertices (mandatory) and optionally
     * some elements of various kinds like and dimensions:
     * edges, triangles, quadrilaterals and tetrahedra are handled.
     *
     * @param nmbVer number of vertices to insert in the octree
     * @param ver1 first vertex’s coordinates
     * @param ver2  second vertex’s coordinates
     * @param nmbEdg number of edges to insert in the octree
     * @param edg1  first edge’s nodes indices
     * @param edg2 second edge’s nodes indices
     * @param nmbTri number of triangles to insert in the octree
     * @param tri1  first triangle’s nodes indices
     * @param tri2 second triangle’s nodes indices
     * @param nmbQad number of quadrilaterals to insert in the octree
     * @param qad1  first quadrilateral’s nodes indices
     * @param qad2  second quadrilateral’s nodes indices
     * @param nmbTet number of tetrahedra to insert in the octree
     * @param tet1  first tetrahedron’s nodes indices
     * @param tet2  second tetrahedron’s nodes indices
     * @param nmbPyr number of pyramids to insert in the octree
     * @param pyr1  first pyramid’s nodes indices
     * @param pyr2  second pyramid’s nodes indices
     * @param nmbPri number of prisms to insert in the octree
     * @param pri1  first prism’s nodes indices
     * @param pri2  second prism’s nodes indices
     * @param nmbHex number of hexahedra to insert in the octree
     * @param hex1  first hexahedron’s nodes indices
     * @param hex2  second hexahedron’s nodes indices
     * @param basIdx starting index of the user’s tables: 0 or 1
     * @param nmbThr maximum number of threads or process that can perform requests concurrently: must be 1 or more
     */
    public Octree(int nmbVer, double[] ver1, double[] ver2,
                  int nmbEdg, int[] edg1, int[] edg2,
                  int nmbTri, int[] tri1, int[] tri2,
                  int nmbQad, int[] qad1, int[] qad2,
                  int nmbTet, int[] tet1, int[] tet2,
                  int nmbPyr, int[] pyr1, int[] pyr2,
                  int nmbPri, int[] pri1, int[] pri2,
                  int nmbHex, int[] hex1, int[] hex2,
                  int basIdx, int nmbThr){
        index = newOctree(nmbVer, ver1, ver2,
                nmbEdg, edg1, edg2,
                nmbTri, tri1, tri2,
                nmbQad, qad1, qad2,
                nmbTet, tet1, tet2,
                nmbPyr, pyr1, pyr2,
                nmbPri, pri1, pri2,
                nmbHex, hex1, hex2,
                basIdx, nmbThr);
    }

    /**
     * Builds an octree from a mesh in STL format.
     * @param nmbTri number of triangles to insert in the octree
     * @param tri1 first triangle’s nodes indices
     * @param tri2 second triangle’s nodes indices
     * @param basIdx starting index of the user’s tables: 0 or 1
     * @param nmbThr maximum number of threads or process that can perform requests concurrently: must be 1 or more
     */
    public Octree(int nmbTri, double[] tri1, double[] tri2,
                  int basIdx, int nmbThr){
        index = newOctreeFromSTL(nmbTri, tri1, tri2,
                basIdx, nmbThr);
    }

    /**
     * Returns a unique octree index or 0 in case of failure.
     * @return the index
     */
    public long getIndex() {
        return index;
    }

    private native long newOctree(int nmbVer, double[] ver1, double[] ver2,
                                  int nmbEdg, int[] edg1, int[] edg2,
                                  int nmbTri, int[] tri1, int[] tri2,
                                  int nmbQad, int[] qad1, int[] qad2,
                                  int nmbTet, int[] tet1, int[] tet2,
                                  int nmbPyr, int[] pyr1, int[] pyr2,
                                  int nmbPri, int[] pri1, int[] pri2,
                                  int nmbHex, int[] hex1, int[] hex2,
                                  int basIdx, int nmbThr);
    private native long newOctreeFromSTL(int nmbTri, double[] tri1, double[] tri2,
                                        int basIdx, int nmbThr);
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
