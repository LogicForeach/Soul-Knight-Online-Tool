package main;
import java.util.Scanner;

import A.StartA;
import B.StartB;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class Client {
	public static String severHost="127.0.0.1";
	public static void main(String[] args) throws Exception {
		System.out.println("请输入服务器地址:");
		Scanner sc = new Scanner(System.in);
		severHost=sc.nextLine();
		System.out.println("请输入操作序号:");
		System.out.println("1.创建房间");
		System.out.println("2.加入房间");
		System.out.print("操作序号:");
		
		int o = sc.nextInt();
		if(o==1){
			new StartA();
		}else{
			new StartB();
		}
		sc.close();
		System.out.println("开始游戏");


	}
	




}

