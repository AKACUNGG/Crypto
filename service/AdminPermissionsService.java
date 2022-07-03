package com.cunff.finance.service;

import com.cunff.finance.entity.AdminPermissions;


import java.util.List;

public interface AdminPermissionsService {

    List<AdminPermissions> selectAdminPermissionsByAdminId(Integer adminId);

    //AdminPermissions selectAdminPermissionsByPermissionId(Integer permissionId);

    Integer insertAdminPermissions(AdminPermissions adminPermissions);

    Integer UpdateAdminPermissions(AdminPermissions adminPermissions);

    Integer deleteAdminPermissionsByPermissionId(Integer permissionId);

    Integer deleteAllAdminPermissionsByAdminId(Integer adminId);
}
