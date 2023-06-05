package MainTest;

import Util.CardUtil;

import java.util.Scanner;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
public class SoSoTest {
    public static void main(String[] args) {
        //使用一个Scanner,方便调用，减少内存使用
        Scanner sc=new Scanner(System.in);
        //初始化工具类，同事初始化用户信息集合，初始化场景
        CardUtil cu=new CardUtil();
        cu.initScene();
        //初始化消费记录单，没次运行消费记录单是新的
        cu.initConsumText();
        //循环使用大厅
    a:    while(true){
            System.out.println("************欢迎使用搜搜移动营业大厅***********");
            System.out.println("1.用户登录  2.用户注册  3.使用soso  4.话费充值  5.资费说明  6.退出系统");
            System.out.print("请选择：");
            String num=sc.next();
            //对长度进行判断，因为如果输入超过10位，会爆出数据类型不匹配异常，int范围是-2147483647~~2147483648
            //调试改用String类型
            //根据用户选择的功能进入相应的程序
            switch (num){
                case "1":
                    //获取用户输入的手机号和密码进行验证判断
                    System.out.print("请输入手机卡号：");
                    String phoneNumber=sc.next();
                    System.out.print("请输入密码:");
                    String passWord=sc.next();
                    boolean existCard = cu.isExistCard(phoneNumber, passWord);
                    if(existCard){
                        cu.toSecondLevel(phoneNumber);
                    }else {
                        System.out.println("账号错误，请您稍后再试~");
                    }

                    break;
                case "2":
                    //用户注册功能，首先输出9个随机号
                    cu.register();
                    break;
                case "3":
                    //使用soso，进入随机场景，添加消费记录，修改用户手机卡各种信息
                    //重复使用一case的用户登录判断
                    System.out.print("请用手机号登录");
                    System.out.print("请输入手机卡号：");
                    phoneNumber=sc.next();
                    System.out.print("请输入密码:");
                    passWord=sc.next();
                    existCard = cu.isExistCard(phoneNumber, passWord);
                    if(existCard){

                        cu.useSoso(phoneNumber);
                    }else {
                        System.out.println("账号错误，请您稍后再试~");
                    }

                    break;
                case "4":

                    cu.chargeMoney();
                    break;
                case "5":
                    cu.showDescription();
                    break;
                case "6":
                    System.out.println("谢谢您的使用，期待我们的下次相遇!!");
                    break a;
                default:
                    break;
            }

        }
    }
}
