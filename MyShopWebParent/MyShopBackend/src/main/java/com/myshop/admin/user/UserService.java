package com.myshop.admin.user;

import com.myshop.common.entity.User;
import com.myshop.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE =4;
    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public Page<User> listByPage(int pageNum,String sortField,String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1,USERS_PER_PAGE,sort);
        if(keyword!=null) {
            return userRepository.search(keyword,pageable);
        }
        return userRepository.findAll(pageable);
    }








    public User saveUser(User user) {
        if(user.getId()!= null) {
            User oldUser = userRepository.findById(user.getId()).get();
            if(user.getPassword().isEmpty()) {
                user.setPassword(oldUser.getPassword());
            }else {
                encodePassword(user);
            }
        }else {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        try {
            get(id);
            userRepository.deleteById(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Couldn't find any user with id = " + id);
        }
    }
    public User get(Integer id) throws UserNotFoundException {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()) {
            return user.get() ;

        }else {
            throw new UserNotFoundException("Couldn't find any user with id = " + id);
        }
    }

    public void updateStatus(Integer id,boolean status) throws UserNotFoundException {
            get(id);
        userRepository.updateStatus(id,status);


    }

    public boolean isUniqueEmail(Integer id,String email) {
        User userByEmail = userRepository.findByEmail(email);
        if(userByEmail== null) {
            return true;
        }
        boolean isCreatingNew = (id==null) ;
        //currently, user is not null
        if(isCreatingNew) {
            return false;
        }else {
            if(userByEmail.getId()!=id) {
                return false;
            }
        }
        return true;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveAccount(User user) {
        User oldUser = userRepository.findById(user.getId()).get();
        if(user.getPassword().isEmpty()) {
            System.out.println(oldUser.getPassword());
            user.setPassword(oldUser.getPassword());
        }else {
            encodePassword(user);
        }



        user.setRoles(oldUser.getRoles());
        user.setEnabled(true);

        return userRepository.save(user);
    }

}
