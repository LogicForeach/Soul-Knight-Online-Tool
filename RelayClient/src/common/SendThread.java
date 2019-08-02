package common;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class SendThread implements Runnable {
	private DatagramPacket dp;
	private DatagramSocket socket;
	
	
	
	public SendThread(DatagramSocket socket,DatagramPacket dp) {
		this.socket=socket;
		this.dp=dp;
		
	}
	@Override
	public void run() {
			InetAddress targetAddress = dp.getAddress();
			if(targetAddress!=null&&!targetAddress.isLoopbackAddress())
				UDP.send(socket, dp);
		
	}

}
