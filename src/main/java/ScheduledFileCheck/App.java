package ScheduledFileCheck;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Hello world!
 *
 */
@Component
public class App 
{
    byte[] fileContent;
    Path p= FileSystems.getDefault().getPath("tempFile.txt");

    public static void main( String[] args )
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-config.xml");
        App app=context.getBean(App.class);
    }

    public App() {
        try {
            this.fileContent = Files.readAllBytes(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron="*/1 * * * * *")
    public void FileCheck() {
        byte[] temp= null;
        try {
            temp = Files.readAllBytes(p);
            if (!Arrays.equals(temp,fileContent)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date)+" - File changed!");
                fileContent=temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
