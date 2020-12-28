package fzu.hhj.help2.pojo;

import javax.persistence.*;

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
    private Integer expTopLimit;

    @Column(name = "expBotLimit")
    private Integer expBotLimit;


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


    public Integer getExpTopLimit() {
        return expTopLimit;
    }

    public void setExpTopLimit(Integer expTopLimit) {
        this.expTopLimit = expTopLimit;
    }


    public Integer getExpBotLimit() {
        return expBotLimit;
    }

    public void setExpBotLimit(Integer expBotLimit) {
        this.expBotLimit = expBotLimit;
    }

}
