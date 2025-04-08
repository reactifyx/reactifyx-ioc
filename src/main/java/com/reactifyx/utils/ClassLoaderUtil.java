/*
 * Copyright 2024-2025 the original author Hoàng Anh Tiến.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reactifyx.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Utility class for dynamically loading classes from a given package.
 * <p>
 * Supports scanning from both the file system and JAR files. This is
 * particularly useful for frameworks that perform classpath scanning for
 * annotated components such as {@code @Component}, {@code @Bean}, etc.
 */
public class ClassLoaderUtil {

    /**
     * Scans the classpath and retrieves all classes under the specified package.
     * <p>
     * Handles both regular file system directories and classes packaged inside JAR
     * files.
     *
     * @param packageName
     *            the base package to scan (e.g., "com.example.myapp")
     * @return a list of classes found in the specified package
     * @throws IOException
     *             if an I/O error occurs while reading from the classpath
     * @throws URISyntaxException
     *             if the resource URI syntax is invalid
     * @throws ClassNotFoundException
     *             if any of the classes cannot be loaded
     */
    public static List<Class<?>> getClasses(String packageName)
            throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URI pkg = Objects.requireNonNull(classLoader.getResource(path)).toURI();
        if (pkg.toString().startsWith("jar:")) {
            Path root;
            try {
                root = FileSystems.getFileSystem(pkg).getPath(path);
            } catch (FileSystemNotFoundException e) {
                root = FileSystems.newFileSystem(pkg, Collections.emptyMap()).getPath(path);
            }

            String extension = ".class";
            try (Stream<Path> allPaths = Files.walk(root)) {
                allPaths.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        String filePath = file.toString().replace('/', '.');
                        String fileName = filePath.substring(
                                filePath.indexOf(packageName), filePath.length() - extension.length());
                        classes.add(Class.forName(fileName));
                    } catch (ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
                    }
                });
            }
        } else {
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
        }
        return classes;
    }

    /**
     * Recursively scans a directory to find all class files and load them.
     *
     * @param directory
     *            the root directory to scan
     * @param packageName
     *            the package name corresponding to the directory
     * @return a list of classes in the given directory (and subdirectories)
     * @throws ClassNotFoundException
     *             if a class cannot be loaded
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName
                        + '.'
                        + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
