package Util;

import card.MobileCard;
import entity.NetPackage;
import entity.SuperPackage;
import entity.TalkPackage;
import enviromentORscene.ConsumInfo;
import enviromentORscene.Scene;
import inter.CallService;
import inter.NetService;
import inter.SendService;
import superClass.ServicePackage;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
public class CardUtil {
    //定义一个Scanner变量
    Scanner sc=new Scanner(System.in);
    //集合，soso用户集合，string表示卡号，MObileCard卡对象
    private Map<String, MobileCard> cards=new HashMap<>();
    //消费记录总集合，用于后面输出 string表示手机号，List<Consuminfo>表示该卡号对应的消费记录
    private Map<String, List<ConsumInfo>> consumInfos=new HashMap<>();
    //定义场景数组
    Scene[] scenes=new Scene[6];
    //创建日期格式化对象
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年-MM月-dd日 HH时-mm分-ss秒");
    //设置构造方法，初始化用户集合，为集合预置一个用户信息
    public CardUtil(){
        //初始化用户集合
        cards.put("13060180503",new MobileCard("13060180503","任煜","123456",new SuperPackage(),78,66,12,12,300));
        cards.put("17339821915",new MobileCard("17339821915","马力","123456",new SuperPackage(),78,77,12,12,300));
        cards.put("12345678900",new MobileCard("12345678900","小胖","123456",new NetPackage(),69,77,0,0,300));

        //初始化消费记录集合
        List<ConsumInfo> list=new ArrayList<>();
        list.add(new ConsumInfo("13060180503","打电话",15));
        list.add(new ConsumInfo("13060180503","发短信",150));
        list.add(new ConsumInfo("13060180503","用流量",1500));
        consumInfos.put("13060180503",list);





    }

    //初始化场景
    public void initScene(){

        scenes[0]=new Scene("打电话",15,"在大厅");
        scenes[1]=new Scene("打电话",12,"在厕所");
        scenes[2]=new Scene("发短信",2,"给同学");
        scenes[3]=new Scene("发短信",20,"给女朋友");
        scenes[4]=new Scene("用流量",200,"看小电影");
        scenes[5]=new Scene("用流量",150,"听音乐");
    }
    //初始化消费记录单。txt文件
    public void initConsumText() {
        FileWriter fw= null;
        try {
            fw = new FileWriter("消费记录.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //根据手机号和密码验证账号是否正确
    public boolean isExistCard(String number,String passWord){
        if(cards.containsKey(number)){
            if(cards.get(number).getPassWord().equals(passWord)){
                return true;
            }
        }
        return false;
    }
    //验证是否存在账号，根据手机号判断
    public boolean isExistCard(String number){
       if(cards.containsKey(number))
           return true;
        return false;
    }
    //生成随机手机号

    /**
     * 生成随机手机号码，要求不能和集合已存在手机号码重复
     * @return String 手机号
     */
    public String createNumber(){
        //创建Random对象
        Random r=new Random();
        //生成8位手机号
        String num="139";
        for (int i = 0; i < 8; i++) {
            int eight=r.nextInt(10);
            num+=eight;
        }
        //获取cards用户的手机号集合
        Set<String> phoneSet=cards.keySet();
        for(String number:phoneSet){
            if(number.equals(num)){
                //递归，获取新手机号，直到num和已知集合内号码不一致
                num=createNumber();
            }
        }
        return num;

    }
    /**
     * 根据count数量生成指定数量的手机号
     *     调用createNumber（）方法生成一个手机号
     */
    public String[] getNewNumbers(int count){
        String[] numbers=new String[count];
        //第一个手机号码先定义好
        numbers[0]=createNumber();
        //从第二个手机号码开始验证是否重复
        for (int i = 1; i < count; i++) {
            String number=createNumber();
            for (int j = 0; j < numbers.length; j++) {
                if(numbers[j]!=null){
                    if(numbers[j]!=number) {
                        numbers[i] = number;
                        break;
                    }
                }
            }

        }
        return numbers;
    }
    //添加一个手机账户
    public void addCard(MobileCard card){

    }
    //删除一个手机账户
    public void delCard(String number){
        if(cards.containsKey(number)) {
            cards.remove(number);
            if(consumInfos.containsKey(number)){
                consumInfos.remove(number);
            }
            System.out.println("卡号"+number+"的用户办理退网成功!\n谢谢使用！！");
        }else {
            System.out.println("没有该手机号，不需要办理退网手续");
        }
    }
    //查看本月剩余套餐信息
    public void showRemainDetail(String number){
        System.out.println("********套餐余量查询********");
        System.out.println("您的卡号是"+number+",套餐内剩余:");
        //获取套餐对象,对套餐类型做判断，后强转类型
        ServicePackage servicePackage=cards.get(number).getSerPackage();

        if(servicePackage instanceof NetPackage){
            //如果是网虫套餐
            NetPackage netPackage=(NetPackage) servicePackage;
            //剩余流量
            int remainFlow=netPackage.getFlow()-cards.get(number).getRealFlow();
            System.out.println("剩余流量："+(remainFlow>0?remainFlow:0)+"MB");
        }else if(servicePackage instanceof SuperPackage){
            //如果是超人套餐
            SuperPackage superPackage=(SuperPackage) servicePackage;
            //剩余通话时长
            int remainTalk=superPackage.getTalkTime()-cards.get(number).getRealTalkTime();
            System.out.println("通话时长："+(remainTalk>0?remainTalk:0)+"分钟");
            //剩余短信条数
            int remainSMS=superPackage.getSMSCount()-cards.get(number).getRealSMSCount();
            System.out.println("短信条数"+(remainSMS>0?remainSMS:0)+"条");
            //剩余上网流量
            int remainFlow=superPackage.getFlow()-cards.get(number).getRealFlow();
            System.out.println("剩余流量："+(remainFlow>0?remainFlow:0)+"MB");
        }else if(servicePackage instanceof TalkPackage){
            //如果是话痨套餐
            TalkPackage talkPackage=(TalkPackage) servicePackage;
            //剩余通话时长
            int remainTalk=talkPackage.getTalkTime()-cards.get(number).getRealTalkTime();
            System.out.println("通话时长："+(remainTalk>0?remainTalk:0)+"分钟");
            //剩余短信条数
            int remainSMS=talkPackage.getSMSCount()-cards.get(number).getRealSMSCount();
            System.out.println("短信条数"+(remainSMS>0?remainSMS:0)+"条");
        }
    }
    //查看本月消费
    public void showAmountDetail(String number){
        //decimalFormat 限制金额小数
        DecimalFormat d=new DecimalFormat("0.0");
        System.out.println("******本月账单查询******");
        //获取卡号
        System.out.println("您的卡号："+number+",当月账单:");
        //获取当月资费
        System.out.println("套餐资费："+d.format(cards.get(number).getSerPackage().getPrice())+"元");
        //获取当月总消费
        System.out.println("合计："+d.format(cards.get(number).getConsumAmount())+"元");
        //获取账户余额
        System.out.println("账户余额："+d.format(cards.get(number).getMoney())+"元");
        //换行
        System.out.println();
    }
    //增加消费记录
    public void addConsumInfo(String number,ConsumInfo info){
        List<ConsumInfo> list = consumInfos.get(number);
        if (list == null) {
            list = new ArrayList<ConsumInfo>();
            list.add(info);
            consumInfos.put(number, list);
        } else {
            list.add(info);
        }
    }
    //使用soso移动大厅
    public void useSoso(String number){
        //用Random创建随机数对象
        Random r=new Random();
        //随机数0-5
        int num=r.nextInt(6);
        System.out.println("手机号"+number+"的号主预计"+scenes[num].getDescription()+scenes[num].getType()+"将使用"+scenes[num].getData()+"  MB流量/分钟/条短信");
        //添加消费记录  此处加入判断条件，如果该套餐支持该功能，添加消费记录
        //判断使用的功能类型

        switch (scenes[num].getType()){
            case "用流量":
                if(cards.get(number).getSerPackage() instanceof NetService){
//
                    /*//对已使用数据进行修改
                    NetPackage netPackage=(NetPackage) cards.get(number).getSerPackage();
                    //剩余流量
                    int allFlow=netPackage.getFlow();
                    int remainLFow=allFlow-cards.get(number).getRealFlow()-scenes[num].getData();

                    if(remainLFow>=0){
                        ConsumInfo consumInfo = new ConsumInfo(number, scenes[num].getType(), scenes[num].getData());
                        //如果套餐满足该功能执行添加消费记录，不满足就再执行一遍
                        addConsumInfo( number,consumInfo);
                    }else if(
                            //如果超出流量的钱比剩余的话费少，任然执行消费，同时对剩余money进行修改
                            cards.get(number).getMoney()-remainLFow*(-1)*0.1>=0
                    ){
                        ConsumInfo consumInfo = new ConsumInfo(number, scenes[num].getType(), scenes[num].getData());
                        //如果套餐满足该功能执行添加消费记录，不满足就再执行一遍
                        addConsumInfo( number,consumInfo);
                        cards.get(number).setMoney(cards.get(number).getMoney()-remainLFow*(-1)*0.1);
                    }
                    else {
                        System.out.println("您的余额不足以执行此操作");
                    }*/
                    //执行用流量操作
                    NetService netService=(NetService) cards.get(number).getSerPackage();
                   int extra= netService.netPlay(scenes[num].getData(),cards.get(number));

                    //执行添加消费记录操作
                    if (extra>0) {
                        ConsumInfo consumInfo = new ConsumInfo(number, scenes[num].getType(), extra);
                        //如果套餐满足该功能执行添加消费记录，不满足就再执行一遍
                        addConsumInfo(number, consumInfo);
                        System.out.println("执行成功");
                    }

                }else {
                    System.out.println("该套餐不支持该功能，将执行新的操作");
                    useSoso(number);
                }
                break;
            case "发短信":
                if(cards.get(number).getSerPackage() instanceof SendService){

                    //执行发短信操作
                    SendService sendService=(SendService) cards.get(number).getSerPackage();
                   int extra= sendService.send(scenes[num].getData(),cards.get(number));

                    //执行添加消费记录操作
                    if(extra>0) {
                        ConsumInfo consumInfo = new ConsumInfo(number, scenes[num].getType(), extra);
                        //如果套餐满足该功能执行添加消费记录，不满足就再执行一遍
                        addConsumInfo(number, consumInfo);
                        System.out.println("执行成功");
                    }
                }else {
                    System.out.println("该套餐不支持该功能，将执行新的操作");
                    useSoso(number);
                }
                break;
            case "打电话":
                if(cards.get(number).getSerPackage() instanceof CallService){

                    //执行打电话操作,返回实际消费的量
                    CallService callService=(CallService) cards.get(number).getSerPackage();
                   int extar= callService.call(scenes[num].getData(),cards.get(number));

                    //执行添加消费记录操作
                    if(extar>0) {
                        ConsumInfo consumInfo = new ConsumInfo(number, scenes[num].getType(), extar);
                        //如果套餐满足该功能执行添加消费记录，不满足就再执行一遍
                        addConsumInfo(number, consumInfo);
                        System.out.println("执行成功");
                    }
                }else {
                    System.out.println("该套餐不支持该功能，将执行新的操作");
                    useSoso(number);
                }
                break;
            default:

                break;
        }


    }
    //资费说明，IO流
    public void showDescription(){
        try {
            BufferedReader fw=new BufferedReader(new FileReader("资费说明.txt"));
            String line=null;
            while((line=fw.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //变更套餐
    public void changingPack(String number){
        System.out.println("*********套餐变更***********");
        System.out.print("1.话痨套餐   2.网虫套餐   3.超人套餐   请选择套餐（请输入序号）：");
       ServicePackage servicePackage=cards.get(number).getSerPackage();
        int num=sc.nextInt();
        switch (num){
            case 1:

                if(servicePackage instanceof TalkPackage){
                    System.out.println("对不起，您已经是改套餐用户，无需更改");
                }else{
                    if(cards.get(number).getMoney()<new TalkPackage().getPrice()){
                        System.out.println("对不起，您的余额不足以支持您更改套餐，请充值后再试！");
                    }else {
                        cards.get(number).setSerPackage(new TalkPackage());
                        cards.get(number).setRealSMSCount(0);
                        cards.get(number).setRealFlow(0);
                        cards.get(number).setRealTalkTime(0);
                        cards.get(number).setConsumAmount(new TalkPackage().getPrice());
                        cards.get(number).setMoney(cards.get(number).getMoney()-new TalkPackage().getPrice());
                        System.out.println("您的套餐变更成功");
                    }
                }
                break;
            case 2:

                if(servicePackage instanceof NetPackage){
                    System.out.println("对不起，您已经是改套餐用户，无需更改");
                }else{
                    if(cards.get(number).getMoney()<new NetPackage().getPrice()){
                        System.out.println("对不起，您的余额不足以支持您更改套餐，请充值后再试！");
                    }else {
                        cards.get(number).setSerPackage(new NetPackage());
                        cards.get(number).setRealSMSCount(0);
                        cards.get(number).setRealFlow(0);
                        cards.get(number).setRealTalkTime(0);
                        cards.get(number).setConsumAmount(new NetPackage().getPrice());
                        cards.get(number).setMoney(cards.get(number).getMoney()-new NetPackage().getPrice());
                        System.out.println("您的套餐变更成功");

                    }
                }
                break;
            case 3:
                if(servicePackage instanceof SuperPackage){
                    System.out.println("对不起，您已经是改套餐用户，无需更改");
                }else{
                    if(cards.get(number).getMoney()<new SuperPackage().getPrice()){
                        System.out.println("对不起，您的余额不足以支持您更改套餐，请充值后再试！");
                    }else {
                        cards.get(number).setSerPackage(new SuperPackage());
                        cards.get(number).setRealSMSCount(0);
                        cards.get(number).setRealFlow(0);
                        cards.get(number).setRealTalkTime(0);
                        cards.get(number).setConsumAmount(new SuperPackage().getPrice());
                        cards.get(number).setMoney(cards.get(number).getMoney()-new SuperPackage().getPrice());
                        System.out.println("您的套餐变更成功");
                    }
                }
                break;
            default:
                break;
        }

    }
    //打印消费记录
    public void printConsumInfo(String number){
        //获取当前手机号的消费记录集合
        List<ConsumInfo> list=consumInfos.get(number); int i =1;
        //定义输出流，输出到消费记录.txt里
        FileWriter fw=null;
        try {
            fw=new FileWriter("消费记录.txt",true);
            fw.write("**********"+number+"消费记录***********"+sdf.format(new Date())+"\n");
            fw.write("序号     类型     数据（通话（条）/上网（MB）/短信（条））\n");
            for (ConsumInfo consumInfo:list) {
                fw.write(i+".      "+consumInfo.getType()+"    "+consumInfo.getConsumData()+"\n");
                i++;
                fw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("输出成功");
    }
    //话费充值
    public void chargeMoney(){
        while (true) {
            System.out.print("请输入充值卡号(充值卡号按0退出充值界面)：");

            String number = sc.next();
            if(number.equals("0")){
                System.out.println("谢谢您的使用");
                break;
            }
            System.out.print("请输入充值金额:");
            double money=sc.nextDouble();
            if(cards.containsKey(number)) {
                if (money < 50) {
                    System.out.println("请至少充值50元(充值卡号按0退出充值界面)");
                } else {
                    cards.get(number).setMoney(cards.get(number).getMoney()+money);
                    System.out.println("充值成功，当前余额是："+cards.get(number).getMoney()+"元");
                    return;
                }
            }else {
                System.out.println("对不起，没有该用户");
            }
        }
    }
    //进入二级菜单
    public void toSecondLevel(String phoneNumber) {
        while (true) {
            System.out.println("***********嗖嗖移动用户菜单**********");
            System.out.print("1.本月账单查询\n2.套餐余量查询\n3.打印消费详情单\n4.套餐变更\n5.办理退网\n请选择（输入1~5选择功能，按其他键返回上一级）:");
            String num=sc.next();
            switch (num){
                case "1":
                    showAmountDetail(phoneNumber);
                    break;
                case "2":
                    showRemainDetail(phoneNumber);
                    break;
                case "3":
                    printConsumInfo(phoneNumber);
                    break;
                case "4":
                    changingPack(phoneNumber);
                    break;
                case "5":
                    delCard(phoneNumber);
                    return;
                default:
                    return;
            }


        }
    }
    //注册功能
    public void register(){
        //创建卡号对象
        MobileCard card=new MobileCard();
        System.out.println("可选择的号码：");
        //调用getNewNumbers方法
        String[] numbers=getNewNumbers(9);
        for (int i = 0; i <numbers.length ; i++) {
            System.out.print((i+1)+"."+numbers[i]+"\t");
            //当一行满三个号码时，换行
            if((i+1)%3==0){
                System.out.println();
            }
        }
        //换行
        System.out.println();
        System.out.print("请选择卡号(输入1~9的序号):");

        int num=sc.nextInt();
        //初始化手机卡的手机号
        card.setCardNumber(numbers[num-1]);

        //选择套餐模块
        System.out.print("1.话痨套餐   2.网虫套餐   3.超人套餐   请选择套餐（请输入序号）：");
        num=sc.nextInt();
        switch (num){
            case 1:
                card.setSerPackage(new TalkPackage());
                break;
            case 2:
                card.setSerPackage(new NetPackage());
                break;
            case 3:
                card.setSerPackage(new SuperPackage());
                break;
            default:
                break;
        }

        //换行
        System.out.println();
        //初始化姓名，密码
        System.out.print("请输入姓名：");
        String name=sc.next();
        System.out.print("请输入密码：");
        String passWord=sc.next();

        card.setUserName(name);
        card.setPassWord(passWord);

        //初始化剩余money money=预存金额-本月套餐资费   预存金额previousMoney  剩余金额money
        System.out.print("请输入预存金额:");
        double previousMoney;
        double money;
        while(true) {
             previousMoney = sc.nextDouble();

             money = previousMoney - card.getSerPackage().getPrice();
            if (money < 0) {
                System.out.print("您预存的话费金额不足以支付本月固定套餐费用，请重新充值：");
            }else {
                //初始化卡号余额
                card.setMoney(money);
                break;
            }

        }

        //换行
        System.out.println();
        //注册完毕，输出新注册的卡号信息
        System.out.println("注册成功！ 卡号："+card.getCardNumber()+",用户名："+card.getUserName()+",当前余额："+card.getMoney()+"元");
        card.getSerPackage().showInfo();

        //将心用户信息输入到用户集合cards里
        cards.put(card.getCardNumber(),card);
        //换行
        System.out.println();
    }
}
