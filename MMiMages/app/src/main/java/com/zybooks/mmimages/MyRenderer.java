package com.zybooks.mmimages;

import android.view.MotionEvent;

import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.Object3D;

import android.content.Context;

public class MyRenderer extends RajawaliRenderer{

    public Context context;
    private DirectionalLight directionalLight;
    private Object3D object;

    public MyRenderer(Context context){
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    public void onTouchEvent(MotionEvent event){
    }

    @Override
    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }

    public void initScene(){

        //Add lighting to the scene
        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        //Load the txt file containing the obj values
        LoaderOBJ objParser = new LoaderOBJ(this, "test_obj.txt");

        //Parse instance of the obj file object
        try{
            objParser.parse();
            object = objParser.getParsedObject();

        }catch(Exception e){
            e.printStackTrace();
        }

        //Add material settings to the render
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);
        object.setMaterial(material);

        //Attach a texture to the rendering
        Texture objTexture = new Texture("white",R.drawable.white_texture);

        try{
            material.addTexture(objTexture);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Set camera
        getCurrentCamera().setZ(4.2f);

        //Attach rendering to the created scene
        getCurrentScene().addChild(object);
    }

    @Override
    public void onRender(final long elapsedTime, final double deltaTime){
        super.onRender(elapsedTime,deltaTime);
        object.rotate(Vector3.Axis.Y, 1.0);
    }

}
