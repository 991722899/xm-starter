package com.xm.starter.umrp.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.umrp.mapper.*;
import com.xm.starter.umrp.model.dto.QuerySysRoleDTO;
import com.xm.starter.umrp.model.dto.SysRoleInsertDTO;
import com.xm.starter.umrp.model.dto.SysRoleUpdateByIdDTO;
import com.xm.starter.umrp.model.po.*;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleListVO;
import com.xm.starter.umrp.model.vo.SysUserDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private @Autowired SysRoleMapper sysRoleMapper;
    private @Autowired SysRoleMenuMapper sysRoleMenuMapper;
    private @Autowired SysUserRoleMapper sysUserRoleMapper;
    private @Autowired SysMenuMapper sysMenuMapper;
    private @Autowired SysUserMapper sysUserMapper;


    @Override
    public void deleteById(List<Long> id) {
        sysRoleMapper.deleteByIds(id);
        sysUserRoleMapper.delete(new LambdaUpdateWrapper<SysUserRolePO>().in(SysUserRolePO::getRoleId,id));
        sysRoleMenuMapper.delete(new LambdaUpdateWrapper<SysRoleMenuPO>().in(SysRoleMenuPO::getRoleId,id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(SysRoleInsertDTO insertDTO) {
        SysRolePO sysRolePO = new SysRolePO();
        BeanUtils.copyProperties(insertDTO,sysRolePO);
        sysRoleMapper.insert(sysRolePO);

        sysRoleMenuMapper.insert(insertDTO.getMenuIdList().stream().map(i->{
            SysRoleMenuPO sysRoleMenuPO = new SysRoleMenuPO();
            sysRoleMenuPO.setRoleId(sysRolePO.getId());
            sysRoleMenuPO.setMenuId(i);
            return sysRoleMenuPO;
        }).collect(Collectors.toList()));

        return sysRolePO.getId().toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateById(SysRoleUpdateByIdDTO updateByIdDTO) {
        SysRolePO sysRolePO = new SysRolePO();
        BeanUtils.copyProperties(updateByIdDTO,sysRolePO);
        sysRoleMapper.updateById(sysRolePO);

        sysRoleMenuMapper.delete(new LambdaUpdateWrapper<SysRoleMenuPO>().in(SysRoleMenuPO::getRoleId,updateByIdDTO.getId()));

        sysRoleMenuMapper.insert(updateByIdDTO.getMenuIdList().stream().map(i->{
            SysRoleMenuPO sysRoleMenuPO = new SysRoleMenuPO();
            sysRoleMenuPO.setRoleId(sysRolePO.getId());
            sysRoleMenuPO.setMenuId(i);
            return sysRoleMenuPO;
        }).collect(Collectors.toList()));

        return sysRolePO.getId().toString();
    }

    @Override
    public PageVO<SysRoleListVO> page(QuerySysRoleDTO query) {
        return sysRoleMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<SysRoleListVO> list(QuerySysRoleDTO query) {
        return sysRoleMapper.list(query);
    }

    @Override
    public List<SysMenuDetailVO> findMenuByRoleId(Long id) {
        List<SysRoleMenuPO> sysRoleMenuPOList = sysRoleMenuMapper.selectList(new LambdaUpdateWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getRoleId,id));

        List<SysMenuPO> sysMenuPOList = sysMenuMapper.selectList(new LambdaUpdateWrapper<SysMenuPO>().in(SysMenuPO::getId,sysRoleMenuPOList.stream().map(SysRoleMenuPO::getMenuId).toArray()));

        return sysMenuPOList.stream().map(j->{
            SysMenuDetailVO menuDetailVO = new SysMenuDetailVO();
            BeanUtils.copyProperties(j,menuDetailVO);
            return menuDetailVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysUserDetailVO> findUserByRoleId(Long id) {
        List<SysUserRolePO> sysUserRolePOList = sysUserRoleMapper.selectList(new LambdaUpdateWrapper<SysUserRolePO>().eq(SysUserRolePO::getRoleId,id));
        List<SysUserPO> sysUserPOList = sysUserMapper.selectList(new LambdaUpdateWrapper<SysUserPO>()
                .in(SysUserPO::getId,sysUserRolePOList.stream().map(SysUserRolePO::getUserId).toArray()));
        return sysUserPOList.stream().map(j->{
            SysUserDetailVO userDetailVO = new SysUserDetailVO();
            BeanUtils.copyProperties(j,userDetailVO);
            return userDetailVO;
        }).collect(Collectors.toList());
    }

    @Override
    public SysRoleDetailVO findById(Long id) {
        SysRolePO sysRolePO = sysRoleMapper.selectById(id);
        SysRoleDetailVO detailVO = new SysRoleDetailVO();
        BeanUtils.copyProperties(sysRolePO,detailVO);
        detailVO.setMenuDetailVOList(findMenuByRoleId(id));

       return detailVO;
    }
}
