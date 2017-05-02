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
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import android.telephony.TelephonyManager;

/**
 * Created by robis on 17/04/28.
 */

public class LoggingAndUpload {
        //logging
        static Context c;
        static Logger logger = Logger.getLogger("Log");
    static File log;
    // static File log = new File(context.getFilesDir(), "log.log");
        static FileHandler handler;
        //upload
        static JSch jsch = new JSch();
        static Session session = null;
        static TelephonyManager tm;

        //logging
    LoggingAndUpload(Context context){
        log = new File(context.getFilesDir(), "log.log");
    }
    public static void Launch(Context context) {
        c=context;
        log = new File(context.getFilesDir(), "log.log");
        try

        {
            handler = new FileHandler(context.getFilesDir() + "log.log", true);
            logger.addHandler(handler);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
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

    public static void Upload() {
        try {
            session = jsch.getSession("logs", "185.117.22.160", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("mea2gr2");
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put(c.getFilesDir() + "log.log", "/home/logs/log+" + tm.getDeviceId() + ".log");
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