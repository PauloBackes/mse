package com.unisinos.mse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public void mongoDatabase() {

       /* ConnectionString connectionString = new ConnectionString("mongodb+srv://mse:mse@cluster0.qdcyn.mongodb.net/test?authSource=admin&authMechanism=SCRAM-SHA-1");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
       MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        return database;*/



        /*String user = "teste";// the user name
        String database = "test"; // the name of the database in which the user is defined
        char[] password = "31bTCPG6o0VBCt8t".toCharArray();

        MongoCredential credential = MongoCredential.createCredential(user, database, password);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("cluster0-shard-00-00.qdcyn.mongodb.net", 27017),
                        new ServerAddress("cluster0-shard-00-01.qdcyn.mongodb.net", 27017),
                        new ServerAddress("cluster0-shard-00-02.qdcyn.mongodb.net", 27017))))
                .applyToSslSettings(builder -> {builder.enabled(true); builder.invalidHostNameAllowed(false);
                })

                .credential(credential)
                .build();

        MongoDriverInformation m = MongoDriverInformation.builder().driverVersion("4.2.3")
                .driverName("mongodb-driver-sync")
                .build();
        MongoClient mongoClient = new MongoClientImpl(settings, m);
        return mongoClient;*/


        //"mongodb+srv://mse:<password>@cluster0.qdcyn.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"
        //MongoClient mongoClient = MongoClients.create("mongodb+srv://mse:mse@cluster0.qdcyn.mongodb.net/?authSource=test&ssl=true");
        /*MongoClient mongoClient = MongoClients.create("mongodb+srv://mse:mse@cluster0.qdcyn.mongodb.net/?authSource=test&sslInvalidHostNameAllowed=true");
        return mongoClient;*/

       /* String user = "mse";// the user name
        String database = "test"; // the name of the database in which the user is defined
        char[] password = "mse".toCharArray();

        MongoCredential credential = MongoCredential.createCredential(user, database, password);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("cluster0-shard-00-00.qdcyn.mongodb.net", 27017),
                        new ServerAddress("cluster0-shard-00-01.qdcyn.mongodb.net", 27017),
                        new ServerAddress("cluster0-shard-00-02.qdcyn.mongodb.net", 27017))))
                .applyToSslSettings(builder -> {builder.enabled(true); builder.invalidHostNameAllowed(false);
                })

                .credential(credential)
                .build();

        return MongoClients.create(settings);*/


    }
}
