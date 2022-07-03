package com.cunff.finance.service;

import com.cunff.finance.entity.Admin;

public interface AdminService {

    Admin selectAdminByTerms(String username, String password);

    Integer updateAdmin(Admin admin);

    Admin selectAdminById(Integer id);
}
