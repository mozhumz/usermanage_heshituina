package com.mozhumz.usermanage.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mozhumz.usermanage.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mozhumz.usermanage.model.qo.UserQo;
import com.mozhumz.usermanage.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface IUserMapper extends BaseMapper<User> {
    Page<UserVO> findUserVOList(Page page,@Param("qo") UserQo userQo);

    List<UserVO> findUserVOList2(@Param("qo") UserQo userQo);
    int addOne(User user);
    UserVO getOneVO(Long id);
}
