package com.deepblue.future.service;

import com.deepblue.future.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * User: YANG
 * Date: 2019/7/28-21:04
 * Description: No Description
 */
@Service
public class FutureOrderService {

	@Autowired
	private RemoteService remoteService;

	private LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();

	public Map<String, Object> getResult(String orderCode) {
		String serialNo = UUID.randomUUID().toString();
		CompletableFuture<Map<String, Object>> future = new CompletableFuture<Map<String, Object>>();
		Request request = new Request();
		request.setOrderCode(orderCode);
		request.setSerialNo(serialNo);
		request.setFuture(future);
		requests.add(request);

		Map<String, Object> resultMap = null;
		try {
			resultMap = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Scheduled(cron="0/1 * * * * ?") //每隔1s执行一次
	public void getListResult() {
		int size = requests.size();
		if (size > 0) {
			System.out.println("requests size:---------------------:" + size + ", time is :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ExecutorService workerService = Executors.newSingleThreadExecutor();
			workerService.execute(new Thread(() -> {
				//记录线程的Map,用作返回
				HashMap<String, CompletableFuture> futureMap = new HashMap<>();
				//组合调用远程的方法用的
				List<Map<String, Object>> paramMapList = new ArrayList<>();

				for (int i = 0; i < size; i++) {
					Request param = requests.poll();
					HashMap<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("orderCode", param.getOrderCode());
					paramMap.put("serialNo", param.getSerialNo());
					paramMapList.add(paramMap);
					futureMap.put(param.getSerialNo(), param.getFuture());
				}
				List<Map<String, Object>> resultMapList = remoteService.getListResult(paramMapList);
				resultMapList.stream().forEach(map -> {
//					System.out.println("serialNo:" + map.get("serialNo").toString() + ", orderCode:" + map.get("orderCode"));
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put(map.get("orderCode").toString(), map.get("resultCode"));
					futureMap.get(map.get("serialNo").toString()).complete(resultMap);
				});
			}));
		}
	}
}
