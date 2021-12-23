package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.repository.util.CriteriaQueryGenerator;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.repository.util.ParameterName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gift certificate DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateDaoImpl extends GiftCertificateDao {
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateToTagRelationDaoImpl relationDao) {
        this.relationDao = relationDao;
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return em.merge(certificate);
    }

    @Override
    public Set<GiftCertificate> findAll(EsmPagination pagination, GiftCertificateSearchParameter parameter) {
        CriteriaQueryGenerator<GiftCertificate> criteriaQueryGenerator = new CriteriaQueryGenerator<>(em, GiftCertificate.class);
        CriteriaQuery<GiftCertificate> cq = criteriaQueryGenerator.generate(parameter);
        return executeQuery(cq, pagination);
    }


    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> optionalGc;

        try {
            CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
            Root<GiftCertificate> fromGc = cq.from(GiftCertificate.class);
            Predicate condition = cb.equal(fromGc.get(ParameterName.NAME), name);
            cq.select(fromGc).where(condition);

            GiftCertificate gc = em.createQuery(cq)
                    .getSingleResult();

            optionalGc = Optional.of(gc);
        } catch (NoResultException e) {
            optionalGc = Optional.empty();
        }

        return optionalGc;
    }

    @Override
    public Set<GiftCertificate> findBy(Tag tag) {
        return relationDao.findAllBy(tag).stream()
                .map(GiftCertificateToTagRelation::getGiftCertificate)
                .collect(Collectors.toSet());
    }

    /**
     * Full JPA query:<br>
     * SELECT gc FROM GiftCertificate gc JOIN Order o ON gc = o.giftCertificate WHERE o.user = :user
     */
    @Override
    public List<GiftCertificate> findBy(User user) {
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<Order> from = cq.from(Order.class);
        Predicate condition = cb.equal(from.get(ParameterName.USER), user);
        cq.select(from.get(ParameterName.GIFT_CERTIFICATE)).where(condition);

        return em.createQuery(cq)
                .getResultStream()
                .collect(Collectors.toList());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        return em.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
