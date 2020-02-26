package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * @Author: zhangsh
 * @Date: 2020/2/26 15:52
 * @Version 1.0
 * Description 消费者1
 */
public class Consumer1 {
    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取mq连接
        Connection connection = ConnectionUtil.getContection();
        //创建channel信道
        Channel channel = connection.createChannel();

        channel.queueDeclare(
                QUEUE_NAME,//队列名称
                false,//是否需要持久化
                false,//是否为排他连接
                false,//是否自动删除
                null);//其他参数

        //定义消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //消费监听对应的队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received 1'" + message + "'");
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
