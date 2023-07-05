package service;

import base.Data;

import java.util.HashMap;

public class ServerInfo extends Data {
    private ServerTypeInfo serverTypeInfo;

    public ServerInfo(HashMap<String, String> map) {
        super(map);
    }

    public ServerTypeInfo getServerTypeInfo() {
        return serverTypeInfo;
    }

    public void setServerTypeInfo(ServerTypeInfo serverTypeInfo) {
        this.serverTypeInfo = serverTypeInfo;
    }
}
