package com.cunff.finance.service.impl;

import com.cunff.finance.entity.FundProduct;
import com.cunff.finance.mapper.FundProductMapper;
import com.cunff.finance.service.FundProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FundProductServiceImpl implements FundProductService {
    @Autowired
    FundProductMapper fundProductMapper;
    @Override
    public List<FundProduct> selectAllFundProduct() {
        return fundProductMapper.selectByExample(null);
    }

    @Override
    public FundProduct selectFundProductById(Integer id) {
        return fundProductMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer insertFundProduct(FundProduct fundProduct) {
        return fundProductMapper.insertSelective(fundProduct);
    }

    @Override
    @Transactional
    public Integer updateFundProduct(FundProduct fundProduct) {
        return fundProductMapper.updateByPrimaryKeySelective(fundProduct);
    }

    @Override
    @Transactional
    public Integer deleteFundProductById(Integer id) {
        return fundProductMapper.deleteByPrimaryKey(id);
    }
}
