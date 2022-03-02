package com.adiscope.kkpoint.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    // 20210217 LocalDataTime은 @Cacheable로 Redis에 캐시할 경우, 직렬화, 역직렬화에 이슈가 있어서 별도의 처리가 필요함.
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime transactionTime;


    // api 응답 코드
    private String resultCode;
    private static String resultOK = "resultOK";
    private static String resultDataOK = "resultDataOK";
    private static String resultDataPageableOK = "resultDataPageableOK";
    private static String resultError = "resultError";


    // api 부가 설명
    private String description;
    private static String descriptionOK = "descriptionOK";
    private static String descriptionDataOK = "resultDataOK";
    private static String descriptionDataPageableOK = "resultDataPageableOK";


    private T data;

    private Pagination pagination;

    // OK
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultOK)
                .description(descriptionOK)
                .build();
    }


    // DATA OK
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultDataOK)
                .description(descriptionDataOK)
                .data(data)
                .build();
    }

    // DATA, PAGINATION OK
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data, Pagination pagination){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultDataPageableOK)
                .description(descriptionDataPageableOK)
                .data(data)
                .pagination(pagination)
                .build();
    }

    // ERROR
    @SuppressWarnings("unchecked")
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultError)
                .description(description)
                .build();
    }
}
