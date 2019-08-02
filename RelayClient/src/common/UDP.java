/**
 * 
 */
package common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Administrator
 *
 */
public class UDP {
	protected static ExecutorService  pool =  Executors.newFixedThreadPool(10);
	private static int DATA_SIZE=1500;
	public static DatagramSocket getUDPSocket(int bindPort){
		try {
			DatagramSocket ds= new DatagramSocket(null);
			ds.setReuseAddress(true);
			ds.bind(new InetSocketAddress(bindPort));
			return ds;
		} catch (SocketException e) {
			System.out.println(String.valueOf(bindPort)+":");
			e.printStackTrace();
		}
		return null;
	
	}
	public static void send(DatagramSocket socket,DatagramPacket dp){
		try {
			socket.send(dp);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void asyncSend(DatagramSocket socket,DatagramPacket dp){
		pool.submit(new SendThread(socket,dp));
	}
	public static DatagramPacket receive(DatagramSocket socket){
		byte[] data = new byte[DATA_SIZE];
		DatagramPacket dp = new DatagramPacket(data, DATA_SIZE);
		try {
			socket.receive(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dp;
	}
}
