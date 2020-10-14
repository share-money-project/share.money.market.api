package share.money.market.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SpringRabbitConfig {

    public static final String EXCHANGE_TICKET = "ticket";
    public static final String QUEUE_TICKET_CREATE = "ticket_create_q";
    public static final String QUEUE_TICKET_RESERVE = "ticket_reserve_q";

    public static final String KEY_TICKET_CREATE = "ticket_create_k";
    public static final String KEY_TICKET_RESERVE = "ticket_reserve_k";
//
//    @Bean
//    public Queue ticketCreatedQueue() {
//        boolean durable = true;
//        return new Queue(QUEUE_TICKET_CREATE, durable);
//    }
//
//    @Bean
//    public Queue ticketReservedQueue() {
//        boolean durable = true;
//        return new Queue(QUEUE_TICKET_RESERVE, durable);
//    }
//
//    @Bean
//    public Exchange ticketDirectExchange() {
//        boolean durable = true;
//        boolean autoDelete = false;
//        return new DirectExchange(EXCHANGE_TICKET, durable, autoDelete);
//    }
//
//    @Bean
//    public Binding ticketCreatedQueueBinding() {
//        Map<String, Object> arguments = null;
//        return new Binding(QUEUE_TICKET_CREATE, Binding.DestinationType.QUEUE, EXCHANGE_TICKET, KEY_TICKET_CREATE, arguments);
//    }
//
//    @Bean
//    public Binding ticketReservedQueueBinding() {
//        Map<String, Object> arguments = null;
//        return new Binding(QUEUE_TICKET_RESERVE, Binding.DestinationType.QUEUE, EXCHANGE_TICKET, KEY_TICKET_RESERVE, arguments);
//    }
}