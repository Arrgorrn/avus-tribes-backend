package org.gfa.avustribesbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

  @Configuration
  public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost("sandbox.smtp.mailtrap.io");
      mailSender.setPort(25);
      mailSender.setUsername("9df1100280b9f2");
      mailSender.setPassword("36f6cbde27134b");

      return mailSender;
    }
  }

