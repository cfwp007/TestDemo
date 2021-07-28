package com.xunixianshi.vrshow.testdemo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Mydata$
 * @Description: java类作用描述
 * @Author: wpeng
 * @CreateDate: 2021/6/30$ 17:11$
 * @Version: 1.0
 */
public class Mydata implements Serializable{
    private List<MyArray> list;
    private Timespan timespan;
    private boolean cache;


    public class MyArray implements Serializable {
        private int id;
        private String code;
        private String type;
        private String name;
        private int isbaremetaloper;
        private List<Childs> childs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsbaremetaloper() {
            return isbaremetaloper;
        }

        public void setIsbaremetaloper(int isbaremetaloper) {
            this.isbaremetaloper = isbaremetaloper;
        }

        public List<Childs> getChilds() {
            return childs;
        }

        public void setChilds(List<Childs> childs) {
            this.childs = childs;
        }
    }

    public class Timespan implements Serializable{
        private String start;
        private String stop;
        private int span;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStop() {
            return stop;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }

        public int getSpan() {
            return span;
        }

        public void setSpan(int span) {
            this.span = span;
        }
    }

    public class Childs implements Serializable{
        private int id;
        private String code;
        private String type;
        private String name;
        private int isbaremetaloper;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsbaremetaloper() {
            return isbaremetaloper;
        }

        public void setIsbaremetaloper(int isbaremetaloper) {
            this.isbaremetaloper = isbaremetaloper;
        }
    }


    public Timespan getTimespan() {
        return timespan;
    }

    public void setTimespan(Timespan timespan) {
        this.timespan = timespan;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public List<MyArray> getList() {
        return list;
    }

    public void setList(List<MyArray> list) {
        this.list = list;
    }
}
