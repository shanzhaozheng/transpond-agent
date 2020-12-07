package com.szzii.cn;

import com.szzii.cn.stereotype.Constant;
import com.szzii.cn.util.ThreadUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;


public class AgentMain {


    public static void premain(String agentArgs, Instrumentation inst) {
        if (agentArgs != null && !agentArgs.equals("")){
            Constant.URL = agentArgs;
        }

        System.out.println("servlet转发内网地址: " + Constant.URL);
        System.out.println("最大线程数: " + ThreadUtil.MaxCorePoolSize);
        System.out.println("最大队列数: " + ThreadUtil.MaxQueueSize);

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            builder = builder.visit(
                    Advice.to(DispatchAdvice.class)
                            .on(ElementMatchers.isMethod()
                                    .and(ElementMatchers.nameStartsWith("doDispatch"))));
            return builder;
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("org.springframework.web.servlet.DispatcherServlet")) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }

}
