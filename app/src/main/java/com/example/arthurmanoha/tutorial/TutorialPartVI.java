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

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static java.lang.Math.PI;
import static java.lang.Math.log;

/**
 * This class is the setup for the Tutorial part VI located at:
 * http://blog.jayway.com/
 *
 * @author Per-Erik Bergman (per-erik.bergman@jayway.com)
 */
public class TutorialPartVI extends Activity {

    private String TAG = "androidMain";

    private HelloOpenGLES10SurfaceView view = null;
    private OpenGLRenderer renderer;
    private Empty userEmpty;

    SensorManager sensorManager;
    Sensor rotationVectorSensor;
    SensorEventListener rotationListener;
    float[] rotationMatrix;

    private final Group root;
    private boolean isWorldFilled;


    private FloatBuffer triangleVB;


    public TutorialPartVI() {
        root = new Group();
        isWorldFilled = false;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

        userEmpty = new Empty();
        userEmpty.setPos(0, 0, 0);
        userEmpty.setPos(-10, 0, 0);
        userEmpty.setTarget(10, 0, 0);

        // Remove the title bar from the window.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Make the windows into full screen mode.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        // Create a OpenGL view.
//        if (view == null) {
        Log.d(TAG, "onCreate: creating view");
        renderer = new OpenGLRenderer();
        view = new HelloOpenGLES10SurfaceView(this, userEmpty, renderer);
//        }
        setContentView(view);

        // Get the dimensions of the screen.
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        view.setRenderer(renderer);
        view.setDimensions(screenSize.x, screenSize.y);

        final Resources res = getResources();

        // Create the terrain within a thread, only the first time.
        if (!isWorldFilled) {
//            new Thread() {
//            int size = 20;
//            int bufferSize = 800;
//            float cubeCoords[] = {
//                    -1.f, 1.f, 1.f,     // Front-top-left
//                    1.f, 1.f, 1.f,      // Front-top-right
//                    -1.f, -1.f, 1.f,    // Front-bottom-left
//                    1.f, -1.f, 1.f,     // Front-bottom-right
//                    1.f, -1.f, -1.f,    // Back-bottom-right
//                    1.f, 1.f, 1.f,      // Front-top-right
//                    1.f, 1.f, -1.f,     // Back-top-right
//                    -1.f, 1.f, 1.f,     // Front-top-left
//                    -1.f, 1.f, -1.f,    // Back-top-left
//                    -1.f, -1.f, 1.f,    // Front-bottom-left
//                    -1.f, -1.f, -1.f,   // Back-bottom-left
//                    1.f, -1.f, -1.f,    // Back-bottom-right
//                    -1.f, 1.f, -1.f,    // Back-top-left
//                    1.f, 1.f, -1.f      // Back-top-right
//            };
//            ByteBuffer vbb = ByteBuffer.allocateDirect(/*bufferSize * */ cubeCoords.length * 4); // # of coordinate values * 4 bytes per float)
//            vbb.order(ByteOrder.nativeOrder());
//
//            renderer.initShapes(vbb);
//            triangleVB = vbb.asFloatBuffer();
//            triangleVB.put(cubeCoords);
//            triangleVB.position(0);
////
////            // Include this in a loop for all cubes:
//            renderer.addCube(cubeCoords);


////                @Override
////                public void run() {
////                    super.run();
//            for (int i = -size; i <= size; i++) {
//                for (int j = -size; j <= size; j++) {
//                    // Front
//                    view.addMesh(new CubeEarth(1, 1.1f * j, -10, 1.1f * i, res));
//                    // Ground
//                    view.addMesh(new CubeEarth(1, 1.1f * j, 1.1f * i, -10, res));
//                }
////                    }
//            }
////            }.start();
        }

        rotationMatrix = new float[16];
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        rotationListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, event.values);
                float[] orientations = new float[3];
                SensorManager.getOrientation(rotationMatrix, orientations);

                userEmpty.resetRotation();
                userEmpty.rotateGlobalY((float) (-orientations[2] + PI / 2));
                userEmpty.rotateGlobalX(orientations[1]);
                userEmpty.rotateGlobalZ(-orientations[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }

        ;
        sensorManager.registerListener(rotationListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);

    }
}