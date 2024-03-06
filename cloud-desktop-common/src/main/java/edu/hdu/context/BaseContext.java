package edu.hdu.context;

/**
 * 用于记录当前用户的id
 */
public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentUserId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentUserId() {
        return threadLocal.get();
    }


    public static void removeCurrentUserId() {
        threadLocal.remove();
    }

}
