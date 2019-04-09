package com.github.nowakprojects.personaleducation.learnannotations.processing.client;

import com.github.nowakprojects.personaleducation.learnannotations.processing.processor.BuilderProperty;

class Person {
    private int age;
    private String name;

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
