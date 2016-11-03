package com.thingword.alphonso;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.google.gson.Gson;
import com.thingword.alphonso.bean.WebSocketID;

/**
 * @ServerEndpoint ע����һ�����ε�ע�⣬���Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 * ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�WebSocket��������
 */
@ServerEndpoint("/websocket")
public class WebSocketWorker {
    //��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
    private static int onlineCount = 0;
    
    public WebSocketID ID;

    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
    public static CopyOnWriteArraySet<WebSocketWorker> webSocketSet = new CopyOnWriteArraySet<WebSocketWorker>();

    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private Session session;

    /**
     * ���ӽ����ɹ����õķ���
     * @param session  ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);     //����set��
        addOnlineCount();           //��������1
//        System.out.println("�������Ӽ��룡��ǰ�����ն�Ϊ" + getOnlineCount());
    }

    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //��set��ɾ��
        subOnlineCount();           //��������1
//        System.out.println("��һ���ӹرգ���ǰ�����ն�Ϊ" + getOnlineCount());
    }

    /**
     * �յ��ͻ�����Ϣ����õķ���
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	Gson gson = new Gson();
    	ID = gson.fromJson(message,WebSocketID.class);
//    	System.out.println(ID.getProductionline());
    }

    /**
     * ��������ʱ����
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
//        System.out.println("��������");
        error.printStackTrace();
    }

    /**
     * ������������漸��������һ����û����ע�⣬�Ǹ����Լ���Ҫ��ӵķ�����
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketWorker.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketWorker.onlineCount--;
    }
}