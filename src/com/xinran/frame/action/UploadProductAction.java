package com.xinran.frame.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

import com.xinran.frame.MainFrame;
import com.xinran.frame.panel.ProductPanel;

public class UploadProductAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(UploadProductAction.class);

	public UploadProductAction(String name) {
		super(name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getMainFrameInstance().setContentPanel(new ProductPanel());
		MainFrame.getMainFrameInstance().repaint();
	}
}
