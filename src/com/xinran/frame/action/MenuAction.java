package com.xinran.frame.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.log4j.Logger;

public class MenuAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(MenuAction.class);

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(getValue(Action.NAME) + " selected.");
	}
	
	public MenuAction(String name) {
		super(name);
	}

}
