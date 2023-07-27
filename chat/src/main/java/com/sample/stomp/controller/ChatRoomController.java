package com.sample.stomp.controller;

import com.sample.stomp.config.WebsocketConfig;
import com.sample.stomp.model.ChatRoom;
import com.sample.stomp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;
//    @Autowired
//    private ChatConfig WebSocketSessionManager webSocketSessionManager;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findById(roomId);
    }

    @PostMapping("/random")
    public ResponseEntity<String> startRandomChat(@RequestParam String username) {
        String roomId = chatService.matchRandomChat(username);
        return roomId == null ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(roomId, HttpStatus.OK);
    }
//    @OnOpen
//    public void onOpen(WebSocketSession session, @PathParam("username") String username) {
//        webSocketSessionManager.register(username, session);
//    }
//
//    @OnClose
//    public void onClose(@PathParam("username") String username) {
//        webSocketSessionManager.unregister(username);
//    }
}
