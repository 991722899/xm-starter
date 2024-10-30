package com.xm.starter.umrp.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xm.starter.auth.service.AuthSupportService;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.base.model.UserInfo;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.umrp.mapper.SysRoleMapper;
import com.xm.starter.umrp.mapper.SysRoleMenuMapper;
import com.xm.starter.umrp.mapper.SysUserMapper;
import com.xm.starter.umrp.mapper.SysUserRoleMapper;
import com.xm.starter.umrp.model.dto.*;
import com.xm.starter.umrp.model.po.SysRoleMenuPO;
import com.xm.starter.umrp.model.po.SysRolePO;
import com.xm.starter.umrp.model.po.SysUserPO;
import com.xm.starter.umrp.model.po.SysUserRolePO;
import com.xm.starter.umrp.model.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    private @Autowired SysUserMapper sysUserMapper;
    private @Autowired SysUserRoleMapper sysUserRoleMapper;
    private @Autowired SysRoleMapper sysRoleMapper;
    private @Autowired SysRoleMenuMapper sysRoleMenuMapper;
    private @Autowired AuthSupportService authSupportService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(List<Long> id) {
       sysUserMapper.deleteByIds(id);
    }

    @Override
    public String insert(SysUserInsertDTO insertDTO) {
        SysUserPO userPO = new SysUserPO();
        BeanUtils.copyProperties(insertDTO,userPO);
        userPO.setSaltValue(RandomUtil.randomString(6));
        userPO.setUserPassword(MD5.create().digestHex(userPO.getUserPassword()+userPO.getSaltValue()));
        sysUserMapper.insert(userPO);

        if(CollUtil.isNotEmpty(insertDTO.getRoleIdList())){
            sysUserRoleMapper.insert(insertDTO.getRoleIdList().stream().map(roleId->{
                SysUserRolePO sysUserRolePO = new SysUserRolePO();
                sysUserRolePO.setRoleId(roleId);
                sysUserRolePO.setUserId(userPO.getId());
                return sysUserRolePO;
            }).collect(Collectors.toList()));
        }

        return userPO.getId().toString();
    }

    @Override
    public String updateById(SysUserUpdateByIdDTO updateByIdDTO) {
        SysUserPO userPO = new SysUserPO();
        BeanUtils.copyProperties(updateByIdDTO,userPO);
        sysUserMapper.updateById(userPO);

        sysUserRoleMapper.delete(new LambdaUpdateWrapper<SysUserRolePO>().eq(SysUserRolePO::getUserId,userPO.getId()));

        if(CollUtil.isNotEmpty(updateByIdDTO.getRoleIdList())){
            sysUserRoleMapper.insert(updateByIdDTO.getRoleIdList().stream().map(roleId->{
                SysUserRolePO sysUserRolePO = new SysUserRolePO();
                sysUserRolePO.setRoleId(roleId);
                sysUserRolePO.setUserId(userPO.getId());
                return sysUserRolePO;
            }).collect(Collectors.toList()));
        }
        return userPO.getId().toString();
    }

    @Override
    public void updatePassword(SysUserUpdatePasswordDTO updatePasswordDTO) {
        SysUserPO sysUserPO = sysUserMapper.selectById(updatePasswordDTO.getUserId());
        updatePasswordDTO.setPassword(MD5.create().digestHex(updatePasswordDTO.getPassword()+sysUserPO.getSaltValue()));
        sysUserMapper.update(new LambdaUpdateWrapper<SysUserPO>().eq(SysUserPO::getId,updatePasswordDTO.getUserId()).set(SysUserPO::getUserPassword,updatePasswordDTO.getPassword()));
    }

    @Override
    public PageVO<SysUserListVO> page(QuerySysUserDTO query) {
        return sysUserMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<SysUserListVO> list(QuerySysUserDTO query) {
        return sysUserMapper.list(query);
    }

    @Override
    public List<SysRoleDetailVO> findRoleByUserId(Long id) {
        List<SysUserRolePO> sysUserRolePOList = sysUserRoleMapper.selectList(new LambdaUpdateWrapper<SysUserRolePO>().eq(SysUserRolePO::getUserId,id));
        List<SysRolePO> sysRolePOList = sysRoleMapper.selectList(new LambdaUpdateWrapper<SysRolePO>().in(SysRolePO::getId,sysUserRolePOList.stream().map(SysUserRolePO::getRoleId).collect(Collectors.toList())));
        return sysRolePOList.stream().map(j->{
            SysRoleDetailVO sysRoleDetailVO = new SysRoleDetailVO();
            BeanUtils.copyProperties(j,sysRoleDetailVO);
            return sysRoleDetailVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDetailVO> findMenuByUserId(Long id) {
        List<SysUserRolePO> sysUserRolePOList = sysUserRoleMapper.selectList(new LambdaUpdateWrapper<SysUserRolePO>().eq(SysUserRolePO::getUserId,id));
        List<SysRolePO> sysRolePOList = sysRoleMapper.selectList(new LambdaUpdateWrapper<SysRolePO>()
                .in(SysRolePO::getId,sysUserRolePOList.stream().map(SysUserRolePO::getRoleId).collect(Collectors.toList())));
        List<SysRoleMenuPO> sysRoleMenuPOList = sysRoleMenuMapper.selectList(new LambdaUpdateWrapper<SysRoleMenuPO>()
                .in(SysRoleMenuPO::getRoleId,sysRolePOList.stream().map(SysRolePO::getId).collect(Collectors.toList())));

        return sysRoleMenuPOList.stream().map(j->{
            SysMenuDetailVO sysMenuDetailVO = new SysMenuDetailVO();
            BeanUtils.copyProperties(j,sysMenuDetailVO);
            return sysMenuDetailVO;
        }).collect(Collectors.toList());
    }

    @Override
    public SysUserDetailVO findById(Long id) {
        SysUserPO userPO = sysUserMapper.selectById(id);
        SysUserDetailVO detailVO = new SysUserDetailVO();
        BeanUtils.copyProperties(userPO,detailVO);
        detailVO.setRoleDetailVOList(findRoleByUserId(id));
       return detailVO;
    }

    @Override
    public LoginSuccessVO login(LoginDTO loginDTO) {
        SysUserPO sysUserPO = sysUserMapper.selectOne(new LambdaUpdateWrapper<SysUserPO>().eq(SysUserPO::getUserName,loginDTO.getUserName()));
        Assert.isTrue(sysUserPO!=null,"用户名或密码错误");
        Assert.isTrue(MD5.create().digestHex(loginDTO.getPassword()+sysUserPO.getSaltValue()).equals(sysUserPO.getUserPassword()),"用户名或密码错误");

        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        BeanUtils.copyProperties(findById(sysUserPO.getId()),loginSuccessVO);

        //创建jwt token 并写入response header
        UserInfo<String> user = new UserInfo<>();
        user.setUserName(sysUserPO.getUserName());
        user.setUserId(sysUserPO.getId().toString());
        user.setUserRealName(sysUserPO.getUserRealName());
        authSupportService.createToken(user);
        return loginSuccessVO;
    }
}
