package com.deepblue.future.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: YANG
 * Date: 2019/7/28-23:45
 * Description: No Description
 */
@Service
public class RemoteService {

	public List<Map<String, Object>> getListResult(List<Map<String, Object>> paramMapList) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		paramMapList.stream().forEach(map -> {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String lastValue = "[orderCode is :" + map.get("orderCode").toString() + "," +
					"serialNo is :" + map.get("serialNo").toString() + "," +
					"time is :" + System.currentTimeMillis() + "]";
			resultMap.put("serialNo", map.get("serialNo"));
			resultMap.put("orderCode", map.get("orderCode"));
			resultMap.put("resultCode", lastValue);
			resultList.add(resultMap);
		});
		return resultList;
	}
}
