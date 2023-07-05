package base;

import java.util.HashMap;

public abstract class Data {
    HashMap<String, String> map = new HashMap<>();

    public Data(HashMap<String, String> map) {
        this.map = map;
    }

    public String GetConfToString(String key) {
        return ((String) this.map.get(key.toUpperCase()));
    }

    public int GetConfToInt(String key) {
        return Integer.parseInt((String) this.map.get(key.toUpperCase()));
    }

    public void AddConf(String key, String value) {
        this.map.put(key.toUpperCase(), value);
    }

}
