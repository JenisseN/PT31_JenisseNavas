package scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import constantes.Constants;


public class Hud {
	//Stage y su propio Viewport para el HUD
    public Stage stage;
    private Viewport viewport;

    private Integer vidas;
    private static int puntos;

    //Scene2D widgets
    private Label vidasNumLabel;
    private static Label puntosLabel;
    private Label vidasLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label supervivienteLabel;

    
    public Hud(SpriteBatch batch){
        //definicion de variables
        vidas = 3;
        puntos = 0;

        //Configuracion del viewport usando una nueva camara separa de la camara del juego
        //definicion de nuestro stage usando el viewport y nuestro spritebatch del juego
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        //Tablas que organizan los labels del HUD
        Table table = new Table();
        table.top();
        //Hace a la tabla hija de el stage entero 
        table.setFillParent(true);
        
        //Label usando string y style de color y fuente
        vidasNumLabel = new Label(String.format("%01d", vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        puntosLabel =new Label(String.format("%01d", puntos), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        vidasLabel = new Label("Vidas", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Mundo", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        supervivienteLabel = new Label("Puntos", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Agregar nuestras labels en la tabla, posicion:padding top,y darle a todas un width con expandX
        table.add(supervivienteLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(vidasLabel).expandX().padTop(10);
        
        //agregar una segunda fila a la tabla 
        table.row();
        table.add(puntosLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(vidasNumLabel).expandX();

        //Agregar la tabla a el stage
        stage.addActor(table);

    }
    

    public static void addScore(int value){
       puntos += value;
       puntosLabel.setText(String.format("%01d", puntos));
    }

    public void dispose() { 
    	stage.dispose(); 
    }


}
    
    



