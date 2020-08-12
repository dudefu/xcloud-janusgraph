package com.xinyi.info.janusgraph.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 多线程向hbase里写数据
 */
public class JanusMain {
    String uptime;
    // 各种路径我都用的绝对路径。。。
    private static final String PROPERTIES_PATH = "";  // 前面配置的 conf/janusgraph-hbase-es.properties 文件的路径
    private static final String DIRECTORY = "项目路径/src/main/resources/";  // 记得把你的文件放在main/resources文件夹下
    private static final String TAG_PATH = DIRECTORY + "poi_tag_leaves_interest.json";
    private static final String BASE_LOG_PATH = "";  // log路径
    /**
     * 自定义线程池
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            18, 18,
            2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new BasicThreadFactory
                    .Builder()
                    .namingPattern("item_tag_custom_thread-%d")
                    .daemon(true)
                    .build(),
            new ThreadPoolExecutor.AbortPolicy());

    private JanusMain() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        uptime = sdf.format(new Date());
    }

    public Logger getLogger(String suffix) {
        String threadName = Thread.currentThread().getName();
        Logger logger = Logger.getLogger(JanusMain.class.getName() + "_" + threadName);
        //设置文件名
        String logPath = BASE_LOG_PATH + LocalDate.now() + suffix + "_" + threadName + ".log";
        //将输出handler加入logger
        try {
            FileHandler fileHandler = new FileHandler(logPath, true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.WARNING, "set Logger Exception", e);
        }
        return logger;
    }

    /**
     * @param fileName:
     * @return
     */
    public JSONObject readJsonFile(String fileName) {
        try {
            String fileRead = new String(Files.readAllBytes(Paths.get(fileName)));
            return JSON.parseObject(fileRead);
        } catch (IOException e) {
            System.out.println(fileName + " 不存在");
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<JSONObject> readTxtFile(String fileName) {
        try {
            String fileRead = new String(Files.readAllBytes(Paths.get(fileName)));
            String[] files = fileRead.split("\n");
            ArrayList<JSONObject> scoreList = new ArrayList<>();
            for (String file : files) {
                scoreList.add(JSON.parseObject(file));
            }
            return scoreList;
        } catch (IOException e) {
            System.out.println(fileName + " 不存在");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 先把所有标签写入库中
     */
    public void writeTag() {
        String[] suffix = TAG_PATH.split("/");
        Logger logger = getLogger(suffix[suffix.length - 1].split("\\.")[0]);
        JanusGraph graph = JanusGraphFactory.open(PROPERTIES_PATH);
        JSONObject items = readJsonFile(TAG_PATH);
        // 写你自己的文件处理逻辑
        // ...
        graph.tx().commit();
        graph.close();
    }

    public void writePOI(File file) {
        JanusGraph graph = JanusGraphFactory.open(PROPERTIES_PATH);
        GraphTraversalSource g = graph.traversal();
        String threadName = Thread.currentThread().getName();

        Logger logger = getLogger(file.getName());
        logger.log(Level.WARNING, "执行线程名：" + threadName);
        logger.log(Level.WARNING, "文件：" + file.getName() + "执行开始");
        // 写你自己的文件处理逻辑
        // ...
        graph.close();
        logger.log(Level.WARNING, "文件：" + file.getName() + "执行完毕");
    }

    /**
     * 用这个函数分发多线程
     */
    public void writePOIs() {
        // 读取原始数据
        File files = new File(DIRECTORY);
        File[] filesArr = files.listFiles();
        int i = 1;
        for (File file : filesArr) {
            threadPoolExecutor.prestartAllCoreThreads();
            System.out.println("当前正在创建第" + i++ + "个线程");
            threadPoolExecutor.submit(() -> {
                new JanusMain().writePOI(file);
            });
        }
    }

    public static void main(String[] args) throws BackendException {
        JanusMain jm = new JanusMain();
        String properties_path = PROPERTIES_PATH;
        Logger logger = jm.getLogger(JanusMain.class.getName());
        JanusGraph graph = JanusGraphFactory.open(properties_path);
        JanusGraphFactory.drop(graph);  // 删除旧的schema, 同时会删除所有数据
        // 设置schema
        new Schema().setSchema(properties_path);
        logger.info("Schema 设置 over");
        graph.close();

        graph = JanusGraphFactory.open(properties_path);
        JanusGraphManagement mgmt = graph.openManagement();
        logger.info(mgmt.printSchema());
        graph.close();
        // 处理tag
        jm.writeTag();
        // 多线程处理poi
        jm.writePOIs();
        // 一直等到所有线程都执行完
        while (true) {
            if (threadPoolExecutor.getActiveCount() == 0) {
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "休眠异常", e);
            }
        }
        graph = JanusGraphFactory.open(properties_path);
        GraphTraversalSource g = graph.traversal();
        // 查询图中有多少节点和边
        logger.info("Vertex count = " + g.V().count().next());
        logger.info("Edges count = " + g.E().count().next());
        graph.close();
    }
}
