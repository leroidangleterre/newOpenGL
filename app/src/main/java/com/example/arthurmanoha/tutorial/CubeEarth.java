package com.example.arthurmanoha.tutorial;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class CubeEarth extends SimpleCube {

    private static int nbCubesMade = 0;

    /**
     * Create a cube with a default size of 1 unit.
     */
    public CubeEarth() {
        this(1, null);
    }

    /**
     * Create a cube.
     *
     * @param size the size of the plane.
     */
    public CubeEarth(float size, Resources res) {

        super(size, res);

        nbCubesMade++;

        loadBitmap(BitmapFactory.decodeResource(res, R.drawable.grassresized));

        // Mapping coordinates for the vertices
        float textureCoordinates[] = {
                .333f, .666f, // 0
                .666f, .666f,
                .666f, .333f,
                .333f, .333f,
                .666f, .666f, // 4
                1.00f, .666f,
                1.00f, .333f,
                .666f, .333f,
                .666f, 1.00f, // 8
                1.00f, 1.00f,
                1.00f, .666f,
                .666f, .666f,
                0.00f, .666f, // 12
                .333f, .666f,
                .333f, .333f,
                0.00f, .333f,
                .333f, .666f, //16
                .333f, 1.00f,
                .666f, 1.00f,
                .666f, .666f,
                .333f, .333f, // 20
                .666f, .333f,
                .666f, 0.00f,
                .333f, 0.00f
        };
        setTextureCoordinates(textureCoordinates);
    }

    public CubeEarth(float size, float xParam, float yParam, float zParam, Resources res) {
        this(size, res);
        x = xParam;
        y = yParam;
        z = zParam;
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

}
