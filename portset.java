package STM32Project;

import gnu.io.*;
import java.util.*;
import javax.swing.JTextArea;

public class portset{
	/*
	 * 串口常见设置
	 * 1)打开串口
	 * 2)设置波特率 根据单板机的需求可以设置为57600 ...
	 * 3)判断端口设备是否为串口设备
	 * 4)端口是否占用
	 * 5)对以上条件进行check以后返回一个串口设置对象new UARTParameterSetup()
	 * 6)return:返回一个SerialPort一个实例对象，若判定该com口是串口则进行参数配置
	 *   若不是则返回SerialPort对象为null
	 */
	static Enumeration<CommPortIdentifier> portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static portset optor=new portset();
	static transmition transer=new transmition();
	
	@SuppressWarnings("unchecked")
	public Enumeration<CommPortIdentifier> getlist() {
		// 获得当前所有可用串口
		portList = CommPortIdentifier.getPortIdentifiers();
		return portList;
	}
	
	public SerialPort portParameterOpen(String portName,int baudrate,JTextArea textArea)
	{
		serialPort=null;
		try
		{  //通过端口名识别串口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			//打开端口并设置端口名字 serialPort和超时时间 2000ms
			CommPort commPort=portIdentifier.open(portName,1000);
			//进一步判断comm端口是否是串口 instanceof
			if(commPort instanceof SerialPort)
			{
				textArea.append("发现"+portName+"串口！");
				//进一步强制类型转换
				serialPort=(SerialPort)commPort;
				//设置baudrate 此处需要注意:波特率只能允许是int型 对于57600足够
				//8位数据位
				//1位停止位
				//无奇偶校验
				serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				//串口配制完成 log
				textArea.append("串口参数设置已完成，波特率为"+baudrate+",数据位8bits,停止位1位,无奇偶校验"+"\n");
			}
			//不是串口
			else
			{
				textArea.append("该com端口不是串口,请检查设备!"+"\n");
				//将com端口设置为null 默认是null不需要操作
			}
		} 
		catch (NoSuchPortException e) 
		{
			e.printStackTrace();
		} 
		catch (PortInUseException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedCommOperationException e)
		{
			e.printStackTrace();
		}
		return serialPort;      
	}
}