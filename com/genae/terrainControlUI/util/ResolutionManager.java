package com.genae.terrainControlUI.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import com.genae.terrainControlUI.main.Main;

public class ResolutionManager 
{
	public static double multiplier = Main.config.multiplier;
	public static Toolkit tk;
	static
	{
		tk = Toolkit.getDefaultToolkit();
	}
	public static int pixel(int px)
	{
		return (int) (px*(tk.getScreenSize().width/1920.0/2 + 0.5)*multiplier);
	}
	public static Dimension pixel(Dimension d)
	{
		d.height = (int) (d.height*(tk.getScreenSize().height/1080.0/2 + 0.5)*multiplier);
		d.width = (int) (d.width*(tk.getScreenSize().width/1920.0/2 + 0.5)*multiplier);
		return d;
	}
	public static ImageIcon resize(ImageIcon icon, int width, int height)
	{
		Image img = icon.getImage();
		icon.setImage(resize(img, width, height));
		return icon;
	}
	public static Image resize(Image img, int width, int height)
	{
		double scaleW = width/(double)img.getWidth(null);
		double scaleH = height/(double)img.getHeight(null);
		double scale = scaleW < scaleH ? scaleW : scaleH;
		width = (int) (img.getWidth(null)*scale*(tk.getScreenSize().width/1920.0/2 + 0.5)*multiplier);
		height = (int) (img.getHeight(null)*scale*(tk.getScreenSize().height/1080.0/2 + 0.5)*multiplier);
		img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return img;
	}
	public static Font fontSize(Font f, int size)
	{
		size = (int)(size*((tk.getScreenSize().width/1920.0)/3.0 + 2/3.0)*multiplier);
		return f.deriveFont(Font.TRUETYPE_FONT, size); 
	}
}
