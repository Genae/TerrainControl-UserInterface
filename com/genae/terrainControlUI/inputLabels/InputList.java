package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.generator.resourcegens.TreeType;


//////////////////////////////////////////////////
//												//
//		The InputList is used for getting		//
//		a multiple Selection of e.g. items.	    //
//		The Button showed in the List 			//
//		tells the amount of selected			//
//		Items, while a popup uses checkBoxes	//
//		for the Selection. Outputs a String		//
//		ready for the Config					//
//												//
//////////////////////////////////////////////////

public class InputList extends JPanel
{
	private static final long serialVersionUID = 1L;
	public JCheckBox[] boxes;
	String[] items;
	boolean[] selection;
	boolean[] labels;
	Font f = Main.optionFont;
	JDialog popup;
	JPanel panel, buttons;
	int amount = 0;
	JButton selected;
	public JButton ok;
	JButton cancel;
	String text = " items selected";
	ActionListener al;
	
	public InputList(String[] items)
	{
		this.items = items;
		labels = new boolean[items.length];
		construct();
	}
	public InputList(String[] items, boolean[] selected)
	{
		this.items = items;
		this.selection = selected;
		labels = new boolean[items.length];
		construct();
	}
	public InputList(String[] items, String text)
	{
		this.items = items;
		this.text = text;
		labels = new boolean[items.length];
		construct();
	}
	public InputList(ArrayList<String> items)
	{
		
		this.items = new String[items.size()];
		for(int i = 0; i < items.size(); i++)
		{
			this.items[i] = items.get(i).toString();
		}
		construct();
		
	}
	public void insert(String s, boolean label)
	{
		for(String str : items)
		{
			if(str.equals(s)) return;
		}
		String[] items = new String[this.items.length + 1];
		boolean[] labels = new boolean[this.items.length + 1];
		boolean[] select = new boolean[this.items.length + 1];
		for(int i = 0; i < this.items.length; i++)
		{
			items[i] = this.items[i];
			labels[i] = this.labels[i];
			select[i] = this.selection[i];
		}
		items[items.length-1] = s;
		labels[items.length-1] = label;
		select[items.length-1] = true;
		this.items = items;
		this.labels = labels;
		this.selection = select;
		removeAll();
		construct();
	}
	private void construct()
	{

		popup = new JDialog();
		popup.setTitle("Choose, what you want");
		popup.setLayout(new BorderLayout());
		panel = new JPanel(new GridLayout(items.length/3 + 1, 3));
		buttons = new JPanel(new GridLayout(1, 2));
		popup.setSize(ResolutionManager.pixel(new Dimension(500, (items.length/3 + 1) * 40 + 70)));
		popup.setLocation(ResolutionManager.pixel(400), ResolutionManager.pixel(400));
		amount = items.length;
		selected = new JButton(amount + text);
		selected.setFont(f);
		ok = new JButton("OK");
		ok.setFont(f);
		cancel = new JButton("cancel");
		cancel.setFont(f);
		buttons.add(ok);
		buttons.add(cancel);
		boxes = new JCheckBox[items.length];
		if(selection == null)
		{
			selection = new boolean[items.length];
			for(int i = 0; i < selection.length; i++)
			{
				selection[i] = true;
			}
		}
		setLayout(new BorderLayout());
		add(selected, BorderLayout.CENTER);
		for(int i = 0; i < items.length; i++)
		{
			boxes[i] = new JCheckBox(items[i]);
			boxes[i].setSelected(selection[i]);
			if(labels[i])boxes[i].setEnabled(false);
			boxes[i].setFont(ResolutionManager.fontSize(Main.font, 20));
			panel.add(boxes[i]);
		}
		
		selected.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				displayList();
			}
			
		});
		
		cancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				for(int i = 0; i < selection.length; i++)
				{
					boxes[i].setSelected(selection[i]);
					popup.setVisible(false);
				}
			}
			
			
		});
		
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				amount = 0;
				for(int i = 0; i < selection.length; i++)
				{
					selection[i] = boxes[i].isSelected();
					if(boxes[i].isSelected()) amount++;
					popup.setVisible(false);
				}
				selected.setText(amount + text);
			}
			
			
			
		});
		
		popup.add(panel, BorderLayout.CENTER);
		popup.add(buttons, BorderLayout.PAGE_END);
	}
	
	public void displayList()
	{
		popup.setVisible(true);
	}
	public String getString() 
	{
		String str = "";
		for(int i = 0; i < selection.length; i++)
		{
			if(selection[i])
			{
					str += items[i] + ",";	
			}
		}
		return str.substring(0, str.length()-2);
	}
	public String[] getArray()
	{
		ArrayList<String> strings = new ArrayList<String>();
		for(int i = 0; i < selection.length; i++)
		{
			if(selection[i])
			{
					strings.add(items[i]);
			}
		}
		return strings.toArray(new String[1]);
	}
	public TreeType[] getTreeTypes()
	{
		ArrayList<TreeType> treeType = new ArrayList<TreeType>();
		for(int i = 0; i < selection.length; i++)
		{
			if(selection[i])
			{
				treeType.add(TreeType.valueOf(items[i]));
			}
		}
		return treeType.toArray(new TreeType[1]);
	}
	public ArrayList<String> getArrayList()
	{
		ArrayList<String> strings = new ArrayList<String>();
		for(int i = 0; i < selection.length; i++)
		{
			if(selection[i])
			{
					strings.add(items[i]);
			}
		}
		return strings;
	}

	public void setValue(String value) 
	{
		String[] array = value.split(",");
		setValue(array);
	}
	
	public void setValue(String[] array)
	{
		for(int i = 0; i < items.length; i++)
		{
			boxes[i].setSelected(false);
		}
		for(String s : array)
		{
			for(int i = 0; i < items.length; i++)
			{
				if(items[i].equals(s))
				{
					boxes[i].setSelected(true);
				}
			}
		}
		amount = 0;
		for(int i = 0; i < selection.length; i++)
		{
			selection[i] = boxes[i].isSelected();
			if(boxes[i].isSelected()) amount++;
			popup.setVisible(false);
		}
		selected.setText(amount + text);
	}
	public void setValue(TreeType[] types)
	{
		String[] strings = new String[types.length];
		int i = 0;
		for(TreeType type:types)
		{
			strings[i++] = type.toString();
		}
		setValue(strings);
		text = " Trees selected";
	}

	public void addActionListener(ActionListener al) 
	{
		this.al = al;
		ok.addActionListener(al);
	}
	public void removeBox(String boxText)
	{
		int index = -1;
		for(int i = 0; i < boxes.length; i++)
		{
			if(boxes[i].getText().equals(boxText))
			{
				index = i;
			
				JCheckBox[] newBoxes = new JCheckBox[boxes.length-1];
				boolean[] newSelection = new boolean[boxes.length-1];
				boolean[] newLabels = new boolean[boxes.length-1];
				String[] newItems = new String[boxes.length-1];
				int oldOne = 0;
				int newOne = 0;
				while(newOne < newBoxes.length)
				{
					if(oldOne == index) oldOne++;
					newBoxes[newOne] = boxes[oldOne];
					newSelection[newOne] = selection[oldOne];
					newLabels[newOne] = labels[oldOne];
					newItems[newOne] = items[oldOne];
					oldOne++;
					newOne++;
				}
				boxes = newBoxes;
				selection = newSelection;
				labels = newLabels;
				items = newItems;
				panel.remove(index);
				amount--;
				selected.setText(amount + text);
			}
		}
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
