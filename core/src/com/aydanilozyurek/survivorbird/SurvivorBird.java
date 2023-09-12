package com.aydanilozyurek.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.1f;
	float enemyVelocity = 2;
	Random random;

	Circle birdCircle;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;



	int numOfEnemies=4;
	float[] enemyX = new float[numOfEnemies];
	float[] enemyOffset = new float[numOfEnemies];
	float[] enemyOffset2 = new float[numOfEnemies];
	float[] enemyOffset3 = new float[numOfEnemies];
	float distance = 0;

	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;

	@Override
	public void create () {
		batch = new SpriteBatch();

		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();

		birdX = Gdx.graphics.getWidth()/2 - bird.getHeight()/2;
		birdY = Gdx.graphics.getHeight()/3;

		birdCircle = new Circle();
		enemyCircle1 = new Circle[numOfEnemies];
		enemyCircle2 = new Circle[numOfEnemies];
		enemyCircle3 = new Circle[numOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);


		for(int i = 0; i < numOfEnemies ; i++){

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1){


			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight()/2){
				score++;

				if(scoredEnemy < numOfEnemies - 1){
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}


			if(Gdx.input.justTouched()){
				velocity = -10 ;
			}
			for(int i = 0; i < numOfEnemies ; i++){

				if(enemyX[i] < Gdx.graphics.getWidth()/15){

					enemyX[i] = enemyX[i] + numOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}else {
					enemyX[i] -= enemyVelocity;
				}

				batch.draw(enemy1,enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy2,enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy3,enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircle1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth()/30 );
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth()/30 );
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth()/30 );
			}


			if(birdY>0){
				velocity = velocity + gravity ;
				birdY = birdY - velocity;
			}else {
				gameState = 2;
			}
		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){

			font2.draw(batch, "Game Over! Tap To Play Again!", 100, Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdY = Gdx.graphics.getHeight()/3;

				for(int i = 0; i < numOfEnemies ; i++){

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

					enemyCircle1[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();
				}

				velocity = 0;
				score = 0;
				scoredEnemy = 0;
			}
		}

		batch.draw(bird, birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch, String.valueOf(score), 100,200);
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

		for(int i = 0 ; i < numOfEnemies; i++){
			if(Intersector.overlaps(birdCircle,enemyCircle1[i]) || Intersector.overlaps(birdCircle, enemyCircle2[i]) || Intersector.overlaps(birdCircle, enemyCircle3[i])){
					gameState = 2;
			}
		}

	}
	
	@Override
	public void dispose () {

	}
}
