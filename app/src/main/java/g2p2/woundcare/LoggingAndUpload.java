package g2p2.woundcare;

import android.content.Context;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by robis on 17/04/28.
 */

public class LoggingAndUpload extends Thread {
        //logging

        static Context c;
        static Logger logger = Logger.getLogger("Log");
    static File log;
    // static File log = new File(context.getFilesDir(), "log.log");r
        static FileHandler handler;
        //upload
        static JSch jsch = new JSch();
        static Session session = null;
    static Format formatt=new Format();



        //logging

    
    public static void Launch(Context context) {
        c=context;
        log = new File(context.getFilesDir(), "log_data.csv");


    

        try

        {
            handler = new FileHandler(context.getFilesDir() + "log_data.csv", true);
            logger.addHandler(handler);
            handler.setFormatter(formatt);
            logger.setUseParentHandlers(false);
        } catch (
                SecurityException e)

        {
            e.printStackTrace();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            session = jsch.getSession("logs", "185.117.22.160", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("mea2gr2");
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;

            sftp.put(c.getFilesDir() + "log_data.csv", "/home/logs/log+" + Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

            sftp.exit();
            session.disconnect();
            System.out.println("success");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }

    public static void info(String x) {
        logger.info(x);
    }
}
class Format extends Formatter{
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date())).append(" , ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
}