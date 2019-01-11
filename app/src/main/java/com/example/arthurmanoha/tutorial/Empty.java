package com.example.arthurmanoha.tutorial;

import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * This class describes the too that positions and orients a solid or a camera in space.
 */

public class Empty {

    private Vector pos;
    private Vector target, left, vertic; // Using the position of the empty as a start.
    private float width; // Half-distance between the two cameras
    private Vector posLeft, posRight;
    private float distanceToTarget; // The distance to the target is the norm of the target vector.
    private float zAngle; // Total rotation around the Z-axis applied on the empty.

    public Empty() {
        pos = new Vector();
        resetRotation();
        width = 0.3f;
        resetRotation();
        distanceToTarget = 1;
        computeLeftRightPos();
    }

    public Empty clone() {
        Empty res = new Empty();

        res.pos = pos.clone();
        res.target = target.clone();
        res.vertic = vertic.clone();
        res.left = left.clone();
        res.zAngle = zAngle;
        res.distanceToTarget = distanceToTarget;
        res.posLeft = posLeft.clone();
        res.posRight = posRight.clone();
        res.width = width;

        return res;
    }

    public void resetRotation() {
        target = new Vector(distanceToTarget, 0, 0);
        left = new Vector(0, 1, 0);
        vertic = new Vector(0, 0, 1);
        zAngle = 0;
    }

    private void computeLeftRightPos() {
        posLeft = pos.sum(left.mult(width));
        posRight = pos.sum(left.mult(-width));
    }

    /**
     * Set the position of the origin.
     */
    public void setPos(float x, float y, float z) {
        pos = new Vector(x, y, z);
        computeLeftRightPos();
    }

    /**
     * Set the position of the origin.
     */
    public void setPos(Vector newPos) {
        pos = newPos;
        computeLeftRightPos();
    }

    /**
     * Set the target of the empty, from the referential of its current position.
     *
     * @param xTarget, yTarget, zTarget the components of the new target
     */
    public void setTarget(float xTarget, float yTarget, float zTarget) {
        this.target = new Vector(xTarget, yTarget, zTarget);
        distanceToTarget = target.getNorm();
        computeLeftVector();
    }

    /**
     * Set the target of the empty, from the referential of its current position.
     *
     * @param newTarget the new target
     */
    public void setTarget(Vector newTarget) {
        this.target = newTarget;
    }

    /**
     * Set the left of the empty. The new value may or may not be actually orthogonal to target and vertic.
     * Used for cloning.
     */
    public void setLeft(Vector newLeft) {
        this.left = newLeft;
    }

    /**
     * Set the vertic of the empty. The new value may or may not be actually orthogonal to target and left.
     * Used for cloning.
     */
    public void setVertic(Vector newVertic) {
        this.left = newVertic;
    }

    /**
     * Compute the coordinates of the left vector, using the current target and after making sure
     * that the vertical vector is properly set.
     */
    private void computeLeftVector() {
        vertic = new Vector(0, 0, 1);
        left = vertic.vectorProduct(target);
    }

    /**
     * Get the position of the empty
     *
     * @return the current position
     */

    public Vector getPos() {
        return pos;
    }

    /**
     * Get the position of a point located on the left of this empty.
     */
    public Vector getLeftPos() {
        return posLeft;
    }

    /**
     * Get the position of a point located on the left of this empty.
     */
    public Vector getRightPos() {
        return posRight;
    }

    public Vector getTarget() {
        return target;
    }

    public Vector getVertic() {
        return vertic;
    }

    /**
     * Rotate the empty around the global X-axis.
     */
    public void rotateGlobalX(float angle) {
        target.rotateGlobalX(angle);
        left.rotateGlobalX(angle);
        vertic.rotateGlobalX(angle);
        computeLeftRightPos();
    }

    /**
     * Rotate the empty around the global Y-axis.
     */
    public void rotateGlobalY(float angle) {
        target.rotateGlobalY(angle);
        left.rotateGlobalY(angle);
        vertic.rotateGlobalY(angle);
        computeLeftRightPos();
    }

    /**
     * Rotate the empty around its local Y-axis.
     */
    public void rotateLocalY(float angle) {
        float currentZAngle = this.zAngle;
        // Step 1: relocate the empty so that it faces the X-axis, looking toward positive values.
        rotateGlobalZ(-currentZAngle);

        // Step 2: perform the rotation around the global Y-axis.
        rotateGlobalY(angle);

        // Step 3: apply the inverse of the z-rotation in step 1.
        rotateGlobalZ(currentZAngle);
        computeLeftRightPos();
    }


    /**
     * Rotate the empty around the Z axis, without changing its center position.
     *
     * @param angle
     */
    public void rotateGlobalZ(float angle) {

        target.rotateGlobalZ(angle);
        left.rotateGlobalZ(angle);
        vertic.rotateGlobalZ(angle);
        computeLeftRightPos();
    }

    /**
     * Rotate the empty around the Z axis while maintaining the target at the same place.
     *
     * @param angle
     */
    public void rotateGlobalZAroundTarget(float angle) {
        Vector realTarget = pos.sum(target);
        rotateGlobalZ(angle);
        centerOnTarget(realTarget);
        Log.d(TAG, "rotateGlobalZAroundTarget: centering on " + realTarget);
        computeLeftRightPos();
    }

    /**
     * Translate the empty so that the target is located at the desired position.
     *
     * @param newTarget the new target
     */
    public void centerOnTarget(Vector newTarget) {

        Vector diff = newTarget.diff(pos.sum(target));
        pos.add(diff);
        computeLeftRightPos();
    }

    public void centerOnTarget(float x, float y, float z) {
        centerOnTarget(new Vector(x, y, z));
        computeLeftRightPos();
    }

    /**
     * Translate the empty without changing its orientation.
     *
     * @param dx
     * @param dy
     * @param dz
     */
    public void translate(float dx, float dy, float dz) {
        pos.add(dx, dy, dz);
    }
}
