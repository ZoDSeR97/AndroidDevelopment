package com.minhle.termproject;

import android.graphics.Bitmap;

public class Board {
    private Bitmap board;
    private int x, y, width, height;

    public Board(Bitmap board, int x, int y, int width, int height){
        this.board = board;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bitmap getBoard(){
        return board;
    }

    public void setBoard(Bitmap board){
        this.board = board;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return width;
    }

    public void getHeight(int height){
        this.height = height;
    }

}