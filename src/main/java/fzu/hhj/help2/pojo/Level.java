package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "expTopLimit")
    private Integer exptoplimit;

    @Column(name = "expBotLimit")
    private Integer expbotlimit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    public Integer getExptoplimit() {
        return exptoplimit;
    }

    public void setExptoplimit(Integer exptoplimit) {
        this.exptoplimit = exptoplimit;
    }


    public Integer getExpbotlimit() {
        return expbotlimit;
    }

    public void setExpbotlimit(Integer expbotlimit) {
        this.expbotlimit = expbotlimit;
    }

}
