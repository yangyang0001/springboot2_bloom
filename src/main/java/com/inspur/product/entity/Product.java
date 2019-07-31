package com.inspur.product.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * User: YANG
 * Date: 2019/7/29-15:24
 * Description: No Description
 */
@Data
@ToString
public class Product implements Serializable {
	private static final long serialVersionUID = -8339362796313296019L;

	private Long id;            //主键ID
	private String productNo;   //商品号
	private Long leftNum;       //剩余数量
	private Integer killNum;    //秒杀数量
	private Long version;       //版本号控制

}
