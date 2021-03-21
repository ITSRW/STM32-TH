package STM32Project;

import gnu.io.SerialPort;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class transmition
{
	/*
	 * 上位机往单板机通过串口发送数据
	 * 串口对象 seriesPort 
	 * 数据帧:dataPackage
	 * 发送的标志:数据未发送成功抛出一个异常
	 */
	public void uartSendDatatoSerialPort(SerialPort serialPort,byte[] dataPackage)
	{
		System.out.println("发送数据...");
		OutputStream out=null;
		try
		{
			out=serialPort.getOutputStream();
			out.write(dataPackage);
			System.out.println("推送中...");
			out.flush();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}finally
		{
			//关闭输出流
			System.out.println("关闭流...");
			if(out!=null)
			{
				try 
				{
					out.close();
					out=null;
					System.out.println("数据已发送完毕!");
				} catch (IOException e) 
				{
					e.printStackTrace();
				}   
			}
		}           
	}
	/*
	 * 上位机接收数据
	 * 串口对象seriesPort
	 * 接收数据buffer
	 * 返回一个byte数组
	 */
	public static String readData(SerialPort serialPort){
        String str="";
        try {
            if(serialPort!=null){
                InputStream inputStream = serialPort.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i;
                while ((i = inputStream.read()) != -1) {
                    baos.write(i);
                }
                str = baos.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}