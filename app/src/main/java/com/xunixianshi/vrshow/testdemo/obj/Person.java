package com.xunixianshi.vrshow.testdemo.obj;

/**
 * @ClassName: Person$
 * @Description: java类作用描述
 * @Author: wpeng
 * @CreateDate: 2021/1/29$ 10:08$
 * @Version: 1.0
 */
public class Person {
  private String name;
  private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


