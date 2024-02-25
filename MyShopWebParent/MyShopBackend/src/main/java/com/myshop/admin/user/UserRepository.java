package com.myshop.admin.user;

import com.myshop.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Integer> {


    @Query("update User set enabled = ?2 where id = ?1")
    @Modifying
    void updateStatus(Integer id,boolean status);


    @Query("select u from User u where concat(u.id,' ',u.email,' ',u.firstName,' ',u.lastName,' ') like %?1%")
    Page<User> search(String keyword,Pageable pageable);


//    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);
}
