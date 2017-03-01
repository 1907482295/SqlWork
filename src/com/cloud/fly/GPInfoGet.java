package com.cloud.fly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GPInfoGet {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<SnakeInfo> list = new ArrayList<SnakeInfo>();
		//readTxtFile(Constant.PATH_GPINFO, list);

	}
	public static ArrayList<SnakeInfo> getAllSnake(){
		ArrayList<SnakeInfo> list = new ArrayList<SnakeInfo>();
		readTxtFile(Constant.PATH_SNAKEALL, list);
		return list;
	}
	public static void readTxtFile(String filePath, ArrayList<SnakeInfo> list){
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
//                        System.out.println(lineTxt);
                    	parseSingle(lineTxt,list);
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
//	public static void parse(String s){
//		if(s == null) {
//			return;
//		}
//		String[] list = s.split(" ");
//		for(int i=0;i<list.length;i++){
//			String str = list[i];
//			int n = str.indexOf('(');
//			String name = str.substring(0, n);
//			String code = str.substring(n+1, str.length()-1);
//            //System.out.println("name:"+name+";code:"+code);
//            SnakeInfo info = new SnakeInfo();
//            info.name = name;
//            info.code = code;
//            mSnakeInfos.add(info);
//		}
//	}
	public static void parseSingle(String str,ArrayList<SnakeInfo> list){
		if(str == null) {
			return;
		}
		int n = str.indexOf('(');
		String name = str.substring(0, n);
		String code = str.substring(n + 1, str.length() - 1);
		//System.out.println("name:" + name + ";code:" + code);
		SnakeInfo info = new SnakeInfo();
		info.name = name;
		info.code = code;
		list.add(info);
	}
}
