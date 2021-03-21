package STM32Project;

import gnu.io.CommPortIdentifier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JTextArea;

public class View extends JFrame {
	public static portset scanner =new portset();
	static Enumeration<CommPortIdentifier> portList;
	static CommPortIdentifier portId;
	private JPanel contentPane;
	private JLabel portlistlable;
	
	public static Thread t;
	public static stringline line;
	final JButton start= new JButton("开始采集");
	JButton stop = new JButton("终止采集");
	JTextField botrate=new JTextField();
	JTextField txtLocation=new JTextField();
	JTextArea textArea = new JTextArea();
	JComboBox<String> COMlist = new JComboBox<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public View() {
		setResizable(false);
		setTitle("数据采集程序");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		portlistlable = new JLabel("串口列表");
		portlistlable.setFont(new Font("宋体", Font.PLAIN, 18));
		portlistlable.setBounds(25, 27, 96, 21);
		contentPane.add(portlistlable);
		
		JLabel botratelable = new JLabel("设置波特率");
		botratelable.setFont(new Font("宋体", Font.PLAIN, 18));
		botratelable.setBounds(25, 61, 96, 21);
		contentPane.add(botratelable);
		COMlist.setBounds(154, 27, 229, 21);
		contentPane.add(COMlist);
		botrate.setText("115200");
		botrate.setBounds(154, 61, 229, 24);
		contentPane.add(botrate);
		botrate.setColumns(10);
		JLabel locationlable = new JLabel("数据存储地址");
		locationlable.setFont(new Font("宋体", Font.PLAIN, 18));
		locationlable.setBounds(25, 95, 120, 21);
		contentPane.add(locationlable);
		txtLocation = new JTextField();
		txtLocation.setText("C:\\Users\\83810\\Desktop\\data.txt");
		txtLocation.setColumns(10);
		txtLocation.setBounds(154, 95, 229, 24);
		contentPane.add(txtLocation);
		

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//线程控制语句：先向串口发送一个信号，然后再开始“接收-入库-显示”工作循环
				line=new stringline(Integer.parseInt(botrate.getText()),txtLocation.getText(),COMlist.getSelectedItem().toString(),textArea);
				t=new Thread(line);
				t.start();
				start.setEnabled(false);
				stop.setEnabled(true);
			}
		});
		start.setBounds(397, 26, 133, 27);
		contentPane.add(start);
		
		stop.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				//终止由start按钮打开的
				t.stop();
				line.stopthread();
				stop.setEnabled(false);
				start.setEnabled(true);
			}
		});
		stop.setBounds(544, 26, 133, 27);
		contentPane.add(stop);
		
		JButton saveasfile = new JButton("保存");
		saveasfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text=textArea.getText();
				String Location=txtLocation.getText();
				File fp=new File(Location);
				PrintWriter pfp;
				try {
					pfp = new PrintWriter(fp);
					pfp.print(text);
					pfp.close();
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}	
				textArea.append("已保存到指定位置！");
			}
		});
		saveasfile.setBounds(397, 60, 133, 27);
		contentPane.add(saveasfile);
		
		JButton exit = new JButton("退出");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setBounds(544, 60, 133, 27);
		contentPane.add(exit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 139, 684, 249);
		contentPane.add(scrollPane);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		portList=scanner.getlist();
		while(portList.hasMoreElements()){
			portId = (CommPortIdentifier)portList.nextElement();
			if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
				COMlist.addItem(portId.getName());
			}
		}
	}
}
