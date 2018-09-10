package andre_filus.com.br.cinq.data;

import android.content.Context;

import java.util.ArrayList;

import andre_filus.com.br.cinq.data.local.UserRepository;
import andre_filus.com.br.cinq.models.User;

/**
 * Created by André Filus on 09/09/2018.
 */

public class Business {

    private UserRepository userRepository;

    public Business(Context context){
        this.userRepository = UserRepository.getInstance(context);
    }

    public Boolean insert(User user){
        return this.userRepository.insert(user);
    }

    public Boolean update(User user){
        return this.userRepository.update(user);
    }

    public Boolean delete(User user){
        return this.userRepository.delete(user);
    }

    public Boolean verifyIfUserExists(String email){
        return this.userRepository.verifyIfUserExists(email);
    }

    public ArrayList<User> getUsers(){
        return this.userRepository.getAllUsers();
    }

    public User getUserByLogin(String email, String password){
        return this.userRepository.getUserByQuery(email, password);
    }

}
