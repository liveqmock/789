package com.icebreak.p2p.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailSender {
	protected final Logger	logger	= LoggerFactory.getLogger(this.getClass());
	
	/** 
	  * 以文本格式发送邮件 
	  * @param mailInfo 待发送的邮件的信息 
	  */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		//发送身份校验	
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器 
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session 
		Session sendMailSession = mailInfo.getMailSession(pro, authenticator);
		try {
			// 根据session创建一个邮件消息 
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址 
			Address from = new InternetAddress(mailInfo.getFromAddress());
			//设置自定义发件人昵称  
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getSenderNickName());
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException", e);
			}
			// 设置邮件消息的发送者 
			mailMessage.setFrom(new InternetAddress(nick + " <" + from + ">"));
			// 创建邮件的接收者地址，并设置到邮件消息中 
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题 
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间 
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容 
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件 
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			logger.error("MessagingException 发送邮件失败", ex);
		}
		return false;
	}
	
	/** 
	  * 以HTML格式发送邮件 
	  * @param mailInfo 待发送的邮件信息 
	  */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证 
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//如果需要身份认证，则创建一个密码验证器  
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session 
		Session sendMailSession = mailInfo.getMailSession(pro,authenticator);
		try {
			// 根据session创建一个邮件消息 
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址 
			Address from = new InternetAddress(mailInfo.getFromAddress());
			//设置自定义发件人昵称  
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getSenderNickName());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 设置邮件消息的发送者 
			mailMessage.setFrom(new InternetAddress(nick + " <" + from + ">"));
			// 创建邮件的接收者地址，并设置到邮件消息中 
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO 
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题 
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间 
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart 
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容 
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			//添加附件
			if (mailInfo.getAttachFiles() != null && mailInfo.getAttachFiles().length > 0) {
				for (int i = 0; i < mailInfo.getAttachFiles().length; i++) {
					File affix = new File(mailInfo.getAttachFiles()[i]);
					String affixName = affix.getName();
					BodyPart messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(affix);
					//添加附件的内容
					messageBodyPart.setDataHandler(new DataHandler(source));
					//添加附件的标题
					//这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
					try {
						messageBodyPart.setFileName(MimeUtility.encodeText(affixName, "GBK", "B"));
					} catch (UnsupportedEncodingException e) {
						logger.error("UnsupportedEncodingException", e);
					}
					mainPart.addBodyPart(messageBodyPart);
				}
			}
			//将multipart对象放到message中
			mailMessage.setContent(mainPart);
			// 发送邮件 
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			logger.error("MessagingException", ex);
			//ex.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {

	}
}
