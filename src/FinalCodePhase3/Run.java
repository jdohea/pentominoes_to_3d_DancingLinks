package FinalCodePhase3;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import static FinalCodePhase3.DancingLinks.*;

public class Run extends Application{
    public static String [] arguments;

    //truck made of small boxes
    private static final int BOXSIZE = 5;

    //starting left-up-front coordinates
    private int xCoord = 0;
    private int yCoord = 0;
    private int zCoord = 0;

    private static final int WIDTH = Parcels.CONTAINER_Z_DIM * BOXSIZE; //z = i
    private static final int HEIGHT = Parcels.CONTAINER_Y_DIM * BOXSIZE; //y = j
    private static final int DEPTH = Parcels.CONTAINER_X_DIM * BOXSIZE; //x = k

    private Scene scene;

    //counters for key events
    private int counterU=0;
    private int counterL=0;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public static int[][][]field = new int [5][8][33];

    public Run(){

        Group mainGroup = new Group();

        //fill randomly the field
        Box[][][] cargo = new Box[field.length][field[0].length][field[0][0].length];

        //creation of an empty cargo made of small boxes
        for (int i = 0; i < cargo.length; i++) {
            for (int j = 0; j < cargo[0].length; j++) {
                for (int k = 0; k < cargo[0][0].length; k++) {
                    cargo[i][j][k] = new Box(BOXSIZE , BOXSIZE, BOXSIZE);
                    cargo[i][j][k].setTranslateX(xCoord);
                    cargo[i][j][k].setTranslateY(yCoord);
                    cargo[i][j][k].setTranslateZ(zCoord);
                    xCoord += (BOXSIZE);
                }
                xCoord = 0;
                yCoord += (BOXSIZE);
            }
            yCoord = 0;
            zCoord += (BOXSIZE);
        }
        for (int i = 0; i < cargo.length; i++) {
            for (int j = 0; j < cargo[0].length; j++) {
                for (int k = 0; k < cargo[0][0].length; k++) {
                    mainGroup.getChildren().add(cargo[i][j][k]);

                    if (field[i][j][k] == 1) {
                        cargo[i][j][k].setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                    }
                    if (field[i][j][k] == 2) {
                        cargo[i][j][k].setMaterial(new PhongMaterial(Color.RED));
                    }
                    if (field[i][j][k] == 3) {
                        cargo[i][j][k].setMaterial(new PhongMaterial(Color.YELLOW));
                    }
                    if(field[i][j][k] == 0){
                        Color transparent = new Color(0, 0, 0, 0);
                        cargo[i][j][k].setMaterial(new PhongMaterial(transparent));
                    }

                }
            }
        }

        String contentLabel="";
        if(!Parcels.useLPT){
            contentLabel = "This truck has been filled with "+bestNoOnes+" A, "+
                    bestNoTwos+" B and "+bestNoThrees+" C.\nTheir values are respectively "
                    +pieceOneValue+", "+pieceTwoValue+", "+pieceThreeValue+" which gives a total of "+currentBestVal;
        }else {
            contentLabel = "This truck has been filled with "+bestNoOnes+" T, "+
                    bestNoTwos+" L and "+bestNoThrees+" P.\nTheir values are respectively "
                    +pieceOneValue+", "+pieceTwoValue+", "+pieceThreeValue+" which gives a total of "+currentBestVal;
        }
        Label stats = new Label(contentLabel);
        stats.setTranslateX(-400);
        stats.setTranslateY(-200);
        stats.setTranslateZ(500);
        Group groupWithLabels = new Group();
        groupWithLabels.getChildren().addAll(stats, mainGroup);

        // position the shapes in the screen not too small not too big not too left not too right
        Camera camera = new PerspectiveCamera(true);
        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-500);
        camera.setNearClip(1);
        camera.setFarClip(1000);

        //add everything to the scene and show it
        scene = new Scene(groupWithLabels, 1500, 800, true);
        scene.setCamera(camera);

        mainGroup.translateXProperty().set(0);
        mainGroup.translateYProperty().set(0);
        mainGroup.translateZProperty().set(0);

        initMouse(mainGroup, scene);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.U) {
                removeUpLayer(cargo);
                counterU++;
            }
            if (e.getCode() == KeyCode.I) {
                replaceUpLayer(cargo);
                counterU--;
            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Result of Phase 3");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public void removeUpLayer(Box[][][] cargo){

        int upperLayer = HEIGHT/BOXSIZE - (HEIGHT/BOXSIZE- counterU);
        System.out.println("UP: "+upperLayer);

        for(int i = 0; i<cargo.length; i++){
            for(int k = 0; k<cargo[0][0].length; k++){
                //System.out.println("coords: "+i+" "+upperLayer+" "+k);
                Color transparent = new Color(0, 0, 0, 0);
                cargo[i][upperLayer][k].setMaterial(new PhongMaterial(transparent));
            }
        }
    }

    public void replaceUpLayer(Box[][][] cargo){

        int upperLayer = HEIGHT/BOXSIZE - (HEIGHT/BOXSIZE- counterU);
        int layerUpReplace = upperLayer - 1;

        System.out.println("to replace: "+layerUpReplace+" counterU: "+counterU+" upper layer: "+upperLayer);

        for(int i = 0; i<cargo.length; i++){
            for(int k = 0; k<cargo[0][0].length; k++){
                Color color = new Color(0,0,0,0);
                if (field[i][layerUpReplace][k] == 1) {
                    color = Color.LIGHTBLUE;
                }
                if (field[i][layerUpReplace][k] == 2) {
                    color = Color.RED;
                }
                if (field[i][layerUpReplace][k] == 3) {
                    color = Color.YELLOW;
                }
                if(field[i][layerUpReplace][k] == 0){
                    Color transparent = new Color(0, 0, 0, 0);
                    color = transparent;
                }
                //System.out.println("coords: "+i+" "+upperLayer+" "+k);
                cargo[i][layerUpReplace][k].setMaterial(new PhongMaterial(color));
            }
        }
    }

    private void initMouse(Group group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }

    public  static void launchProgram(){
        UI.frame.setVisible(false);
        Application.launch(arguments);
    }
}