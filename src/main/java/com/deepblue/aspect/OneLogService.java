package com.deepblue.aspect;

public class OneLogService {

    @OneLog(openFlag = true)
    public void first() {
        System.out.println("first invoking ...");
    }

    @OneLog(openFlag = false)
    public void second() {
        System.out.println("second invoking ...");
    }

    @OneLog(openFlag = true)
    public Result getResult(String username, String password) {
        Result result = Result.builder().username("YangJianWei").password("Yang199001").build();
        System.out.println("getResult invoking ...");
        return result;
    }
}
