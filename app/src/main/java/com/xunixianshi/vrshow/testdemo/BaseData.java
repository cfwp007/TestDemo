package com.xunixianshi.vrshow.testdemo;

import java.io.Serializable;

/**
 * @ClassName: BaseData$
 * @Description: java类作用描述
 * @Author: wpeng
 * @CreateDate: 2021/6/30$ 17:08$
 * @Version: 1.0
 */
public class BaseData implements Serializable {
    private boolean result;
    private int code;
    private Mydata data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Mydata getData() {
        return data;
    }

    public void setData(Mydata data) {
        this.data = data;
    }
}
