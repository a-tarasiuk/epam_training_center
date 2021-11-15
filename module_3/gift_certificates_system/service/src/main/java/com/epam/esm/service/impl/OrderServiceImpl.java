package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNonExistentException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.MessagePropertyKey;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Order service implementation.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final GiftCertificateServiceImpl gcService;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao gcDao;

    /**
     * Instantiates a new tag service.
     *
     * @param modelMapper Model mapper.
     * @param orderDao    Tag DAO layer.
     * @param userDao     User DAO layer.
     * @param gcDao       Gift certificate DAO layer.
     * @param gcService   Gift certificate service layer.
     */
    public OrderServiceImpl(ModelMapper modelMapper, GiftCertificateServiceImpl gcService, OrderDao orderDao, UserDao userDao, GiftCertificateDao gcDao) {
        this.modelMapper = modelMapper;
        this.gcService = gcService;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.gcDao = gcDao;
    }

    @Override
    public OrderDto create(OrderDto entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDto create(long userId, long giftCertificateId) {
        // Find requested User in the database
        User user = getUserOrElseThrow(userId);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Find requested Gift certificate in the database
        GiftCertificate gc = getGiftCertificateOrElseThrow(giftCertificateId);
        GiftCertificateDto gcDto = modelMapper.map(gc, GiftCertificateDto.class);

        // Create OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(userDto);
        orderDto.setGiftCertificate(gcDto);

        // Create Order entity with User and Gift certificate
        Order order = modelMapper.map(orderDto, Order.class);
        BigDecimal gcPrice = gc.getPrice();
        order.setPrice(gcPrice);
        order.setUser(user);
        order.setGiftCertificate(gc);

        return this.build(orderDao.create(order));
    }

    @Override
    public Set<OrderDto> findAll(EsmPagination esmPagination) {
        return orderDao.findAll(esmPagination, Order.class).stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> findAllByUserId(long userId, EsmPagination pagination) {
        User user = getUserOrElseThrow(userId);
        return orderDao.findAllBy(user, pagination).stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderDto findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    private User getUserOrElseThrow(long userId) {
        return userDao.findById(userId).orElseThrow(() -> new EntityNonExistentException(MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND, userId));
    }

    private GiftCertificate getGiftCertificateOrElseThrow(long gcId) {
        return gcDao.findById(gcId).orElseThrow(() -> new EntityNonExistentException(MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, gcId));
    }

    private OrderDto build(Order order) {
        GiftCertificate gc = order.getGiftCertificate();
        GiftCertificateDto gcDto = gcService.findById(gc.getId());
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setGiftCertificate(gcDto);
        return orderDto;
    }
}
