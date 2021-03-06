package com.xyzq.kid.portal.action.cms.action;

import com.google.gson.Gson;
import com.xyzq.kid.logic.cms.entity.CMSEntity;
import com.xyzq.kid.logic.cms.service.CMSService;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取所有退票记录
 * Created by Brann on 17/7/29.
 */
@MaggieAction(path = "kid/portal/getMateriel")
public class GetMaterielAction implements IAction {
	@Autowired
	private CMSService cmsService;

	Gson gson = new Gson();

	/**
	 * 日志对象
	 */
	public static Logger logger = LoggerFactory.getLogger(GetMaterielAction.class);


	@Override
	public String execute(Visitor visitor, Context context) throws Exception {

		Integer categoryid = (Integer) context.parameter("type", 0);
		logger.info("[kid/portal/getMateriel]-in:categoryid[" + categoryid + "]");

		List<Map<String, Object>> mapList = new ArrayList<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<CMSEntity> cmsEntityList = cmsService.getCMSByCategoryid(categoryid);
		if (null != cmsEntityList && cmsEntityList.size() > 0) {
			for (CMSEntity cmsEntity : cmsEntityList) {
				Map<String, Object> map = new HashMap<>();
				map.put("title", cmsEntity.title);
				map.put("content", cmsEntity.content);
				map.put("imgUrl", cmsEntity.imageurl);
				map.put("link", cmsEntity.link);
				map.put("updatetime", sdf.format(cmsEntity.updatetime));
				mapList.add(map);
			}
		}
		context.set("data", gson.toJson(mapList));
		logger.info("[kid/portal/getTicketList]-out:" + gson.toJson(mapList));
		return "success.json";
	}
}
