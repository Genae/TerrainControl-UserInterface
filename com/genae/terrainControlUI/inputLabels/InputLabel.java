package com.genae.terrainControlUI.inputLabels;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;

public class InputLabel extends JPanel
{
	private static final long serialVersionUID = 1L;
	String key, labelText, tooltip;
	JLabel label;
	JPanel inputPanel;
	
	public InputLabel(JPanel inputPanel, String key, String text)
	{
		ResourceBundle labels = null;
		ResourceBundle tooltips = null;
		if(text.equals("world"))
		{
			labels = Main.worldLabels;
			tooltips = Main.worldTooltips;
		}
		else if(text.equals("biome"))
		{
			labels = Main.biomeLabels;
			tooltips = Main.biomeTooltips;
		}
		else if(text.equals("tree"))
		{
			labels = Main.treeLabels;
		}
		this.inputPanel = inputPanel;
		setLayout(new GridLayout(1,2));
		labelText = labels.getString(key);
		label = new JLabel(labelText);
		label.setFont(Main.optionFont);
		try
		{
			label.setToolTipText(tooltips.getString(key));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			label.setToolTipText(key);
		}
		add(label);
		add(inputPanel);
	}
	
	public void setText(String text)
	{
		label.setText(text);
	}
	@Override
	public boolean isEnabled()
	{
		return inputPanel.isEnabled();
	}
}
