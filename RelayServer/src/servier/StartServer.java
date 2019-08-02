/**
 * 
 */
package servier;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.ListenThread;
import common.ReceiveEvent;
import common.UDP;


/**
 * @author Administrator
 *
 */
public class StartServer {
	private static Map<String, SocketAddress> targetMap = new ConcurrentHashMap<String, SocketAddress>();
	public static void setTarget(String name,SocketAddress target){
		targetMap.put(name, target);
	}
	public static SocketAddress getTarget(String name){
		return targetMap.get(name);
	}
	public StartServer() {
		setTarget("B34444",new InetSocketAddress("127.0.0.1",0));
		setTarget("A8888",new InetSocketAddress("127.0.0.1",0));
		setTarget("B9999",new InetSocketAddress("127.0.0.1",0));
		
		new ListenThread(34444, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=34444;
				String targetName="B34444";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<2){
					setTarget("B34444",dp.getSocketAddress());
					return;
				}
					

				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				if(target.getAddress().isLoopbackAddress())
					return;
				
				setTarget("A8888", dp.getSocketAddress());
				
				dp.setSocketAddress(target);
				
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
				
			}
		}).start();
		
		
		new ListenThread(8888, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=34444;
				String targetName="A8888";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<5)
					return;
				
				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				if(target.getAddress().isLoopbackAddress())
					return;
				
				setTarget("B9999", dp.getSocketAddress());
				
				dp.setSocketAddress(target);
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
				
			}
		}).start();
		
		new ListenThread(9999, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=8888;
				String targetName="B9999";
				
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
		
	}
}
