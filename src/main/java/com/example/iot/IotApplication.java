package com.example.iot;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotApplication {

    public static void main(String[] args) {
        String topic        = "kpi/meridian/temperature/5/set";
        String topic2        = "kpi/meridian/metrics/test";
        String content      = "{\"test\": 121}";
        int qos             = 0;
        String broker       = "tcp://147.232.205.204:1883";
        String clientId     = "tester";
        char[] pass = "mother.mqtt.password".toCharArray();
        String userName = "maker";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setPassword(pass);
            connOpts.setUserName(userName);
            System.out.println("Connecting to broker: "+broker);

            sampleClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {}

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message: " + message.toString());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            sampleClient.subscribeWithResponse(topic2, 0);
            //System.out.println("Message published");
            //sampleClient.disconnect();
            //System.out.println("Disconnected");
            //System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
