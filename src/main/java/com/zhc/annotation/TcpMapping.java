package com.zhc.annotation;


import java.lang.annotation.*;

/**
 * 自定义一个注解 将命令对应的执行放入到命令中 不可能我100个命令 写100个判断吧 所有提前将执行放进map中 用的时候直接取 应该会方便点吧
 * */
@Target({ElementType.METHOD})//定义注解修饰方法
@Retention(RetentionPolicy.RUNTIME) //执行时期 有编译期class 运行期 runtime 还有个源码期  我觉得我这个应该在编译期 但是事实证明他是在运行期
@Documented
public @interface TcpMapping {
    int[] cmd() default {};
}
