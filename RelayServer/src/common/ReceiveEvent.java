/**
 * 
 */
package common;

import java.net.DatagramPacket;

/**
 * @author Administrator
 *
 */
public abstract class ReceiveEvent {
	public abstract void  onReceive(DatagramPacket dp) throws Exception;
}
