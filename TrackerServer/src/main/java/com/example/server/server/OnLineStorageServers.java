package com.example.server.server;

import com.example.server.model.StorageFileInfo;
import com.example.server.model.StorageServerModel;

import java.util.HashMap;


public class OnLineStorageServers {
    //key 127.0.0.1:2689   记录心跳的storage服务列表
    public static HashMap<String,StorageServerModel> onLines = new HashMap<>();

    public static HashMap<String,StorageFileInfo> storages = new HashMap<>();
}
