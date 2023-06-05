package superClass;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//套餐父类
public abstract class ServicePackage {
    //定义价格
    private double price;

    //showinfo（）展示套餐信息
    public abstract void showInfo();

    public ServicePackage() {
    }

    public ServicePackage(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
