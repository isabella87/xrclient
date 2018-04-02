package com.xinran.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.xinran.XrBundle;
import com.xinran.serviceConf.RpcService;

public class LoginFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Logger logger = Logger.getLogger(LoginFrame.class);

	public static final String ROOT_TITLE = XrBundle.bundle.getString("LOGIN_TITLE");
	public static boolean isLogin = false;
	private static LoginFrame loginFrame = null;
	private JLabel errorJl = new JLabel();

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static LoginFrame getLoginFrameInstance() {
		if (loginFrame == null) {
			loginFrame = new LoginFrame();
		}
		return loginFrame;
	}

	public LoginFrame() {
		initLoginFrame(); // 设置主面板

		setContentPanel(); // 设置根面板

		setVisible(true);
	}

	private void initLoginFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setSize(screenWidth * 1 / 6, screenHeight * 2 / 11);
		setLocation(screenWidth * 3 / 7, screenHeight * 3 / 7);
		setResizable(false);

		setTitle(ROOT_TITLE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setEnabled(true);

		this.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				if (!LoginFrame.isLogin) {
					MainFrame.getMainFrameInstance().setEnabled(false);
					LoginFrame.getLoginFrameInstance().toFront();
				} else {
					MainFrame.getMainFrameInstance().setEnabled(true);
				}
			}
		});
	}

	public void setContentPanel() {

		Container cp = getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel accJl = new JLabel(XrBundle.bundle.getString("ACCOUNT"));
		accJl.setHorizontalAlignment(JLabel.RIGHT);
		JTextField accJtf = new JTextField(20);
		accJtf.setPreferredSize(new Dimension(20, 30));
		accJtf.setName("user-name");
		accJtf.setHorizontalAlignment(JTextField.LEFT);

		JLabel pwdJl = new JLabel(XrBundle.bundle.getString("PWD"));
		pwdJl.setHorizontalAlignment(JLabel.RIGHT);
		JPasswordField jpf = new JPasswordField(20);
		jpf.setPreferredSize(new Dimension(20, 30));
		jpf.setName("password");

		JLabel codeJl = new JLabel(XrBundle.bundle.getString("AUTH_CODE"));
		codeJl.setHorizontalAlignment(JLabel.RIGHT);
		JLabel imageJl = new JLabel();
		ImageIcon icon = null;
		byte[] bytes = new RpcService().getCaptchaImage(new HashMap<String, String>());
		if (bytes != null && bytes.length != 0) {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes); // 将b作为输入流；
			try {
				BufferedImage image = ImageIO.read(in);
				icon = new ImageIcon(image);
				icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*8/9,
						icon.getIconHeight()*9/10, Image.SCALE_DEFAULT));
				imageJl.setIcon(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showConfirmDialog(null, XrBundle.bundle.getString("UNCONNECT"), XrBundle.bundle.getString("UNCONNECT_TITLE"),
					JOptionPane.CLOSED_OPTION);
			System.exit(0);
		}

		JTextField codeJtf = new JTextField(10);
		codeJtf.setPreferredSize(new Dimension(20, 30));
		codeJtf.setName("captcha-code");
		codeJtf.setHorizontalAlignment(JTextField.LEFT);

		JPanel codeJp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		codeJp.add(codeJl);
		codeJp.add(imageJl);
		codeJp.add(codeJtf);

		JPanel blankJp = new JPanel();
		blankJp.add(errorJl);

		JButton loginJB = new JButton(XrBundle.bundle.getString("OK"));
		JButton cancelJB = new JButton(XrBundle.bundle.getString("CANCEL"));
		JPanel jbJp = new JPanel();
		jbJp.add(loginJB);
		jbJp.add(cancelJB);

		cp.add(accJl);
		cp.add(accJtf);
		cp.add(pwdJl);
		cp.add(jpf);
		// cp.add(codeJl);
		cp.add(codeJp);
		// cp.add(codeJtf);
		cp.add(blankJp);
		cp.add(jbJp);

		imageJl.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				byte[] bytes = new RpcService().getCaptchaImage(new HashMap<String, String>());
				if (bytes != null && bytes.length != 0) {

					ByteArrayInputStream in = new ByteArrayInputStream(bytes); // 将b作为输入流；
					BufferedImage image = null;
					try {
						image = ImageIO.read(in);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon(image);
					icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*8/9,
							icon.getIconHeight()*9/10, Image.SCALE_DEFAULT));
					imageJl.setIcon(icon);
				} else {
					JOptionPane.showConfirmDialog(null, XrBundle.bundle.getString("UNCONNECT"), XrBundle.bundle.getString("UNCONNECT_TITLE"),
							JOptionPane.CLOSED_OPTION);
					System.exit(0);
				}
				imageJl.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		loginJB.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(accJtf.getName(), accJtf.getText().trim());
				paramMap.put(jpf.getName(), jpf.getText().trim());
				paramMap.put(codeJtf.getName(), codeJtf.getText().trim());
				String loginStr = new RpcService().login(paramMap);
				if (loginStr.equals("true")) {
					isLogin = true;
					LoginFrame.getLoginFrameInstance().setVisible(false);
					MainFrame.getMainFrameInstance().setEnabled(true);
					MainFrame.getMainFrameInstance().toFront();
				} else {
					errorJl.setText(XrBundle.bundle.getString("FAILED_LOGIN"));
					errorJl.setForeground(Color.red);
					errorJl.repaint();
				}
			}
		});

		cancelJB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
