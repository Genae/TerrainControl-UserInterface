package com.genae.terrainControlUI.inputLabels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;

//////////////////////////////////////////////////
//												//
//		The InputColor is used for getting		//
//		a File via JFileChooser. Gets the		//
//		absolute Path. Also provides			//
//		a TextField for direct Input of			//
//		paths or files in the used directory	//
//												//
//////////////////////////////////////////////////

public class InputFile extends JPanel
{
	private static final long serialVersionUID = 1L;
	JTextField box;
	JButton open;
	String typ = "";
	
	public InputFile(String def)
	{
		construct(def);
	}
	public InputFile(String def, String typ)
	{
		this.typ = typ;
		construct(def);
	}
	private void construct(String def)
	{
		box = new JTextField(def);
		box.setFont(ResolutionManager.fontSize(Main.font, 15));
		this.open = new JButton(Main.open);
		setLayout(new BorderLayout());
		add(box, BorderLayout.CENTER);
		add(this.open, BorderLayout.LINE_END);
		this.open.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				searchFile();
			}
		});
	}
	
	public void searchFile()
	{
		JFileChooser choose = new JFileChooser(box.getText());
		if(!typ.equals("") && !typ.equals("directory"))
		choose.setFileFilter(new FileFilter() {
            public boolean accept(File f) 
            {
                return f.getName().toLowerCase().endsWith(typ) || f.isDirectory();
            }
            public String getDescription() {
                return "Jar-Files (*.jar)";
            }
        });
		if(typ.equals("directory"))
		{
			choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		choose.showOpenDialog(this);
		File f = choose.getSelectedFile();
		if(f != null)
		box.setText(f.getAbsolutePath());
	}

	public String getValue() 
	{
		return box.getText();
	}

	public void setValue(String value) 
	{
		box.setText(value);
	}

	public void addActionListener(ActionListener al) 
	{
		box.addActionListener(al);
	}
	public void setEnabled(boolean b)
	{
		open.setEnabled(b);
		box.setEnabled(b);
	}
	public boolean isEnabled()
	{
		return open.isEnabled();
	}
}
