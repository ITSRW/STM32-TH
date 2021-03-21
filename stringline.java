package STM32Project;

import java.text.SimpleDateFormat;
import java.util.Date;

import gnu.io.SerialPort;

import javax.swing.JTable;
import javax.swing.JTextArea;

public class stringline implements Runnable{

	public int botrate;
	public String location;
	public String port;
	public JTable table;
	public JTextArea textArea;
	
	transmition tran=new transmition();
	public portset setport=new portset();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	public stringline(int botrate,String location,String port,JTextArea textArea){//构造方法
		this.botrate=botrate;
		this.location=location;
		this.port=port;
		this.textArea=textArea;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run(){
		SerialPort port=setport.portParameterOpen(this.port, this.botrate,this.textArea);
		while(true){
			String data=tran.readData(port);
			if(data.length()!=0){
				
				data=df.format(new Date())+"  实时湿度："+data.split(" ")[0]+"%  实时温度:"+data.split(" ")[1]+"℃";
				this.textArea.append(data+"\n");
				
			}
		}
	}

	public void stopthread(){
		setport.serialPort.close();
		this.textArea.append("终止采集！"+"\n");
		this.textArea.append("串口已关闭！"+"\n");
	}
}
