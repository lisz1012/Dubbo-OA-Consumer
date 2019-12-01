package com.lisz.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lisz.controller.ResponseStatus;
import com.lisz.entity.Account;

public interface AccountService {

	Account findByUsernameAndPassword(String username, String password);

	List<Account> findAll();

	PageInfo<Account> findByPage(int pageNum, int pageSize);

	ResponseStatus deleteById(int id);

	ResponseStatus updatePasswordById(Integer id, String newPassword);

	Account findById(Integer id);

	ResponseStatus updateProfileUrlById(String profileUrl, Integer id);

	ResponseStatus update(Account account);

	void addRolesForAccount(int[] roleIds, int accountId);

}