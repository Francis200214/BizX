package com.biz.oss.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.biz.oss.properties.OssProperties;
import com.biz.oss.template.AmazonS3TemplateImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 自动配置类。
 * <p>根据配置文件中的属性自动配置 Amazon S3 客户端和模板。</p>
 *
 * <p>该配置类在配置文件中存在 <code>biz.oss.endpoint</code>、<code>biz.oss.accessKey</code> 和 <code>biz.oss.secretKey</code> 属性时才会被加载。</p>
 *
 * @see AmazonS3
 * @see OssProperties
 * @see AmazonS3TemplateImpl
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
@Configuration
@ConditionalOnProperty(prefix = "biz.oss", name = {"endpoint", "accessKey", "secretKey"})
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    /**
     * 配置 Amazon S3 客户端。
     *
     * <p>根据 {@link OssProperties} 中的配置创建 Amazon S3 客户端实例。</p>
     *
     * @param ossProperties OSS 配置属性
     * @return AmazonS3 客户端实例
     * @see ClientConfiguration
     * @see AwsClientBuilder.EndpointConfiguration
     * @see BasicAWSCredentials
     */
    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 ossClient(OssProperties ossProperties) {
        // 客户端配置，主要是全局的配置信息
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(ossProperties.getMaxConnections());

        // URL以及Region配置
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                ossProperties.getEndpoint(), ossProperties.getRegion());

        // 凭证配置
        AWSCredentials awsCredentials = new BasicAWSCredentials(ossProperties.getAccessKey(),
                ossProperties.getSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        // 构建 AmazonS3Client 客户端
        return AmazonS3Client.builder().withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration).withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding().withPathStyleAccessEnabled(ossProperties.getPathStyleAccess()).build();
    }

    /**
     * 配置 AmazonS3TemplateImpl 模板。
     *
     * <p>该模板在 {@link AmazonS3} 客户端存在时才会被创建。</p>
     *
     * @param amazonS3 Amazon S3 客户端实例
     * @return AmazonS3TemplateImpl 模板实例
     * @see AmazonS3TemplateImpl
     */
    @Bean
    @ConditionalOnBean(AmazonS3.class)
    public AmazonS3TemplateImpl ossTemplate(AmazonS3 amazonS3) {
        return new AmazonS3TemplateImpl(amazonS3);
    }
}
