package drintau.accountmanager.dao;

import drintau.accountmanager.domain.entity.MyAccountPO;

import java.util.List;

public interface MyAccountBackupRepository {

    void truncate();

    void insertBatch(List<MyAccountPO> pos);

}
