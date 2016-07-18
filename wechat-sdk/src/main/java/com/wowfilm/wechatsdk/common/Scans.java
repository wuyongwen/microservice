/**
 * WeiXin
 * @title Scans.java
 * @package com.chn.common
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月15日-下午5:47:15
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @class Scans
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class Scans {

    /**
     * 从包package中获取所有的Class
     * 
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive) {
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.add(loadClass(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            //Ignore
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        //Ignore
                    }
                }
            }
        } catch (IOException e) {
            //Ignore
        }
        return classes;
    }
    
    private static void findAndAddClassesInPackageByFile(String packageName,
            String packagePath, boolean recursive, Set<Class<?>> classes) {
        
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(new ClassFileFilter(recursive));
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(loadClass(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {
                    //Ignore
                }
            }
        }
    }
    
    private static class ClassFileFilter implements FileFilter {
        
        private boolean recursive;
        public ClassFileFilter(boolean recursive) {
            this.recursive = recursive;
        }
        public boolean accept(File file) {
            return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
        }
    }
    
    private static Class<?> loadClass(String clazz) throws ClassNotFoundException {
        
        return Thread.currentThread().getContextClassLoader().loadClass(clazz);
    }
}
