package com.genae.terrainControlUI.Image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.genae.terrainControlUI.biomePanels.CustomBiomeList;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

public class ImageFrame extends JDialog 
{
	private static final long serialVersionUID = 1L;
	Main main;
	JLabel label;
	public BiomeImage img;
	public JTextField chunks;
	JProgressBar progress;
	public ImageFrame(Main main)
	{
		setTitle("Biome Map");
		chunks = new JTextField("200");
		this.main = main;
		progress = new JProgressBar();
		img = new BiomeImage(new File(System.getProperty("user.home") + "\\TerrainControl\\TestServer\\plugins\\TerrainControl\\worlds\\" + Main.getWorldName()), 0, 100, 0, 0, this, Main.world);
		label = new JLabel();
		label.setIcon(ResolutionManager.resize(new ImageIcon(img), 400, 400));
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JButton refresh = new JButton("refresh");
		setLayout(new BorderLayout());
		setSize(ResolutionManager.pixel(new Dimension(400, 480)));
		setLocation(ResolutionManager.pixel(1050), ResolutionManager.pixel(50));
		add(label, BorderLayout.CENTER);
		panel.add(refresh, BorderLayout.LINE_END);
		panel.add(chunks, BorderLayout.CENTER);
		panel.add(new JLabel("radius(chunks)"), BorderLayout.LINE_START);
		add(panel, BorderLayout.PAGE_END);
		add(progress, BorderLayout.PAGE_START);
		
		refresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				refresh();
			}
			
		});

	}
	public void refresh()
	{
		int size = 200;
		try
		{
			size = Integer.parseInt(chunks.getText());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			size = 200;
		}
		CustomBiomeList.customBiomeFrame.biome.saveConfig();
		main.mainFrame.biomes.saveConfig();
		main.mainFrame.world.save();
		img = new BiomeImage(new File(System.getProperty("user.home") + "\\TerrainControl\\TestServer\\plugins\\TerrainControl\\worlds\\" + Main.getWorldName()), 0, size, 0, 0, this, Main.world);
		label.setIcon(new ImageIcon(ResolutionManager.resize(img, 400, 400)));
        label.paint(label.getGraphics());
	}
	public void setProgress(int i)
	{
		progress.setValue(i);
		progress.update(progress.getGraphics());
	}
}
