package com.app.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			SendMail("博博","20181126","bobo@163.com","你啊你");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 379532707@qq.com  发往 13121019741@163.com
	public static void SendMail(String name, String mobile, String email, String content) throws Exception {
		Properties props = new Properties();
		// 开启debug调试
		String account = "13121019741@163.com";
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		// 设置环境信息
		Session session = Session.getInstance(props);
		// 创建邮件对象
		Message msg = new MimeMessage(session);
		msg.setSubject("娄店");   // 邮件标题
		String contents = String.format("******这是一封由服务器转发的邮件，请勿直接回复******\n您好，我是会员%s\n手机会为%s\n电子邮件为%s\n我的预约售后服务为：%s\n请及时帮我处理！", name, mobile, email, content);
		// 设置邮件内容 ******这是一封验证邮箱的邮件******\n 如果您有投资的意愿，请回复邮件，我们会有业务员跟您联系！
		msg.setText(contents);
		// 设置发件人,并设置标头
		msg.setFrom(new InternetAddress("379532707@qq.com", "胡英姿"));
		// 获得链接
		Transport transport = session.getTransport();
		// 连接邮件服务器，这里设置发件人的邮箱和密码。如果是163的邮箱就把"smtp.qq.com"改成"smtp.163.com"
		// 其他的同理，不然连接不到邮件服务器
		// **开启379532707@qq.com 邮箱的POP3/SMTP/IMAP:服务 **
		transport.connect("smtp.qq.com", "379532707@qq.com", "ifbspsmpbddocagc");  // 这个不是密码 而是授权码？
		// 发送邮件  （多个地址 数组）
		transport.sendMessage(msg, new Address[] { new InternetAddress("172624610@qq.com"),new InternetAddress(account) });
		// 关闭连接
		transport.close();
	}

}
