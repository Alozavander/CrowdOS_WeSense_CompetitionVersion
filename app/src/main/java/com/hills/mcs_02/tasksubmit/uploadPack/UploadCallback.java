package com.hills.mcs_02.tasksubmit.uploadPack;

public interface UploadCallback {
    /** Single File Upload record*/
    void onProgressUpdate(long uploaded);

    void onError();

    void onFinish();
}
