package com.biz.oss.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件实体，表示OSS相关的配置属性。
 * <p>该类使用Spring Boot的{@link ConfigurationProperties}注解，将配置文件中的属性映射到该实体类的字段中。</p>
 *
 * <p>配置属性的前缀为 <code>biz.oss</code>。</p>
 *
 * <pre>
 * 示例：
 * biz.oss.endpoint=http://oss-example.com
 * biz.oss.region=us-west-2
 * biz.oss.pathStyleAccess=true
 * biz.oss.accessKey=your-access-key
 * biz.oss.secretKey=your-secret-key
 * biz.oss.maxConnections=200
 * </pre>
 *
 * <p>注意：确保在配置文件中正确设置这些属性，以便OSS客户端能够正常工作。</p>
 *
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @see lombok
 * @author francis
 * @version 1.4.11
 * @since 2023-04-21
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "biz.oss")
public class OssProperties {

    /**
     * 对象存储服务的URL。
     */
    private String endpoint;

    /**
     * 区域，默认为 "1"。
     */
    private String region = "1";

    /**
     * 是否启用Path-Style访问模式。
     * <p>当设置为true时，使用Path-Style模式（例如：http://endpoint/bucketname）；当设置为false时，使用Virtual-Hosted-Style模式（例如：http://bucketname.endpoint）。</p>
     * <p>一些OSS服务提供商（如阿里云）需要配置为Virtual-Hosted-Style模式。</p>
     */
    private Boolean pathStyleAccess = true;

    /**
     * 访问密钥（Access Key）。
     */
    private String accessKey;

    /**
     * 秘密密钥（Secret Key）。
     */
    private String secretKey;

    /**
     * 最大连接数，默认为100。
     */
    private Integer maxConnections = 100;

}
