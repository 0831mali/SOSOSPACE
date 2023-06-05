package enviromentORscene;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//使用场景
public class Scene {
    //类型
    private String type;
    //数据量
    private int data;
    //描述
    private String description;

    public Scene(String type, int data, String description) {
        this.type = type;
        this.data = data;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Scene() {
    }
}
