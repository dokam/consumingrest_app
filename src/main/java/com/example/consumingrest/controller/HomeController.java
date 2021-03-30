package com.example.consumingrest.controller;

import com.example.consumingrest.core.UserDetails;
import com.example.consumingrest.process.UserInfo;
import com.example.consumingrest.process.UsersAllJson;
import com.example.consumingrest.repository.UserDetailsRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class HomeController {
    private static HttpURLConnection con;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @RequestMapping(value="/json", method=RequestMethod.GET)
    @ResponseBody
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) throws IOException {

        String url = "https://reqres.in/api/users";

        StringBuilder content;
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {

                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

        } finally {

            con.disconnect();
        }

        String data = content.toString();
        UsersAllJson msg = new Gson().fromJson(data, UsersAllJson.class);

        System.out.println( msg.getData().size());

        for (UserInfo user : msg.getData()){
            UserDetails newUser = new UserDetails();
            newUser.setId(user.getId());
            newUser.setFirst_name(user.first_name);
            newUser.setLastName(user.last_name);
            newUser.setEmail(user.getEmail());
            newUser.setAvatar(user.getAvatar());
            userDetailsRepository.save(newUser);
        }

        return content.toString();
    }


}
