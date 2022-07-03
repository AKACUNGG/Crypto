package com.cunff.finance.service;

import com.cunff.finance.entity.FlowOfFunds;

import java.util.List;

public interface FlowOfFundsService {

    Integer insertFlowOfFunds(FlowOfFunds flowOfFunds);

    List<FlowOfFunds> selectFlowOfFundsByUserId(Integer userId);
}
