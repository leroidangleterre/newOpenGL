/**
 * Copyright 2010 Per-Erik Bergman (per-erik.bergman@jayway.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.arthurmanoha.tutorial;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;
import static javax.microedition.khronos.opengles.GL10.GL_MODELVIEW;

public class OpenGLRenderer implements Renderer {
    private final Group root;
    private int width, height;
    private float ratio;

    // Percentage of the width of the screen used by the image
    // If the image is too wide, the user cannot focus both eyes properly.
    // Values from 0 (image compressed on the central pixel line) to 1 (full screen used).
    private float screenPercentage;

    // Point of view; the two images will be rendered from either side of this point.
    private Empty viewerEmpty;

    private FloatBuffer triangleVB;
    private FloatBuffer squareVB;
    private FloatBuffer cubeVB;

    public OpenGLRenderer() {
        // Initialize our root.
        Group group = new Group();
        root = group;
        screenPercentage = 0.8f;
    }

    public OpenGLRenderer(int widthParam, int heightParam) {
        this();
        width = widthParam;
        height = heightParam;
    }

    public void setDimensions(int widthParam, int heightParam) {
        width = widthParam;
        height = heightParam;
        ratio = (float) width / (float) height;
        Log.d(TAG, "OpenGLRenderer: ratio is " + ", " + ratio);
    }

    public void setEmpty(Empty e) {
        viewerEmpty = e;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
     * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
     */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        initShapes();

        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }


    private void initShapes() {

        float triangleAltitude = 3.0f;
        float triangleCoords[] = {
                // X, Y, Z
                -0.5f, -0.25f, triangleAltitude,
                0.5f, -0.25f, triangleAltitude,
                0.0f, 0.559016994f, triangleAltitude
        };
        float squareAltitude = -3.0f;
        float squareCoords[] = {
                -1f, -1f, squareAltitude,
                -1f, 1f, squareAltitude,
                1f, -1f, squareAltitude,
                1f, 1f, squareAltitude
        };
        float cubeCoords[] = {
                -1.f, 1.f, 1.f,     // Front-top-left
                1.f, 1.f, 1.f,      // Front-top-right
                -1.f, -1.f, 1.f,    // Front-bottom-left
                1.f, -1.f, 1.f,     // Front-bottom-right
                1.f, -1.f, -1.f,    // Back-bottom-right
                1.f, 1.f, 1.f,      // Front-top-right
                1.f, 1.f, -1.f,     // Back-top-right
                -1.f, 1.f, 1.f,     // Front-top-left
                -1.f, 1.f, -1.f,    // Back-top-left
                -1.f, -1.f, 1.f,    // Front-bottom-left
                -1.f, -1.f, -1.f,   // Back-bottom-left
                1.f, -1.f, -1.f,    // Back-bottom-right
                -1.f, 1.f, -1.f,    // Back-top-left
                1.f, 1.f, -1.f      // Back-top-right
        };

        // initialize vertex Buffer for triangle
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangleVB.position(0);            // set the buffer to read the first coordinate

        // ... and for square
        ByteBuffer vbbSquare = ByteBuffer.allocateDirect(squareCoords.length * 4);
        vbbSquare.order(ByteOrder.nativeOrder());
        squareVB = vbbSquare.asFloatBuffer();
        squareVB.put(squareCoords);
        squareVB.position(0);

        // ... and for cube
        ByteBuffer vbbCube = ByteBuffer.allocateDirect(cubeCoords.length * 4);
        vbbCube.order(ByteOrder.nativeOrder());
        cubeVB = vbbCube.asFloatBuffer();
        cubeVB.put(cubeCoords);
        cubeVB.position(0);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
     * khronos.opengles.GL10)
     */

    public void onDrawFrame(GL10 gl) {

        Empty drawEmpty = viewerEmpty.clone();

        // Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL_MODELVIEW);

        drawEmpty.centerOnTarget(0, 0, 0);

        float eyeX, eyeY, eyeZ;

        eyeX = drawEmpty.getPos().getX();
        eyeY = drawEmpty.getPos().getY();
        eyeZ = drawEmpty.getPos().getZ();

        eyeX = drawEmpty.getLeftPos().getX();
        eyeY = drawEmpty.getLeftPos().getY();
        eyeZ = drawEmpty.getLeftPos().getZ();
        Log.d(TAG, "onDrawFrame: " + (drawEmpty.getPos().getX() - eyeX) + ", "
                + (drawEmpty.getPos().getY() - eyeY) + ", "
                + (drawEmpty.getPos().getZ() - eyeZ));

        // Left image
        gl.glViewport((int) ((width / 2) * (1 - screenPercentage)),
                (int) ((height / 2) * (1 - screenPercentage)),
                (int) (screenPercentage * width / 2),
                (int) (screenPercentage * height));
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, eyeX, eyeY, eyeZ,
                eyeX + drawEmpty.getTarget().getX(), eyeY + drawEmpty.getTarget().getY(), eyeZ + drawEmpty.getTarget().getZ(),
                drawEmpty.getVertic().getX(), drawEmpty.getVertic().getY(), drawEmpty.getVertic().getZ());
        drawHalfFrame(gl);

        eyeX = drawEmpty.getPos().getX();
        eyeY = drawEmpty.getPos().getY();
        eyeZ = drawEmpty.getPos().getZ();

//        eyeX = drawEmpty.getRightPos().getX();
//        eyeY = drawEmpty.getRightPos().getY();
//        eyeZ = drawEmpty.getRightPos().getZ();

        // Right image
        gl.glViewport(width / 2,
                (int) ((height / 2) * (1 - screenPercentage)),
                (int) (screenPercentage * width / 2),
                (int) (screenPercentage * height));
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, eyeX, eyeY, eyeZ,
                eyeX + drawEmpty.getTarget().getX(), eyeY + drawEmpty.getTarget().getY(), eyeZ + drawEmpty.getTarget().getZ(),
                drawEmpty.getVertic().getX(), drawEmpty.getVertic().getY(), drawEmpty.getVertic().getZ());
        drawHalfFrame(gl);


    }

    private void drawHalfFrame(GL10 gl) {

        // Draw the triangle
        gl.glColor4f(0.63671875f, 0.76953125f, 0.22265625f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVB);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // Draw the square
        gl.glColor4f(0.0f, 0, 1.0f, 0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, squareVB);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // Draw the cube
        gl.glColor4f(1.0f, 0, 0.0f, 0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVB);
//        gl.glColorPointer(3, GL10.GL_FLOAT, 0, colorVB);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 14);
    }

    public void onSurfaceChanged(GL10 gl, int widthParam, int heightParam) {

        width = widthParam;
        height = heightParam;

        gl.glViewport(0, 0, widthParam, heightParam);

        // make adjustments for screen ratio
        float ratio = (float) (widthParam / heightParam) / 2; // For the double image
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 1000);  // apply the projection matrix
    }

    /**
     * Adds a mesh to the root.
     *
     * @param mesh the mesh to add.
     */
    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }

//    public void addMesh(Mesh mesh, float x, float y, float z) {
//        root.add(mesh, x, y, z);
//    }
}