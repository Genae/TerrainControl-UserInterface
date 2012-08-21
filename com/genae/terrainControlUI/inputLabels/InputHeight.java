package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

public class InputHeight extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton selected;
	JDialog popup;
	InputSlider[] slider;
	JPanel buttons;
	JButton ok, cancel;
	double[] values;
	
	public InputHeight()
	{
		slider = new InputSlider[17];
		values = new double[slider.length];
		selected = new JButton("Height Control");
		popup = new JDialog();
		ok = new JButton("ok");
		ok.setFont(ResolutionManager.fontSize(Main.font, 25));
		cancel = new JButton("cancel");
		cancel.setFont(ResolutionManager.fontSize(Main.font, 25));
		buttons = new JPanel(new GridLayout(1, 2));
		popup.setLayout(new GridLayout(18, 1));
		for(int i = 0; i < slider.length; i++)
		{
			slider[i] = new InputSlider(-40000, 40000, 0, 10);
			popup.add(slider[i]);
		}
		buttons.add(ok);
		buttons.add(cancel);
		popup.add(buttons);
		popup.setSize(ResolutionManager.pixel(new Dimension(400, 600)));
		popup.setLocation(ResolutionManager.pixel(200), ResolutionManager.pixel(200));
		selected.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(selected);
		
		selected.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(true);	
			}
			
		});
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(false);
				for(int i = 0; i < slider.length; i++)
				{
					values[i] = slider[i].getFloat();
				}
			}
			
		});
		cancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(false);
			}
			
		});
	}

	public double[] getDoubleArray() 
	{
		return values;
	}
	public String getString()
	{
		String value = "";
		for(double d : values)
		{
			value += d + ",";
		}
		return value;
	}

	public void setValue(String value) 
	{
		String[] array = value.split(",");
		for(int i = 0; i < array.length; i++)
		{
			slider[i].setValue(Float.parseFloat(array[i]));
		}
		
	}
	public void setValue(double[] array) 
	{
		for(int i = 0; i < array.length; i++)
		{
			slider[i].setValue(array[i]);
		}
		
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
