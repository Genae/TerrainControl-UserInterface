package com.genae.terrainControlUI.inputLabels;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.generator.resourcegens.TreeType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

public class InputTreeChance extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton selected, ok, cancel;
	JPanel main, buttons;
	JDialog popup;
	InputList types;
	ArrayList<InputSlider> sliders;
	HashMap<String, Integer> values;
	String[] trees;
	public InputTreeChance(InputList treeTypes)
	{
		main = new JPanel();
		buttons = new JPanel(new GridLayout(1, 2, 10, 10));
		types = treeTypes;
		selected = new JButton("Chances");
		ok = new JButton("ok");
		cancel = new JButton("cancel");
		popup = new JDialog();
		sliders = new ArrayList<InputSlider>();
		values = new HashMap<String, Integer>();

		setLayout(new BorderLayout());
		selected.setFont(Main.optionFont);
		ok.setFont(Main.optionFont);
		cancel.setFont(Main.optionFont);
		
		buttons.add(ok);
		buttons.add(cancel);
		popup.add(main, BorderLayout.CENTER);
		popup.add(buttons, BorderLayout.PAGE_END);
		add(selected);
		
		popup.setSize(ResolutionManager.pixel(new Dimension(400, 400)));
		popup.setLocation(300, 300);
		
		selected.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				trees = types.getArray();
				main.removeAll();
				sliders.clear();
				int amount = trees.length;
				while(amount > sliders.size())
				{
					sliders.add(new InputSlider(0, 100, 10));
				}
				main.setLayout(new GridLayout(amount, 1));
				popup.setSize(ResolutionManager.pixel(new Dimension(400, amount*50 +100)));
				
				for(int i = 0; i < trees.length; i++)
				{
					main.add(new InputLabel(sliders.get(i), trees[i], "tree"));
					int value = 0;
					if(values.containsKey(trees[i]))		
					{
						value = values.get(trees[i]);
					}
					sliders.get(i).setValue(value);
				}
				popup.setVisible(true);
			}
			
		});
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				for(int  i = 0; i < trees.length; i++)
				{
					values.put(trees[i], sliders.get(i).getInt());
				}
				popup.setVisible(false);
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
	public void setValue(int[] values, TreeType[] types)
	{
		String[] strings = new String[types.length];
		int i = 0;
		for(TreeType type:types)
		{
			strings[i++] = type.toString();
		}

		for(i = 0; i < types.length; i++)
			this.values.put(strings[i], values[i]);
	}
	public int[] getIntArray()
	{
		int[] values = new int[trees.length];
		for(int i = 0; i < trees.length; i++)
		{
			values[i] = this.values.get(trees[i]);
		}
		return values;
	}
	public void setEnabled(boolean b)
	{
		selected.setEnabled(b);
	}
}
