package com.example.arthurmanoha.tutorial;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import static android.content.ContentValues.TAG;

class HelloOpenGLES10SurfaceView extends GLSurfaceView {

    Empty empty;
    OpenGLRenderer renderer;

    public HelloOpenGLES10SurfaceView(Context context, Empty e) {
        this(context, e, new OpenGLRenderer());
    }

    public HelloOpenGLES10SurfaceView(Context context, Empty e, OpenGLRenderer renderer) {

        super(context);
        empty = e;
        renderer.setEmpty(e);
//        setRenderer(renderer);
    }

    public void setRenderer(OpenGLRenderer r) {
        Log.d(TAG, "setRenderer: ");
        super.setRenderer(r);
        this.renderer = r;
    }


    /**
     * Transmit the dimension information to the renderer.
     *
     * @param width
     * @param height
     */
    public void setDimensions(int width, int height) {
        Log.d(TAG, "setDimensions: renderer is " + renderer);
        renderer.setDimensions(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getX() + ", " + event.getY());

        // Perform an action that depends on the coordinates of the event
        int x = (int) event.getX();
        int y = (int) event.getY();

        return false;
    }


    public void addMesh(Mesh newMesh) {
        renderer.addMesh(newMesh);
    }


//    public void addMesh(Mesh newMesh, float x, float y, float z) {
//        renderer.addMesh(newMesh, x, y, z);
//    }
}