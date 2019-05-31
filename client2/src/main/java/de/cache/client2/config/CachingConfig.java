package de.cache.client2.config;

import com.hazelcast.config.*;
import de.cache.client2.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnExpression("${app.cache.enable}")
public class CachingConfig {

    @Value(value = "${app.hazelcast}")
    private String hazelcastUrl;

    @Bean
    public Config config() {
        Config config = new Config()
                .setManagementCenterConfig(new ManagementCenterConfig(hazelcastUrl, 10).setEnabled(true))
                .addMapConfig(
                        new MapConfig()
                                .setName("persons")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(3000));
        config.getSerializationConfig().addDataSerializableFactory(1, (int id) -> (id == 1) ? new Person() : null);
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(true)
                .setMulticastGroup("224.2.2.3")
                .setMulticastPort(54327)
                .setMulticastTimeoutSeconds(2)
                .setMulticastTimeToLive(32);
        joinConfig.getTcpIpConfig().setEnabled(false);
        return config;
    }
}