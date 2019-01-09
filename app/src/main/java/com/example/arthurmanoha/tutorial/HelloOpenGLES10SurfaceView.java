package com.example.arthurmanoha.tutorial;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

class HelloOpenGLES10SurfaceView extends GLSurfaceView {

    Empty empty;

    public HelloOpenGLES10SurfaceView(Context context, Empty e) {
        super(context);
        empty = e;
//        // Set the Renderer for drawing on the GLSurfaceView
//        OpenGLRenderer renderer = new OpenGLRenderer(getWidth(), getHeight());
//        renderer.setEmpty(e);
//        setRenderer(renderer);

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


}