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

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;


//////////////////////////////////////////////////
//												//
//		The InputBlockChooser is handling		//
//		the Block choosing. It supports			//
//		all ID's up to 124(activated)/			//
//		136(inactive) and the different			//
//		typs behind the ID's. It shows			//
//		a button in the list and has			//
//		a Popup to select.						//
//												//
//		ToDo:									//
//		Recommended Blocks Tab					//
//												//
//////////////////////////////////////////////////

public class InputBlockChooser extends JPanel
{
	private static final long serialVersionUID = 1L;
	////////////////////////
	//Declare Elements
	JButton selected;
	JDialog popup;
	int choosen;
	public int typInt;
	HashMap<String, String> ids;
	ImageIcon[] blockimgs;
	BlockButton[] blockButtons;
	InputBlockList list;
	boolean typ = false;
	final int NUMBER_OF_BLOCKS = 190; //For snapshot 12w25-27 use 190 (including wooden Slabs up to Wooden Stairs, ID: 125 - ID: 136
	
	public InputBlockChooser(int def, boolean typ, InputBlockList list)
	{
		this.list = list;
		construct(def, typ);
	}
	public InputBlockChooser(int def, boolean typ)
	{
		construct(def, typ);
	}
	private void construct(int def, boolean typ)
	{
		////////////////////////
		//Initialize Elements
		this.typ = typ;
		typInt = -1;
		blockimgs = Main.objManager.getBlockImgs();
		blockButtons = new BlockButton[NUMBER_OF_BLOCKS];
		this.ids = Main.objManager.getIDs();
		choosen = def;
		popup = new JDialog();
		popup.setSize(ResolutionManager.pixel(new Dimension(700, 548)));
		popup.setLocation(ResolutionManager.pixel(300), ResolutionManager.pixel(300));
		selected = new JButton("ID: " + choosen + ", " + ids.get(choosen));
		selected.setFont(Main.optionFont);
		setLayout(new BorderLayout());
		add(selected, BorderLayout.CENTER);
		
		////////////////////////
		//Setting up Popup		
		popup.setLayout(new GridLayout(10, 1));
		int x = 0;
		int z = 0;
		//creating Buttons
		for(int i = 0; z < NUMBER_OF_BLOCKS; i++)
		{

			
			
			if(i + 1 == Main.objManager.idTypes[x][0])
			{
				for(int j = 0; j < Main.objManager.idTypes[x][1]; j++)
				{
					if(list == null)	blockButtons[z] = new BlockButton(i + 1, j, blockimgs[z], this);
					else 				blockButtons[z] = new BlockButton(i + 1, j, blockimgs[z], list);
					blockButtons[z].setEnabled(typ || j == 0);
					z++;
				}
				x++;
			}
			else
			{
				if(list == null) 	blockButtons[z] = new BlockButton(i + 1, -1, blockimgs[z], this);
				else				blockButtons[z] = new BlockButton(i + 1, -1, blockimgs[z], list);
				z++;
			}
		}
		//adding Buttons
		for(int i = 0; i < blockButtons.length; i++)
		{
			popup.add(blockButtons[i]);
		}
		
		//Listen to main Button
		selected.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(true);
			}
			
		});

	}
	public int getInt() 
	{
		return choosen;
	}
	public int getData()
	{
		return typInt;
	}
	public byte getByte() 
	{
		return new Integer(choosen).byteValue();
	}
	public String getString() 
	{			
		return choosen + "." + typInt;
	}
	
	////////////////////////
	//When Block is choosen
	public void setValue(byte value) 
	{
		int i = new Integer(value);
		setValue(i + "");
	}
	public void setValue(int value) 
	{
		setValue(value + "");
	}
	public void setValue(String value) 
	{
		try
		{
			int id = 0;
			int typ = -1;
			if(value.contains("."))
			{
				int div = value.indexOf(".");
				id = Integer.parseInt(value.substring(0, div));
				typ = Integer.parseInt(value.substring(div + 1, value.length()));
				typInt = typ;
				System.out.println(typ);
			}
			else
			{
				id = Integer.parseInt(value);
			}
			choosen = id;
			String idstr = id + "-" + typ;
			if(ids.get(idstr) == null)
			{
				selected.setText("ID: " + choosen + ", " + ids.get(choosen + ""));
				selected.setToolTipText(selected.getText());
				popup.setVisible(false);
			}
			else
			{
				selected.setText("ID: " + choosen + ", Typ: " + typ + ", " + ids.get(idstr));
				selected.setToolTipText(selected.getText());
				popup.setVisible(false);
			}
		}
		catch(Exception e)
		{
			System.out.println("BlockChooser had problems setting " + value);
		}
	}
	public void addActionListener(ActionListener al) 
	{
		selected.addActionListener(al);
	}
	public void refresh() 
	{
		setValue(choosen + "." + typInt);
	}
	public void setEnabled(boolean b)
	{
		selected.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return selected.isEnabled();
	}
	public void setDataAllowed(boolean b) 
	{
		typ = b;
		int z = 0, x = 0;
		for(int i = 0; z < NUMBER_OF_BLOCKS; i++)
		{
			if(i + 1 == Main.objManager.idTypes[x][0])
			{
				for(int j = 0; j < Main.objManager.idTypes[x][1]; j++)
				{
					blockButtons[z].setEnabled(typ || j == 0);
					z++;
				}
				x++;
			}
			else
			{
				z++;
			}
		}
	}
}

////////////////////////
//The Buttons used for the Elements
class BlockButton extends JButton
{
	private static final long serialVersionUID = 1L;
	int id, typ;
	ImageIcon img;
	InputBlockChooser chooser;
	InputBlockList list;

	public BlockButton(int id, int typ, ImageIcon img, InputBlockChooser chooser)
	{
		super(img);
		this.id = id;
		this.typ = typ;
		this.img = img;
		this.chooser = chooser;
		addListener();
	}
	public BlockButton(int id, int typ, ImageIcon img, InputBlockList list)
	{
		super(img);
		this.id = id;
		this.typ = typ;
		this.img = img;
		this.list = list;
		addListener();
	}
	private void addListener()
	{
		addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(chooser != null ) chooser.setValue(id + "." + typ);
				if(list != null ) list.choosen(id, typ);
			}
			
		});
	}	
}