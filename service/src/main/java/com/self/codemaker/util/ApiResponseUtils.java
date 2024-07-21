package com.self.codemaker.util;

import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.enums.ApiResponseCode;
import com.self.codemaker.excption.ApiServiceException;
import com.self.codemaker.excption.BaseApiException;

/**
 * @author huangchangjun
 * @date 2024/7/17
 */
public class ApiResponseUtils {
    /**
     * 判断scf接口返回值是否成功
     *
     * @param apiResponse
     * @return
     */
    public static boolean isSuccess(ApiResponse apiResponse) {
        return apiResponse != null && ApiResponseCode.SUCCESS.getCode().equals(apiResponse.getCode());
    }

    /**
     * 获取返回值数据
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T getResultData(ApiResponse<T> response) {
        if (isSuccess(response)) {
            T data = response.getData();
            return data;
        }
        throw new ApiServiceException(response);
    }

    /**
     * apiResponse成功并且data not null，返回data，否则抛出异常
     * @param apiResponse
     * @param dataName
     * @return
     */
    public static <T> T getData(ApiResponse<T> apiResponse, String dataName) {
        if (isSuccess(apiResponse)) {
            T data = apiResponse.getData();
            if( null == data){
                throw new ApiServiceException(ApiResponseCode.DATA_EMPTY,"获取" + dataName + "异常，数据不存在");
            }
            return data;
        }
        throw new ApiServiceException(apiResponse);
    }

    /**
     * 获取Long值
     *
     * @param longVal
     * @return
     */
    public static long getLongValue(Long longVal) {
        if (longVal != null) {
            return longVal;
        }
        throw new BaseApiException("获取Long值为空");
    }
}
