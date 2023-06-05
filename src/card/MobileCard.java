package card;

import superClass.ServicePackage;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//手机卡类
public class MobileCard {
    //手机卡号
    private String cardNumber;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //套餐信息
    private ServicePackage serPackage;
    //当月总消费
    private double consumAmount;
    //月剩余话费
    private double money;
    //实际通话时长
    private int realTalkTime;
    //实际发送短信条数
    private int realSMSCount;
    //实际使用流量
    private int realFlow;

    //展示电话卡使用信息
    public void showMeg() {
        System.out.println("该手机卡卡号是：" + getCardNumber() + ",用户名称是：" + getUserName() + ",用户密码是：" + getPassWord() + ",选择的套餐是：" + getSerPackage() + ",当月总消费金额是：" + getConsumAmount()
                + ",剩余话费是：" + getMoney() + ",当月实际通话时长是：" + getRealTalkTime() + ",当月实际使用短信条数：" + getRealSMSCount() + ",当月实际使用流量：" + getRealFlow());
    }

    public MobileCard(String cardNumber, String userName, String passWord, ServicePackage serPackage, double consumAmount, double money, int realTalkTime, int realSMSCount, int realFlow) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.serPackage = serPackage;
        this.consumAmount = consumAmount;
        this.money = money;
        this.realTalkTime = realTalkTime;
        this.realSMSCount = realSMSCount;
        this.realFlow = realFlow;
    }

    public MobileCard() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public ServicePackage getSerPackage() {
        return serPackage;
    }

    public void setSerPackage(ServicePackage serPackage) {
        this.serPackage = serPackage;
    }

    public double getConsumAmount() {
        return consumAmount;
    }

    public void setConsumAmount(double consumAmount) {
        this.consumAmount = consumAmount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getRealTalkTime() {
        return realTalkTime;
    }

    public void setRealTalkTime(int realTalkTime) {
        this.realTalkTime = realTalkTime;
    }

    public int getRealSMSCount() {
        return realSMSCount;
    }

    public void setRealSMSCount(int realSMSCount) {
        this.realSMSCount = realSMSCount;
    }

    public int getRealFlow() {
        return realFlow;
    }

    public void setRealFlow(int realFlow) {
        this.realFlow = realFlow;
    }
}
