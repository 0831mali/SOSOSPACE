package entity;

import card.MobileCard;
import inter.NetService;
import superClass.ServicePackage;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//网虫套餐
public class NetPackage extends ServicePackage implements NetService {
    //流量
    private int flow;
    //重写展示信息
    @Override
    public void showInfo() {
        System.out.println("网虫套餐:"+"每月资费："+getPrice()+"元/月,套餐流量是:"+getFlow()+"MB/月");
    }

    public NetPackage(int flow) {
        this.flow = flow;
    }

    public NetPackage(double price, int flow) {
        super(price);
        this.flow = flow;
    }
    public NetPackage(){
        this.flow=224;
        super.setPrice(68);
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
    //上网功能
    @Override
    public int netPlay(int flow, MobileCard card) {
        int extra=0;
        //每条计算
        for (int i = 0; i < flow; i++) {
            //如果还有剩余的短信条数
            if(this.flow-card.getRealFlow()>=flow){
                extra=flow;
                card.setRealFlow(card.getRealFlow()+ flow);

                break;
            }else if(card.getMoney()>=0.1){
                card.setMoney(card.getMoney()-0.1);
                card.setRealFlow(card.getRealFlow()+1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                extra++;
            }else {
                System.out.println("您的余额不足，请充值后再试,已执行"+extra+"MB上网流量");
                break;
            }

        }
        return extra;
    }
}
