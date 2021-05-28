package com.system;

import com.entity.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface NotificationSystem {

    void sendNotification(Entity from, Entity to, String content, LocalDateTime time);
    void diffuseNotification(Entity from, ArrayList<Entity> to, String content, LocalDateTime time);

}
