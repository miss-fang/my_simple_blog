package com.cl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 用于后台事件邮件通知
 */
@Service
public class MailSendService {
	private static final Logger mail_logger = LoggerFactory.getLogger("mail_logger");
	@Autowired
	private JavaMailSender mailSender;
	@Value("${mail.fromMail}")
	private String from;

	public void sendMail(String to, String title, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(title);
		message.setText(msg);
		try {
			mailSender.send(message);
			mail_logger.info("发送消息-" + msg + "-成功");
		} catch (MailException e) {
			mail_logger.error("发送消息-" + msg + "-失败");
			e.printStackTrace();
		}
	}
}
