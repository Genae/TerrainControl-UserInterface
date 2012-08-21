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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.genae.terrainControlUI.inputLabels.InputBlockChooser;
import com.genae.terrainControlUI.inputLabels.InputBlockList;
import com.genae.terrainControlUI.inputLabels.InputList;
import com.genae.terrainControlUI.inputLabels.InputSlider;
import com.genae.terrainControlUI.inputLabels.InputTreeChance;
import com.genae.terrainControlUI.main.InputTab;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.configuration.Resource;
import com.khorn.terraincontrol.generator.resourcegens.ResourceType;
import com.khorn.terraincontrol.generator.resourcegens.TreeType;


public class InputResources extends JPanel 
{
	private static final long serialVersionUID = 1L;
	ArrayList<ResourcePack> res;
	JTable list;
	DefaultTableModel table;
	JDialog popup;
	JPanel buttons;
	InputTab center;
	JComboBox<String> types;
	JButton add, cancel, move;
	JTextField moveTo;
	JCheckBox useHere, copySettings;
	int activeRow = 0;
	String biome = "";
	InputResources me = this;
	
	public InputResources()
	{
		//create Objects
		res = new ArrayList<ResourcePack>();
		table = new DefaultTableModel(new String[][]{}, new String[]{"*", "Typ", "Resources"});
		list = new JTable(table);
		list.setPreferredScrollableViewportSize(ResolutionManager.pixel(new Dimension(850, res.size()*50)));
		list.getColumnModel().getColumn(2).setPreferredWidth(1000);
		list.getColumnModel().getColumn(1).setPreferredWidth(400);
		JScrollPane scrollPane = new JScrollPane(list);
		popup = new JDialog();
		buttons = new JPanel();
		types = new JComboBox<String>(new String[]{"Ore", "UnderWaterOre", "Plant", "Liquid", "Grass", "Reed", "Cactus", "Dungeon", "Tree", "CustomObject", "UnderGroundLake", "AboveWaterRes", "Vines", "SmallLake"});

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
		list.setRowHeight(40);
		popup.setSize(ResolutionManager.pixel(new Dimension(600, 600)));
		popup.setLocation(ResolutionManager.pixel(200), ResolutionManager.pixel(200));
		types.setFont(Main.optionFont);
		buttons.setLayout(new GridLayout(2, 4));
		add.setFont(Main.optionFont);
		cancel.setFont(Main.optionFont);
		moveTo.setFont(Main.optionFont);
		move.setFont(Main.optionFont);
		useHere.setFont(Main.optionFont);
		copySettings.setFont(Main.optionFont);
		
		//add
		center = new InputTab(10, 580);
		
		final InputSlider minAltitude = new InputSlider(0, 256, 0);
		center.add("minAltitude", minAltitude, "biome");
		final InputSlider maxAltitude = new InputSlider(0, 256, 64);
		center.add("maxAltitude", maxAltitude, "biome");
		final InputSlider minSize = new InputSlider(0, 256, 64);
		center.add("minSize", minSize, "biome");
		final InputSlider maxSize = new InputSlider(0, 256, 128);
		center.add("maxSize", maxSize, "biome");
		final InputBlockChooser block = new InputBlockChooser(3, true);
		center.add("block", block, "biome");
		final InputBlockList sourceBlock = new InputBlockList(3);
		center.add("sourceBlock", sourceBlock, "biome");
		final InputSlider frequency = new InputSlider(0, 256, 64);
		center.add("frequency", frequency, "biome");
		final InputSlider rarity = new InputSlider(0, 100, 100);
		center.add("rarity", rarity, "biome");
		final InputList treeTypes = new InputList(Main.objManager.getTrees(), " Trees selected");
		center.add("treeTypes", treeTypes, "biome");
		final InputTreeChance treeChance = new InputTreeChance(treeTypes);
		center.add("treeChance", treeChance, "biome");
		
		center.finalize();

		add(scrollPane, BorderLayout.CENTER);

		buttons.add(moveTo);
		buttons.add(move);
		buttons.add(useHere);
		buttons.add(add);
		buttons.add(cancel);
		buttons.add(copySettings);
		popup.add(types, BorderLayout.PAGE_START);
		popup.add(center, BorderLayout.CENTER);
		popup.add(buttons, BorderLayout.PAGE_END);
		
		//Button new
		table.insertRow(0, new String[]{"", "", "add new Resource"});

		
		//ActionListeners
		copySettings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				res.get(activeRow).setCopy(copySettings.isSelected());
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
				Resource resource;
				if(add.getText().equals("change"))
				{
					resource = res.get(activeRow).getRes(biome); 
				}
				else
				{
					resource = new Resource(stringToType(types.getSelectedItem().toString()));
				}
				resource.MinAltitude = center.getSlider("minAltitude").getInt();
				resource.MaxAltitude = center.getSlider("maxAltitude").getInt();
				resource.MinSize = center.getSlider("minSize").getInt();
				resource.MaxSize = center.getSlider("maxSize").getInt();
				resource.BlockId = center.getBlockChooser("block").getInt();
				resource.BlockData = center.getBlockChooser("block").getData();
				resource.SourceBlockId = center.getBlockList("sourceBlock").getValue();
				resource.Frequency = center.getSlider("frequency").getInt();
				resource.Rarity = center.getSlider("rarity").getInt();
				resource.TreeTypes = center.getList("treeTypes").getTreeTypes();
				resource.TreeChances = center.getTreeChance("treeChance").getIntArray();
				if(!add.getText().equals("change"))
				{
					add(resource, biome);
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
						ResourcePack oldRes = res.get(activeRow);
						table.removeRow(activeRow);
						res.remove(activeRow);
						table.insertRow(newRow, old);
						res.add(newRow, oldRes);
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
				res.get(activeRow).setUsed(biome, useHere.isSelected(), me);
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
        		if(row < res.size())
        		if(res.get(row).resActive.get(biome) != null)
        		if(res.get(row).resActive.get(biome))
        		{
        			bg = Color.WHITE;
        		}
        		if(!(row < res.size()))
        		{
        			bg = Color.WHITE;
        		}
        		c.setBackground(bg);
        		
        		Color fg = Color.BLACK;
        		if(row < res.size())
        		switch (res.get(row).type)
        		{
					case SmallLake:
						fg = Color.BLUE.brighter();
						break;
					case Ore:
						fg = Color.ORANGE.darker().darker();
						break;
					case UnderWaterOre:
						fg = Color.CYAN.darker();
						break;
					case Plant:
						fg = Color.GREEN;
						break;
					case Liquid:
						fg = Color.BLUE;
						break;
					case Grass:
						fg = Color.GREEN.brighter();
						break;
					case Reed:
						fg = Color.YELLOW;
						break;
					case Cactus:
						fg = Color.YELLOW.darker();
						break;
					case Dungeon:
						fg = Color.DARK_GRAY;
						break;
					case Tree:
						fg = Color.GREEN.darker();
						break;
					case CustomObject:
						fg = Color.MAGENTA;
						break;
					case UnderGroundLake:
						fg = Color.BLUE.darker();
						break;
					case AboveWaterRes:
						fg = Color.CYAN;
						break;
					case Vines:
						fg = Color.GREEN;
						break;
        		}
        		c.setForeground(fg.darker());
                return this;
            }
        });
		ActionListener al = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				minAltitude.setEnabled(false);
				maxAltitude.setEnabled(false);
				minSize.setEnabled(false);
				maxSize.setEnabled(false);
				block.setEnabled(false);
				sourceBlock.setEnabled(false);
				frequency.setEnabled(false);
				rarity.setEnabled(false);
				treeTypes.setEnabled(false);
				treeChance.setEnabled(false);
				switch(types.getSelectedIndex())
				{
					case 0: //Ore
					{
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						maxSize.setEnabled(true);
						block.setEnabled(true);
						sourceBlock.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(true);
						break;
					}
					case 1: //UnderWaterOre
					{
						maxSize.setEnabled(true);
						block.setEnabled(true);
						sourceBlock.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(false);
						break;
					}
					case 2: case 4://Plant Grass
					{
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						block.setEnabled(true);
						sourceBlock.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(true);
						break;
					}
					case 3: case 5: case 6: //Liquid Reed Cactus
					{
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						block.setEnabled(true);
						sourceBlock.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(false);
						break;
					}
					case 7: case 12://Dungeon Vines
					{
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						break;
					}
					case 8://Tree
					{
						frequency.setEnabled(true);
						treeTypes.setEnabled(true);
						treeChance.setEnabled(true);
						break;
					}
					case 9://Custom Object
					{
						break;
					}
					case 10://UnderGroundLake
					{
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						minSize.setEnabled(true);
						maxSize.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						break;
					}
					case 11://AboveWaterRes
					{
						block.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(false);
						break;
					}
					case 13://SmallLake
					{
						block.setEnabled(true);
						minAltitude.setEnabled(true);
						maxAltitude.setEnabled(true);
						frequency.setEnabled(true);
						rarity.setEnabled(true);
						block.setDataAllowed(true);
						break;
					}
				}
				popup.setSize(popup.getWidth()-1, popup.getHeight()-1);
				popup.setSize(center.removeDisabled());
				useEnabled();
			}
			
		};
		types.addActionListener(al);
		al.actionPerformed(new ActionEvent(this, 0, null));
	}
	public void useEnabled()
	{
		center.getSlider("minAltitude").setEnabled(useHere.isSelected());
		center.getSlider("maxAltitude").setEnabled(useHere.isSelected());
		center.getSlider("minSize").setEnabled(useHere.isSelected());
		center.getSlider("maxSize").setEnabled(useHere.isSelected());
		center.getBlockChooser("block").setEnabled(useHere.isSelected());
		center.getBlockList("sourceBlock").setEnabled(useHere.isSelected());
		center.getSlider("frequency").setEnabled(useHere.isSelected());
		center.getSlider("rarity").setEnabled(useHere.isSelected());
		center.getList("treeTypes").setEnabled(useHere.isSelected());
		center.getTreeChance("treeChance").setEnabled(useHere.isSelected());
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
			types.setSelectedItem("Ore");
			center.getSlider("minAltitude").setValue(0);
			center.getSlider("maxAltitude").setValue(256);
			center.getSlider("minSize").setValue(0);
			center.getSlider("maxSize").setValue(32);
			center.getBlockChooser("block").setValue(1);
			center.getBlockList("sourceBlock").setValue(new int[]{});
			center.getSlider("frequency").setValue(10);
			center.getSlider("rarity").setValue(100);
			center.getTreeChance("treeChance").setValue(new int[]{}, new TreeType[]{});
			center.getBlockChooser("block").setEnabled(true);
			popup.setVisible(true);
			useHere.setSelected(true);
			useEnabled();
			types.setEnabled(true);
		}
		else if(res.get(row).getRes(biome) == null)
		{
			add.setText("add");
			set(res.get(row).getRes(res.get(row).resPack.keySet().toArray(new String[]{})[0])); //get res of first
			activeRow = row;
			useHere.setSelected(false);
			popup.setVisible(true);
			copySettings.setSelected(res.get(row).copy);
			useEnabled();
			center.getBlockChooser("block").setEnabled(false);
			types.setEnabled(false);
		}
		else
		{
			set(res.get(row).getRes(biome));
			useHere.setSelected(res.get(row).isUsed(biome));
			add.setText("change");
			activeRow = row;
			popup.setVisible(true);
			copySettings.setSelected(res.get(row).copy);
			useEnabled();
			center.getBlockChooser("block").setEnabled(false);
			types.setEnabled(false);
		}
		moveTo.setText((row+1) + "");
	}
	public void set(Resource res)
	{
		types.setSelectedItem(typeToString(res.Type));
		center.getSlider("minAltitude").setValue(res.MinAltitude);
		center.getSlider("maxAltitude").setValue(res.MaxAltitude);
		center.getSlider("minSize").setValue(res.MinSize);
		center.getSlider("maxSize").setValue(res.MaxSize);
		center.getBlockChooser("block").setValue(res.BlockId + "." + res.BlockData);
		center.getBlockList("sourceBlock").setValue(res.SourceBlockId);
		center.getSlider("frequency").setValue(res.Frequency);
		center.getSlider("rarity").setValue(res.Rarity);
		center.getList("treeTypes").setValue(res.TreeTypes);
		center.getTreeChance("treeChance").setValue(res.TreeChances, res.TreeTypes);
	}
	public void add(Resource resource, String biome)
	{
		boolean found = false;
		for(ResourcePack pack : res)
		{
			if(pack.fit(resource))
			{
				if(!pack.contains(biome))
				{
					pack.add(resource, biome);
				}
				found = true;
				break;
			}
		}
		if(!found)
		{
			res.add(new ResourcePack(resource, biome));
			String type = typeToString(resource.Type);
			if(resource.BlockId != 0)
			{
				String idstr = resource.BlockId + "-" + resource.BlockData;
				if(Main.objManager.ids.get(idstr) == null)
				{
					this.table.insertRow(table.getRowCount()-1, new String[]{table.getRowCount() + "", type, Main.objManager.ids.get(resource.BlockId + "")});
				}
				else
				{
					this.table.insertRow(table.getRowCount()-1, new String[]{table.getRowCount() + "", type, Main.objManager.ids.get(idstr)});
				}
			}
			else
			{
				this.table.insertRow(table.getRowCount()-1, new String[]{table.getRowCount() + "", type, ""});
			}
		}

	}
	public void loadBiomeSettings(Resource[] resources, String biome)
	{
		ResourcePack.biome = biome;
		this.biome = biome;
		for(Resource load : resources)
		{

			if(load != null)
			add(load, biome);
		}
		if(Main.main.mainFrame != null)
		if(Main.main.mainFrame.biomes.tabbedPane.getSelectedIndex() == 3) list.paint(list.getGraphics());
	}
	public ResourceType stringToType(String str)
	{
		ResourceType type;
		if(str.equals("SmallLake")) type = ResourceType.SmallLake;
		else if(str.equals("Ore")) type = ResourceType.Ore;
		else if(str.equals("UnderWaterOre")) type = ResourceType.UnderWaterOre;
		else if(str.equals("Plant")) type = ResourceType.Plant;
		else if(str.equals("Liquid")) type = ResourceType.Liquid;
		else if(str.equals("Grass")) type = ResourceType.Grass;
		else if(str.equals("Reed")) type = ResourceType.Reed;
		else if(str.equals("Cactus")) type = ResourceType.Cactus;
		else if(str.equals("Dungeon")) type = ResourceType.Dungeon;
		else if(str.equals("CustomObject")) type = ResourceType.CustomObject;
		else if(str.equals("UnderGroundLake")) type = ResourceType.UnderGroundLake;
		else if(str.equals("AboveWaterRes")) type = ResourceType.AboveWaterRes;
		else if(str.equals("Vines")) type = ResourceType.Vines;
		else return ResourceType.Tree;
		return type;
	}
	public String typeToString(ResourceType type)
	{
		String typ = "";
		switch(type)
		{
			case SmallLake:
				typ = "SmallLake";
				break;
			case Ore:
				typ = "Ore";
				break;
			case UnderWaterOre:
				typ = "UnderWaterOre";
				break;
			case Plant:
				typ = "Plant";
				break;
			case Liquid:
				typ = "Liquid";
				break;
			case Grass:
				typ = "Grass";
				break;
			case Reed:
				typ = "Reed";
				break;
			case Cactus:
				typ = "Cactus";
				break;
			case Dungeon:
				typ = "Dungeon";
				break;
			case Tree:
				typ = "Tree";
				break;
			case CustomObject:
				typ = "CustomObject";
				break;
			case UnderGroundLake:
				typ = "UnderGroundLake";
				break;
			case AboveWaterRes:
				typ = "AboveWaterRes";
				break;
			case Vines:
				typ = "Vines";
				break;
		}
		return typ;
	}
	public void removeRes(ResourcePack resourcePack) 
	{
		table.removeRow(activeRow);
		res.remove(resourcePack);
	}
	public Resource[] getResources(String biome)
	{
		Resource[] resources = new Resource[256];
		int num = 0;
		for(ResourcePack resPack : res)
		{
			if(resPack.resActive.containsKey(biome))
				if(resPack.resActive.get(biome))
				{
					resources[num] = resPack.resPack.get(biome);
					num++;
				}
		}
		return resources;
	}

}

