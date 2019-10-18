package com.zhc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProtobufTests {



public static void main(String arg[]){

    //java代理
    



}



//    public static void main(String arg[]) throws IOException {
//        Socket socket = new Socket();
//        InetSocketAddress isa= new InetSocketAddress("10.10.2.179", 12345);
//        socket.connect(isa);
//        InputStream bis = socket.getInputStream();
//        OutputStream bos = socket.getOutputStream();
//
//        byte[] binHead = new byte[100];
//        int read_len = 0;
//        while((read_len = bis.read(binHead, 0, 100)) != -1){
//            for(byte b:binHead){
//                System.out.print(b+"-");
//            }
//        }
//
//    }









//    public static void main(String arg[]) throws IOException {
//        Socket socket = new Socket();
//        InetSocketAddress isa= new InetSocketAddress("10.10.2.179", 12345);
//        socket.connect(isa);
//        socket.sendUrgentData(0);
//
//        InputStream bis = socket.getInputStream();
//        OutputStream bos = socket.getOutputStream();
//        byte[] binHead = new byte[20];
//        int read_len = 0;
//
//        ProtobufProtocol.DATA.Builder builder= ProtobufProtocol.DATA.newBuilder();
//        builder.setPid(123456);
//        builder.setMsg("你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界," +
//                "你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界" +
//                "你好世界,你好世界你好世界,你好世界你好世界,你好世界你好世界,你好世界"
//               );
//        ProtobufProtocol.DATA build = builder.build();
//        byte[] bytes = build.toByteArray();
//
//
//        byte[] length = new byte[4];
//        to_4_byte(length,0,bytes.length);
//
//
//        byte[] datas=new byte[bytes.length+length.length];
//        System.arraycopy(length,0,datas,0,length.length);
//        System.arraycopy(bytes,0,datas,length.length,bytes.length);
//
//        System.out.println(length.length+"*************"+bytes.length+"************"+datas.length);
//        System.out.println("发送的数据");
//
//        for(byte b:datas){
//            System.out.println(b);
//        }
//
//        for(int i=0;i<10000;i++){
//            System.out.println("发送第"+i+"次");
//            bos.write(datas);
//            bos.flush();
//        }
//
//
//        while((read_len = bis.read(binHead, 0, 20)) != -1){
//
//            if (read_len == 0) {
//               System.out.println("有毛病了");
//                return;
//            }
//            System.out.println("接收到的数据");
//            for(byte b:binHead){
//                System.out.println(b);
//            }
//
//        }
//
//
//
//
//
//
//    }

    public static byte[] toByteByInt(int value) {
        byte[] b=null;
        if(value<=127&&value>0){
            b=new byte[1];
            b[0] = (byte) value;
        }else if(value<=32767&&value>127){
            b=new byte[2];
            b[0] = (byte) (value >> 8);
            b[1] = (byte) value;
        }
        return b;
    }

    public static void to_4_byte(byte[] b, int offset, int value) {
        b[offset] = (byte) (value >> 24 & 0xff);
        b[offset + 1] = (byte) (value >> 16 & 0xff);
        b[offset + 2] = (byte) (value >> 8 & 0xff);
        b[offset + 3] = (byte) (value & 0xff);
    }

    public static void to_2_byte(byte[] b, int offset, int value) {
        b[offset] = (byte) (value >> 8);
        b[offset + 1] = (byte) value;
    }


//    public static void main(String arg[]) throws InterruptedException {
//        EventLoopGroup loop = new NioEventLoopGroup();
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(loop);
//        bootstrap.channel(NioSocketChannel.class);
//        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            public void initChannel(SocketChannel socketChannel) throws Exception {
//                ChannelPipeline pipeline = socketChannel.pipeline();
//                pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
//                pipeline.addLast(new ProtobufVarint32FrameDecoder());
//                pipeline.addLast(new ProtobufDecoder(ProtobufProtocol.DATA.getDefaultInstance()));
//
//                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//                pipeline.addLast(new ProtobufEncoder());
//                pipeline.addLast(new ClientHandler());
//            }
//        });
//
//
//        bootstrap.remoteAddress("127.0.0.1", 12345);
//        ChannelFuture f = bootstrap.connect();
//
//        f.channel().closeFuture().sync();
//        loop.shutdownGracefully();
//    }




}
