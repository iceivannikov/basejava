package com.ivannikov.webapp;

import com.ivannikov.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        String name = field.getName();
        System.out.println(name);
        Object object = field.get(resume);
        System.out.println(object);
        field.set(resume, "new uuid");
        System.out.println(resume);

        //Calling the toString method via Reflection
        Class<? extends Resume> aClass = resume.getClass();
        Method method = aClass.getMethod("toString");
        String result = (String) method.invoke(resume);
        System.out.println("Result of calling toString(): " + result);
    }
}
