package com.zhc.aop;

import com.zhc.annotation.TcpMapping;
import com.zhc.tcp.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 项目启动的时候 通过该实现类 找到项目中存在的@TcpMapping的方法 并将它放到我们的映射集合中
 * */

@Slf4j
@Component
public class TcpMappingImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            //获取这个方法的TcpMapping注解
            TcpMapping annotation = AnnotationUtils.findAnnotation(method, TcpMapping.class);
            //要是存在该注解
            if (annotation != null) {
                int[] cmds = annotation.cmd();//注解里面的参数命令  毕竟一个方法不仅仅只能执行一条命令  就和RequestMapping一样嘛
                for (int key:cmds){
                    BaseController.methodMap.put(key,method);
                }
            }
        }
        return bean;
    }
}
