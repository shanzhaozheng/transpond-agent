package com.szzii.cn;

import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.stereotype.Routing;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author szz
 */
public class DispatchAdvice {


    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument (0) Object var1,@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        try {
            Class<?> requestClass = var1.getClass();
            if (!var1.getClass().getSimpleName().equals("MyRequestWrapper")){
                return;
            }
            Method getServletPath = requestClass.getMethod("getServletPath");
            String servletPath = (String)getServletPath.invoke(var1);
            if (!filterUrl(servletPath)){
                return;
            }
            try {
                RequestEntity requestEntity = RequestEntity.buildRequestEntity(var1);
                Dispatch.doDispatch(requestEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
    }


    public static boolean filterUrl(String url){
        Routing[] values = Routing.values();
        for (Routing value : values) {
            if (url.startsWith(value.getUrl())){
                return true;
            }
        }
        return false;
    }



}
