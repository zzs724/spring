package com.zzs.service.impl;

import com.zzs.service.IAccountService;

public class AccountServiceImpl implements IAccountService{
    @Override
    public void saveAccount() {
        System.out.println("保存。。。。");
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("更新。。。。");
    }

    @Override
    public int deleteAccount() {
        System.out.println("删除。。。。");
        return 0;
    }
}
