/**
 * 
 */
package A;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.Client;
import common.ListenThread;
import common.ReceiveEvent;
import common.UDP;

/**
 * @author Administrator
 *
 */
public class StartA {
	private static Map<String, SocketAddress> targetMap = new ConcurrentHashMap<String, SocketAddress>();
	public static void setTarget(String name,SocketAddress target){
		targetMap.put(name, target);
	}
	public static SocketAddress getTarget(String name){
		return targetMap.get(name);
	}
	public StartA() throws Exception {

		setTarget("S34444",new InetSocketAddress(InetAddress.getByName(Client.severHost), 34444));
		setTarget("phoneA",new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 7777));
		setTarget("S9999",new InetSocketAddress(InetAddress.getByName(Client.severHost), 9999));


		new ListenThread(23333, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=8888;
				String targetName="S34444";
				
				if(dp.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))
					return;
				if(dp.getLength()<5)
					return;
				InetSocketAddress target = (InetSocketAddress) getTarget(targetName);
				InetSocketAddress phoneA = new InetSocketAddress(dp.getAddress(), 7777);
				setTarget("phoneA", phoneA);
				if(target.getAddress().isLoopbackAddress())
					return;
				dp.setSocketAddress(target);
				UDP.asyncSend(UDP.getUDPSocket(sendPort), dp);
			}
		}).start();
		
		new ListenThread(8888, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=9898;
				String targetName="phoneA";
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
		
		new ListenThread(9898, new ReceiveEvent() {
			@Override
			public void onReceive(DatagramPacket dp) throws Exception {
				int sendPort=9999;
				String targetName="S9999";
				
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
		
		
		
		
	}


}
