package com.anningtex.permissionx.request;

/**
 * Maintain the task chain of permission request process.
 *
 * @author Song
 */
public class RequestChain {

    /**
     * Holds the first task of request process. Permissions request begins here.
     */
    private BaseTask headTask;

    /**
     * Holds the last task of request process. Permissions request ends here.
     */
    private BaseTask tailTask;

    /**
     * Add a task into task chain.
     *
     * @param task task to add.
     */
    public void addTaskToChain(BaseTask task) {
        if (headTask == null) {
            headTask = task;
        }
        // add task to the tail
        if (tailTask != null) {
            tailTask.next = task;
        }
        tailTask = task;
    }

    /**
     * Run this task chain from the first task.
     */
    public void runTask() {
        headTask.request();
    }
}