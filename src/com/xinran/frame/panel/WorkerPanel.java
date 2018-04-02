package com.xinran.frame.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinran.XrBundle;
import com.xinran.frame.MainFrame;
import com.xinran.frame.baseUtil.DateChooser;
import com.xinran.frame.baseUtil.DateUtils;
import com.xinran.httpUtil.HttpUtils;
import com.xinran.serviceConf.RpcService;

public class WorkerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(WorkerPanel.class);

	private static List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
	private DefaultTableModel dtm = null;
	private File file ;
	private File fileSingle ;
	private static ArrayList<String> workerLevel =new ArrayList<String>();
	static {
		workerLevel.add("--");
		workerLevel.add("玉皇大帝");
		workerLevel.add("太上老君");
		workerLevel.add("托塔天王");
		workerLevel.add("哪吒");
		workerLevel.add("虾兵蟹将");
	}
	public WorkerPanel() {
		setLayout(new BorderLayout());
		initJPanel();
	}

	private void initJPanel() {
		//
		JLabel workerNameJl = new JLabel(XrBundle.bundle.getString("MM_NAME"));
		JLabel workerNoJl = new JLabel(XrBundle.bundle.getString("MM_NO"));
		JLabel workerLevelJl = new JLabel(XrBundle.bundle.getString("MM_LEVEL"));
		JLabel workerMobileJl = new JLabel(XrBundle.bundle.getString("MM_MOBILE"));
		JLabel workerIntroJl = new JLabel(XrBundle.bundle.getString("MM_INTRO"));
		
		JLabel workerPictureJl = new JLabel(XrBundle.bundle.getString("PRO_PICTURE"));
		
		
		JTextField workerNameJtf = new JTextField(16);
		workerNameJtf.setName("mm-name");
		workerNameJtf.setHorizontalAlignment(JTextField.LEFT);
		
		JTextField workerNoJtf = new JTextField(16);
		workerNoJtf.setName("mm-no");
		workerNoJtf.setHorizontalAlignment(JTextField.LEFT);
		
		JComboBox<String> workerLevelBox = new JComboBox<String>();
		workerLevelBox.setName("mm-level");
		for(String level:workerLevel){
			workerLevelBox.addItem(level);
		}
		
		JTextField workerMobileJtf = new JTextField(16);
		workerMobileJtf.setName("mm-mobile");
		workerMobileJtf.setHorizontalAlignment(JTextField.LEFT);
		
		JTextArea workerIntroJta = new JTextArea(4,16);
		workerIntroJta.setName("mm-intro");
		
		JButton fileButton = new JButton("文件选择...");
		fileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);  
		        jfc.showDialog(new JLabel(), "选择");  
		        file=jfc.getSelectedFile();  
		        if(file.isDirectory()){  
		            System.out.println("文件夹:"+file.getAbsolutePath());  
		        }else if(file.isFile()){  
		            System.out.println("文件:"+file.getAbsolutePath());  
		        }  
		        System.out.println("文件名："+file.getName());
		        System.out.println(jfc.getSelectedFile().getName());  
				
			}
			
		});
		
		
		JButton fileSingleButton = new JButton("文件选择...");
		fileSingleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc1=new JFileChooser();  
		        jfc1.setFileSelectionMode(JFileChooser.APPROVE_OPTION);  
		        jfc1.showDialog(new JLabel(), "选择");  
		        fileSingle=jfc1.getSelectedFile();  
		        if(fileSingle.isDirectory()){  
		            System.out.println("文件夹:"+fileSingle.getAbsolutePath());  
		        }else if(fileSingle.isFile()){  
		            System.out.println("文件:"+fileSingle.getAbsolutePath());  
		        }  
		        System.out.println("文件名："+fileSingle.getName());
		        System.out.println(jfc1.getSelectedFile().getName());  
				
			}
			
		});

		JButton uploadJb = new JButton(XrBundle.bundle.getString("UPLOAD"));
		JLabel createDateJl = new JLabel(XrBundle.bundle.getString("CREATE_TIME"));
		DateChooser createStartDate = DateChooser.getInstance("yyyy-MM-dd");
		DateChooser createEndDate = DateChooser.getInstance("yyyy-MM-dd");
		JTextField createStartDateJtf = new JTextField(XrBundle.bundle.getString("DATA_ITEM_DESC"));
		createStartDateJtf.setName("start-date");
		JLabel createConDate = new JLabel("-");
		JTextField createEndDateJtf = new JTextField(XrBundle.bundle.getString("DATA_ITEM_DESC"));
		createEndDateJtf.setName("end-date");
		createStartDate.register(createStartDateJtf);
		createEndDate.register(createEndDateJtf);

		JButton searchJb = new JButton(XrBundle.bundle.getString("SEARCH"));
		
		JLabel mmIdJl = new JLabel(XrBundle.bundle.getString("MM_ID"));
		
		JTextField mmIdJtf = new JTextField(16);
		mmIdJtf.setText("用逗号分隔id可上传多位将士的图像");
		mmIdJtf.setName("mm-id");
		mmIdJtf.setHorizontalAlignment(JTextField.LEFT);

		JButton uploadSingleJb = new JButton(XrBundle.bundle.getString("UPLOAD"));
		
		uploadSingleJb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileSingle==null){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请选择上传图片", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(mmIdJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请填写将士ID", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(mmIdJtf.getName(), mmIdJtf.getText());
				paramMap.put("file-content", Base64.encodeBase64String(getBytesFromFile(fileSingle)));
				paramMap.put("file-name", fileSingle.getName());
				for(String key:paramMap.keySet()){
					System.out.println("key:"+key+";value:"+paramMap.get(key));
				}
				String returnStr = new RpcService().uploadSingleWorker(paramMap);
				System.out.println("aaaaaa:"+returnStr);
				if(returnStr.equals("true")){
					fileSingle = null;
					mmIdJtf.setText("");
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "恭喜您，创建成功！", "消息",JOptionPane.WARNING_MESSAGE);  
				
					//上传成功后刷新界面
					Map<String, String> paramMap1 = new HashMap<String, String>();

					String createStartDateStr = createStartDateJtf.getText().trim();
					if (createStartDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
						createStartDateStr = "";
					}
					String createEndDateStr = createEndDateJtf.getText().trim();
					if (createEndDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
						createEndDateStr = "";
					}
					paramMap1.put(createStartDateJtf.getName(), createStartDateStr);
					paramMap1.put(createEndDateJtf.getName(), createEndDateStr);

					while (dtm.getRowCount() > 0) {
						dtm.removeRow(dtm.getRowCount() - 1);
					}

					String jsonStr = new RpcService()
							.queryWorkerListForBg(paramMap1);
					JSONArray jsonArray = JSONArray.parseArray(jsonStr);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray.get(i);
						String mmName = jsonObj.getString("mmName");
						System.out.println(mmName);
						Vector<String> v = new Vector<String>();
						v.add(i + 1 + "");
						v.add(jsonObj.getString("mmId"));
						v.add(jsonObj.getString("mmName"));
						v.add(jsonObj.getString("mmNo"));
						v.add(jsonObj.getString("mmLevel"));
						v.add(jsonObj.getString("mmMobile"));
						v.add(jsonObj.getString("mmIntro"));
						v.add(jsonObj.getString("enable").equals("1") ? "可见"
								: "不可见");
						v.add(jsonObj.getString("hasFile").equals("1") ? "有" : "无");
						v.add(jsonObj.getString("creator"));
						v.add(DateUtils.toStandardStr(jsonObj.getDate("createTime")));
						v.add(jsonObj.getString("updator"));
						v.add(DateUtils.toStandardStr(jsonObj.getDate("updateTime")));
						dtm.addRow(v);
					}
					dtm.fireTableDataChanged();
				
				}else{
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "很遗憾，创建失败，再来一次！", "消息",JOptionPane.WARNING_MESSAGE);  
					
				}
			}

		});
		
		uploadJb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file==null){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请选择上传图片", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(workerNameJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请填写将士姓名", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(workerMobileJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请留下将士电话", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(workerLevelBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请选择将士级别", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(workerNameJtf.getName(), workerNameJtf.getText());
				paramMap.put(workerNoJtf.getName(), workerNoJtf.getText());
				paramMap.put(workerMobileJtf.getName(), workerMobileJtf.getText());
				paramMap.put(workerIntroJta.getName(), workerIntroJta.getText());
				paramMap.put(workerLevelBox.getName(),String.valueOf(workerLevelBox.getSelectedIndex()));
				paramMap.put("file-content", Base64.encodeBase64String(getBytesFromFile(file)));
				paramMap.put("file-name", file.getName());
				for(String key:paramMap.keySet()){
					System.out.println("key:"+key+";value:"+paramMap.get(key));
				}
				//TODO file.getAbsolutePath();
				
				String returnStr = new RpcService().uploadWorker(paramMap);
				System.out.println("aaaaaa:"+returnStr);
				if(returnStr.equals("true")){
					file = null;
					workerNameJtf.setText("");
					workerNoJtf.setText("");
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "恭喜您，创建成功！", "消息",JOptionPane.WARNING_MESSAGE);  
				
					//上传成功后刷新界面
					Map<String, String> paramMap1 = new HashMap<String, String>();

					String createStartDateStr = createStartDateJtf.getText().trim();
					if (createStartDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
						createStartDateStr = "";
					}
					String createEndDateStr = createEndDateJtf.getText().trim();
					if (createEndDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
						createEndDateStr = "";
					}
					paramMap1.put(createStartDateJtf.getName(), createStartDateStr);
					paramMap1.put(createEndDateJtf.getName(), createEndDateStr);

					while (dtm.getRowCount() > 0) {
						dtm.removeRow(dtm.getRowCount() - 1);
					}

					String jsonStr = new RpcService()
							.queryWorkerListForBg(paramMap1);
					JSONArray jsonArray = JSONArray.parseArray(jsonStr);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray.get(i);
						String mmName = jsonObj.getString("mmName");
						System.out.println(mmName);
						Vector<String> v = new Vector<String>();
						v.add(i + 1 + "");
						v.add(jsonObj.getString("mmId"));
						v.add(jsonObj.getString("mmName"));
						v.add(jsonObj.getString("mmNo"));
						v.add(jsonObj.getString("mmLevel"));
						v.add(jsonObj.getString("mmMobile"));
						v.add(jsonObj.getString("mmIntro"));
						v.add(jsonObj.getString("enable").equals("1") ? "可见"
								: "不可见");
						v.add(jsonObj.getString("hasFile").equals("1") ? "有" : "无");
						v.add(jsonObj.getString("creator"));
						v.add(DateUtils.toStandardStr(jsonObj.getDate("createTime")));
						v.add(jsonObj.getString("updator"));
						v.add(DateUtils.toStandardStr(jsonObj.getDate("updateTime")));
						dtm.addRow(v);
					}
					dtm.fireTableDataChanged();
				
				}else{
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "很遗憾，创建失败，再来一次！", "消息",JOptionPane.WARNING_MESSAGE);  
					
				}
			}

		});
		

		JPanel conditionPanel = new JPanel();
		JPanel contentPanel = new JPanel();

		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPane = new JScrollPane();
		JTable table = new JTable();
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		contentPanel.add(scrollPane);
		add(conditionPanel, BorderLayout.NORTH);
		JSeparator js = new JSeparator();
		add(js);
		add(scrollPane, BorderLayout.CENTER);

		conditionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		conditionPanel.add(workerNameJl);
		conditionPanel.add(workerNameJtf);
//		conditionPanel.add(workerNoJl);
//		conditionPanel.add(workerNoJtf);
		
		conditionPanel.add(workerLevelJl);
		conditionPanel.add(workerLevelBox);
		conditionPanel.add(workerMobileJl);
		conditionPanel.add(workerMobileJtf);
		conditionPanel.add(workerIntroJl);
		conditionPanel.add(workerIntroJta);
		conditionPanel.add(workerPictureJl);
		conditionPanel.add(fileButton);
		conditionPanel.add(uploadJb);
		
		conditionPanel.add(createDateJl);
		conditionPanel.add(createStartDateJtf);
		conditionPanel.add(createConDate);
		conditionPanel.add(createEndDateJtf);
		conditionPanel.add(searchJb);
		
		conditionPanel.add(mmIdJl);
		conditionPanel.add(mmIdJtf);
		conditionPanel.add(fileSingleButton);
		conditionPanel.add(uploadSingleJb);

		searchJb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> paramMap = new HashMap<String, String>();

				String createStartDateStr = createStartDateJtf.getText().trim();
				if (createStartDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
					createStartDateStr = "";
				}
				String createEndDateStr = createEndDateJtf.getText().trim();
				if (createEndDateStr.equals(XrBundle.bundle.getString("DATA_ITEM_DESC"))) {
					createEndDateStr = "";
				}
				paramMap.put(createStartDateJtf.getName(), createStartDateStr);
				paramMap.put(createEndDateJtf.getName(), createEndDateStr);

				while (dtm.getRowCount() > 0) {
					dtm.removeRow(dtm.getRowCount() - 1);
				}
				
				String jsonStr = new RpcService().queryWorkerListForBg(paramMap);
				JSONArray jsonArray = JSONArray.parseArray(jsonStr);
				for (int i = 0;i<jsonArray.size();i++){
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					String mmName = jsonObj.getString("mmName");
					System.out.println(mmName);
					Vector<String> v = new Vector<String>();
					v.add(i+1+"");
					v.add(jsonObj.getString("mmId"));
					v.add(jsonObj.getString("mmName"));
					v.add(jsonObj.getString("mmNo"));
					v.add(jsonObj.getString("mmLevel"));
					v.add(jsonObj.getString("mmMobile"));
					v.add(jsonObj.getString("mmIntro"));
					v.add(jsonObj.getString("enable").equals("1") ? "可见":"不可见");
					v.add(jsonObj.getString("hasFile").equals("1") ? "有":"无");
					v.add(jsonObj.getString("creator"));
					v.add(DateUtils.toStandardStr(jsonObj.getDate("createTime")));
					v.add(jsonObj.getString("updator"));
					v.add(DateUtils.toStandardStr(jsonObj.getDate("updateTime")));
					dtm.addRow(v);
				}
				dtm.fireTableDataChanged();
			}

		});
		// data
		String[] tableHeads = { XrBundle.bundle.getString("NO_ID"),
				XrBundle.bundle.getString("MM_ID"),
				XrBundle.bundle.getString("MM_NAME"),
				XrBundle.bundle.getString("MM_NO"),
				 XrBundle.bundle.getString("MM_LEVEL"),
				XrBundle.bundle.getString("MM_MOBILE"),
				XrBundle.bundle.getString("MM_INTRO"),
				XrBundle.bundle.getString("VISIBLE"),
				XrBundle.bundle.getString("HAS_FILE"),
				XrBundle.bundle.getString("CREATOR"),
				XrBundle.bundle.getString("CREATE_TIME"),
				XrBundle.bundle.getString("UPDATOR"),
				XrBundle.bundle.getString("UPDATE_TIME")};
		dtm = (DefaultTableModel) table.getModel();//
		dtm.setColumnIdentifiers(tableHeads);

		// 设置列宽

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(20);
		}

		table.setRowHeight(20);// 设置列高

		// 调整位置

		// table.setPreferredScrollableViewportSize(new Dimension(js.getX(),
		// 700));

		// 设置列的排序

		RowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<TableModel>(
				table.getModel());

		table.setRowSorter(sorter);
		scrollPane.setAutoscrolls(true);

		Container rootPanel = MainFrame.getMainFrameInstance().getContentPane();
		conditionPanel.setPreferredSize(new Dimension(rootPanel.getWidth(),
				rootPanel.getHeight() / 6));
		
	}
	

	private byte[] getBytesFromFile(File dealedFile) {

		InputStream is = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(dealedFile);
			bytes = new byte[is.available()];
			is.read(bytes);
		} catch (Exception e) {
			//log.error("Get File Object Failed!", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//
				}
			}
//			dealedFile.delete();
		}
		return bytes;
	}

}
