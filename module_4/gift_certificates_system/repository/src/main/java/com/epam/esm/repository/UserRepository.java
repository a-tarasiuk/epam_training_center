package com.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.pojo.UserInformation;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findById(long id);

    @Query("SELECT o.user, SUM(o.price) AS sumOfAllOrders FROM Order o GROUP BY o.user ORDER BY SUM(o.price) DESC")
    Set<UserInformation> findUsersWithHighestCostOfAllOrders();
}
