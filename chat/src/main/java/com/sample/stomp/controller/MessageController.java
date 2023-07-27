package com.sample.stomp.controller;

import com.sample.stomp.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    //클라이언트에게 메세지를 전송할 때 사용되는 인터페이스

    @MessageMapping("/chat/message")
    //메세지 핸들링 어노테이션으로 웹소켓 메세지를 처리
    public void enter(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
        //실제 메세지가 전송되는 부분
    }
}
