package core;

import java.io.IOException;

public abstract interface CoreService {
    public abstract void Init() throws IOException;

    public abstract void Run() throws IOException;

    public abstract void End();
}
