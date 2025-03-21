package com.example.demo.domain.dto;



import com.example.demo.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {



    private Long no;
    @NotBlank(message = "username을 입력하세요")
    @Email(message = "올바른 이메일 주소를 입력하세요")
    private String username;
    private String nickname;
    private String title;
    private String content;
    private String tag;
    private String isnotice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regdate;
    private Long count;


    public static BoardDto Of(Board board) {
        BoardDto dto = new BoardDto();
        dto.no = board.getNo();
        dto.tag = board.getTag();
        dto.title=board.getTitle();
        dto.content = board.getContent();
        dto.regdate = board.getRegdate();
        dto.count = board.getCount();
        dto.username  = board.getUsername();
        dto.nickname = board.getNickname();
        dto.isnotice = board.getIsnotice();

        return dto;
    }
}
