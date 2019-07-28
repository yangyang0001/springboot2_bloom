package com.inspur.future.controller;

import com.inspur.future.service.FutureOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * User: YANG
 * Date: 2019/7/29-0:16
 * Description: No Description
 */
@RestController
@Slf4j
public class FutureOrderController {

	@Autowired
	private FutureOrderService futureOrderService;

	@RequestMapping("/getResult")
	public Map<String, Object> getResult(String orderCode) {
		Map<String, Object> result = futureOrderService.getResult(orderCode);
		return result;
//		return null;
	}
}
