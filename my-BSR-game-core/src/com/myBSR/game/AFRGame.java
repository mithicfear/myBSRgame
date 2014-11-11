package com.myBSR.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class AFRGame extends Game {
	SpriteBatch batch;
	public BitmapFont font;
	private Texture drop;
	private Texture bsr;
	private Sound rainDrop;
	private Music rain;
	private OrthographicCamera camera;
	private Rectangle hitbox;
	private Array<Rectangle> rainDrops;
	private Long lastDropTime;
	
	@Override
	public void create () {
		//Creationism
		batch = new SpriteBatch();
		drop = new Texture(Gdx.files.internal("droplet.png"));
		bsr = new Texture(Gdx.files.internal("buttsexrobot.png"));
		rainDrop = Gdx.audio.newSound(Gdx.files.internal("raindrop.mp3"));
		rain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 900, 400);
		font = new BitmapFont();
		this.setScreen(BSRmenu(this));
		
		//loopin
		rain.setLooping(true);
		rain.play();
		
		//bucket thangs
		hitbox = new Rectangle();
		hitbox.x = 900 / 2 - 64 /2;
		hitbox.y = 20;
		hitbox.width = 64;
		hitbox.height = 64;
		
		//rain
		rainDrops = new Array<Rectangle>();
		spawnRains();
	}

	private Screen BSRmenu(MyBSRGame myBSRGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bsr, hitbox.x, hitbox.y);
		for(Rectangle raindrop: rainDrops) {
			batch.draw(drop, raindrop.x, raindrop.y);
		}
		batch.end();
		
		//movement n shit
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			hitbox.x = touchPos.x - 64 / 2;
		}
		
		//keyboard movement n shit
		if(Gdx.input.isKeyPressed(Keys.LEFT)) hitbox.x -= 400 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) hitbox.x += 400 * Gdx.graphics.getDeltaTime();
		
		//Stayin on the DAMN SCREEN
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 900 - 64) hitbox.x = 900 - 64;
		
		//TAKIN TOO DAMN LONG WITH THE RAIN MAN
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRains();
		
		//movin rain
		Iterator<Rectangle> itererter = rainDrops.iterator();
		while(itererter.hasNext()) {
			Rectangle rainderp = itererter.next();
			rainderp.y -= 200 * Gdx.graphics.getDeltaTime();
			if(rainderp.y + 64 < 0) itererter.remove();
			if(rainderp.overlaps(hitbox)) {
				rainDrop.play();
				itererter.remove();
		    }
		}
	}
	
	private void spawnRains() {
		Rectangle drop = new Rectangle();
	    drop.x = MathUtils.random(0, 900-64);
	    drop.y = 400;
	    drop.width = 64;
	    drop.height = 64;
	    rainDrops.add(drop);
	    lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	 public void dispose() {
		drop.dispose();
		bsr.dispose();
		rainDrop.dispose();
		rain.dispose();
		batch.dispose();
		font.dispose();
	}

}
