package acc.br.summerAcademy.consumer;

import acc.br.summerAcademy.service.PaymentService;
import acc.br.summerAcademy.domain.TypeOfStatus;
import acc.br.summerAcademy.domain.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentService paymentService;

    public PaymentProcessor(RabbitTemplate rabbitTemplate, PaymentService paymentService) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "orders.v1.order-status.updated")
    public void processOrder(Order order) {
        System.out.println("Recebendo pedido para processamento: " + order);

        paymentService.simulatePayment(order);

        order.setStatus(TypeOfStatus.DELIVERED);

        System.out.println("Pedido processado e atualizado: " + order);
        System.out.println(order.getStatus() + "!");


        rabbitTemplate.convertAndSend(
                "order.exchange",
                "order.created",
                order
        );
    }
}