package cn.iponkan.controller;
import cn.iponkan.util.DigestUtil;
import cn.iponkan.util.JsonUtil;
import cn.iponkan.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/api")
@Controller
public class ApiController extends BaseController{

    @RequestMapping(value = "/getJson.do",method = RequestMethod.POST)
    @ResponseBody
    public String getJson(){
        //获取请求中的数据
        PageData pageData = super.getPageData();
        //封装返回结果
        PageData result = new PageData();

        //签名验证
        if(!DigestUtil.toSign(pageData).equals(pageData.getString("sign"))){
            result.put("errCode",100);
            result.put("errMsg","签名验证失败");
            return JsonUtil.getJson(result);
        }

        //获取请求中的参数
        //比如传递的参数是 1 就返回一个list数据，否则返回一个string
        int parameter = pageData.getInt("parameter");

        //根据获得的参数，写处理逻辑，可以与数据库交互，这里模拟一个list返回
        if(parameter == 1){
            //模拟数据，可与dao层连接
            List<String> list = new ArrayList<>();
            list.add("clare");
            list.add("tung");

            //参数匹配返回结果
            result.put("data",JsonUtil.listToJson(list));
            result.put("errCode",0);
            result.put("errMsg","");
            return JsonUtil.getJson(result);
        }

        //其他结果返回
        result.put("data","Nothing");
        result.put("errCode",102);
        result.put("errMsg","请求没有结果");
        return JsonUtil.getJson(result);
    }
}
