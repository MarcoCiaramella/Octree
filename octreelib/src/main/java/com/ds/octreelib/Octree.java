package com.ds.octreelib;

public class Octree {

    static {
        System.loadLibrary("Octree");
    }

    public static final int VER = 1;
    public static final int EDG = 2;
    public static final int TRI = 3;
    public static final int QAD = 4;

    private final long index;

    /**
     * Builds an octree from a mesh made of vertices (mandatory) and optionally
     * some elements of various kinds like and dimensions:
     * edges, triangles, quadrilaterals and tetrahedra are handled.
     *
     * @param nmbVer number of vertices to insert in the octree
     * @param ver coordinates of vertices
     * @param nmbEdg number of edges to insert in the octree
     * @param edg indices of edges
     * @param nmbTri number of triangles to insert in the octree
     * @param tri indices of triangles
     * @param nmbQad number of quadrilaterals to insert in the octree
     * @param qad indices of quadrilaterals
     * @param basIdx starting index of the user’s tables: 0 or 1
     * @param nmbThr maximum number of threads or process that can perform requests concurrently: must be 1 or more
     */
    public Octree(int nmbVer, double[] ver,
                  int nmbEdg, int[] edg,
                  int nmbTri, int[] tri,
                  int nmbQad, int[] qad,
                  int basIdx, int nmbThr){
        index = newOctree(nmbVer, ver,
                nmbEdg, edg,
                nmbTri, tri,
                nmbQad, qad,
                basIdx, nmbThr);
    }

    /**
     * Returns a unique octree index or 0 in case of failure.
     * @return the index
     */
    public long getIndex() {
        return index;
    }

    /**
     * Destroy this Octree.
     */
    public void free(){
        freeOctree(index);
    }

    /**
     * Intersect the whole mesh with a rectangular box and return the list of entities included
     * in this area. The intersection test is restricted to a single kind of entity and can be performed
     * concurrently as long as the calling processes provide a unique index. The list of entities
     * is stored in a user-provided table whose size must be given and will limit the number of
     * returned included elements.
     *
     * @param typ kind of mesh entity to look for: VER, EDG, TRI, QAD or TET
     * @param itmTab a user-provided table that will be filled with the intersected elements
     * @param minCrd coordinates of the lower bounding box corner
     * @param maxCrd coordinates of the upper bounding box corner
     * @param thrIdx thread or calling process number or 0 in serial case
     * @return the number of entities included in the box
     */
    public int getWithinBoundingBox(int typ, int[] itmTab,
                                    double[] minCrd, double[] maxCrd, int thrIdx){
        return getWithinBoundingBox(index, typ, itmTab.length, itmTab, minCrd, maxCrd, thrIdx);
    }

    /**
     * Search for the closest mesh entity from a given point. The kind of entity to search for
     * can be restricted to a specified type or to a maximum distance from the
     * source point. This procedure can safely be called in parallel as long as the concurrent
     * caller’s ID are unique.
     *
     * @param typ kind of entity to look for : VER, EDG, TRI, QAD or TET
     * @param verCrd coordinates of the source point
     * @param minDis a set of coordinates that will be filled with the closest projection
     * @param maxDis allows restricting the search to a maximum distance, set it to 0 for unbounded search
     * @param thrIdx thread or calling process number or 0 in serial case
     * @return the closest entity’s index or 0 in case of failure
     */
    public int getNearest(int typ, double[] verCrd, double[] minDis, double maxDis, int thrIdx){
        return getNearest(index, typ, verCrd, minDis, maxDis, thrIdx);
    }

    /**
     * This procedure enables some kind of ray-tracing intersection tests. A ray is made of
     * a source vertex and a directional vector and the procedure returns the first triangle
     * intersected by this ray.
     *
     * @param verCrd coordinates of the ray source vertex
     * @param verTng directional unit vector
     * @param minDis a set of coordinates that will be filled with the closest intersection
     * @param maxDis give a size to restrict the search distance or 0 for unbounded search
     * @param thrIdx thread or calling process number or 0 in serial case
     * @return the index of the first intersected triangle or 0 if none were found
     */
    public int getIntersectedSurface(double[] verCrd, double[] verTng, double[] minDis, double maxDis, int thrIdx){
        return getIntersectedSurface(index, verCrd, verTng, minDis, maxDis, thrIdx);
    }

    /**
     * Check if a point is inside or outside the geometry
     * based on the number vector-triangle intersections.
     *
     * @param verCrd coordinates of the point
     * @param verTng directional unit vector
     * @param thrIdx thread or calling process number or 0 in serial case
     * @return the number vector-triangle intersections
     */
    public int isInside(double[] verCrd, double[] verTng, int thrIdx){
        return isInside(index, verCrd, verTng, thrIdx);
    }

    /**
     * Geometrical and topological projection of a point on an arbitrary mesh entity. The
     * procedure computes the projection’s coordinates, the distance between the point and this
     * projection, as well as its topological location. Regardless the kind of mesh entity that the
     * point is projected on, the projection may lie very close to a specific topological location in
     * this entity. For instance, when projecting a point on a triangle, the image may be close to
     * one of its vertices, or one of its edges or simply lies within the triangle interior. An image
     * is considered close to an entity if it’s closer than the mesh’s bounding box size multiplied
     * by the single precision floating point smallest value (i.e. 10E-7).
     *
     * @param verCrd coordinates of the vertex to project
     * @param typ kind of mesh entity to project on: VER, EDG, TRI, QAD or TET
     * @param idx index of the mesh entity
     * @param crd coordinates that will receive the projection
     * @param thrIdx thread or calling process number or 0 in serial case
     * @return 0: error, 1: projection falls on a vertex, 2: on an edge, 3: within a triangle or quad
     */
    public int projectVertex(double[] verCrd, int typ,
                             int idx, double[] crd, int thrIdx){
        return projectVertex(index, verCrd, typ, idx, crd, thrIdx);
    }

    private native long newOctree(int nmbVer, double[] ver,
                                  int nmbEdg, int[] edg,
                                  int nmbTri, int[] tri,
                                  int nmbQad, int[] qad,
                                  int basIdx, int nmbThr);
    private native int freeOctree(long octIdx);
    private native int getWithinBoundingBox(long octIdx, int typ, int maxItm, int[] itmTab,
                                            double[] minCrd, double[] maxCrd, int thrIdx);
    private native int getNearest(long octIdx, int typ, double[] verCrd, double[] minDis, double maxDis, int thrIdx);
    private native int getIntersectedSurface(long octIdx, double[] verCrd, double[] verTng, double[] minDis, double maxDis, int thrIdx);
    private native int isInside(long octIdx, double[] verCrd, double[] verTng, int thrIdx);
    private native int projectVertex(long octIdx, double[] verCrd, int typ,
                                     int idx, double[] crd, int thrIdx);
}
