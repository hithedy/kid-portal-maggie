package com.xyzq.kid.portal.action.ticket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.xyzq.kid.logic.config.common.ConfigCommon;
import com.xyzq.kid.logic.config.service.ConfigService;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;

/**
 * 范例动作
 */
@MaggieAction(path = "kid/portal/getTicketPrice")
public class GetTicketPriceAction implements IAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private ConfigService configService;

    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(GetTicketPriceAction.class);
    
    Gson gson=new Gson();

    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        Map result = configService.getPriceInfo();
        Map<String,Object> map=new HashMap<>();
        if(result!=null&&result.size()>0){
        	map.put("single", result.get(ConfigCommon.FEE_SINGLETICKET));
        	map.put("group", result.get(ConfigCommon.FEE_GROUPTICKET));
        	map.put("refundInsurance", result.get(ConfigCommon.FEE_INSURANCE));
        }
        if(null != map) {
            context.set("data", gson.toJson(map));
        }
        logger.info("[kid/portal/getTicketPrice]-in:" + gson.toJson(map));
        return "success.json";
    }
}
