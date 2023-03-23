package com.rental.premisesrental.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author 20179
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    @Contract(" -> new")
    public static<T> @NotNull Response<T> success() {
        return new Response<>(200,"成功",null);
    }


    public Response<T> setSuccessCode(Integer code) {
        this.setCode(code);
        return this;
    }
    public  Response<T> setSuccessMessage(String message) {
        this.setMessage(message);
        return this;
    }
    public Response<T> setSuccessData(T data) {
        this.setData(data);
        return this;
    }
    @Contract(" -> new")
    public static<T> @NotNull Response<T> fail() {
        return new Response<>(500,"失败",null);
    }
    public Response<T> setFailMessage(String message) {
        this.setMessage(message);
        return this;
    }
    public Response<T> setFailCode(Integer code) {
        this.setCode(code);
        return this;
    }
}
