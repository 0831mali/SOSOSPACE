package entity;

import card.MobileCard;
import inter.CallService;
import inter.SendService;
import superClass.ServicePackage;

/**
 * 作者：马力
 * 时间：2023/6/1
 */

//话痨套餐
public class TalkPackage extends ServicePackage implements CallService, SendService {
    //通话时长
    private int talkTime;
    //短信条数
    private int SMSCount;
    //重写展示信息
    @Override
    public void showInfo() {
        System.out.println("话痨套餐：每月资费:"+getPrice()+"元/月,通话时长为:"+getTalkTime()+"分钟/月,短信条数为:"+getSMSCount()+"条/月");
    }

    public TalkPackage(int talkTime, int SMSCount) {
        this.talkTime = talkTime;
        this.SMSCount = SMSCount;
    }

    public TalkPackage(double price, int talkTime, int SMSCount) {
        super(price);
        this.talkTime = talkTime;
        this.SMSCount = SMSCount;
    }
    public TalkPackage(){
        this.talkTime=500;
        this.SMSCount=30;
        super.setPrice(58);
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public int getSMSCount() {
        return SMSCount;
    }

    public void setSMSCount(int SMSCount) {
        this.SMSCount = SMSCount;
    }

    //带电话功能
    @Override
    public int call(int minCount, MobileCard card) {
        int extra=0;
        //每条计算
        for (int i = 0; i < minCount; i++) {
            //如果还有剩余的短信条数
            if(this.talkTime-card.getRealTalkTime()>=minCount){
                extra=minCount;
                card.setRealTalkTime(card.getRealTalkTime()+minCount);
                break;
            }else if(card.getMoney()>=0.2){
                card.setMoney(card.getMoney()-0.1);
                card.setRealTalkTime(card.getRealTalkTime()+1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                extra++;
            }else {
                System.out.println("您的余额不足，请充值后再试,已经打了"+extra+"分钟电话");
                break;
            }

        }
        return extra;
    }
    //发短信功能
    @Override
    public int send(int count, MobileCard card) {
        int extra=0;
        //每条计算
        for (int i = 0; i < count; i++) {
            //如果还有剩余的短信条数
            if(this.SMSCount-card.getRealSMSCount()>=count){
                extra=count;
                card.setRealSMSCount(card.getRealSMSCount()+count);
                break;
            }else if(card.getMoney()>=0.1){
                card.setMoney(card.getMoney()-0.1);
                card.setRealSMSCount(card.getRealSMSCount()+1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                extra++;
            }else {
                System.out.println("您的余额不足，请充值后再试,已经发了"+extra+"条短信");
                break;
            }

        }
        return extra;

    }
}
