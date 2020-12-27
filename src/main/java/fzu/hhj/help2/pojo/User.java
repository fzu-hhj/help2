package fzu.hhj.help2.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "head")
    private String head;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "is_verify")
    private String isVerify;

    @Column(name = "realname")
    private String realname;

    @Column(name = "college")
    private String college;

    @Column(name = "class_num")
    private int classNum;

    @Column(name = "level")
    private int level;

    @Column(name = "is_suspend")
    private String isSuspend;

    @Column(name = "suspend_time")
    private LocalDateTime suspendTime;

    @Column(name = "exp")
    private Integer exp;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }


    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }


    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(String isSuspend) {
        this.isSuspend = isSuspend;
    }


    public LocalDateTime getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(LocalDateTime suspendTime) {
        this.suspendTime = suspendTime;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
