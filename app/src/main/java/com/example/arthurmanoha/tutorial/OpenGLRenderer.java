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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.Display;

import static android.content.ContentValues.TAG;

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

    public OpenGLRenderer() {
        // Initialize our root.
        Group group = new Group();
        root = group;
        screenPercentage = 0.8f;

        // Create a new plane.
        SimplePlane plane = new SimplePlane(1, 1);

        // Move and rotate the plane.
        plane.z = 1.7f;
        plane.rx = -65;
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
        Log.d(TAG, "OpenGLRenderer: ratio is " + ratio);
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
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
     * khronos.opengles.GL10)
     */
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix

//        Log.d(TAG, "onDrawFrame: width, height = " + width + ", " + height);
        // Left image
//        gl.glViewport((int) ((width / 2) * (1 - screenPercentage)),
//                (int) ((height / 2) * (1 - screenPercentage)),
//                (int) (screenPercentage * width / 2),
//                (int) (screenPercentage * height));
//        Log.d(TAG, "onDrawFrame: wh = " + width + ", " + height);


        gl.glViewport(0, 0, width / 2, height);
        gl.glLoadIdentity();
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);

        // Draw our scene.
        root.draw(gl);

        gl.glViewport(width / 2, 0, width / 2, height);
        gl.glLoadIdentity();
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
        // Draw our scene.
        root.draw(gl);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
     * .khronos.opengles.GL10, int, int)
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) (2 * height), 0.1f,
                1000.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    /**
     * Adds a mesh to the root.
     *
     * @param mesh the mesh to add.
     */
    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }
}
