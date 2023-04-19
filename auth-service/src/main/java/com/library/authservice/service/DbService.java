package com.library.authservice.service;

import com.library.authservice.model.Role;
import com.library.authservice.model.User;
import com.library.authservice.repository.RoleRepository;
import com.library.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
   @Autowired
   private UserRepository user;
   @Autowired
   private RoleRepository role;

   @Autowired
   private UserService userService;
   public void dbinit(){

               Role adm = new Role(null, "ADMIN");
               Role emp = new Role(null, "EMPLOYEE");
               Role cos = new Role(null, "CUSTOMER");

               role.save(adm);
               role.save(emp);
               role.save(cos);

               userService.saveUser(new User(null, "Admin", "admin@hotmail.com", "123456", List.of(adm,emp)));


//        userService.saveUser(new User(null, "Admin", "admin@hotmail.com", "123456", new ArrayList<>()));
//        userService.saveUser(new User(null, "Juliano Teste", "teste@teste.com", "123456", new ArrayList<>()));
//        userService.saveUser(new User(null, "Juliano Vieira", "proprietyofkurd@hotmail.com", "123456", new ArrayList<>()));

//

//           userService.addRoleToUser("proprietyofkurd@hotmail.com", "ADMIN");
//		    userService.addRoleToUser("abcdddd@atos.net", "ADMIN2");
//			userService.addRoleToUser("juliano.vieira@atos.net", "ADMIN");

//        userService.addRoleToUser("proprietyofkurd@hotmail.com", "ADMINNNNN");
//			userService.addRoleToUser("proprietyofkurd@hotmail.com", "EMPLOYEE");
//        userService.addRoleToUser("proprietyofkurd@hotmail.com", "EMPLOYEE");
//        userService.addRoleToUser("proprietyofkurd@hotmail.com", "CUSTOMER");

   }
}
