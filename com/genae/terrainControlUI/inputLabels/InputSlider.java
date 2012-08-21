package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;


//////////////////////////////////////////////////
//												//
//		The InputSlider is used for getting		//
//		int or double values. It uses a			//
//		Slider and an TextField for Input.		//
//		To get double Values, you have to		//
//		use increased Borders and the Divisor	//
//		low = 0, high = 100, div = 100 ->		//
//		values from 0.00 to 1.00				//
//												//
//		ToDo:									//
//		- warn if TextField goes over 			//
//		  Borders but allow it by increasing	//
//		  them.									//
//		- change Borders with Generation		//
//		  Depth									//
//												//
//////////////////////////////////////////////////

public class InputSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	JSlider slider;
	JTextField txtBox;
	int low = 0, high = 1, defaultValue = 0, divide = 1;
	ActionListener al;
	public InputSlider(int low, int high, int defaultValue)
	{
		this.low = low;
		this.high = high;
		this.defaultValue = defaultValue;
		construct();
	}
	
	public InputSlider(int low, int high, int defaultValue, int div)
	{
		this.low = low;
		this.high = high;
		this.defaultValue = defaultValue;
		this.divide = div;
		construct();
	}

	
	private void construct()
	{
		slider = new JSlider(low, high);
		txtBox = new JTextField();
		txtBox.setPreferredSize(ResolutionManager.pixel(new Dimension(80, 50)));
		slider.setFont(Main.optionFont);
		txtBox.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(slider, BorderLayout.CENTER);
		add(txtBox, BorderLayout.LINE_END);
		
		
		///////////////////////////
		//fit Slider and TextField
		slider.addChangeListener(new ChangeListener()
		{

			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				if(divide != 1)txtBox.setText(((double)slider.getValue())/divide + "");
				else txtBox.setText(slider.getValue() + "");
				al.actionPerformed(new ActionEvent(this, 0, null));
			}
			
		});
		
		txtBox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				slider.setValue((int)(Double.parseDouble(txtBox.getText())*divide));
			}
			
		});
		al = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{

			}
			
		};

		txtBox.setText(defaultValue + "");
		slider.setValue(defaultValue/divide);

	}

	public int getInt()
	{
		return Integer.parseInt(txtBox.getText());
	}
	public float getFloat()
	{
		return Float.parseFloat(txtBox.getText());
	}
	public double getDouble()
	{
		return Double.parseDouble(txtBox.getText());
	}

	public void setValue(int i) 
	{
		txtBox.setText(i + "");
		slider.setValue(i);
	}
	public void setValue(float f) 
	{
		txtBox.setText(f + "");
		slider.setValue((int)(f * divide));
	}
	public void setValue(double d) 
	{
		txtBox.setText(d + "");
		slider.setValue((int)(d * divide));
	}


	public void addActionListener(ActionListener al) 
	{
		this.al = al;
		txtBox.addActionListener(this.al);
	}
	public void setMaxValue(int i, String name)
	{
		high = i;
		if(slider.getValue() <= i)
		{
			slider.setMaximum(i);
		}
		else
		{
			slider.setMaximum(i);
			JOptionPane.showMessageDialog(this, "The value " + name + " has been changed, because it was bigger than the new maxValue", "Value has changed", JOptionPane.WARNING_MESSAGE);
		}
	}
	public void setEnabled(boolean b)
	{
		slider.setEnabled(b);
		txtBox.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return slider.isEnabled();
	}

}