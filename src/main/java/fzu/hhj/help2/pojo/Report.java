package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    @Column(name = "reporter_id")
    private Integer reporterId;

    @Column(name = "is_handled")
    private String isHandled;

    @Column(name = "reason_id")
    private Integer reasonId;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "reported_id")
    private Integer reportedId;

    @Column(name = "reported_category")
    private Integer reportedCategory;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }


    public String getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(String isHandled) {
        this.isHandled = isHandled;
    }


    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    public Integer getReportedId() {
        return reportedId;
    }

    public void setReportedId(Integer reportedId) {
        this.reportedId = reportedId;
    }


    public Integer getReportedCategory() {
        return reportedCategory;
    }

    public void setReportedCategory(Integer reportedCategory) {
        this.reportedCategory = reportedCategory;
    }

}
