/**
 * 
 */
package B;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.ListenThread;
import common.ReceiveEvent;
import common.UDP;
import main.Client;


/**
 * @author Administrator
 *
 */
public class StartB {
	private static Map<String, SocketAddress> targetMap = new ConcurrentHashMap<String, SocketAddress>();
	public static void setTarget(String name,SocketAddress target){
		targetMap.put(name, target);
	}
	public static SocketAddress getTarget(String name){
		return targetMap.get(name);
	}
	public StartB() throws Exception {
		setTarget("B23333",new InetSocketAddress(InetAddress.getByName("255.255.255.255"), 23333));
		setTarget("B8888",new InetSocketAddress(InetAddress.getByName(Client.severHost), 8888));
		setTarget("phoneB",new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0));
		
		new ListenThread(34444, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=34444;
				String targetName="B23333";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<5)
					return;
				
				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				if(target.getAddress().isLoopbackAddress())
					return;
				
				dp.setSocketAddress(target);
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
			}
		}).start();
		
		new ListenThread(7777, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=9999;
				String targetName="B8888";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<3)
					return;
				
				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				if(target.getAddress().isLoopbackAddress())
					return;
				
				setTarget("phoneB", dp.getSocketAddress());
			
				dp.setSocketAddress(target);
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
			}
		}).start();
		
		new ListenThread(9999, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=7777;
				String targetName="phoneB";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<3)
					return;
				
				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				if(target.getAddress().isLoopbackAddress())
					return;
				
				dp.setSocketAddress(target);
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try {
						DatagramSocket ds= new DatagramSocket(null);
						ds.setReuseAddress(true);
						ds.bind(new InetSocketAddress(34444));
						ds.send(new DatagramPacket(new byte[1], 1,InetAddress.getByName(Client.severHost),34444));
						ds.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
}
