package com.xinran.frame.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinran.XrBundle;
import com.xinran.frame.MainFrame;
import com.xinran.frame.baseUtil.DateChooser;
import com.xinran.frame.baseUtil.DateUtils;
import com.xinran.serviceConf.RpcService;

public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(InfoPanel.class);

	private static List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
	private DefaultTableModel dtm = null;

	public InfoPanel() {
		setLayout(new BorderLayout());
		initJPanel();
	}

	private void initJPanel() {
		//
		JLabel createDateJl = new JLabel(XrBundle.bundle.getString("CREATE_TIME"));
		JLabel noticeTypeJl = new JLabel(XrBundle.bundle.getString("TYPE_ITEM"));
		JLabel keyJl = new JLabel(XrBundle.bundle.getString("KEY"));
		JTextField keyJtf = new JTextField(16);
		keyJtf.setName("search-key");
		keyJtf.setHorizontalAlignment(JTextField.LEFT);

		JComboBox<String> noticeTypeBox = new JComboBox<String>();
		noticeTypeBox.setName("type");
		noticeTypeBox.addItem(XrBundle.bundle.getString("NOTICE_TYPE"));
		noticeTypeBox.addItem(XrBundle.bundle.getString("NOTICE_TYPE1"));
		noticeTypeBox.addItem(XrBundle.bundle.getString("NOTICE_TYPE2"));
		noticeTypeBox.addItem(XrBundle.bundle.getString("NOTICE_TYPE3"));
		noticeTypeBox.addItem(XrBundle.bundle.getString("NOTICE_TYPE4"));

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
		conditionPanel.add(createDateJl);
		conditionPanel.add(createStartDateJtf);
		conditionPanel.add(createConDate);
		conditionPanel.add(createEndDateJtf);
		conditionPanel.add(noticeTypeJl);
		conditionPanel.add(noticeTypeBox);
		conditionPanel.add(keyJl);
		conditionPanel.add(keyJtf);
		conditionPanel.add(searchJb);

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
				paramMap.put(noticeTypeBox.getName(), String.valueOf(noticeTypeBox.getSelectedIndex()));
				paramMap.put(keyJtf.getName(), keyJtf.getText());

				while (dtm.getRowCount() > 0) {
					dtm.removeRow(dtm.getRowCount() - 1);
				}

				String jsonStr = new RpcService()
						.getCmNotices(paramMap);
				JSONArray jsonArray = JSONArray.parseArray(jsonStr);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					Vector<String> v = new Vector<String>();
					v.add(i + 1 + "");
					v.add(jsonObj.getString("cnId"));
					v.add(getNoticeType(jsonObj.getString("type")));
					v.add(jsonObj.getString("title"));
					v.add(jsonObj.getString("key"));
					v.add(getNoticeStatus(jsonObj.getString("status")));
					v.add(jsonObj.getString("recommend").equals("1")? "推荐":"不推荐");
					v.add(jsonObj.getString("abstractContent"));
					v.add(jsonObj.getString("abstractContent"));
					v.add(jsonObj.getString("creator"));
					v.add(DateUtils.toStandardStr(jsonObj.getDate("createTime")));
					v.add(jsonObj.getString("updater"));
					v.add(DateUtils.toStandardStr(jsonObj.getDate("updateTime")));
					v.add(jsonObj.getString("updater"));
					v.add(jsonObj.getString("updater"));
					dtm.addRow(v);
				}
				dtm.fireTableDataChanged();
				
			}

		});
		// data
		String[] tableHeads = { XrBundle.bundle.getString("ID"),
				XrBundle.bundle.getString("CN_ID"),
				 XrBundle.bundle.getString("TYPE_ITEM"),
				XrBundle.bundle.getString("TITLE"),
				XrBundle.bundle.getString("KEY_ITEM"),
				XrBundle.bundle.getString("STATUS"),
				XrBundle.bundle.getString("RECOMMEND"),
				XrBundle.bundle.getString("ABSTRACT_CONTENT"),
				XrBundle.bundle.getString("CONTENT"),
				XrBundle.bundle.getString("CREATOR"),
				XrBundle.bundle.getString("CREATE_TIME_ITEM"),
				XrBundle.bundle.getString("UPDATER"),
				XrBundle.bundle.getString("UPDATE_TIME"),
				XrBundle.bundle.getString("PUBLISHER"),
				XrBundle.bundle.getString("SUBMITTER")
				};
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

	protected String getNoticeType(String type) {
		if(type.equals("1")){
			return "维修资讯";
		}else if(type.equals("2")){
			return "常见故障";
		}else if(type.equals("3")){
			return "维护保养";
		}else if(type.equals("4")){
			return "电脑问题";
		}
		return null;
	}

	protected String getNoticeStatus(String status) {
		if(status.equals("0")){
			return "待提交";
		}else if(status.equals("1")){
			return "待审核";
		}else if(status.equals("2")){
			return "上线";
		}else if(status.equals("3")){
			return "撤下";
		}
		return null;
	}

}
