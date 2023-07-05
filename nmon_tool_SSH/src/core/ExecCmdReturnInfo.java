package core;

import java.util.ArrayList;

public class ExecCmdReturnInfo {
    int exitStatus = 0;
    ArrayList<String> outInfo = new ArrayList<>();
    ArrayList<String> errInfo = new ArrayList<>();

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public void AddOutInfo(String str) {
        this.outInfo.add(str);
    }

    public void AddErrInfo(String str) {
        this.errInfo.add(str);
    }

    public String GetOutInfoString() {
        StringBuffer tmp = new StringBuffer();
        for (String str : this.outInfo) {
            tmp.append(str);
        }
        return tmp.toString();
    }

    public ArrayList<String> GetOutInfoArrayList() {
        return this.outInfo;
    }

    public String GetErrInfoString() {
        StringBuffer tmp = new StringBuffer();
        for (String str : this.errInfo) {
            tmp.append(str);
        }
        return tmp.toString();
    }

    public ArrayList<String> GetErrInfoArrayList() {
        return this.errInfo;
    }

    public String toString() {
        StringBuffer tmp = new StringBuffer();
        tmp.append("\r\n");

        tmp.append("ExitStatus: " + getExitStatus());
        tmp.append("\r\n");
        tmp.append("ErrInfo: ");
        tmp.append(GetErrInfoString());
        tmp.append("\r\n");
        tmp.append("OutInfo: ");
        tmp.append(GetOutInfoArrayList());

        return tmp.toString();
    }
}
