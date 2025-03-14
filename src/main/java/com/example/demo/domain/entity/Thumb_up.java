package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class  Thumb_up{
    //fk User의 username, Reply의 rno
    //Boolean thumb_up

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;

    private Boolean thumb_up;
    @ManyToOne
    @JoinColumn(name = "username",foreignKey = @ForeignKey(name = "FK_username",
            foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE") )
    private User user;

    @ManyToOne
    @JoinColumn(name = "rno",foreignKey = @ForeignKey(name = "FK_rno",
            foreignKeyDefinition = "FOREIGN KEY (rno) REFERENCES reply(rno) ON DELETE CASCADE ON UPDATE CASCADE") )
    private Reply reply;

}
