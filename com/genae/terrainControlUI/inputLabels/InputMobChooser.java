package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EnumSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.DefaultMobType;

public class InputMobChooser extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton selected, ok, cancel;
	DefaultMobType mob;
	JTable list;
	DefaultTableModel table;
	JDialog popup;
	JScrollPane scroll;
	JPanel info, buttons;
	JLabel infoLabel, imgLabel;
	
	public InputMobChooser()
	{
		//init Objects
		info = new JPanel(new BorderLayout(10, 10));
		buttons = new JPanel(new GridLayout(1, 2));
		infoLabel = new JLabel();
		imgLabel = new JLabel();
		selected = new JButton(DefaultMobType.CREEPER.getFormalName());
		ok = new JButton("ok");
		cancel = new JButton("cancel");
		popup = new JDialog();
		table = new DefaultTableModel(new String[][]{}, new String[]{";)", "Monster"});
		list = new JTable(table)
		{
			private static final long serialVersionUID = 1L;
            public Class<?> getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        //insert ListItems
		for (DefaultMobType type : EnumSet.allOf(DefaultMobType.class))
		{
			table.addRow(new Object[]{Main.objManager.faces.get(type.getFormalName()), type.getFormalName()});
		}
		scroll= new JScrollPane(list);
		
		//LayoutStuff
		setLayout(new BorderLayout(10, 10));
		popup.setLayout(new BorderLayout());
		popup.setSize(ResolutionManager.pixel(new Dimension(600, 600)));
		popup.setLocation(ResolutionManager.pixel(200), ResolutionManager.pixel(200));
		list.setPreferredScrollableViewportSize(ResolutionManager.pixel(new Dimension(500, list.getRowCount()*50)));
		list.getColumnModel().getColumn(1).setPreferredWidth(400);
		list.getColumnModel().getColumn(0).setPreferredWidth(120);
		list.setRowHeight(ResolutionManager.pixel(70));
		info.setPreferredSize(ResolutionManager.pixel(new Dimension(200, 600)));
		JLabel placeholder = new JLabel();
		placeholder.setPreferredSize(ResolutionManager.pixel(new Dimension(10, 10)));

		
		//Fonts
		selected.setFont(Main.optionFont);
		list.setFont(Main.optionFont);
		list.getTableHeader().setFont(Main.optionFont);
		infoLabel.setFont(Main.optionFont);
		ok.setFont(Main.optionFont);
		cancel.setFont(Main.optionFont);
		
		//Default
		mob = DefaultMobType.CREEPER;
		list.changeSelection(0, 1, false, false);
		
		//adding
		add(selected, BorderLayout.CENTER);
		info.add(infoLabel, BorderLayout.PAGE_END);
		info.add(imgLabel, BorderLayout.CENTER);
		info.add(placeholder, BorderLayout.LINE_START);
		buttons.add(ok);
		buttons.add(cancel);
		popup.add(scroll, BorderLayout.CENTER);
		popup.add(info, BorderLayout.LINE_END);
		popup.add(buttons, BorderLayout.PAGE_END);
		
		
		//ActionListener
		selected.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(true);
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
		ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				mob = DefaultMobType.fromName(list.getValueAt(list.getSelectedRow(), 1).toString());
				selected.setText(mob.getFormalName());
				popup.setVisible(false);
			}
			
		});
		list.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent arg0) 
			{
				imgLabel.setIcon(Main.objManager.body.get(list.getValueAt(list.getSelectedRow(), 1).toString()));
				infoLabel.setText(Main.monsterTooltips.getString(list.getValueAt(list.getSelectedRow(), 1).toString()));
				list.setFocusable(false);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) 
			{
				list.setFocusable(true);
			}
			
		});
	}
	public void setValue(DefaultMobType mob) 
	{
		this.mob = mob;
		selected.setText(mob.getFormalName());
	}
	public String getString()
	{
		return mob.getFormalName();
	}
	public DefaultMobType getMobType()
	{
		return mob;
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
