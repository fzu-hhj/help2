               package fzu.hhj.help2.Util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
public class ImgUtil {
    /**
     * 将数据库中的头像转化为html代码
     * @param fileName 头像的名称(带后缀)
     * @return 头像对应的html代码
     */
    @NotNull
    @Contract(pure = true)
    public static String changeAvatar(String fileName) {
        return "<img src='../head/" + fileName + "' width='100%' height='100%' style='border-radius: 100%' alt=''>";
    }

    @NotNull
    @Contract(pure = true)
    public static String changeAvatar(String fileName, int upper) {
        String up = "";
        for (int i = 0; i < upper; i++) {
            up += "../";
        }

        return "<img src='"+ up +"head/" + fileName + "' width='100%' height='100%' style='border-radius: 100%' " +
                "alt=''>";
    }


}
