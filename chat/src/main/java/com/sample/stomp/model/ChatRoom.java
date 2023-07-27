package com.sample.stomp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {

    private String roomId;
    private String roomName;


    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }
    public static ChatRoom createRandom(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = name;
        room.roomName = room.roomId;
        return room;
    }
}
