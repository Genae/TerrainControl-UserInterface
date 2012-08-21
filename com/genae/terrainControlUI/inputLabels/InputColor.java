package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

//////////////////////////////////////////////////
//												//
//		The InputColor is handling the 			//
//		Color choosing. It supports				//
//		different predefined Input				//
//		options and outputs a Hex-String.		//
//		It uses a Button in the list			//
//		as well as a colored Panel and 			//
//		a Popup to select.						//
//												//
//////////////////////////////////////////////////


public class InputColor extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	Color color;
	JButton selected;
	JLabel colored;
	
	public InputColor(int def)
	{
		color = new Color(def);
		colored = new JLabel();
		colored.setPreferredSize(ResolutionManager.pixel(new Dimension(50, 50)));
		colored.setBackground(color);
		colored.setOpaque(true);
		selected = new JButton("0x" + Integer.toHexString(color.getRGB()).substring(2, 8));
		selected.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(selected, BorderLayout.CENTER);
		add(colored, BorderLayout.LINE_END);
		selected.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				chooseColor();
				
			}
			
		});
	}
	public void chooseColor()
	{
		Color ncolor = JColorChooser.showDialog(this, "Choose Color", color);
		if(ncolor!= null) color = ncolor;
		selected.setText("0x" + Integer.toHexString(color.getRGB()).substring(2, 8).toUpperCase());
		colored.setBackground(color);
	}
	public String getString() 
	{
		return "0x" + Integer.toHexString(color.getRGB()).substring(2, 8);
	}
	public int getInt()
	{
		return Integer.parseInt(Integer.toHexString(color.getRGB()).substring(2, 8), 16);
	}
	public void setValue(String value)
	{
		try
		{
			int def = Integer.parseInt(value.substring(2), 16);
			color = new Color(def);
			colored.setBackground(color);
			selected.setText("0x" + Integer.toHexString(color.getRGB()).substring(2, 8).toUpperCase());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void setValue(int value)
	{
		color = new Color(value);
		colored.setBackground(color);
		selected.setText("0x" + Integer.toHexString(color.getRGB()).substring(2, 8).toUpperCase());
	}
	
	public void addActionListener(ActionListener al) 
	{
		selected.addActionListener(al);
	}
	public void setEnabled(boolean b)
	{
		selected.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return selected.isEnabled();
	}
}
