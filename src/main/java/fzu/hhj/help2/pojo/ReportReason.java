package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report_reason")
public class ReportReason {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
