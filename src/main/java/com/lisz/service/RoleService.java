package com.lisz.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lisz.controller.ResponseStatus;
import com.lisz.entity.Role;

public interface RoleService {

	List<Role> findAll();

	PageInfo<Role> findByPage(int pageNum, int pageSize);

	ResponseStatus deleteById(int id);

	Role findById(Integer id);

	ResponseStatus add(Role role);

	ResponseStatus update(Role role);

	void addPermissionsForRole(int[] permissionIds, int roleId);

}