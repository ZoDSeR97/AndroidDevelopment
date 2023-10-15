package com.minhle.termproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class Game extends View {

    private Bitmap board1, board2, bmSnake, bmApple;
    private ArrayList<Board> arrBoard = new ArrayList<>();
    private int w = 12, h=21;
    public static int sizeElementMap = 75*Constraint.WIDTH/1080;
    private Snake snake;
    private Apple apple;
    private Handler handler;
    private Runnable r;
    private boolean move = false;
    private float mx, my;
    public static boolean isPlaying = false;
    public static int score = 0, bestScore = 0;
    private Context context;

    public Game(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sp!=null){
            bestScore = sp.getInt("bestscore",0);
        }
        board1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        board1 = Bitmap.createScaledBitmap(board1, sizeElementMap, sizeElementMap, true);
        board2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        board2 = Bitmap.createScaledBitmap(board2, sizeElementMap, sizeElementMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeElementMap, sizeElementMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeElementMap, sizeElementMap, true);
        for(int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if((j+i)%2==0){
                    arrBoard.add(new Board(board1, j*board1.getWidth() + Constraint.WIDTH/2 - (w/2)*board1.getWidth(), i*board1.getHeight()+50*Constraint.HEIGHT/1920, board1.getWidth(), board1.getHeight()));
                }else{
                    arrBoard.add(new Board(board2, j*board2.getWidth() + Constraint.WIDTH/2 - (w/2)*board2.getWidth(), i*board2.getHeight()+50*Constraint.HEIGHT/1920, board2.getWidth(), board2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake,arrBoard.get(126).getX(),arrBoard.get(126).getY(), 4);
        apple = new Apple(bmApple, arrBoard.get(randomApple()[0]).getX(), arrBoard.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private int[] randomApple(){
        int []xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrBoard.size()-1);
        xy[1] = r.nextInt(arrBoard.size()-1);
        Rect rect = new Rect(arrBoard.get(xy[0]).getX(), arrBoard.get(xy[1]).getY(), arrBoard.get(xy[0]).getX()+sizeElementMap, arrBoard.get(xy[1]).getY()+sizeElementMap);
        boolean check = true;
        while (check){
            check = false;
            for (int i = 0; i < snake.getArrPartSnake().size(); i++){
                if(rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                    check = true;
                    xy[0] = r.nextInt(arrBoard.size()-1);
                    xy[1] = r.nextInt(arrBoard.size()-1);
                    rect = new Rect(arrBoard.get(xy[0]).getX(), arrBoard.get(xy[1]).getY(), arrBoard.get(xy[0]).getX()+sizeElementMap, arrBoard.get(xy[1]).getY()+sizeElementMap);
                }
            }
        }
        return xy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a){
            case  MotionEvent.ACTION_MOVE:{
                if(move==false){
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }else{
                    if(mx - event.getX() > 100 && !snake.isMove_right()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_left(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getX() - mx > 100 &&!snake.isMove_left()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_right(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getY() - my > 100 && !snake.isMove_up()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_down(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(my - event.getY() > 100 && !snake.isMove_down()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_up(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(0xFF065700);
        for(int i = 0; i < arrBoard.size(); i++){
            canvas.drawBitmap(arrBoard.get(i).getBoard(), arrBoard.get(i).getX(), arrBoard.get(i).getY(), null);
        }
        if(isPlaying){
            snake.update();
            if(snake.getArrPartSnake().get(0).getX() < this.arrBoard.get(0).getX()
                    ||snake.getArrPartSnake().get(0).getY() < this.arrBoard.get(0).getY()
                    ||snake.getArrPartSnake().get(0).getY()+sizeElementMap>this.arrBoard.get(this.arrBoard.size()-1).getY() + sizeElementMap
                    ||snake.getArrPartSnake().get(0).getX()+sizeElementMap>this.arrBoard.get(this.arrBoard.size()-1).getX() + sizeElementMap){
                gameOver();
            }
            for (int i = 1; i < snake.getArrPartSnake().size(); i++){
                if (snake.getArrPartSnake().get(0).getrBody().intersect(snake.getArrPartSnake().get(i).getrBody())){
                    gameOver();
                }
            }
        }
        snake.drawSnake(canvas);
        apple.draw(canvas);
        if(snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())){
            apple.reset(arrBoard.get(randomApple()[0]).getX(), arrBoard.get(randomApple()[1]).getY());
            snake.addPart();
            score++;
            MainActivity.txt_score.setText(score+"");
            if(score > bestScore){
                bestScore = score;
                SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("bestscore", bestScore);
                editor.apply();
                MainActivity.txt_best_score.setText(bestScore+"");
            }
        }
        handler.postDelayed(r, 100);
    }

    private void gameOver() {
        isPlaying = false;
        MainActivity.dialogScore.show();
        MainActivity.txt_dialog_best_score.setText(bestScore+"");
        MainActivity.txt_dialog_score.setText(score+"");
    }

    public void reset(){
        for(int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if((j+i)%2==0){
                    arrBoard.add(new Board(board1, j*board1.getWidth() + Constraint.WIDTH/2 - (w/2)*board1.getWidth(), i*board1.getHeight()+50*Constraint.HEIGHT/1920, board1.getWidth(), board1.getHeight()));
                }else{
                    arrBoard.add(new Board(board2, j*board2.getWidth() + Constraint.WIDTH/2 - (w/2)*board2.getWidth(), i*board2.getHeight()+50*Constraint.HEIGHT/1920, board2.getWidth(), board2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake,arrBoard.get(126).getX(),arrBoard.get(126).getY(), 4);
        apple = new Apple(bmApple, arrBoard.get(randomApple()[0]).getX(), arrBoard.get(randomApple()[1]).getY());
        score = 0;
    }
}