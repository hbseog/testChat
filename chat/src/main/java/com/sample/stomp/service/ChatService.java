package com.sample.stomp.service;

import com.sample.stomp.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private Map<String, ChatRoom> chatRooms;
    private Queue<String> waitingUsers; //##대기중인 사용자를 저장할 큐
    private String roomName;
    @PostConstruct
    //빈 생성 후 의존성 주입이 끝나면 초기화
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
        waitingUsers = new LinkedList<>();
        log.info("ChatService initialized");
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public String matchRandomChat(String username) {
        //##username이 null이거나 name과 값이 같다면 null을 반환
        if (username == null ) {
            System.out.println("11??");
            return null;
        }
        //##대기중인 사용자가 없다면 현재 사용자를 대기열에 넣고 null을 반환
        if(waitingUsers.peek() == null || waitingUsers.peek().isEmpty()) {
            waitingUsers.offer(username);
            System.out.println("대기중"+username);
            return null;
        }
        if(Objects.equals(waitingUsers.peek(), username)){
            System.out.println("대기중!!!!!!!1"+username);
            if(!roomName.isEmpty()){
                return roomName;
            }
            return null;
        }
        //##대기중인 사용자가 있다면 대기열에서 한명을 빼고 그 사용자와의 채팅방을 반환
        else {
            System.out.println("22?");
            waitingUsers.offer(username);
            roomName = waitingUsers.poll() + "와 " + waitingUsers.poll() + "의 랜덤채팅";
            ChatRoom chatRoom = ChatRoom.createRandom(roomName);
            chatRooms.put(chatRoom.getRoomId(), chatRoom);
            return chatRoom.getRoomId();
        }

    }



}
