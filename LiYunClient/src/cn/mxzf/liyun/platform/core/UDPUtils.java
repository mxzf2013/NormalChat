package cn.mxzf.liyun.platform.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPUtils {
	private static final String TAG = UDPUtils.class.getSimpleName();

	public static boolean send(byte[] soServerBytes, int length) {
		DatagramPacket packet = new DatagramPacket(soServerBytes, length);
		DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance()
				.getLocalUDPSocket();
		if ((localUDPSocket == null) || (localUDPSocket.isClosed()))
			return false;
		try {
			localUDPSocket.send(packet);
			return true;
		} catch (IOException e) {
			System.err.println(TAG + ": " + e.getMessage());
			return false;
		}
	}
}
