package com.genae.terrainControlUI.biomePanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.genae.terrainControlUI.inputLabels.InputComboBox;
import com.genae.terrainControlUI.inputLabels.InputMobChooser;
import com.genae.terrainControlUI.inputLabels.InputSlider;
import com.genae.terrainControlUI.main.InputTab;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.DefaultMobType;
import com.khorn.terraincontrol.configuration.WeightedMobSpawnGroup;

public class InputMonster extends JPanel 
{
	private static final long serialVersionUID = 1L;
	ArrayList<MonsterGroupPack> monster;
	JTable list;
	DefaultTableModel table;
	JDialog popup;
	JPanel buttons;
	InputTab center;
	JButton add, cancel, move;
	JTextField moveTo;
	JCheckBox useHere, copySettings;
	int activeRow = 0;
	String biome = "";
	InputMonster me = this;
	
	public InputMonster()
	{
		//create Objects
		monster = new ArrayList<MonsterGroupPack>();
		table = new DefaultTableModel(new String[][]{}, new String[]{"*",";)", "Typ", "Monster"});
		list = new JTable(table)
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
            public Class<?> getColumnClass(int column)
            {
            	if(getValueAt(0, column) == null) return new Object().getClass();
                return getValueAt(0, column).getClass();
            }
        };

		list.setPreferredScrollableViewportSize(ResolutionManager.pixel(new Dimension(850, monster.size()*50)));
		list.getColumnModel().getColumn(3).setPreferredWidth(1000);
		list.getColumnModel().getColumn(2).setPreferredWidth(400);
		list.getColumnModel().getColumn(1).setPreferredWidth(120);
		JScrollPane scrollPane = new JScrollPane(list);
		popup = new JDialog();
		buttons = new JPanel();
		
		add = new JButton("add");
		cancel = new JButton("cancel");
		move = new JButton("move");
		moveTo = new JTextField();
		useHere = new JCheckBox("use Here");
		copySettings = new JCheckBox("Copy Settings");
		
		//Set Layout
		setLayout(new BorderLayout());
		popup.setLayout(new BorderLayout());
		
		//Set Preferences
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		list.setFont(Main.optionFont);
		list.getTableHeader().setFont(ResolutionManager.fontSize(Main.font, 35));
		list.setRowHeight(70);
		popup.setSize(ResolutionManager.pixel(new Dimension(600, 400)));
		popup.setLocation(200, 200);
		buttons.setLayout(new GridLayout(2, 4));
		add.setFont(Main.optionFont);
		cancel.setFont(Main.optionFont);
		moveTo.setFont(Main.optionFont);
		move.setFont(Main.optionFont);
		useHere.setFont(Main.optionFont);
		copySettings.setFont(Main.optionFont);
		
		//add
		center = new InputTab(5, 580);
		
		final InputMobChooser mobname = new InputMobChooser();
		center.add("mobname", mobname, "biome");
		final InputComboBox<String> type = new InputComboBox<String>(new String[]{MonsterType.Creature.string, MonsterType.Monster.string, MonsterType.WaterCreature.string});
		center.add("type", type, "biome");
		final InputSlider weight = new InputSlider(0, 64, 10);
		center.add("weight", weight, "biome");
		final InputSlider minAmount = new InputSlider(0, 64, 4);
		center.add("minAmount", minAmount, "biome");
		final InputSlider maxAmount = new InputSlider(0, 64, 4);
		center.add("maxAmount", maxAmount, "biome");
		center.finalize();

		add(scrollPane, BorderLayout.CENTER);

		buttons.add(moveTo);
		buttons.add(move);
		buttons.add(useHere);
		buttons.add(add);
		buttons.add(cancel);
		buttons.add(copySettings);
		popup.add(center, BorderLayout.CENTER);
		popup.add(buttons, BorderLayout.PAGE_END);
		
		//Button new
		table.insertRow(0, new Object[]{"", null, "", "add new Monster"});

		
		//ActionListeners
		copySettings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				monster.get(activeRow).setCopy(copySettings.isSelected());
			}
			
		});
		list.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) 
			{
				actionInRow(list.getSelectedRow());
				list.setFocusable(false);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) 
			{
				list.setFocusable(true);
			}
			
		});
		add.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				WeightedMobSpawnGroup mob;
				if(add.getText().equals("change"))
				{
					mob = monster.get(activeRow).getMonster(biome); 
				}
				else
				{
					mob = new WeightedMobSpawnGroup();
				}
				mob.setWeight(center.getSlider("weight").getInt());
				mob.setMin(center.getSlider("minAmount").getInt());
				mob.setMax(center.getSlider("maxAmount").getInt());
				mob.setMobName(center.getMobChooser("mobname").getString());
				if(!add.getText().equals("change"))
				{
					add(mob, biome, MonsterType.get(center.getComboBox("type").getString()));
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
		move.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					int newRow = Integer.parseInt(moveTo.getText()) - 1;
					if(newRow >= 0 && newRow < table.getRowCount())
					{
						String[] old = new String[table.getColumnCount()];
						for(int i = 0; i < table.getColumnCount(); i++)
						{
							old[i] = table.getValueAt(activeRow, i).toString();
						}
						MonsterGroupPack oldMonster = monster.get(activeRow);
						table.removeRow(activeRow);
						monster.remove(activeRow);
						table.insertRow(newRow, old);
						monster.add(newRow, oldMonster);
						for(int i = 0; i < table.getRowCount()-1; i++)
						{
							table.setValueAt((i+1), i, 0);
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
		});
		useHere.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				useEnabled();
				monster.get(activeRow).setUsed(biome, useHere.isSelected(), me);
				list.paint(list.getGraphics());
			}
			
		});
		list.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() 
		{
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) 
			{
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        		Color bg = Color.LIGHT_GRAY;
        		if(row < monster.size())
        		if(monster.get(row).monsterActive.get(biome) != null)
        		if(monster.get(row).monsterActive.get(biome))
        		{
        			bg = Color.WHITE;
        		}
        		if(!(row < monster.size()))
        		{
        			bg = Color.WHITE;
        		}
        		c.setBackground(bg);
        		
        		Color fg = Color.BLACK;
        		if(row < monster.size())
        		switch (monster.get(row).type)
        		{
					case Monster:
						fg = Color.RED;
						break;
					case Creature:
						fg = Color.GREEN;
						break;
					case WaterCreature:
						fg = Color.BLUE;
						break;
        		}
        		c.setForeground(fg.darker());
                return this;
            }
        });
	}
	public void useEnabled()
	{
		center.getSlider("minAmount").setEnabled(useHere.isSelected());
		center.getSlider("maxAmount").setEnabled(useHere.isSelected());
		center.getSlider("weight").setEnabled(useHere.isSelected());
		center.getMobChooser("mobname").setEnabled(useHere.isSelected());
		center.getComboBox("type").setEnabled(useHere.isSelected());
	}
	public void actionInRow(int row)
	{
		if(row == -1)
		{
			return;
		}
		if(row == table.getRowCount()-1)
		{
			add.setText("add");
			center.getSlider("minAmount").setValue(4);
			center.getSlider("maxAmount").setValue(4);
			center.getSlider("weight").setValue(10);
			center.getMobChooser("mobname").setValue(DefaultMobType.CREEPER);
			center.getComboBox("type").setValue("Monster");
			popup.setVisible(true);
			useHere.setSelected(true);
			useEnabled();
		}
		else if(monster.get(row).getMonster(biome) == null)
		{
			add.setText("add");
			set(monster.get(row).getMonster(monster.get(row).monsterPack.keySet().toArray(new String[]{})[0]), monster.get(row).type); //get monster of first
			activeRow = row;
			useHere.setSelected(false);
			popup.setVisible(true);
			copySettings.setSelected(monster.get(row).copy);
			useEnabled();
			center.getMobChooser("mobname").setEnabled(false);
			center.getComboBox("type").setEnabled(false);
			list.clearSelection();
		}
		else
		{
			set(monster.get(row).getMonster(biome), monster.get(row).type);
			useHere.setSelected(monster.get(row).isUsed(biome));
			add.setText("change");
			activeRow = row;
			popup.setVisible(true);
			copySettings.setSelected(monster.get(row).copy);
			useEnabled();
			center.getMobChooser("mobname").setEnabled(false);
			center.getComboBox("type").setEnabled(false);
		}
		moveTo.setText((row+1) + "");
	}
	public void set(WeightedMobSpawnGroup monster, MonsterType type)
	{
		center.getSlider("minAmount").setValue(monster.getMin());
		center.getSlider("maxAmount").setValue(monster.getMax());
		center.getSlider("weight").setValue(monster.getWeight());
		center.getMobChooser("mobname").setValue(monster.getDefaultMobType());
		center.getComboBox("type").setValue(type.string);
	}
	public void add(WeightedMobSpawnGroup monsterGroup, String biome, MonsterType type)
	{
		boolean found = false;
		for(MonsterGroupPack pack : monster)
		{
			if(pack.fit(monsterGroup, type))
			{
				if(!pack.contains(biome))
				{
					pack.add(monsterGroup, biome);
				}
				found = true;
				break;
			}
		}
		if(!found)
		{
			monster.add(new MonsterGroupPack(monsterGroup, biome, type));
			this.table.insertRow(table.getRowCount()-1, new Object[]{table.getRowCount() + "",Main.objManager.faces.get(monsterGroup.getDefaultMobType().getFormalName()), type.string, monsterGroup.getDefaultMobType().getFormalName()});
		}

	}
	public void loadBiomeSettings(List<WeightedMobSpawnGroup> monsterGroups, String biome, MonsterType type)
	{
		MonsterGroupPack.biome = biome;
		this.biome = biome;
		for(WeightedMobSpawnGroup load : monsterGroups)
		{

			if(load != null)
			add(load, biome, type);
		}
		if(Main.main.mainFrame != null)
		if(Main.main.mainFrame.biomes.tabbedPane.getSelectedIndex() == 4) list.paint(list.getGraphics());
	}
	
	public void removeMonster(MonsterGroupPack monsterGroupPack) 
	{
		table.removeRow(activeRow);
		monster.remove(monsterGroupPack);
	}
	public List<WeightedMobSpawnGroup> getMonsters(String biome, MonsterType type)
	{
		ArrayList<WeightedMobSpawnGroup> resources = new ArrayList<WeightedMobSpawnGroup>();
		for(MonsterGroupPack monsterPack : monster)
		{
			if(monsterPack.monsterActive.containsKey(biome))
				if(monsterPack.monsterActive.get(biome))
					if(monsterPack.type == type)
					{
						resources.add(monsterPack.monsterPack.get(biome));
					}
		}
		return resources;
	}

}
