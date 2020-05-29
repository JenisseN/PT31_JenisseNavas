package screensOrder;

import java.util.Iterator;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import constantes.Constants;
import enemigos.IEnemic;
import enemigos.ZombieZigZag;
import scenes.Hud;

public class CGameScreen extends ScreenAdapter {

	private JuegoHSBase juego;
	private OrthographicCamera gameCamara;
	private Viewport gameport;
	//hud de widgets
	private Hud hud;

	// Grafics
	private Texture texturaJugador;
	private Texture texturaEnemigo;
	private Texture texturaDisparo;
	private Texture texturaFondo;
	private Rectangle jugadorR;
	private Array<IEnemic> enemigosZ;
	private Array<Rectangle> disparosJugador;

	// Musica y sonidos
	private Music cityMusic;
	private Music enemiDeadSound;
	
	// Variables del tiempo
	private int tiempoUltimoEnemigo = 2;
	
	//Variables de puntos
	private int killed=0;
	private int puntostotal=0;


	public CGameScreen(JuegoHSBase juego) {
		this.juego = juego;
		this. gameCamara = new OrthographicCamera();
		this. gameCamara.setToOrtho(false, Constants.MON_AMPLE, Constants.MON_ALT);
		this. gameport = new FitViewport(Constants.MON_AMPLE, Constants.MON_ALT, gameCamara);
		//hud de Widgets
		this. hud= new Hud(juego.batch );
		
		this.jugadorR = new Rectangle(10, 0, Constants.ANCHO_JUGADOR, Constants.ALTO_JUGADOR);
		this.enemigosZ = new Array<IEnemic>();
		this.disparosJugador = new Array<Rectangle>();

		//Testuras de los juegadores
		this.texturaJugador = new Texture(Gdx.files.internal("Characteres/survivorGun.png"));
		this.texturaEnemigo = new Texture(Gdx.files.internal("Characteres/zombie.png"));
		this.texturaDisparo = new Texture(Gdx.files.internal("Characteres/laserGreen.png"));
		this.texturaFondo = new Texture(Gdx.files.internal("Escenarios/BackgroundNivel1.jpg"))
				;
		// Musica
		this.cityMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Zombie_Garden.mp3"));
		this.enemiDeadSound = Gdx.audio.newMusic(Gdx.files.internal("music/ZombieDead.wav"));
		this.cityMusic.setLooping(false);
		this.cityMusic.play();
	}

	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void render(float delta) {

		//Como el jugador se muev
		keabordJugadorMovements();
		//Calculos de movimientos de los zombies, choques
		enemicsMovements();
		
		juego.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		juego.batch.begin();
		juego.batch.draw(texturaFondo, 0, 0, Constants.MON_AMPLE, Constants.MON_ALT);
		juego.batch.draw(texturaJugador, jugadorR.x, jugadorR.y, jugadorR.width, jugadorR.height);
		
		//Creacion de enemigos
		for (IEnemic enemic : enemigosZ) {
			enemic.dibujarse(juego.batch);
		}
		for (Rectangle disparo : disparosJugador) {
			juego.batch.draw(texturaDisparo, disparo.x, disparo.y, Constants.ANCHO_CUCHILLO, Constants.ALTO_CUCHILLO);
		}
		juego.batch.end();
	}

	private void keabordJugadorMovements() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			jugadorR.x -= Constants.VELOCIDAD_JUGADOR * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			jugadorR.x += Constants.VELOCIDAD_JUGADOR * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			jugadorR.y += Constants.VELOCIDAD_JUGADOR * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			jugadorR.y -= Constants.VELOCIDAD_JUGADOR * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			float x = jugadorR.x + jugadorR.width / 2;
			float y = jugadorR.y + jugadorR.height;
			disparosJugador.add(new Rectangle(x, y, Constants.ANCHO_CUCHILLO, Constants.ALTO_CUCHILLO));
		}

		int larchScreen = Constants.MON_AMPLE;
		int altScreen = Constants.MON_ALT;

		if (jugadorR.x < 0)
			jugadorR.x = 0;
		if (jugadorR.x > (larchScreen - jugadorR.width))
			jugadorR.x = larchScreen - jugadorR.width;

		if (jugadorR.y < 0)
			jugadorR.y = 0;

		if (jugadorR.y > (altScreen - jugadorR.width))
			jugadorR.y = altScreen - jugadorR.width;
	}

	private void enemicsMovements() {
		float delta = Gdx.graphics.getDeltaTime();
		tiempoUltimoEnemigo += 1 ;

		if (tiempoUltimoEnemigo > Constants.TIEMPO_ENTRE_APARICIONES_ENEMISMOS_S) {
			int x = new Random().nextInt(Constants.MON_AMPLE);
			int y = Constants.MON_ALT;
			//Ahora todos los zombien hacen zigZag
			IEnemic nouEnemic = new ZombieZigZag(x, y, Constants.ALTO_ZOMBIE, Constants.ANCHO_ZOMBIE, texturaEnemigo);
			enemigosZ.add(nouEnemic);
			tiempoUltimoEnemigo = 0;
		}


		for (Rectangle disparo : disparosJugador) {			
			disparo.y += Constants.VELOCIDAD_JUGADOR * delta;
		}

		for (IEnemic ememi : enemigosZ) {
			ememi.actualizarse(delta);
		}

	
		// Colisiones de disparos con zombies y los zombies avanzan
		for (Iterator iterEnemic = enemigosZ.iterator(); iterEnemic.hasNext();) {
			IEnemic enemic = (IEnemic) iterEnemic.next();

			for (Iterator iterDisparo = disparosJugador.iterator(); iterDisparo.hasNext();) {
				Rectangle playerDisp = (Rectangle) iterDisparo.next();
	
				if (enemic.choque(playerDisp)) {
					enemiDeadSound.play();
					iterEnemic.remove();
					iterDisparo.remove();
					puntostotal+=killed;
					//Por cada zombie muerto agrega un punto mas
					hud.addScore(killed++);
					Gdx.app.debug("Zombie die", "Good Job");
					break;
				}
			}
		}

		// Enemigos fuera de la pantalla se eliminan
		for (Iterator iterator = enemigosZ.iterator(); iterator.hasNext();) {
			IEnemic enemic = (IEnemic) iterator.next();
			if (enemic.fueradePantalla()) {
				iterator.remove();
			}
		}
		
		
		// Colisiones de enemigos con Player, si chocan vuelve se le quita una vida y sale la pantalla gameOver y si llega a 1500 puntos gana en ese mundo
		for (IEnemic ememi : enemigosZ) {
			if (ememi.choque(jugadorR)) {
				cityMusic.stop();
				juego.setScreen(new DGameoOver(juego, puntostotal));
			}
		}
		
		if(puntostotal==500) {
			cityMusic.stop();
			juego.setScreen(new EGameWinner(juego, puntostotal));
		}
	}

	@Override
	public void dispose() {
		texturaJugador.dispose();
		texturaEnemigo.dispose();
		texturaDisparo.dispose();
		cityMusic.dispose();
		texturaFondo.dispose();
		enemiDeadSound.dispose();
		hud.stage.dispose();
	}

}
