package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private int id;

    @Column(name = "reporter_id")
    private int reporterId;

    @Column(name = "is_handled")
    private String isHandled;

    @Column(name = "reason_id")
    private int reasonId;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "reported_id")
    private int reportedId;

    @Column(name = "reported_category")
    private int reportedCategory;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }


    public String getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(String isHandled) {
        this.isHandled = isHandled;
    }


    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
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


    public int getReportedId() {
        return reportedId;
    }

    public void setReportedId(int reportedId) {
        this.reportedId = reportedId;
    }


    public int getReportedCategory() {
        return reportedCategory;
    }

    public void setReportedCategory(int reportedCategory) {
        this.reportedCategory = reportedCategory;
    }

}
