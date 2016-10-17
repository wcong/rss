package org.wcong.rss.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wcong.rss.model.Rss;

/**
 * @author wcong<wc19920415@gmail.com>
 * @since 2016/10/17
 */
public interface RssMapper {

	@Select("select * from tb_rss where id = #{id}")
	Rss getById(@Param("id") int id);

}
