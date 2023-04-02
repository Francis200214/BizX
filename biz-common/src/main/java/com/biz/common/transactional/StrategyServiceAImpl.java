package com.biz.common.transactional;

import com.biz.common.str.InfoEnum;
import com.biz.common.str.InfoStrategy;

/**
 * @author francis
 * @create 2023/4/2 9:35
 */
public class StrategyServiceAImpl implements InfoStrategy {

    @Override
    public boolean check(InfoEnum infoEnum) {
        return false;
    }
}

