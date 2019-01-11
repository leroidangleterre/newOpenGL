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

import android.content.res.Resources;

/**
 * SimpleCube is a setup class for Mesh that creates a cube.
 *
 * @author Per-Erik Bergman (per-erik.bergman@jayway.com)
 */
public class SimpleCube extends Mesh {
    /**
     * Create a cube with a default size of 1 unit.
     */
    public SimpleCube() {
        this(1, null);
    }

    /**
     * Create a cube.
     *
     * Define the mesh and the indices but not the texture, which is defined in the subclasses.
     * @param size the size of the plane.
     */
    public SimpleCube(float size, Resources res) {

        short[] indices = new short[]{
                0, 1, 2,
                0, 2, 3,
                4, 5, 6,
                4, 6, 7,
                8, 9, 10,
                8, 10, 11,
                12, 13, 14,
                12, 14, 15,
                16, 17, 18,
                16, 18, 19,
                20, 21, 22,
                20, 22, 23,
        };

        float[] vertices = new float[]{
                // Front
                -0.5f * size, -0.5f * size, -0.5f * size,// 0
                0.5f * size, -0.5f * size, -0.5f * size,
                0.5f * size, -0.5f * size, 0.5f * size,
                -0.5f * size, -0.5f * size, 0.5f * size,
                // Right
                0.5f * size, -0.5f * size, -0.5f * size,// 4
                0.5f * size, 0.5f * size, -0.5f * size,
                0.5f * size, 0.5f * size, 0.5f * size,
                0.5f * size, -0.5f * size, 0.5f * size,
                // Back
                0.5f * size, 0.5f * size, -0.5f * size,//8
                -0.5f * size, 0.5f * size, -0.5f * size,
                -0.5f * size, 0.5f * size, 0.5f * size,
                0.5f * size, 0.5f * size, 0.5f * size,
                // Left
                -0.5f * size, 0.5f * size, -0.5f * size,//12
                -0.5f * size, -0.5f * size, -0.5f * size,
                -0.5f * size, -0.5f * size, 0.5f * size,
                -0.5f * size, 0.5f * size, 0.5f * size,
                // Bottom
                -0.5f * size, -0.5f * size, -0.5f * size,//16
                -0.5f * size, 0.5f * size, -0.5f * size,
                0.5f * size, 0.5f * size, -0.5f * size,
                0.5f * size, -0.5f * size, -0.5f * size,
                // Up
                -0.5f * size, -0.5f * size, 0.5f * size,//20
                0.5f * size, -0.5f * size, 0.5f * size,
                0.5f * size, 0.5f * size, 0.5f * size,
                -0.5f * size, 0.5f * size, 0.5f * size,
        };

        setIndices(indices);
        setVertices(vertices);
    }
}
