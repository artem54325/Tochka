package com.example.test.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
public class Subscriber {

    @Id
    private UUID uuid;
    private String fullName;
    private int balance;
    private int hold;
    private boolean isStatus;

    public Subscriber() {
    }

    public Subscriber(UUID uuid, String fullName, int balance, int hold, boolean isStatus) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.balance = balance;
        this.hold = hold;
        this.isStatus = isStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }
}
