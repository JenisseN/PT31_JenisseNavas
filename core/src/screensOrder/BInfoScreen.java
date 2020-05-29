package screensOrder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import constantes.Constants;

public class BInfoScreen implements Screen {

	private JuegoHSBase juego;
	private BitmapFont fontC;
	private Texture background;
	private Texture frame;

	public BInfoScreen(JuegoHSBase joc) {
		this.juego = joc;
		this.background = new Texture(Gdx.files.internal("Escenarios/CBackground.jpg"));
		this.frame = new Texture(Gdx.files.internal("Escenarios/Cframe.png"));
		this.fontC = new BitmapFont(Gdx.files.internal("fonts/fuente4.fnt"), Gdx.files.internal("fonts/fuente4.png"),
				false);
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			juego.setScreen(new CGameScreen(juego));
		} else {
			juego.batch.begin();
			juego.batch.draw(background, 0, 0, Constants.MON_AMPLE, Constants.MON_ALT);
			juego.batch.draw(frame, 50, 20, 900, 750);
			this.fontC.draw(juego.batch, "**Instrucciones del Juego: ", 250, 600);
			this.fontC.draw(juego.batch, "*No dejes que te muerdan!!", 250, 480);
			this.fontC.draw(juego.batch, "Tienes 3 vidas", 250, 450);
			this.fontC.draw(juego.batch, "*Aumenta tus puntos!!", 250, 390);
			this.fontC.draw(juego.batch, "Consigue alimentos, agua y medicinas", 250, 360);
			this.fontC.draw(juego.batch, "*Apunta y dispara!!", 250, 300);// ancho largo
			this.fontC.draw(juego.batch, "Sobrevivir esta en tus manos, tira a la cabeza...", 350, 150);
			juego.batch.end();
		}

	}

	@Override
	public void show() {
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
		background.dispose();
		frame.dispose();
		fontC.dispose();
		juego.dispose();
		background.dispose();
		juego.batch.dispose();
	}

}
