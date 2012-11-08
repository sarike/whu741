package net.rusb.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class SendEmail implements Runnable{
	private static String host ;
	private static String from;
	private static String password;
	private static String subject;
	private static String port;
	
	static {
		host = Configs.get("host");
		from = Configs.get("from");
		password = Configs.get("password");
		subject = Configs.get("subject");
		port = Configs.get("port");
	}
	
    //使用超文本格式发送邮件
	//  MailSender sendmail = MailSender.getHtmlMailSender(host, from,password);
	//使用纯文本格式发送邮件
	MailSender sendmail = null;
	
	public void send(String msg,List<String> addressList){
		if(addressList==null){
			return;
		}
		String[] toAddress = new String[addressList.size()];
		addressList.toArray(toAddress);
		for (String string : toAddress) {
			System.out.println(string);
		}
		sendmail = MailSender.getTextMailSender(host, from,password);
        try {
            sendmail.setSubject("风流倜傥741");
            sendmail.setSendDate(DateTool.getCST());
            sendmail.setMailContent(msg); //
            sendmail.setMailFrom(from);
            sendmail.setMailTo(toAddress, "to");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
			try {
	            //开始发送邮件
	            System.out.println("正在发送邮件请稍候.......");
	            sendmail.sendMail();
	            System.out.println("恭喜你，邮件已经成功发送!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		SendEmail se = new SendEmail();
		List<String> addrList = new ArrayList<String>();
		addrList.add("821398245@qq.com");
//		addrList.add("sl_nevergiveup@163.com");
		se.send("你好吗，磊哥", addrList);
	}
}
