package com.example.arthurmanoha.tutorial;

public class Point {

    private float x, y, z;
    public Point(float xParam, float yParam, float zParam){
        x=xParam;
        y=yParam;z=zParam;
    }
    public Point(){
        this(0,0,0);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
