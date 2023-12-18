package com.noteu.noteu.chat.controller;

import com.noteu.noteu.chat.dto.ChatMessageResponse;
import com.noteu.noteu.chat.dto.request.ChatMessageRequestDto;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;
import com.noteu.noteu.chat.service.SocketChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations sendingOperations;
    private final SocketChatService chatService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessageRequestDto chatMessageRequestDto) {
        log.info("채팅으로 넘어온 값 : {}", chatMessageRequestDto);

        ChatMessageResponse chatMessageResponseDto = chatService.reqMessage(chatMessageRequestDto);

        sendingOperations.convertAndSend("/topic/chats/room/"+chatMessageRequestDto.getRoomId(),chatMessageResponseDto);
    }
}
