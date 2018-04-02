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

public class ProductPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(ProductPanel.class);

	private static List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
	private DefaultTableModel dtm = null;
	private File file ;
	private File fileSingle ;
	private static ArrayList<String> proType =new ArrayList<String>();
	static {
		proType.add("--");
		proType.add("鼠标");
		proType.add("键盘");
		proType.add("显示器");
	}
	public ProductPanel() {
		setLayout(new BorderLayout());
		initJPanel();
	}

	private void initJPanel() {
		//
		JLabel proNameJl = new JLabel(XrBundle.bundle.getString("PRO_NAME"));
		JLabel proPriceJl = new JLabel(XrBundle.bundle.getString("PRO_PRICE"));
		JLabel proTypeJl = new JLabel(XrBundle.bundle.getString("PRO_TYPE"));
		JLabel proPictureJl = new JLabel(XrBundle.bundle.getString("PRO_PICTURE"));
		
		JTextField proNameJtf = new JTextField(16);
		proNameJtf.setName("product-name");
		proNameJtf.setHorizontalAlignment(JTextField.LEFT);
		
		JTextField proPriceJtf = new JTextField(16);
		proPriceJtf.setName("product-price");
		proPriceJtf.setHorizontalAlignment(JTextField.LEFT);
		
		JComboBox<String> proTypeBox = new JComboBox<String>();
		proTypeBox.setName("product-type");
		for(String type:proType){
			proTypeBox.addItem(type);
		}
		
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
		
		JLabel proIdJl = new JLabel(XrBundle.bundle.getString("MP_ID"));
		
		JTextField proIdJtf = new JTextField(16);
		proIdJtf.setText("用逗号分隔id可上传多个商品的图片");
		proIdJtf.setName("mp-id");
		proIdJtf.setHorizontalAlignment(JTextField.LEFT);

		JButton uploadSingleJb = new JButton(XrBundle.bundle.getString("UPLOAD"));
		
		uploadSingleJb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileSingle==null){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请选择上传图片", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(proIdJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请填写商品ID", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(proIdJtf.getName(), proIdJtf.getText());
				paramMap.put("file-content", Base64.encodeBase64String(getBytesFromFile(fileSingle)));
				paramMap.put("file-name", fileSingle.getName());
				for(String key:paramMap.keySet()){
					System.out.println("key:"+key+";value:"+paramMap.get(key));
				}
				String returnStr = new RpcService().uploadSingleProduct(paramMap);
				System.out.println("aaaaaa:"+returnStr);
				if(returnStr.equals("true")){
					fileSingle = null;
					proIdJtf.setText("");
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
							.queryMajorProductListForBg(paramMap1);
					JSONArray jsonArray = JSONArray.parseArray(jsonStr);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray.get(i);
						String proName = jsonObj.getString("proName");
						System.out.println(proName);
						Vector<String> v = new Vector<String>();
						v.add(i + 1 + "");
						v.add(jsonObj.getString("mpId"));
						v.add(jsonObj.getString("mpId"));
						v.add(jsonObj.getString("proName"));
						v.add(jsonObj.getString("proNo"));
						v.add(jsonObj.getString("price"));
						v.add(jsonObj.getString("type"));
						v.add(getProductStatus(jsonObj.getString("status")));
						v.add(jsonObj.getString("visible").equals("1") ? "可见"
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
				if(proNameJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请填写商品名称", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(proPriceJtf.getText().isEmpty()){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请填写商品价格", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				if(proTypeBox.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(MainFrame.getMainFrameInstance().getContentPane(), "请选择商品类别", "消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put(proNameJtf.getName(), proNameJtf.getText());
				paramMap.put(proPriceJtf.getName(), proPriceJtf.getText());
				paramMap.put(proTypeBox.getName(),String.valueOf(proTypeBox.getSelectedIndex()));
				paramMap.put("file-content", Base64.encodeBase64String(getBytesFromFile(file)));
				paramMap.put("file-name", file.getName());
				for(String key:paramMap.keySet()){
					System.out.println("key:"+key+";value:"+paramMap.get(key));
				}
				//TODO file.getAbsolutePath();
				
				String returnStr = new RpcService().uploadProduct(paramMap);
				System.out.println("aaaaaa:"+returnStr);
				if(returnStr.equals("true")){
					file = null;
					proNameJtf.setText("");
					proPriceJtf.setText("");
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
							.queryMajorProductListForBg(paramMap1);
					JSONArray jsonArray = JSONArray.parseArray(jsonStr);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray.get(i);
						String proName = jsonObj.getString("proName");
						System.out.println(proName);
						Vector<String> v = new Vector<String>();
						v.add(i + 1 + "");
						v.add(jsonObj.getString("mpId"));
						v.add(jsonObj.getString("mpId"));
						v.add(jsonObj.getString("proName"));
						v.add(jsonObj.getString("proNo"));
						v.add(jsonObj.getString("price"));
						v.add(jsonObj.getString("type"));
						v.add(getProductStatus(jsonObj.getString("status")));
						v.add(jsonObj.getString("visible").equals("1") ? "可见"
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
		conditionPanel.add(proNameJl);
		conditionPanel.add(proNameJtf);
		conditionPanel.add(proPriceJl);
		conditionPanel.add(proPriceJtf);
		
		conditionPanel.add(proTypeJl);
		conditionPanel.add(proTypeBox);
		conditionPanel.add(proPictureJl);
		conditionPanel.add(fileButton);
		conditionPanel.add(uploadJb);
		
		conditionPanel.add(createDateJl);
		conditionPanel.add(createStartDateJtf);
		conditionPanel.add(createConDate);
		conditionPanel.add(createEndDateJtf);
		conditionPanel.add(searchJb);
		
		conditionPanel.add(proIdJl);
		conditionPanel.add(proIdJtf);
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
				
				String jsonStr = new RpcService().queryMajorProductListForBg(paramMap);
				JSONArray jsonArray = JSONArray.parseArray(jsonStr);
				for (int i = 0;i<jsonArray.size();i++){
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					String proName = jsonObj.getString("proName");
					System.out.println(proName);
					Vector<String> v = new Vector<String>();
					v.add(i+1+"");
					v.add(jsonObj.getString("mpId"));
					v.add(jsonObj.getString("mpId"));
					v.add(jsonObj.getString("proName"));
					v.add(jsonObj.getString("proNo"));
					v.add(jsonObj.getString("price"));
					v.add(jsonObj.getString("type"));
					v.add(getProductStatus(jsonObj.getString("status")));
					v.add(jsonObj.getString("visible").equals("1") ? "可见":"不可见");
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
				XrBundle.bundle.getString("MP_ID"),
				XrBundle.bundle.getString("PRO_NAME"),
				XrBundle.bundle.getString("PRO_NO"),
				 XrBundle.bundle.getString("PRICE"),
				XrBundle.bundle.getString("TYPE"),
				XrBundle.bundle.getString("STATUS"),
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
				rootPanel.getHeight() / 11));
		
	}
	
	protected String getProductStatus(String status) {
		if(status.equals("0")){
			return "待提交";
		}else if(status.equals("1")){
			return "已上架";
		}else if(status.equals("-1")){
			return "已下架";
		}
		return null;
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
