package com.rental.premisesrental.util;

import com.rental.premisesrental.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author 20179
 */
@Component
public class UserHolder {
    private static final ThreadLocal<User> USER_THREAD_LOCAL;

    static {
        USER_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static void put(User user)  {
        System.out.println(Thread.currentThread().threadId());
        USER_THREAD_LOCAL.set(user);
    }

    public static User getCurrentUser() {
        System.out.println(Thread.currentThread().threadId());
        return USER_THREAD_LOCAL.get();
    }
}
