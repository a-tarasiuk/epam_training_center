package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.OrderShortInformationDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.PageMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ORDER_FOR_USER_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ORDER_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;

/**
 * Order service implementation.
 */
@Service
@Transactional
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GiftCertificateRepository certificateRepository,
                            ModelMapper modelMapper, PageMapper pageMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public OrderDto create(long userId, long giftCertificateId) {
        // Find requested User in the database
        User user = getUserOrElseThrow(userId);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Find requested Gift certificate in the database
        GiftCertificate certificate = getGiftCertificateOrElseThrow(giftCertificateId);
        GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);

        // Create OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(userDto);
        orderDto.setGiftCertificate(certificateDto);

        // Create Order entity with User and Gift certificate
        Order order = modelMapper.map(orderDto, Order.class);
        BigDecimal gcPrice = certificate.getPrice();
        order.setPrice(gcPrice);
        order.setUser(user);
        order.setGiftCertificate(certificate);

        log.info("Order for user with ID {} and gift certificate with ID {} successfully created.", userId, giftCertificateId);
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public Page<OrderDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<Order> orders = orderRepository.findAll(pageable);

        log.info("Total orders {} found.", orders.getTotalElements());
        return pageMapper.map(orders, OrderDto.class);
    }

    @Override
    public Page<OrderDto> findAllByUserId(long userId, EsmPagination pagination) {
        User user = getUserOrElseThrow(userId);
        Pageable pageable = pageMapper.map(pagination);
        Page<Order> orders = orderRepository.findAllByUserId(userId, pageable);

        log.info("Total orders {} found by user with ID {}.", orders.getTotalElements(), userId);
        return pageMapper.map(orders, OrderDto.class);
    }

    @Override
    public OrderShortInformationDto findByOrderIdAndUserId(long orderId, long userId) {
        User user = getUserOrElseThrow(userId);
        Order order = getOrderOrElseThrow(orderId);

        if (Objects.equals(user, order.getUser())) {
            log.info("{} found by order ID {} and user ID {}.", order, orderId, userId);
            return modelMapper.map(order, OrderShortInformationDto.class);
        } else {
            throw new EntityNotFoundException(EXCEPTION_ORDER_FOR_USER_NOT_FOUND, userId);
        }
    }

    @Override
    public OrderDto findById(long id) {
        Order order = getOrderOrElseThrow(id);

        log.info("{} found by order ID {}.", order, id);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    private User getUserOrElseThrow(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(EXCEPTION_USER_ID_NOT_FOUND, userId));
    }

    private Order getOrderOrElseThrow(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(EXCEPTION_ORDER_ID_NOT_FOUND, orderId));
    }

    private GiftCertificate getGiftCertificateOrElseThrow(long gcId) {
        return certificateRepository.findById(gcId).orElseThrow(() -> new EntityNotFoundException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, gcId));
    }
}
