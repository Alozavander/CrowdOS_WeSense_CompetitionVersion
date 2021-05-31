package com.hills.mcs_02.downloadpack;

public interface DownloadListener {
    void onStart();

    void onProgress(int currentLength);

    void onFinish(String localPath);

    void onFailure();
}
