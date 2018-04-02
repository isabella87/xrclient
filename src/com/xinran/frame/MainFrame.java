package com.xinran.frame;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.xinran.XrBundle;
import com.xinran.frame.action.ExitAction;
import com.xinran.frame.action.UploadInfoAction;
import com.xinran.frame.action.UploadProductAction;
import com.xinran.frame.action.UploadWorkerAction;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Logger logger = Logger.getLogger(MainFrame.class);

	public static final String ROOT_IMG_PATH = "/image/mc.png";
	public static final String ROOT_TITLE = XrBundle.bundle.getString("ROOT_TITLE");
	private static CardLayout cardLayout = new CardLayout();
	private JPanel contentPanel = new JPanel(cardLayout);
	private JMenuBar menuBar;
	private static MainFrame mainFrame = null;
	
	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static MainFrame getMainFrameInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	public MainFrame() {
		initMainFrame(); // 设置主面板

		initMenu();// 设置主菜单栏
		setRootContentPanel(); // 设置根面板

		setVisible(true);
	}

	/**
	 * 设置主面板外观属性
	 */
	private void initMainFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setSize(screenWidth * 5 / 6, screenHeight * 4 / 5);
		setLocation(screenWidth / 11, screenHeight / 10);

		Image img = kit.getImage(ROOT_IMG_PATH);
		setIconImage(img);
		setTitle(ROOT_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setEnabled(true);

	}

	/**
	 * 初始化菜单
	 */
	private void initMenu() {
		JMenu productMenu = new JMenu(XrBundle.bundle.getString("PRODUCT_MGR"));
		productMenu.add(new UploadProductAction(XrBundle.bundle.getString("PRODUCT_CREATE")));
		productMenu.add(new ExitAction(XrBundle.bundle.getString("EXIT_AND_CLOSE_SYS")));

		JMenu workerMenu = new JMenu(XrBundle.bundle.getString("WORKER_MGR"));
		workerMenu.add(new UploadWorkerAction(XrBundle.bundle.getString("MAINTENANCE_WORKER")));
		
		JMenu infoMenu = new JMenu(XrBundle.bundle.getString("INFO_MGR"));
		infoMenu.add(new UploadInfoAction(XrBundle.bundle.getString("INFO_CREATE")));
		JMenu windowMenu = new JMenu(XrBundle.bundle.getString("WINDOW_LIST"));
		JMenu helpMenu = new JMenu(XrBundle.bundle.getString("HELP_ITEM"));

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBar.add(productMenu);
		menuBar.add(workerMenu);
		menuBar.add(infoMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);

	}

	/**
	 * 设置当前主面板显示card
	 * 
	 * @param jpanel
	 */
	public void setContentPanel(JPanel jpanel) {
		contentPanel.add(jpanel);
		cardLayout.next(contentPanel);
		contentPanel.repaint();
	}

	/**
	 * 设置根面板
	 */
	public void setRootContentPanel() {
		Container cp = getContentPane();
		cp.add(contentPanel);
	}

	/**
	 * 主程序入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame.getMainFrameInstance();
		LoginFrame.getLoginFrameInstance();
		
		
		//打印从资源文件中取得的消息
		System.out.println( XrBundle.bundle.getString("ROOT_TITLE"));
	}
}
