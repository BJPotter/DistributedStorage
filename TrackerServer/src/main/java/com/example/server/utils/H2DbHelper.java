package com.example.server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.server.model.StorageFileInfo;
import com.example.server.model.StorageFileInfoNode;
import com.example.server.system.SystemConstant;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.Map;


public class H2DbHelper {
    private static Connection connection;
    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:file:"+ SystemConstant.trackerDataPath+ File.separatorChar+"ctjdfs", "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createConnection() throws SQLException {
        if (connection.isClosed()){
            connection =DriverManager.getConnection("jdbc:h2:file:"+ SystemConstant.trackerDataPath+ File.separatorChar+"ctjdfs", "sa", "");
        }
    }


    public static void createTable() throws SQLException {
        try {
            createConnection();
            Statement statement = connection.createStatement();
            String sql = "create table t_file(id VARCHAR (32) PRIMARY  KEY,name VARCHAR(50),size int(15),metadate VARCHAR(300), storageNodes VARCHAR(5000) )";
            int i = statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            if (!e.getMessage().contains("already exists")){
                e.printStackTrace();
            }
        }
    }

    public static void insertData(StorageFileInfo storageFileInfo) throws SQLException {
        createConnection();
        String sql = "insert into t_file(id,name,size,metadate,storageNodes) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,storageFileInfo.getId());
        preparedStatement.setString(2,storageFileInfo.getName());
        preparedStatement.setLong(3,storageFileInfo.getSize());
        String metaDataStr = JSON.toJSONString(storageFileInfo.getMetadate());
        preparedStatement.setString(4,metaDataStr);
        String storageNodesStr = JSON.toJSONString(storageFileInfo.getStorageNodes());
        preparedStatement.setString(5,storageNodesStr);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void deleteData(String fileId) throws SQLException {
        createConnection();
        String sql = "delete from t_file WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,fileId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static StorageFileInfo findData(String fileId) throws SQLException {
        createConnection();
        String sql = "select * from t_file where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,fileId);
        ResultSet resultSet = preparedStatement.executeQuery();
        StorageFileInfo fileInfo = new StorageFileInfo();
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            long size = resultSet.getLong("size");
            String metadateStr = resultSet.getString("metadate");
            Map metadate = JSON.parseObject(metadateStr, Map.class);
            String storageNodesStr = resultSet.getString("storageNodes");
            LinkedList<StorageFileInfoNode> storageFileInfoNodes = JSON.parseObject(storageNodesStr, new TypeReference<LinkedList<StorageFileInfoNode>>() {
            });
            fileInfo.setId(id);
            fileInfo.setName(name);
            fileInfo.setSize(size);
            fileInfo.setMetadate(metadate);
            fileInfo.setStorageNodes(storageFileInfoNodes);
        }
        preparedStatement.close();
        connection.close();
        return fileInfo;
    }

    public static void main(String[] args) throws SQLException {
        createTable();

    }


}
