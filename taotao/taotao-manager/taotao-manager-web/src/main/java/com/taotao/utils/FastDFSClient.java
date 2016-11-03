package com.taotao.utils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient storageClient = null;
	
	public FastDFSClient(String conf) throws Exception{
		if(conf.contains("classpath")){
			conf =conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		//ClientGlobal.init("F:\\taotao\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	public String uploadFile(String fileName,String extName) throws Exception{
		String[] strings = storageClient.upload_file(fileName, extName, null);
		//storageClient.upload_file()
		String result = strings[0]+"/"+strings[1];
		return result;
	}

	 
	public String uploadFile(byte[] bytes,String extName) throws Exception{
		
		String[] strings = storageClient.upload_file(bytes,extName,null);
		String result = strings[0]+"/"+strings[1];
		return result;
	}
}
