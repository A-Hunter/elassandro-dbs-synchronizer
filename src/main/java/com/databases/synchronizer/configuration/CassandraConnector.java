package com.databases.synchronizer.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

/**
 * Created by Ghazi Ennacer on 14/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@Configuration
public class CassandraConnector extends AbstractCassandraConfiguration {

    static Logger LOGGER = Logger.getLogger(CassandraConnector.class.getName());

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private String port;

    @Override
    public String getKeyspaceName() {
        return keyspace;
    }

    @Override
    @Bean
    public CassandraClusterFactoryBean cluster() {
        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(contactPoints);
        cluster.setPort(Integer.parseInt(port));
        LOGGER.info("Cluster created with contact points [" + contactPoints + "] " + "& port [" + port + "].");
        return cluster;
    }

    @Override
    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}