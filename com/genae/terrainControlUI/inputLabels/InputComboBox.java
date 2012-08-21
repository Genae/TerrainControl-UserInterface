package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;

//////////////////////////////////////////////////
//												//
//		The InputColor is used for getting		//
//		one of multiple predefined values		//
//		Uses a Drop-Down menu.					//
//												//
//////////////////////////////////////////////////

public class InputComboBox<T> extends JPanel
{
	private static final long serialVersionUID = 1L;
	public JComboBox<Object> box;
	
	public InputComboBox(Object[] options)
	{
		box = new JComboBox<Object>(options);
		box.setFont(Main.optionFont);
		setLayout(new BorderLayout(0, 10));
		add(box, BorderLayout.CENTER);
	}

	public String getString() 
	{
		return box.getSelectedItem().toString();
	}

	public void setValue(String value) 
	{
		box.setSelectedItem(value);
	}
	public void addActionListener(ActionListener al) 
	{
		box.addActionListener(al);
	}
	public void setEnabled(boolean b)
	{
		box.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return box.isEnabled();
	}
}
