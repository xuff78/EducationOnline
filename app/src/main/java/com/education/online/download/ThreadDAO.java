package com.education.online.download;

import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public interface ThreadDAO {
    /**
     * 插入线程信息
     *
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     *
     * @param url
     * @param threadId
     */
    public void deleteThread(String url);

    /**
     * 更新线程
     *
     * @param url
     * @param threadId
     */
    public void updateThread(String url,int finished);

    /**
     * 线程消息是否存在
     * @param url
     * @param threadId
     * @return
     */
    public boolean isExtists(String url);
}