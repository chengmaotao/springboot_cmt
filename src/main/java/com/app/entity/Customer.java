package com.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author wsh
 * @since 2018-04-12
 */
@Data
@ToString
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date createTime;
	private Date updateTime;
	private Date registerTime;
	private Date loginTime;
	/**
	 * 用户名，默认为手机号
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像链接
	 */
	private String photoUrl;
	/**
	 * 算力
	 */
	private Integer power;
	/**
	 * 钻石余额
	 */
	private BigDecimal diamondBalance;
	/**
	 * 国家区号
	 */
	private String countryAreaCode;
	/**
	 * 状态，0已发送验证码，1已注册，2冻结，3注销
	 */
	private Integer status;

	/**
	 * 用户自己的邀请码
	 */
	private String inviteCode;

	private Long fromInviteCid;

	private Integer invitePriseStatus;

	private Integer inviteCount;

	private BigDecimal ctcBalance;

	private String channelId;

	private String appVersion;

	private Integer sbFlag;

	private Integer inviteRewardCount;

	private String password;

	private Integer batchRegistration;

	private String ip;
	private String fromPlat;

	private String suspicious; // 1是可疑用户 同一个sid超过三个人再用

	private Integer jdstatus;

	private String shortUrl;

}
