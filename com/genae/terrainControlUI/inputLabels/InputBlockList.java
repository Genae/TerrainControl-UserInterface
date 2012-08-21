package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

public class InputBlockList extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton selected, newOne, edit, delete, ok, cancel;
	JPanel blocks, buttons;
	JDialog popupList, popupReplace;
	JTable replaces;
	DefaultTableModel table;
	JScrollPane scroll;
	InputBlockChooser chooser;
	HashMap<Integer, String> ids;
	HashMap<String, String> typs;
	ImageIcon[] imgs;
	boolean editBoolean = false;
	
	public InputBlockList(int def)
	{
		selected = new JButton("0 replaces");
		selected.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(selected);
		
		popupList = new JDialog();
		popupList.setSize(ResolutionManager.pixel(new Dimension(400, 400)));
		popupList.setLocation(ResolutionManager.pixel(300), ResolutionManager.pixel(300));
		popupList.setLayout(new BorderLayout());
		
		table = new DefaultTableModel(new String[][]{}, new String[]{"Block"});
		replaces = new JTable(table);
		replaces.setPreferredScrollableViewportSize(ResolutionManager.pixel(new Dimension(300, 300)));
		
		scroll = new JScrollPane(replaces);
		
		buttons = new JPanel(new GridLayout(1, 3, 5, 5));
		newOne = new JButton("New");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		buttons.add(newOne);
		buttons.add(edit);
		buttons.add(delete);
		
		popupList.add(scroll, BorderLayout.CENTER);
		popupList.add(buttons, BorderLayout.PAGE_END);

		chooser = new InputBlockChooser(def, false, this);
		
		popupReplace = chooser.popup;
		
		ok = new JButton("ok");
		cancel = new JButton("cancel");
		
		
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
				chooser.setValue("12");
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
					chooser.setValue(replaces.getValueAt(row, 0) + "");
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
	}

	public void choosen(int id, int typ)
	{
		popupReplace.setVisible(false);
		String[] data = new String[1];
		data[0] = id + "";
		if(typ != -1) data[0] = id + "." + typ;
		
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
		selected.setText(table.getRowCount() + " blocks");
	}

	public int[] getValue() 
	{
		int[] ids = new int[table.getRowCount()];
		for(int i = 0; i < table.getRowCount(); i++)
		{
			ids[i] = Integer.parseInt(replaces.getValueAt(i, 0).toString());
		}
		return ids;
	}

	public void setValue(int[] value)
	{
		while(table.getRowCount() != 0)
		{
			table.removeRow(0);
			System.out.println("row removed");
		}
		for(int i = 0; i < value.length; i++)
		{
			table.addRow(new String[]{value[i] + ""});
		}
		selected.setText(table.getRowCount() + " blocks");
	}
	public void setValue(String value)
	{
		while(table.getRowCount() != 0)
		{
			table.removeRow(0);
			System.out.println("row removed");
		}
		if(!value.equals(""))
		{
			int start = 0;
			int end = 0;
			String row = "";
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
				table.addRow(new String[]{row});
				start = ++end;
			}
		}
		selected.setText(table.getRowCount() + " blocks");
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