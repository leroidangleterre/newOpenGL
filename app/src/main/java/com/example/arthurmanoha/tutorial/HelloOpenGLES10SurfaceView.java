package com.example.arthurmanoha.tutorial;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

class HelloOpenGLES10SurfaceView extends GLSurfaceView {

    Empty empty;
    OpenGLRenderer renderer;

    public HelloOpenGLES10SurfaceView(Context context, Empty e) {
        super(context);
        empty = e;
//        renderer = new OpenGLRenderer(getWidth(), getHeight());
        renderer = new OpenGLRenderer();
        renderer.setEmpty(e);
        setRenderer(renderer);
    }

    /**
     * Transmit the dimension information to the renderer.
     * @param width
     * @param height
     */
    public void setDimensions(int width, int height){
        renderer.setDimensions(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getX() + ", " + event.getY());

//        // Perform an action that depends on the coordinates of the event
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        if (x >= 900 && x <= 1300 && y >= 900) {
//            // Bottom center
//            // Hide the controls.
//            ((OpenGLRenderer) getContext()).toggleSystemUi();
//        }

        return false;
    }


    public void addMesh(Mesh newMesh) {
        renderer.addMesh(newMesh);
    }


}