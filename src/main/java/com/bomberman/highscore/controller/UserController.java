package com.bomberman.highscore.controller;

import com.bomberman.highscore.entity.User;
import com.bomberman.highscore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "All Operations with the User Database", tags = "User")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success!"),
        @ApiResponse(code = 201, message = "Success! It's created."),
        @ApiResponse(code = 204, message = "Success! - no content"),
        @ApiResponse(code = 401, message = "You are Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
})
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get all Users from the Database")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> allUsers(){
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get one User from the Database")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable long userId){
        try{
            return new ResponseEntity<User>(userService.getOneUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Add one User to the Database")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(HttpServletRequest request, @RequestBody User user){
        try{
            if (user != null) {
                return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update one User in the Database")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable long userId, @RequestBody User user){
        try{
            if (user != null) {
                return new ResponseEntity<User>(userService.updateUser(user, userId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete one User from the Database")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable long userId){
        try{
            return new ResponseEntity<User>(userService.deleteUserById(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", this.userService.getAllUsers());
        return "users";
    }*/

    /*@PostMapping("/users")
    public User create(@RequestBody Map<String, String> body){
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        return userService.saveOrUpdateUser(new User(firstName, lastName));
    }*/
}