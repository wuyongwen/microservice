/**
 * WeiXin
 * @title StringTemplate.java
 * @package com.chn.common
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月17日-上午9:58:53
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @class StringTemplate
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class StringTemplate {
    
    private List<Element> list = new ArrayList<Element>();
    private StringTemplate(List<Element> list) {
        this.list = list;
    }
    
    /**
     * @param src 例：尊敬的${who}用户，您该月话费为${amount}元，谢谢${2}！！
     * @return 解析生成的模版，调用 <code>replace</code> 方法即可生成结果
     */
    public static StringTemplate compile(String src) {
        List<Element> list = new ArrayList<Element>();
        final char[] cs = src.toCharArray();
        int index1 = 0, index2 = 0;
        while(true) {
            index2 = next(cs, index1, '$');
            if(index2 != -1 && index2 < cs.length - 1) {
                if(cs[index2 + 1] == '{') {
                    list.add(new AppendElement(cs, index1, index2 - index1));
                    index1 = index2 + 2;
                    index2 = next(cs, index1, '}');
                    list.add(new ReplaceElement(new String(cs, index1, index2 - index1)));
                    index1 = index2 + 1;
                } else {
                    list.add(new AppendElement(cs, index1, index2 - index1 + 1));
                    index1 = index2 + 1;
                }
            } else {
                break;
            }
        }
        list.add(new AppendElement(cs, index1, cs.length - index1));
        return new StringTemplate(list);
    }
    
    public static StringTemplate compileFromClassPath(String src) {
        
        InputStream is = null;
        try {
            is = StringTemplate.class.getResourceAsStream(src);
            return compile(IOUtils.toString(is, "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("加载模板失败：" + src, e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
 
    /**
     * @param replacements
     * @return 根据模版生成字符串，变量均按replacements中同key值替换
     */
    public String replace(ReplaceHolder replacements) {
        StringBuilder sb = new StringBuilder();
        for(Element ele : list) {
            ele.append(sb, replacements);
        }
        return sb.toString();
    }
    
    public String replace(final Map<String, Object> map) {
        
        return this.replace(new ReplaceHolder() {
            
            @Override
            public String get(String key) {
                Object result = map.get(key);
                return result == null ? "" : result.toString();
            }
        });
    }
   
    private static int next(char[] cs, int begin, char dest) {
        for(int i = begin; i < cs.length; i ++) {
            if(cs[i] == dest) {
                return i;
            }
        }
        return -1;
    }
    private static abstract class Element {
        abstract void append(StringBuilder sb, ReplaceHolder map);
    }
    private static class AppendElement extends Element {
        private char[] cs;
        private int index;
        private int length;
        AppendElement(char[] cs, int index, int length) {
            this.cs = cs;
            this.index = index;
            this.length = length;
        }
        @Override
        void append(StringBuilder sb, ReplaceHolder map) {
            sb.append(cs, index, length);
        }
    }
    private static class ReplaceElement extends Element {
        private String key;
        ReplaceElement(String key) {
            this.key = key;
        }
        @Override
        void append(StringBuilder sb, ReplaceHolder map) {
            String replaceStr = map.get(key);
            sb.append(replaceStr == null ? "" : replaceStr);
        }
    }
    
    public static interface ReplaceHolder {
        
        public String get(String key);
    }
    
}