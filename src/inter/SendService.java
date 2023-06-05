package inter;

import card.MobileCard;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//短信服务
public interface SendService {
    int send(int count,MobileCard card);
}
