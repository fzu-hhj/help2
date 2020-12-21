package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private int content;

    @Column(name = "time")
    private int time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
