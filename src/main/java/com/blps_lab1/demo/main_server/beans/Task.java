package com.blps_lab1.demo.main_server.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="komus_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ID;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Notification> notifications;


    public long getID() {
        return ID;
    }

    public User getUser() {
        return user;
    }


    public void setID(long ID) {
        this.ID = ID;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
