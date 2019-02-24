package com.example.bilkent.DataClasses;

public class UsersChoice {
    private String choice;
    private int numberOfUsers;

    public UsersChoice(String choice, int numberOfUsers) {
        this.choice = choice;
        this.numberOfUsers = numberOfUsers;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
