package screensOrder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import constantes.Constants;

public class EGameWinner implements Screen {


	private JuegoHSBase juego;
	private int puntos=0;
	private Texture imgBackground;
	
	public EGameWinner(JuegoHSBase juego, int puntostotal) {
		this.puntos=puntostotal;
		this.imgBackground = new Texture(Gdx.files.internal("Escenarios/FinalWin.jpg"));
	}


	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			this.juego.setScreen(new AFirstScreen(this.juego) );
		} else {
			juego.batch.begin();
			juego.batch.draw(imgBackground, 0, 0, Constants.MON_AMPLE, Constants.MON_ALT);
			juego.fontA.draw(juego.batch, "Eres un superviviente del Apocalipsis!!! ", 150, 700);
			juego.fontA.draw(juego.batch, "PUNTAJE "+puntos , 150, 700);
			juego.fontA.draw(juego.batch, "PRESIONA ENTER PARA SEGUIR JUGANDO ", 200, 500);
			juego.batch.end();
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
