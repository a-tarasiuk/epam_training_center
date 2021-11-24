package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNonExistentException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_ORDER_FOR_USER_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_ORDER_ID_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;

/**
 * Order service implementation.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final UserDao userDao;
    private final GiftCertificateDao certificateDao;
    private final PageMapper pageMapper;

    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, UserDao userDao,
                            GiftCertificateDao certificateDao, PageMapper pageMapper) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
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

        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public Page<OrderDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<Order> orders = orderRepository.findAll(pageable);
        return pageMapper.map(orders, OrderDto.class);
    }

    @Override
    public Page<OrderDto> findAllByUserId(long userId, EsmPagination pagination) {
        User user = getUserOrElseThrow(userId);
        Pageable pageable = pageMapper.map(pagination);
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        return pageMapper.map(orders, OrderDto.class);
    }

    @Override
    public OrderDto findOrderForUser(long orderId, long userId) {
        User user = getUserOrElseThrow(userId);
        Order order = getOrderOrElseThrow(orderId);

        if (Objects.equals(user, order.getUser())) {
            return modelMapper.map(order, OrderDto.class);
        } else {
            throw new EntityNonExistentException(EXCEPTION_ORDER_FOR_USER_NOT_FOUND, userId);
        }
    }

    @Override
    public OrderDto findById(long id) {
        Order order = getOrderOrElseThrow(id);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    private User getUserOrElseThrow(long userId) {
        return userDao.findById(userId).orElseThrow(() -> new EntityNonExistentException(EXCEPTION_USER_ID_NOT_FOUND, userId));
    }

    private Order getOrderOrElseThrow(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNonExistentException(EXCEPTION_ORDER_ID_NOT_FOUND, orderId));
    }

    private GiftCertificate getGiftCertificateOrElseThrow(long gcId) {
        return certificateDao.findById(gcId).orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, gcId));
    }
}
