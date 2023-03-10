package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private static final String DOWNLOADED = "downloaded_";
    private static final int ONE_SECOND = 1000;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(DOWNLOADED + url.
                     substring(url.lastIndexOf("/") + 1))) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            var launch = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    var diff = System.currentTimeMillis() - launch;
                    if (diff < ONE_SECOND) {
                        Thread.sleep(ONE_SECOND - diff);
                    }
                    downloadData = 0;
                    launch = System.currentTimeMillis();
                }

                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not acceptable quantity of the parameters.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
