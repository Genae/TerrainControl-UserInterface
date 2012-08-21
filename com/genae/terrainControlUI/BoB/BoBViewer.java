package com.genae.terrainControlUI.BoB;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class BoBViewer extends Applet
{
	private static final long serialVersionUID = 1L;
	public static void main(String[] args)
	{
		new BoBViewer();
	}
	public BoBViewer()
	{
		setLayout(new BorderLayout());
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add(canvas3D, BorderLayout.CENTER);
		
		BranchGroup scene = createSceneGraph();
		scene.compile();
		
		SimpleUniverse simpleU = new SimpleUniverse();
		
		simpleU.getViewingPlatform().setNominalViewingTransform();
		
		simpleU.addBranchGraph(scene);
	}
	public BranchGroup createSceneGraph()
	{
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(new ColorCube(0.4));
		return objRoot;
	}
}

