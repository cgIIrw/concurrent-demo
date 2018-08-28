package com.cg.com.cg.patterns;

/**
 * 非java.util.Concurrent包实现
 */
public class FutureDemo {
    public static void main(String[] args) {

        Host host = new Host();
        IData iData = host.request();
        System.out.println(iData.getContent());
    }
}

class Host {
    public IData request() {
        final FutureData future = new FutureData();
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData();
                future.setData(realData);
            }
        }.start();

        return future;
    }
}

interface IData {
    String getContent();

}

class FutureData implements IData {
    private IData data;
    private boolean ready = false;

    public synchronized void setData(IData data) {
        if (ready) {
            return;
        }
        this.data = data;
        ready = true;
        notifyAll();
    }

    @Override
    public synchronized String getContent() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data.getContent();
    }
}

class RealData implements IData {
    private String workout;
    public RealData() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workout = "计算出了真正的结果";
    }

    @Override
    public String getContent() {
        return workout;
    }
}