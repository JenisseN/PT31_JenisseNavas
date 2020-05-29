package screensOrder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import constantes.Constants;

public class DGameoOver implements Screen {
	
	private JuegoHSBase juego;
	private Texture imgGameOver;
	
	//variables de puntaje
	private String puntajeS=null;
	
	public DGameoOver(JuegoHSBase joc, int puntos) {
		this.juego = joc;
		this.imgGameOver = new Texture(Gdx.files.internal("Escenarios/EYouDie.jpg"));
		this.puntajeS=String.valueOf(puntos);
	}
	

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			this.juego.setScreen(new AFirstScreen(this.juego) );
		} else {
			juego.batch.begin();
			juego.batch.draw(imgGameOver, 0, 0, Constants.MON_AMPLE, Constants.MON_ALT);
			juego.fontA.draw(juego.batch, "Puntos ", 300, 700);
			juego.fontA.draw(juego.batch, puntajeS , 550, 700);
			juego.fontA.draw(juego.batch, "PRESIONA ENTER ", 200, 200);
			juego.batch.end();
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		juego.batch.dispose();
		imgGameOver.dispose();
	}

}
