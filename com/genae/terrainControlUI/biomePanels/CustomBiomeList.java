package com.genae.terrainControlUI.biomePanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.genae.terrainControlUI.inputLabels.InputFile;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.genae.terrainControlUI.util.TCFileWriter;
import com.khorn.terraincontrol.configuration.BiomeConfig;

public class CustomBiomeList extends JPanel
{
	private static final long serialVersionUID = 1L;
	ArrayList<BiomeButton> biomeButtons;
	JButton newBiome;
	JPanel panel;
	JScrollPane scroll;
	PopupNewCustom popup;
	public static CustomBiomeFrame customBiomeFrame;
	public static final int DEFAULT = 0, COPY = 1, READ = 2;
	
	public CustomBiomeList()
	{
		biomeButtons = new ArrayList<BiomeButton>();
		newBiome = new JButton("<html><div align=\"center\">new<p>custom<p>Biome</div></html>");
		panel = new JPanel();
		scroll = new JScrollPane(panel);
		popup = new PopupNewCustom();

		
		newBiome.setFont(Main.optionFont);
		
		for(String s:TCFileWriter.getAllCustomBiomes())
		{
			BiomeConfig biome = Main.objManager.getBiomeByName(s);
			Main.objManager.biomes.put(biome.Name, biome);
			biomeButtons.add(new BiomeButton(biome));
			TCFileWriter.readDescription(s);
		}
		
		addAll();
		
		newBiome.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				popup.setVisible(true);
			}
			
		});
	}


	public void addNew(String text, String copy, int mode) 
	{
		Main.objManager.addCustomBiome(text, copy, mode);
		BiomeConfig biome = Main.objManager.getBiomeByName(text);
		biomeButtons.add(new BiomeButton(biome));
		panel.remove(biomeButtons.size());
		panel.add(biomeButtons.get(biomeButtons.size()-1), biomeButtons.size());
		Main.main.mainFrame.paint(Main.main.mainFrame.getGraphics());
	}
	private void addAll()
	{
		int height = 5;
		if((biomeButtons.size() + 1)/5.0f > 5)
		{
			height = (biomeButtons.size() + 1)/5 + 1;
		}
		
		setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(height, 5));
		panel.setPreferredSize(ResolutionManager.pixel(new Dimension(800, height*121)));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		
		panel.add(newBiome);
		add(scroll, BorderLayout.CENTER);
		for(BiomeButton button:biomeButtons)
		{
			panel.add(button);
		}
		while(panel.getComponentCount() < height*5)
		{
			panel.add(new JLabel());
		}
	}

}
class PopupNewCustom extends JDialog
{
	private static final long serialVersionUID = 1L;
	JLabel nameL;
	JRadioButton useDefault, copyFromL, loadConfigL;
	ButtonGroup generateFrom;
	InputFile loadConfig;
	JTextField name;
	JComboBox<String> copyFrom;
	JButton ok, cancel;
	int mode = 0;
	
	public PopupNewCustom()
	{
		nameL = new JLabel("Name: ");
		useDefault = new JRadioButton("Use Default");
		copyFromL = new JRadioButton("copy Settings From:");
		loadConfigL = new JRadioButton("Load BiomeConfig:");
		name = new JTextField("insert Name");
		copyFrom = new JComboBox<String>(Main.objManager.getAllBiomes(true));
		loadConfig = new InputFile(Main.config.CustomBiomeDirectory, ".ini");
		ok = new JButton("ok");
		cancel = new JButton("cancel");

		
		setLayout(new GridLayout(5, 2));
		
		nameL.setFont(Main.optionFont);
		useDefault.setFont(Main.optionFont);
		copyFromL.setFont(Main.optionFont);
		loadConfigL.setFont(Main.optionFont);
		copyFrom.setFont(Main.optionFont);
		name.setFont(Main.optionFont);
		ok.setFont(Main.optionFont);
		cancel.setFont(Main.optionFont);
		
		generateFrom = new ButtonGroup();
		generateFrom.add(useDefault);
		generateFrom.add(copyFromL);
		generateFrom.add(loadConfigL);
		
		add(nameL);
		add(name);
		add(useDefault);
		add(new JLabel());
		add(copyFromL);
		add(copyFrom);
		add(loadConfigL);
		add(loadConfig);
		add(ok);
		add(cancel);
		
		
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}
			
		});
		ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addNewBiome();
				setVisible(false);
			}
			
		});
		useDefault.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				copyFrom.setEnabled(false);
				loadConfig.setEnabled(false);
				mode = CustomBiomeList.DEFAULT;
			}
			
		});
		copyFromL.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				copyFrom.setEnabled(true);
				loadConfig.setEnabled(false);
				mode = CustomBiomeList.COPY;
			}
			
		});
		loadConfigL.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				copyFrom.setEnabled(false);
				loadConfig.setEnabled(true);
				mode = CustomBiomeList.READ;
			}
			
		});
		useDefault.setSelected(true);
		copyFrom.setEnabled(false);
		loadConfig.setEnabled(false);
		setSize(ResolutionManager.pixel(new Dimension(600, 300)));
		setLocation(ResolutionManager.pixel(200), ResolutionManager.pixel(200));
	}
	private void addNewBiome()
	{
		if(mode == CustomBiomeList.COPY)Main.main.mainFrame.customBiomes.addNew(name.getText(), copyFrom.getSelectedItem().toString(), mode);
		if(mode == CustomBiomeList.READ)Main.main.mainFrame.customBiomes.addNew(name.getText(), loadConfig.getValue(), mode);
	}
}
class BiomeButton extends JButton
{
	private static final long serialVersionUID = 1L;
	BiomeConfig biome;
	public BiomeButton(BiomeConfig biomeConfig)
	{
		biome = biomeConfig;
		setToolTipText(biome.Name);
		String name = "<html><div align=\"center\">" + biome.Name + "</div></html>";
		setText(name);
		setFont(Main.optionFont);
		addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(CustomBiomeList.customBiomeFrame == null)
					CustomBiomeList.customBiomeFrame = new CustomBiomeFrame(biome);
				CustomBiomeList.customBiomeFrame.biome.loadConfig(biome);
				CustomBiomeList.customBiomeFrame.setVisible(true);
			}
			
		});
	}
}