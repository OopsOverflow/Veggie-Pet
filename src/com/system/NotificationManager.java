package com.system;

import com.entity.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotificationManager{

    /**
     * Méthode modélisant l'envoi d'une notification
     * @param content le contenu de la notification
     * @param from l'entité qui envoie la notification
     * @param time la date de la notification
     * @param to l'entité destinataire de la notification
     */
    public static void sendNotification(Entity from, Entity to, String content, LocalDateTime time) {
        String stb = "[Notification] ------- " + time.toString() + "\n" + "\tFrom : " + from.getName() + "\n" +
                "\tContent : \n" + content + "\n";
        to.appendLetterBox(stb);
    }

    /**
     * Méthode permettant la diffusion d'une notification
     * @param time la date de la notification
     * @param from l'entité qui envoi la notification
     * @param content le contenu de la notification
     * @param to l'entité destinataire de la notification
     */
    public static void diffuseNotification(Entity from, ArrayList<Entity> to, String content, LocalDateTime time) {
        to.forEach(dest -> {
            sendNotification(from, dest, content, time);
        });
    }

    /**
     * Méthode modélisant l'envoi d'une notification ainsi qu'un rapport. Ce rapport contiendra les informations de la
     * notification
     * @param time la date de la notification
     * @param from l'entité qui envoi la notification
     * @param content le contenu de la notification
     * @param to l'entité destinataire de la notification
     * @param attachement le rapport
     */
    public static void sendNotificationWithReport(Entity from, Entity to, String content, Report attachement ,LocalDateTime time){
        String stb = "[Notification] ------- " + time.toString() + "\n" + "\tFrom : " + from.getName() + "\n" +
                "\tContent : \n" + content + "\n" + attachement.toString() + "\n";
        to.appendLetterBox(stb);
    }
}
