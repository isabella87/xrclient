package com.xinran.frame.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

import com.xinran.frame.MainFrame;
import com.xinran.frame.panel.WorkerPanel;

public class UploadWorkerAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(UploadWorkerAction.class);

	public UploadWorkerAction(String name) {
		super(name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getMainFrameInstance().setContentPanel(new WorkerPanel());
		MainFrame.getMainFrameInstance().repaint();
	}
}
