package com.cunff.finance.controller;

import com.cunff.finance.common.Msg;
import com.cunff.finance.entity.FlowOfFunds;
import com.cunff.finance.entity.PayMoney;
import com.cunff.finance.entity.UserPayMoney;
import com.cunff.finance.service.FlowOfFundsService;
import com.cunff.finance.service.PayMoneyService;
import com.cunff.finance.service.UserPayMoneyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class PayMoneyController {

    @Autowired
    PayMoneyService payMoneyService;
    @Autowired
    UserPayMoneyService userPayMoneyService;
    @Autowired
    FlowOfFundsService flowOfFundsService;

    /**
     * 跳转到ETH质押界面
     * @param model
     * @return
     */
    @RequestMapping("/user/finance/toPayMoney.html")
    public String toPaymoney(Model model){
        List<PayMoney> list = payMoneyService.selectAllPayMoney();
        model.addAttribute("payMoneyList",list);
        model.addAttribute("pageTopBarInfo","ETH质押界面");
        model.addAttribute("activeUrl1","financeActive");
        model.addAttribute("activeUrl2","payMoneyActive");
        return "/user/finance/paymoney";
    }

    /**
     * 购买ETH质押产品
     * @param payMoneyId
     * @param userId
     * @return
     */
    @PostMapping("/user/buyPayMoney")
    @ResponseBody
    public Msg buyPayMoney(@RequestParam("payMoneyId")Integer payMoneyId,
                           @RequestParam("userId") Integer userId ){
        PayMoney pm = payMoneyService.selectPayMoneyById(payMoneyId);
        UserPayMoney upm = new UserPayMoney();
        upm.setUserid(userId);
        upm.setPayid(payMoneyId);
        upm.setStarttime(new Date());
        upm.setAveryield(new BigDecimal("0.03123"));
        upm.setProfit(new BigDecimal("0.03123").multiply(pm.getMonthmoney()));
        upm.setStatus(1);
        Integer result = userPayMoneyService.insertUserPayMoney(upm);
        if (result==1){
            FlowOfFunds fof = new FlowOfFunds();
            fof.setUserid(userId);
            fof.setFlowmoney(pm.getMonthmoney());
            fof.setType(1);
            fof.setSource("ETH质押");
            fof.setCreatetime(new Date());
            if (pm.getType()==1){
                fof.setFunddesc("ETH1.0");
            }else if(pm.getType()==2){
                fof.setFunddesc("ETH2.0");
            }
            flowOfFundsService.insertFlowOfFunds(fof);
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    /**
     * 跳转到ETH质押管理界面（管理员）
     * @param pageNum
     * @param pageSize
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/admin/finance/toPayMoney.html")
    public String toPayMoneyInfo(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             Model model, HttpSession session) {
        PageHelper.startPage(pageNum, pageSize);
        List<PayMoney> list = payMoneyService.selectAllPayMoney();
        PageInfo<PayMoney> pageInfo = new PageInfo<PayMoney>(list, 5);
        model.addAttribute("finacnePageInfo",pageInfo);
        model.addAttribute("financeList",list);

        model.addAttribute("activeUrl1", "financeActive");
        model.addAttribute("activeUrl2", "paymoneyActive");
        model.addAttribute("pageTopBarInfo", "ETH质押管理界面");
        return "/admin/finance/paymoney";
    }

    /**
     * 新增ETH质押产品
     * @param payMoney
     * @return
     */
    @PostMapping("/admin/addPayMoney")
    @ResponseBody
    public Msg addPayMoney(PayMoney payMoney){
        Integer result = payMoneyService.insertPayMoney(payMoney);
        if (result==1){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * 更新时回显信息
     * @param id
     * @return
     */
    @GetMapping("/admin/getPayMoneyInfoById/{id}")
    @ResponseBody
    public Msg getPayMoneyInfoById(@PathVariable("id") Integer id){
        PayMoney payMoney = payMoneyService.selectPayMoneyById(id);
        return Msg.success().add("payMoney",payMoney);
    }

    /**
     * 更新
     * @param id
     * @param payMoney
     * @return
     */
    @PutMapping("/admin/updatePayMoney/{id}")
    @ResponseBody
    public Msg updatePayMoney(@PathVariable("id") Integer id,PayMoney payMoney){
        payMoney.setId(id);
        Integer result = payMoneyService.updatePayMoney(payMoney);
        if (result==1){
            return Msg.success();
        }
        return Msg.fail();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/admin/deletePayMoneyById/{id}")
    @ResponseBody
    public Msg deletePayMoneyById(@PathVariable("id") Integer id){
        Integer result = payMoneyService.deletePayMoneyById(id);
        if (result==1){
            return Msg.success();
        }
        return Msg.fail();
    }
}
