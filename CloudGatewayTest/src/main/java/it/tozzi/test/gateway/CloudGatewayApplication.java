package it.tozzi.test.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
public class CloudGatewayApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(final RouteLocatorBuilder builder) {
		
		return builder.routes()

				.route("api",
						r -> r.path("/api/**").filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
								.uri("http://localhost:7091")) //
				.build();

	}

	@Component
	public class CustomizeNetty implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

		@Value("${server.max-http-header-size}")
		DataSize maxHeaderSize;
		
		public void customize(NettyReactiveWebServerFactory container) {
						
			container.addServerCustomizers(httpServer -> httpServer
					.httpRequestDecoder(httpRequestDecoderSpec -> httpRequestDecoderSpec.maxHeaderSize((int) maxHeaderSize.toBytes())));

		}
	}

}
