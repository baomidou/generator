package com.baomidou.mybatisplus.generator.config.rules;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;

import java.util.Arrays;

public class NamingStrategyUtils {
    public static String underlineToCamel(String name) {
        if (StringUtils.isBlank(name)) {
            return StringPool.EMPTY;
        }
        String tempName = name;
        if (StringUtils.isCapitalMode(name) || StringUtils.isMixedMode(name)) {
            tempName = name.toLowerCase();
        }
        StringBuilder result = new StringBuilder();
        String[] camels = tempName.split(ConstVal.UNDERLINE);
        Arrays.stream(camels).filter(camel -> !StringUtils.isBlank(camel)).forEach(camel -> {
            if (result.length() == 0) {
                result.append(StringUtils.firstToLowerCase(camel));
            } else {
                result.append(capitalFirst(camel));
            }
        });
        return result.toString();
    }

    private static String capitalFirst(String name) {
        if (StringUtils.isNotBlank(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return StringPool.EMPTY;
    }
}
