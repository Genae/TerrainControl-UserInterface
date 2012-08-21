package com.genae.terrainControlUI.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.genae.terrainControlUI.inputLabels.*;
import com.genae.terrainControlUI.util.ResolutionManager;

public class InputTab extends JPanel 
{
	static final long serialVersionUID = 1L;
	JPanel panel;
	JScrollPane scrollPane;
	HashMap<String, InputBlockChooser> inputBlockChooser;
	HashMap<String, InputCheckbox> inputCheckbox;
	HashMap<String, InputColor> inputColor;
	HashMap<String, InputComboBox<?>> inputComboBox;
	HashMap<String, InputFile> inputFile;
	HashMap<String, InputHeight> inputHeight;
	HashMap<String, InputList> inputList;
	HashMap<String, InputReplaceBlocks> inputReplaceBlocks;
	HashMap<String, InputSlider> inputSlider;
	HashMap<String, InputBlockList> inputBlockList;
	HashMap<String, InputMobChooser> inputMobChooser;
	HashMap<String, InputTreeChance> inputTreeChance;
	ArrayList<Component> labels;

	public InputTab(int amount)
	{
		/////////////////////
		//Init Elements
		int number = (amount < 10) ? 10 : amount;
		
		panel = new JPanel(new GridLayout(number, 1, 5, 5));
		panel.setPreferredSize(ResolutionManager.pixel(new Dimension(850, number * 50)));
		scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(ResolutionManager.pixel(new Dimension(870, 540)));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		labels = new ArrayList<Component>();
		
	}
	public InputTab(int amount, int width)
	{
		/////////////////////
		//Init Elements
		
		panel = new JPanel(new GridLayout(amount, 1, 5, 5));
		panel.setPreferredSize(ResolutionManager.pixel(new Dimension(width-20, amount * 50)));
		scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(ResolutionManager.pixel(new Dimension(width, 540)));
		scrollPane.getVerticalScrollBar().setUnitIncrement(ResolutionManager.pixel(16));
		labels = new ArrayList<Component>();
		
	}
	
	public void finalize()
	{
		/////////////////////
		//Finally
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void add(String key, InputBlockChooser input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputBlockChooser == null)
		{
			inputBlockChooser = new HashMap<String, InputBlockChooser>();
		}
		inputBlockChooser.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputCheckbox input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputCheckbox == null)
		{
			inputCheckbox = new HashMap<String, InputCheckbox>();
		}
		inputCheckbox.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputBlockList input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputBlockList == null)
		{
			inputBlockList = new HashMap<String, InputBlockList>();
		}
		inputBlockList.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputColor input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputColor == null)
		{
			inputColor = new HashMap<String, InputColor>();
		}
		inputColor.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputComboBox<String> input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputComboBox == null)
		{
			inputComboBox = new HashMap<String, InputComboBox<?>>();
		}
		inputComboBox.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputFile input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputFile == null)
		{
			inputFile = new HashMap<String, InputFile>();
		}
		inputFile.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputHeight input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputHeight == null)
		{
			inputHeight = new HashMap<String, InputHeight>();
		}
		inputHeight.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputList input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputList == null)
		{
			inputList = new HashMap<String, InputList>();
		}
		inputList.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputReplaceBlocks input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputReplaceBlocks == null)
		{
			inputReplaceBlocks = new HashMap<String, InputReplaceBlocks>();
		}
		inputReplaceBlocks.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputSlider input, String resource)
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputSlider == null)
		{
			inputSlider = new HashMap<String, InputSlider>();
		}
		inputSlider.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputMobChooser input, String resource) 
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputSlider == null)
		{
			inputMobChooser = new HashMap<String, InputMobChooser>();
		}
		inputMobChooser.put(key, input);
		labels.add(label);
	}
	public void add(String key, InputTreeChance input, String resource) 
	{
		InputLabel label = new InputLabel(input, key, resource);
		panel.add(label);
		if(inputTreeChance == null)
		{
			inputTreeChance = new HashMap<String, InputTreeChance>();
		}
		inputTreeChance.put(key, input);
		labels.add(label);
	}
	public InputMobChooser getMobChooser(String name)
	{
		return inputMobChooser.get(name);
	}
	public InputBlockChooser getBlockChooser(String name)
	{
		return inputBlockChooser.get(name);
	}
	public InputBlockList getBlockList(String name)
	{
		return inputBlockList.get(name);
	}
	public InputCheckbox getCheckbox(String name)
	{
		return inputCheckbox.get(name);
	}
	public InputColor getColor(String name)
	{
		return inputColor.get(name);
	}
	public InputComboBox<?> getComboBox(String name)
	{
		return inputComboBox.get(name);
	}
	public InputFile getFile(String name)
	{
		return inputFile.get(name);
	}
	public InputHeight getHeightControl(String name)
	{
		return inputHeight.get(name);
	}
	public InputList getList(String name)
	{
		return inputList.get(name);
	}
	public InputReplaceBlocks getReplaceBlocks(String name)
	{
		return inputReplaceBlocks.get(name);
	}
	public InputSlider getSlider(String name)
	{
		return inputSlider.get(name);
	}
	public InputTreeChance getTreeChance(String name)
	{
		return inputTreeChance.get(name);
	}
	public Dimension removeDisabled() 
	{
		int amount = 0;
		for(Component label : labels)
		{
			if(label.isEnabled()) amount++;
		}
		panel.removeAll();
		panel.setLayout(new GridLayout(amount, 1, 5, 5));
		panel.setPreferredSize(new Dimension(570, amount * 50));
		for(int i = 0; i < labels.size(); i++)
		{
			if(labels.get(i).isEnabled())
			{
				panel.add(labels.get(i));
			}
		}
		return new Dimension(620, amount * 50 + 170);
	}
}
