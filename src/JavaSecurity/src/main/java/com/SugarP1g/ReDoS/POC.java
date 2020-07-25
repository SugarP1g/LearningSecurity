package com.SugarP1g.ReDoS;

public class POC {
    public static void main(String[] args) {
        String regex = "^(a+)*$";
        for (int i = 16; i < 70; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j <= i; j++) {
                sb.append("a");
            }
            sb.append("!");
            System.out.println(sb);
            String text = new String(sb);
            System.out.println(text.matches(regex));
        }
    }
}
