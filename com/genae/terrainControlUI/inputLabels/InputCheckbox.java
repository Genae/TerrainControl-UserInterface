package com.genae.terrainControlUI.inputLabels;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

//////////////////////////////////////////////////
//												//
//		The InputCheckbox is handling the		//
//		boolean Values. It has a default		//
//		value and outputs a String (true/		//
//		false)									//
//												//
//////////////////////////////////////////////////

public class InputCheckbox extends JPanel
{
	private static final long serialVersionUID = 1L;
	JCheckBox check;
	
	public InputCheckbox(boolean def)
	{
		check = new JCheckBox();
		check.setSelected(def);
		add(check);
	}
	public boolean getValue() 
	{
		return check.isSelected();
	}
	public void setValue(boolean value) 
	{
		check.setSelected(value);
	}
	public void addActionListener(ActionListener al) 
	{
		check.addActionListener(al);
	}
	public void setEnabled(boolean b)
	{
		check.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return check.isEnabled();
	}
}
