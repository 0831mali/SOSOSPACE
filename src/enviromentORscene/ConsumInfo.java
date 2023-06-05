package enviromentORscene;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//消费记录
public class ConsumInfo {
    //手机号
    private String cardNumber;
    //干事的类型
    private String type;
    //数据量 电话 分   短信/条  流量/MB
    private int consumData;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getConsumData() {
        return consumData;
    }

    public void setConsumData(int consumData) {
        this.consumData = consumData;
    }

    public ConsumInfo() {
    }

    public ConsumInfo(String cardNumber, String type, int consumData) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.consumData = consumData;
    }
}
