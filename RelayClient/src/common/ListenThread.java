/**
 * 
 */
package common;

import java.net.DatagramSocket;

/**
 * @author Administrator
 *
 */
public class ListenThread extends Thread{
	
	private int listenPort;
	private ReceiveEvent event;

	public ListenThread(int listenPort,ReceiveEvent event) {
		this.listenPort = listenPort;
		this.event = event;
	}
	@Override
	public void run() {
		DatagramSocket ds = UDP.getUDPSocket(listenPort);
		while(true){
			try {
				event.onReceive(UDP.receive(ds));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}


