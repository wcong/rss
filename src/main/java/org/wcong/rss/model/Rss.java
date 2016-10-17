package org.wcong.rss.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wcong<wc19920415@gmail.com>
 * @since 2016/10/17
 */
@Data
public class Rss {

	private int id;

	private Timestamp createTime;

	private Timestamp updateTime;

	private int channelId;

	private int userId;

}
