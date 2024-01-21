package com.example.iot;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class Recieve {

    private MqttClient client;

    public Recieve(MqttClient client) {
        this.client = client;
    }
}

