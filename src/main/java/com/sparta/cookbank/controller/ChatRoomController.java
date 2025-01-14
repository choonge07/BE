package com.sparta.cookbank.controller;

import com.sparta.cookbank.ResponseDto;
import com.sparta.cookbank.domain.chat.dto.MessageResponseDto;
import com.sparta.cookbank.domain.room.dto.*;
import com.sparta.cookbank.service.ChatService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatService chatService;


    @GetMapping("api/class") // 쿠킹클래스 전체 조회
    @ResponseBody
    public ResponseDto<?> room() {
        List<RoomResponseDto> Rooms = chatService.findAllRoom();
        boolean empty = false;
        if(Rooms.isEmpty())  empty = true;
        return ResponseDto.success(new ClassDto(empty, Rooms),"성공적으로 클래스를 가져왔습니다.");
    }

    @PostMapping("/api/class") //쿠킹클래스 생성
    @ResponseBody
    public ResponseDto<?> CreateClass(@ModelAttribute RoomRequestDto requestDto) throws IOException, OpenViduJavaClientException, OpenViduHttpException {
        ViduRoomResponseDto room = chatService.CreateRoom(requestDto);
        return ResponseDto.success(room,"성공적으로 방을 만들었습니다");
    }


    @GetMapping("api/class/enter/{class_id}") //쿠킹클래스 입장(이전채팅 불러오기)
    @ResponseBody
    public ResponseDto<?> EnterClass(@PathVariable Long class_id) throws OpenViduJavaClientException, OpenViduHttpException {
        MessageResponseDto responseDto = chatService.EnterRoom(class_id);
        return ResponseDto.success(responseDto,"성공적으로 이전 채팅을 가져왔습니다.");
    }

    @GetMapping("api/class/{class_id}") //쿠킹클래스 레시피 조회
    @ResponseBody
    public ResponseDto<?> RecipeInfo(@PathVariable Long class_id){
        RoomInfoResponseDto recipe = chatService.ClassRecipeInfo(class_id);
        return ResponseDto.success(recipe,"성공적으로 레시피를 가져왔습니다.");
    }

    @DeleteMapping("api/class/{class_id}") //쿠킹클래스 종료
    @ResponseBody
    public ResponseDto<?> RemoveClass(@PathVariable Long class_id){
        chatService.ApiRemoveClass(class_id);
        return ResponseDto.success(null,"클래스가 종료되었습니다.");
    }
}