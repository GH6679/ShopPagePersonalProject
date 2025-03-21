package com.example.demo.restcontroller;



import com.example.demo.domain.dto.BoardDto;
import com.example.demo.domain.dto.ReplyDto;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardRestController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private UserRepository userRepository;





    //-------------------
    // 삭제하기
    //-------------------
    @DeleteMapping("/delete")
    public String delete(Long no){
        log.info("DELETE /board/delete no " + no);

        boolean isremoved =  boardService.removeBoard(no);
        if(isremoved)
            return "success";
        else
            return "failed";

    }

    //-------------------
    //댓글추가
    //-------------------
    @GetMapping("/reply/add")
    public void addReply(Long bno,String contents , String username){
        log.info("GET /board/reply/add " + bno + " " + contents + " " + username);
        boardService.addReply(bno,contents, username);
    }


    //-------------------
    //댓글 조회
    //-------------------
    @GetMapping("/reply/list")
    public List<ReplyDto> getListReply(Long bno){
        log.info("GET /board/reply/list " + bno);
        List<ReplyDto> list =  boardService.getReplyList(bno);
        return list;
    }




    //-------------------
    //댓글 카운트
    //-------------------
    @GetMapping("/reply/count")
    public Long getCount(Long bno){
        log.info("GET /board/reply/count " + bno);
        Long cnt = boardService.getReplyCount(bno);

        return cnt;
    }


    //-------------------
    //댓글수정
    //-------------------

    @PostMapping("/reply/update")
    public ResponseEntity<String> updateReply(@RequestBody ReplyDto replyDto) {
        try {
            // 수정된 내용을 데이터베이스에 업데이트
            boardService.updateReply(replyDto);
            return ResponseEntity.ok("댓글 수정 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 실패");
        }
    }

    //-------------------
    //권한
    //-------------------
    @GetMapping("/getRole/{username}")
    public String getRole(@PathVariable String username) {
        String role = boardService.getRole(username);
        System.out.println("현재 권한: "+role);
        return role;
    }


    @GetMapping("/getVouch/{username}")
    public Long getVouch(@PathVariable String username) {
        Long vouch = boardService.getVouch(username);
        System.out.println(username+"의 현재 vouch: "+vouch);
        return vouch;
    }


    //================================================================
    //공지사항을 불러오기
    //================================================================
    @GetMapping("/getnotice")
    public List<BoardDto> getNotice() {

        List<BoardDto> dto = boardService.getNotice();

        System.out.println("공지 호출 = " + dto);

        return dto;

    }




}
