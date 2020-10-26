package com.deepblue.future.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * User: YANG
 * Date: 2019/7/29-0:01
 * Description: No Description
 */
@Data
public class Request implements Serializable{
	private static final long serialVersionUID = -1687932879842532843L;
	private String orderCode;
	private String serialNo;
	private CompletableFuture<Map<String, Object>> future;

	@Override
	public String toString() {
		return "Request{" +
				"orderCode='" + orderCode + '\'' +
				", serialNo='" + serialNo + '\'' +
				", future=" + future +
				'}';
	}
}
