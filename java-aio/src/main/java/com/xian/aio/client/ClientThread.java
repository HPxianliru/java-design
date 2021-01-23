package com.xian.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;


public class ClientThread implements Runnable{
    private AsynchronousSocketChannel asc;


    public ClientThread( ) throws IOException {
        asc = AsynchronousSocketChannel.open();
    }
    public void connect(){
        asc.connect(new InetSocketAddress("127.0.0.1",8000));
    }
    public void write(String request){
        try {
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //把數據读入缓冲区中
            asc.read(buffer).get();
            //切换缓冲区的读取模式
            buffer.flip();
            //构造一个字节数组接受缓冲区中的数据
            byte[] respBytes = new byte[buffer.remaining()];
            buffer.get(respBytes);
            System.out.println("客户端接收服务器端："+new String(respBytes,"utf-8").trim());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientThread c1 = new ClientThread();
        c1.connect();
        ClientThread c2 = new ClientThread();
        c2.connect();
        ClientThread c3 = new ClientThread();
        c3.connect();

        new Thread(c1,"c1").start();
        new Thread(c2,"c2").start();
        new Thread(c3,"c3").start();

        Thread.sleep(1000);

        c1.write("c1 aaa");
        c2.write("c2 bbhb");
        c3.write("c3 cccc");
    }
}
