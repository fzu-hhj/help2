package fzu.hhj.help2.Util;

public class ConstantUtil {
    //公共
    /**
     * 返回的JSON中resultCode的值，代表请求对应的操作执行成功
     */
    public final static int SUCCESS = 0;
    /**
     * 返回的JSON中resultCode的值，代表用户未登录
     */
    public final static Integer UN_LOGIN = 1;
    /**
     * 返回的JSON中resultCode的值，代表内容存在敏感词
     */
    public final static int JSON_RESULT_CODE_VERIFY_TEXT_FAIL = 10;
    /**
     * 返回的JSON中resultCode的值，代表用户被封禁
     */
    public final static int JSON_RESULT_CODE_BAN = 11;
    /**
     * 返回的JSON中resultCode的值
     * 内容不存在，现在用于question/viewQuestion.action、answer/viewAnswer.action、comment/viewComments.action、
     * reply/showMoreReply.action中
     */
    public final static int JSON_RESULT_CODE_NON_EXISTENT = 12;

    /**
     * 返回的JSON中resultCode的值
     * 没有更多了
     */
    public final static int NO_MORE = 2;
    public final static String JSON_RETURN_CODE_NAME = "resultCode";
    public final static String SESSION_USER = "user";
    public final static String RETURN_STRING = "success";

    public final static Integer HASH_MAP_NUM = 20;
    public final static int MIN_HASH_MAP_NUM = 10;

    //Admin
    public final static String LOGIN_ADMIN_SESSION_NAME = "admin";
    public final static String NO_ADMIN = null;
    public final static int DAILY_QUESTION_SUM_STATICS_TYPE_CODE = 0;
    public final static int DAILY_USER_SUM_STATICS_TYPE_CODE = 1;
    public final static int DAILY_ACTIVE_USER_STATICS_TYPE_CODE = 0;
    public final static int DAILY_ACTIVE_USER_STATICS_LENGTH = 7;
    public final static int MONTHLY_ACTIVATE_USER_STATICS_TYPE_CODE = 1;
    public final static int MONTHLY_ACTIVATE_USER_STATICS_LENGTH = 3;
    public final static int SCHOOL_IS_EXIST = 1;
    public final static int SCHOOLNAME_IS_BLANK = 2;
    public final static int STUDENT_NUM_IS_EXIST = 1;
    public final static int JOB_NUM_IS_EXIST = 1;


    //Answer
    public final static int MISS_QUESTIONID = 2;
    public final static int MISS_ANSWER_IF = 3;
    public final static int USER_IS_NOT_QUESTIONER = 2;
    public final static int USER_IS_NOT_ANSWERER = 1;
    public final static int USER_IS_NOT_ANSWERER_TWO = 2;
    public final static int RECOMMEND_ANSWERS_NUM_PER_TIME = 20;
    public final static int ANSWER_IN_NOT_APPROVED = 2;

    //Comment
    public final static int MISS_COMMENT_INF = 2;
    public final static int RESULT_CODE_APPROVED = 2;
    public final static int RESULT_CODE_NOT_APPROVED = 2;
    public final static int COMMENT_DEFAULT_SORT = 0;
    public final static int NO_COMMENTER = 2;

    //Partition
    public final static String JSON_QUESIONTS = "questions";
    public final static int RESPONSE_NUM = 20;
    public final static int PARTITION_QUESTION_COUNT = 20;




    //User
    public final static String EMAIL = "email";
    public final static String VERIFY_CODE = "verifyCode"
            ;
    public final static int LOGON_WRONG_VERIFY_CODE = 3;
    public final static int LOGON_WRONG_EMAIL = 4;
    public final static int LOGON_EXIST_USERNAME = 1;
    public final static int LOGON_EXIST_EMAIL = 2;

    public final static int LOGIN_WRONG_EMAIL = 4;
    public final static int LOGIN_WRONG_VERIFY_CODE = 2;
    public final static int LOGIN_NO_ELIGIBLE_USER = 3;
    public final static int LOGIN_WRONG_PASSWORD = 1;
    public final static String LOGIN_USER_SESSION_NAME = "user";
    public final static int NO_USER = 1;

    //IdentityAction
    //认证失败
    public final static Integer AUTHENTICATION_FAILED = 1;


    /**
     * 前端发来的数据出错
     */
    public static final Integer RESULT_CODE_PARAMS_ERROR = 2;

    //MessageUtil
    //表示用户向用户发送的消息
    public final static int USER2USER = 0;
    //表示系统的通知消息
    public final static int SYSTEM_NOTIFICATION = 1;
    //表示系统的奖励消息
    public final static int SYSTEM_REWARD = 2;
    //表示系统的处罚消息
    public final static int SYSTEM_PENALTY = 3;

    //Reply
    //按likes降序
    public static final int REPLY_DEFAULT_SORT = 0;
    //按时间降序
    public static final int REPLY_DATE_SORT = 1;
    public static final int EMPTY_STRING = 2;
    public static final int NOT_EXIST_REPLY = 3;
    public static final boolean SUPPORT_REPLY = true;
    public static final boolean OBJECT_REPLY = false;
    public static final int TASK_HAS_ADOPTED = 4;

    //Task
    public static final int NO_PERMISSION = 5;
    public static final int MISS_TASK_INF = 2;

    //Report
    public static final int NO_CONTENT = 2;
    public static final String REPORT_TO_USER = "toUser";
    public static final String REPORT_TO_TASK = "toTask";
    public static final String REPORT_TO_REPLY = "toReply";
    public static final String REPORT_TO_MESSAGE = "toMessage";
    public static final int MISS_SUSPEND_TIME = 3;

    //Admin
    public static final int ERROR_LOGIN_INF = 2;
    //JsonUtil
    public final static int IS_NOT_JSON_ARRAY = 1;
}
