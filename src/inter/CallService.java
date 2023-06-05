package inter;

import card.MobileCard;

/**
 * 作者：马力
 * 时间：2023/6/1
 */
//打电话功能
public interface CallService {
    int call(int minCount, MobileCard card);
}
