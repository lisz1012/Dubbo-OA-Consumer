package com.lisz.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lisz.controller.ResponseStatus;
import com.lisz.entity.Permission;

public interface PermissionService {

	List<Permission> findAll();

	PageInfo<Permission> findByPage(int pageNum, int pageSize);

	ResponseStatus deleteById(int id);

	Permission findById(Integer id);

	ResponseStatus add(Permission permission);

	ResponseStatus update(Permission permission);

	PageInfo<Permission> getPermissionsForRoleId(int id);

}