package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

public class InputReplaceBlocks extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton selected, newOne, edit, delete, ok, cancel;
	JPanel blocks, buttons;
	JFrame popupList, popupReplace;
	JTable replaces;
	DefaultTableModel table;
	JScrollPane scroll;
	InputBlockChooser from, to;
	InputSlider min, max;
	HashMap<Integer, String> ids;
	HashMap<String, String> typs;
	ImageIcon[] imgs;
	Font small = ResolutionManager.fontSize(Main.font, 15);
	boolean editBoolean = false;
	
	public InputReplaceBlocks(int def)
	{
		selected = new JButton("0 replaces");
		selected.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(selected);
		
		popupList = new JFrame("Add new Replaces here");
		popupList.setSize(ResolutionManager.pixel(new Dimension(400, 400)));
		popupList.setLocation(ResolutionManager.pixel(300), ResolutionManager.pixel(300));
		popupList.setLayout(new BorderLayout());
		
		table = new DefaultTableModel(new String[][]{}, new String[]{"Block", "replaced by", "min Height", "max Height"});
		replaces = new JTable(table);
		replaces.setPreferredScrollableViewportSize(ResolutionManager.pixel(new Dimension(300, 300)));
		replaces.setFont(small);
		replaces.getTableHeader().setFont(small);
		
		scroll = new JScrollPane(replaces);
		
		buttons = new JPanel(new GridLayout(1, 3, 5, 5));
		newOne = new JButton("New");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		newOne.setFont(Main.optionFont);
		edit.setFont(Main.optionFont);
		delete.setFont(Main.optionFont);
		buttons.add(newOne);
		buttons.add(edit);
		buttons.add(delete);
		
		popupList.add(scroll, BorderLayout.CENTER);
		popupList.add(buttons, BorderLayout.PAGE_END);

		
		popupReplace = new JFrame("Choose Replaces");
		popupReplace.setSize(ResolutionManager.pixel(new Dimension(450, 250)));
		popupReplace.setLocation(ResolutionManager.pixel(350), ResolutionManager.pixel(350));
		popupReplace.setLayout(new GridLayout(5, 2, 5, 5));
		
		ok = new JButton("ok");
		ok.setFont(Main.optionFont);
		cancel = new JButton("cancel");
		cancel.setFont(Main.optionFont);
		
		JLabel labelFrom = new JLabel("From");
		JLabel labelTo = new JLabel("To");
		JLabel labelMin = new JLabel("Min Height");
		JLabel labelMax = new JLabel("Max Height");
		labelFrom.setFont(Main.optionFont);
		labelTo.setFont(Main.optionFont);
		labelMin.setFont(Main.optionFont);
		labelMax.setFont(Main.optionFont);
		
		from = new InputBlockChooser(def, false);
		to = new InputBlockChooser(def, true);
		min = new InputSlider(0, 256, 0);
		max = new InputSlider(0, 256, 256);
		
		popupReplace.add(labelFrom);
		popupReplace.add(from);
		popupReplace.add(labelTo);
		popupReplace.add(to);
		popupReplace.add(labelMin);
		popupReplace.add(min);
		popupReplace.add(labelMax);
		popupReplace.add(max);
		popupReplace.add(ok);
		popupReplace.add(cancel);
		
		addListeners();
	}
	
	private void addListeners()
	{
		selected.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popupList.setVisible(true);
			}
			
		});
		newOne.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				from.setValue("12");
				to.setValue("12");
				min.setValue(0);
				max.setValue(256);
				popupReplace.setVisible(true);
			}
			
		});
		edit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(replaces.getSelectedRow() != -1)
				{
					int row = replaces.getSelectedRow();
					from.setValue(replaces.getValueAt(row, 0) + "");
					to.setValue(replaces.getValueAt(row, 1) + "");
					min.setValue(Integer.parseInt(replaces.getValueAt(row, 2) + ""));
					max.setValue(Integer.parseInt(replaces.getValueAt(row, 3) + ""));
					popupReplace.setVisible(true);
					editBoolean = true;
				}
			}
			
		});
		delete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(replaces.getSelectedRow() != -1)
				{
					table.removeRow(replaces.getSelectedRow());
				}
				selected.setText(table.getRowCount() + " replaces");
			}
			
		});
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popupReplace.setVisible(false);
				String[] data = new String[4];
				data[0] = from.getString();
				data[1] = to.getString();
				data[2] = min.getInt() + "";
				data[3] = max.getInt() + "";
				if(!editBoolean)
				{
					table.insertRow(table.getRowCount(), data);
				}
				else
				{
					int row = replaces.getSelectedRow();
					table.removeRow(row);
					table.insertRow(row, data);
					editBoolean = false;
				}
				selected.setText("saving");
				selected.setText(table.getRowCount() + " replaces");
			}
			
		});
		cancel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popupReplace.setVisible(false);
			}
			
		});
	}

	public String getString() 
	{
		String line = "";
		boolean first = true;
		for(int i = 0; i < table.getRowCount(); i++)
		{
			if(!first) line += ",";
			line += replaces.getValueAt(i, 0) + "=" + replaces.getValueAt(i, 1);
			if(Integer.parseInt(replaces.getValueAt(i, 2).toString()) != 0 || Integer.parseInt(replaces.getValueAt(i, 3).toString()) != 256)
			{
				line += "(" + replaces.getValueAt(i, 2) + "-" + replaces.getValueAt(i, 3) + ")";
			}
			first = false;
		}
		System.out.println(line);
		if(line.equals("")) return "NONE";
		return line;
	}

	public short[][] getShortArray()
	{
		short[][] array = new short[0][0];
		
		for(int r = 0; r <table.getRowCount(); r++)
		{		
			String s = replaces.getValueAt(r, 1).toString();
			int blockId = 0;
		    int blockData = 0;
		    int fromBlockId = Integer.parseInt(replaces.getValueAt(r, 1).toString());
			if(s.contains("."))
			{
				String[] str = s.split(".");
				blockId = Integer.parseInt(str[0]);
				blockData = Integer.parseInt(str[1]);
			}
			int min = Integer.parseInt(replaces.getValueAt(r, 2).toString());
			int max = Integer.parseInt(replaces.getValueAt(r, 3).toString());

               
            if (array[fromBlockId] == null)
            {
            	array[fromBlockId] = new short[256];
                for (int i = 0; i < 256; i++)
                {
                    array[fromBlockId][i] = -1;
                }
            }
            for (int y = min; y < max; y++)
            {
                array[fromBlockId][y] = (short) (blockId << 4 | blockData);
            }

		}
		return array;
	}
	public void setValue(short[][] array)
	{

	}
	public void setValue(String value)
	{
		try
		{
			while(table.getRowCount() != 0)
			{
				table.removeRow(0);
				System.out.println("row removed");
			}
			if(!value.equals("None"))
			{
				int start = 0;
				int end = 0;
				String row = "";
				String to = "";
				String min = "";
				String max = "";
				boolean last = false;
				while(!last)
				{
					if(value.indexOf(",", start) == -1)
					{
						end = value.length();
						last = true;
					}
					else
					{
						end = value.indexOf(",", start);
					}
					row = value.substring(start, end);
					String from = row.substring(0, row.indexOf("="));
					if(row.contains("("))
					{
						to = row.substring(row.indexOf("=") + 1, row.indexOf("("));
						min = row.substring(row.indexOf("(") + 1, row.indexOf("-"));
						max = row.substring(row.indexOf("-") + 1, row.indexOf(")"));
						table.addRow(new String[]{from, to, min, max});
					}
					else
					{
						to = row.substring(row.indexOf("=") + 1, row.length());
						table.addRow(new String[]{from, to, "0", "256"});
					}
					start = ++end;
				}
			}
			selected.setText(table.getRowCount() + " replaces");
		}
		catch(Exception e)
		{
			System.out.println("ReplaceBlock had problems setting " + value);
		}
	}

	public void addActionListener(ActionListener al) 
	{
		ok.addActionListener(al);
		delete.addActionListener(al);
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
