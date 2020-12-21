package fzu.hhj.help2;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "fzu.hhj.help2.mapper")
@SpringBootApplication
public class Help2Application {

    public static void main(String[] args) {
        SpringApplication.run(Help2Application.class, args);
    }

}
