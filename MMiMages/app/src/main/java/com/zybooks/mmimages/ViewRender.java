package com.zybooks.mmimages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ActionBar;
import android.view.ViewGroup;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class ViewRender extends AppCompatActivity {

    MyRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_render);

        //Create a new surface view
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        //Attach rendering to surface view
        renderer = new MyRenderer(this);
        surface.setSurfaceRenderer(renderer);
    }
}

