package com.example.chats.client;

public class Chat {

    String text;
    int image;

    public Chat(int image, String text){
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

}