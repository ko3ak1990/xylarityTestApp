package com.umanets.xylaritytestapp.data.model;

/**
 * Created by ko3ak_zhn on 7/7/17.
 */

public class SyncEvent {
    public SyncEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    private boolean success;
}
