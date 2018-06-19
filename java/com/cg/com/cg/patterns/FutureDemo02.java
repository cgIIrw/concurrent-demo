package com.cg.com.cg.patterns;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureDemo02 {
    public static void main(String[] args) {
        Host02 host02 = new Host02();
        IData iData= host02.request();
        System.out.println(iData.getContent());
    }
}


class FutureData02 extends FutureTask<RealData> implements IData {
    public FutureData02(Callable<RealData> callable) {
        super(callable);
    }

    @Override
    public String getContent() {
        String result = null;
        try {
            result = get().getContent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}

class Host02 {
    public IData request() {
        FutureData02 future02 = new FutureData02(new Callable<RealData>() {
            @Override
            public RealData call() {
                RealData realData = new RealData();
                return realData;
            }
        });
        new Thread(future02).start();
        return future02;
    }
}
