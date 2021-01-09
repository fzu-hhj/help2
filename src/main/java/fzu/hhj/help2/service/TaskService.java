package fzu.hhj.help2.service;

import java.util.Map;

public interface TaskService {

    /**
     * 发布任务
     * @param synopsis 简介
     * @param content 内容
     * @param categoryId 类别id
     * @return 结果码
     */
    public Map<String, Object> publishTask(String synopsis, String content, Integer categoryId);

    /**
     * 获取任务类别供选择
     * @return 任务类别和结果码
     */
    public Map<String, Object> getTaskCategory();
    /**
     * 根据任务id，获取任务信息和任务下的length个回复
     * @param taskId 任务id
     * @param sort 回复的排序方法
     * @return 任务的具体信息和结果码
     */
    public Map<String, Object> getTaskById(Integer taskId, Integer sort);

    /**
     * 根据问题id获取问题的简单信息
     * @param taskId 任务id
     * @return 任务简单信息
     */
    public Map<String, Object> getSimpleTaskInf(Integer taskId);

    /**
     * 获取当前登录的用户的发布的任务
     * @return 任务集和结果码
     */
    public Map<String, Object> listTasksByUser();

    /**
     * 任务发布者取消任务
     * @param taskId 任务id
     * @return 结果码
     */
    public Map<String, Object> cancelTask(Integer taskId);

    /**
     * 任务发布者完成任务
     * @param taskId 任务id
     * @return 结果码
     */
    public Map<String, Object> finishTask(Integer taskId);

    /**
     * 获取所有的任务
     * @return 根据条件查询出任务
     */
    public Map<String, Object> listSelectedTasks(Integer categoryId, String isCompleted, Integer sort);
}
