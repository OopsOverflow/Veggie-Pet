package com.system;

import com.entity.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotificationManager{

    public static void sendNotification(Entity from, Entity to, String content, LocalDateTime time) {
        String stb = "[Notification] ------- " + time.toString() + "\n" + "\tFrom : " + from.getName() + "\n" +
                "\tContent : \n" + content + "\n";
        to.appendLetterBox(stb);
    }

    public static void diffuseNotification(Entity from, ArrayList<Entity> to, String content, LocalDateTime time) {
        to.forEach(dest -> {
            sendNotification(from, dest, content, time);
        });
    }
}
